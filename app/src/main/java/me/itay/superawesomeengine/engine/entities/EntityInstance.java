package me.itay.superawesomeengine.engine.entities;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.itay.superawesomeengine.engine.entities.builtin.CTexturedModel;
import me.itay.superawesomeengine.engine.renderEngine.EntityRenderer;
import me.itay.superawesomeengine.engine.renderEngine.MasterRenderer;
import me.itay.superawesomeengine.engine.shaders.StaticShader;
import me.itay.superawesomeengine.engine.toolbox.Maths;

/**
 * Created by Itay on 4/19/2017.
 */

public class EntityInstance {

    private HashMap<Class<? extends Component>, List<Component>> componentsMap = new HashMap<>();
    private List<Component> components;
    private boolean inited = false;

    public EntityInstance(EntityBlueprint blueprint, List<Component> components) {
        this.components = components;
    }

    private List<Component> toUpdate = new ArrayList<>();
    private List<Component> toRender = new ArrayList<>();
    private boolean updateable = false;
    private boolean renderable = false;
    private boolean process = false;

    protected void init() {
        if(inited) return;
        inited = true;

        for(Component component : components) {
            Class<? extends Component> type = component.getClass();
            String name = component.getName();
            if(!componentsMap.containsKey(type)) {
                componentsMap.put(type, new ArrayList<Component>());
            }
            List<Component> comps = componentsMap.get(type);
            comps.add(component);
            if(component.isRenderable()) {
                renderable = true;
                toRender.add(component);
            }
            if(component.isUpdateable()) {
                updateable = true;
                toUpdate.add(component);
            }
            if(component.getClass().isAssignableFrom(CTexturedModel.class)) {
                renderable = true;
                process = true;
            }

        }
        for(Component c : components) {
            if(!c.isInited()) {
                c.init();
            }
        }
    }

    public boolean isUpdateable() {
        return updateable;
    }

    public boolean isRenderable() {
        return renderable;
    }

    public void update() {
        for(Component c : toUpdate) {
            if(c.isActive()) c.update();
         }
    }

    public void render(MasterRenderer renderer) {
        if(process) renderer.processEntity(this);
        for(Component c : toRender) {
            if(c.isVisible()) c.render(renderer);
        }
    }

    public void tick() {
        for(Component c : toUpdate) {
            if(c.isActive()) c.tick();
        }
    }

    public <T extends Component> T getComponent(Class<T> type) {
        try {
            List<Component> components = componentsMap.get(type);
            T component = type.cast(components.get(0));
            if(!component.isInited()) component.init();
            return component;
        }catch(Exception e) {
            return null;
        }
    }

    public <T extends Component> T getComponent(Class<T> type, String name) {
        try {
            List<Component> components = componentsMap.get(type);
            for(Component c : components) {
                if(c.getName().equals(name)) {
                    T component = type.cast(c);
                    if(!component.isInited()) component.init();
                    return component;
                }
            }
            return null;
        }catch(Exception e) {
            return null;
        }
    }

}
