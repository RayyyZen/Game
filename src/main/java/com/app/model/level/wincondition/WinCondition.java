package com.app.model.level.wincondition;

import com.app.model.level.Level;

/**
 * The win interface
 * @version 5.0 (Fifth world)
 * @since 5.0 (Fifth world)
 * @author Rayane
 */
public interface WinCondition {
    
    /**
     * Checks if the player achieved the win condition of a level
     * @param level The level that will be checked
     * @return True if the player achieved the win condition of the level, or false otherwise
     */
    boolean win(Level level);

    /**
     * Returns the description of the win condition
     * @return the description of the win condition
     */
    String description();

}
