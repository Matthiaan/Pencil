package com.pencil.engine.utils.service.manager;

public abstract class Manager {

    private String name;

    public Manager() {}

    public String getName() {
        return name;
    }

    public abstract void run();

}
