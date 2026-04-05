package com.app.usable;

import java.util.Comparator;

import com.app.usable.item.Item;
import com.app.usable.skill.Skill;

public class UsableComparator implements Comparator<Usable> {

    @Override
    public int compare(Usable usable1, Usable usable2) {

        if(usable1 instanceof Item && usable2 instanceof Skill){
            return -1;

        } else if(usable1 instanceof Skill && usable2 instanceof Item) {
            return 1;

        } else {
            //Both are the same
            if(usable1 instanceof Item){
                return ((Item)usable1).compareTo((Item)usable2);

            } else {
                return ((Skill)usable1).compareTo((Skill)usable2);
            }
        }
        
    }
    
}
