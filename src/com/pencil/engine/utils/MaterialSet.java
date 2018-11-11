package com.pencil.engine.utils;

import org.bukkit.inventory.Inventory;


public class MaterialSet {

    private Inventory stone;
    private Inventory natural;
    private Inventory wood;
    private Inventory slab;
    private Inventory cOne;
    private Inventory cTwo;
    private Inventory cThree;
    private Inventory sea;
    private Inventory random;

    public MaterialSet(Inventory stone, Inventory natural, Inventory wood, Inventory slab, Inventory cOne, Inventory cTwo, Inventory cThree, Inventory sea, Inventory random) {
        this.stone = stone;
        this.natural = natural;
        this.wood = wood;
        this.slab = slab;
        this.cOne = cOne;
        this.cTwo = cTwo;
        this.cThree = cThree;
        this.sea = sea;
        this.random = random;
    }

    public Inventory getStone() {
        return stone;
    }

    public Inventory getNatural() {
        return natural;
    }

    public Inventory getWood() {
        return wood;
    }

    public Inventory getSlab() {
        return slab;
    }

    public Inventory getcOne() {
        return cOne;
    }

    public Inventory getcTwo() {
        return cTwo;
    }

    public Inventory getcThree() {
        return cThree;
    }

    public Inventory getSea() {
        return sea;
    }

    public Inventory getRandom() {
        return random;
    }
}