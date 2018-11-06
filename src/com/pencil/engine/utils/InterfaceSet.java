package com.pencil.engine.utils;

import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class InterfaceSet {

    private ArrayList<Inventory> set;

    public InterfaceSet() {
        set = new ArrayList<>();
    }

    public ArrayList<Inventory> getSet() {
        return set;
    }

    public void add(Inventory inventory) {
        set.add(inventory);
    }

    public Inventory getFirst() {
        return set.get(0);
    }

    public Inventory get(int index) {
        try {
            return set.get(index);
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
