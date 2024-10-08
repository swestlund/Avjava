package org.example;


import java.util.ArrayList;
import java.util.List;

public class Inventory implements IInventory {
private List<Item> items = new ArrayList<>();

    @Override
    public void addItem(Item item){
        this.items.add(item);
    }
    @Override
    public void removeItem(Item item){
        this.items.remove(item);
    }

}