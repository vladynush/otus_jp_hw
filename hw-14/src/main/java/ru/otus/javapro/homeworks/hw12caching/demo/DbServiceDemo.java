package ru.otus.javapro.homeworks.hw12caching.demo;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.javapro.homeworks.hw12caching.core.cache.CacheManager;
import ru.otus.javapro.homeworks.hw12caching.core.cache.SoftReferenceCacheManager;
import ru.otus.javapro.homeworks.hw12caching.core.repository.DataTemplateHibernate;
import ru.otus.javapro.homeworks.hw12caching.core.repository.HibernateUtils;
import ru.otus.javapro.homeworks.hw12caching.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.javapro.homeworks.hw12caching.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.javapro.homeworks.hw12caching.crm.model.Address;
import ru.otus.javapro.homeworks.hw12caching.crm.model.Client;
import ru.otus.javapro.homeworks.hw12caching.crm.model.Phone;
import ru.otus.javapro.homeworks.hw12caching.crm.service.DBServiceClient;
import ru.otus.javapro.homeworks.hw12caching.crm.service.DbServiceClientImpl;

import java.util.ArrayList;
import java.util.List;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    public static final int TOTAL_ITEMS_COUNT_TO_CREATE = 100000;

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);

        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        CacheManager<Long, Client> cacheManager = new SoftReferenceCacheManager<>();
        DBServiceClient serviceClient = new DbServiceClientImpl(transactionManager, clientTemplate, cacheManager);
        List<Long> preparedData = prepareData(serviceClient);

        var performDurationWithCache = performProcessing(preparedData, serviceClient);
        cacheManager.invalidate();
        var performDuration = performProcessing(preparedData, serviceClient);

        log.info("Processing %s records without cache in %s msec.".formatted(TOTAL_ITEMS_COUNT_TO_CREATE, performDuration));
        log.info("Processing %s records with cache in %s msec.".formatted(TOTAL_ITEMS_COUNT_TO_CREATE, performDurationWithCache));

    }

    public static List<Long> prepareData(DBServiceClient serviceClient) {
        List<Long> ids = new ArrayList<>();
            for (int i = 0; i < TOTAL_ITEMS_COUNT_TO_CREATE; i++) {
                var newClient = serviceClient.saveClient(new Client("Client_%s".formatted(i)));
                ids.add(newClient.getId());
            }
            return ids;
    }

    private static long performProcessing(List<Long> preparedData, DBServiceClient serviceClient) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < TOTAL_ITEMS_COUNT_TO_CREATE; i++) {
            final Long id = preparedData.get(i);
            var gettedClient = serviceClient.getClient(id)
                    .orElseThrow(() -> new RuntimeException("Client not found, id:" + id));
            gettedClient.setName(gettedClient.getName() + "_updated");
        }
        return System.currentTimeMillis() - startTime;
    }

}
