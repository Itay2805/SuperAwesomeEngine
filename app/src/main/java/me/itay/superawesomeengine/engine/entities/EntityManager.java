package me.itay.superawesomeengine.engine.entities;

import java.util.ArrayList;
import java.util.List;

import me.itay.superawesomeengine.engine.renderEngine.MasterRenderer;
import me.itay.superawesomeengine.engine.toolbox.Test;

/**
 * Created by Itay on 4/20/2017.
 */

public class EntityManager {

    private static List<EntityInstance> instanceList = new ArrayList<>();

    private static List<EntityInstance> renderable = new ArrayList<>();
    private static List<EntityInstance> updateable = new ArrayList<>();

    public static void addEntity(EntityInstance instance) {
        instanceList.add(instance);
        if(instance.isRenderable()) renderable.add(instance);
        if(instance.isUpdateable()) updateable.add(instance);
    }

    public static void tick() {
        update();
    }

    public static void update() {
        for(EntityInstance instance : updateable) {
            instance.update();
        }
    }

    public static void render(MasterRenderer renderer) {
        for(EntityInstance instance : renderable) {
            instance.render(renderer);
        }
    }

}
