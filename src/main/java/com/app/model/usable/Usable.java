package com.app.model.usable;

import com.app.model.level.Level;

/**
 * The usable interface that will be implemented by the items and the skills
 * @version 5.0 (Fifth world)
 * @since 5.0 (Fifth world)
 * @author Rayane
 */
public interface Usable {

    String getName();

    String getSymbol();
    
    void use(Level level);

}