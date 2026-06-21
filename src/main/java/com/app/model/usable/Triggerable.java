package com.app.model.usable;

import com.app.model.level.Level;

public interface Triggerable {
    
    boolean shouldTrigger(Level level);

}
