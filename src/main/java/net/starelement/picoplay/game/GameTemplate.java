package net.starelement.picoplay.game;

import net.starelement.picoplay.level.PicoLevel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameTemplate {

    private Class aClass;
    private List<PicoLevel> list = new ArrayList<>();

    public GameTemplate(Class aClass) {
        this.aClass = aClass;
    }

    public void addLevel(PicoLevel level) {
        if (level.getGame().equals(aClass.getSimpleName())) {
            list.add(level);
        }
    }

    public PicoLevel getRandomLevel() {
        Collections.shuffle(list);
        return list.get(0);
    }

}
