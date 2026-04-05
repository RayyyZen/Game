package com.app.usable;

import com.app.level.Level;

public interface Triggerable {
    
    UseMode getUseMode();

    boolean isTriggerable(Level level);

}
