package me.itay.superawesomeengine.engine.entities;

import java.util.ArrayList;
import java.util.List;

import me.itay.superawesomeengine.engine.renderEngine.EntityRenderer;
import me.itay.superawesomeengine.engine.renderEngine.MasterRenderer;
import me.itay.superawesomeengine.engine.shaders.StaticShader;

/**
 * Created by Itay on 4/19/2017.
 */

public abstract class Component {

    private EntityInstance instance;
    private boolean sealed = false;
    private List<Class<? extends Component>> depends = new ArrayList<>();
    private String name;
    private boolean inited = false;

    private boolean renderable = false;
    private boolean updateable = false;
    private boolean active = true;
    private boolean visible = true;

    public Component(boolean renderable, boolean updateable) {
        this("Anonymous", renderable, updateable);
    }

    public Component(String name, boolean renderable, boolean updateable) {
        this.name = name;
        this.renderable = renderable;
        this.updateable = updateable;
    }
    
    protected void addDepend(Class<? extends Component> c) {
    	if(!sealed) depends.add(c);
    }
    
    public List<Class<? extends Component>> getDepends() {
		return depends;
	}
    
    protected final void setEntity(EntityInstance instance) {
        if(!sealed) this.instance = instance;
    }

    public final void seal() {
        sealed = true;
    }
    
    public void init() {}
    public void update() {}
    public void tick() {}
    public void render(MasterRenderer renderer) {}

    public abstract Component clone();

    public boolean isSealed() {
        return sealed;
    }

    public String getName() {
        return name;
    }

    public <T extends Component> T getComponent(Class<T> type) {
        return instance.getComponent(type);
    }

    public EntityInstance getInstance() {
        return instance;
    }

    public boolean isRenderable() {
        return renderable;
    }

    public boolean isUpdateable() {
        return updateable;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isInited() {
        return inited;
    }

    public void setInited() {
        this.inited = true;
    }
}
