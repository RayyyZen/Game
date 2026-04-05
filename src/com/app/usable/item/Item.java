package com.app.usable.item;

import com.app.usable.Usable;

/**
 * The item class
 * @version 5.0 (Fifth world)
 * @since 5.0 (Fifth world)
 * @author Rayane
 */
public abstract class Item implements Usable, Comparable<Item> {
    
    private final String symbol;

    public Item(String symbol){
        this.symbol = symbol;
    }

    public String getItemSymbol(){
        return this.symbol;
    }

    @Override
    public int compareTo(Item other){
        return this.getClass().getSimpleName().compareTo(other.getClass().getSimpleName());
    }
}
