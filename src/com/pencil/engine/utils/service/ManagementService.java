package com.pencil.engine.utils.service;

import com.pencil.engine.utils.service.manager.Manager;

import java.util.ArrayList;

public class ManagementService {

    private ArrayList<Manager> managers;

    public ManagementService() {
        managers = new ArrayList<>();
    }

    public void register(Manager manager) {
        managers.add(manager);
    }

    public Manager getManager(String name) {
        for (Manager manager : managers) {
            if (manager.getName().equals(name)) {
                return manager;
            }
        }

        return null;
    }

}
