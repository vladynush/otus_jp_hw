package ru.otus.javapro.homeworks.hw12caching.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.javapro.homeworks.hw12caching.core.cache.CacheManager;
import ru.otus.javapro.homeworks.hw12caching.core.repository.DataTemplate;
import ru.otus.javapro.homeworks.hw12caching.core.sessionmanager.TransactionManager;
import ru.otus.javapro.homeworks.hw12caching.crm.model.Client;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;

    private final CacheManager<Long, Client> cacheManager;

    public DbServiceClientImpl(TransactionManager transactionManager,
                               DataTemplate<Client> clientDataTemplate,
                               CacheManager<Long, Client> cacheManager) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cacheManager = cacheManager;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                var savedClient = clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                if (!Objects.isNull(cacheManager)) {
                    cacheManager.put(savedClient.getId(), client);
                }
                return savedClient;
            }
            var savedClient = clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", savedClient);
            if (!Objects.isNull(cacheManager)) {
                cacheManager.put(savedClient.getId(), client);
            }
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        if (!Objects.isNull(cacheManager) && cacheManager.exist(id)) {
            return Optional.of(cacheManager.get(id));
        }
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
       });
    }
}
