package com.app.usable;

import com.app.level.Level;

/**
 * The usable interface that will be implemented by the items and the skills
 * @version 5.0 (Fifth world)
 * @since 5.0 (Fifth world)
 * @author Rayane
 */
public interface Usable {

    String getName();
    
    void use(Level level);

}