package com.app.usable.item;

import com.app.level.Level;
import com.app.usable.Usable;

/**
 * The item class
 * @version 5.0 (Fifth world)
 * @since 5.0 (Fifth world)
 * @author Rayane
 */
public abstract class Item implements Usable, Comparable<Item> {
    
    private final String name;

    private final String symbol;

    public Item(String name, String symbol){
        this.name = name;
        this.symbol = symbol;
    }

    public String getName(){
        return this.name;
    }

    public String getItemSymbol(){
        return this.symbol;
    }

    public abstract void use(Level level);

    @Override
    public int compareTo(Item other){
        return this.name.compareTo(other.name);
    }
}
