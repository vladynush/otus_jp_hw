package ru.otus;

import ru.otus.appcontainer.AppComponentsContainerImpl;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.config.AppConfig;
import ru.otus.services.GameProcessor;

public class App {

    public static void main(String[] args) throws Exception {
       AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);
       GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
       gameProcessor.startGame();
    }
}
