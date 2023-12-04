package ru.otus.javapro.homeworks.hw12caching.crm.service;

import ru.otus.javapro.homeworks.hw12caching.crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();
}
