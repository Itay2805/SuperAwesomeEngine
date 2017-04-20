package me.itay.superawesomeengine.engine.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Itay on 4/19/2017.
 */

public class EntityBlueprint {

    private List<Component> components = new ArrayList<>();

    public void addComponent(Component c) {
        if(c.isSealed()) {
            throw new RuntimeException("Can not add a sealed component");
        }
        components.add(c);
    }

    public <T extends Component> T getComponent(Class<T> c) {
        for(Component comp : components) {
            if(c.isInstance(comp)) return c.cast(comp);
        }
        return null;
    }

    public void build() {
    	for(Component comp : components) {
    		for(Class<? extends Component> c : comp.getDepends()) {
    			if(!hasComponent(c)) {
    				throw new RuntimeException("Missing component: " + c.getName());
    			}
    		}
    	}
    }
    
    private boolean hasComponent(Class<? extends Component> c) {
    	for(Component comp : components) {
    		if(c.isInstance(comp)) return true;
    	}
    	return false;
    }

    public EntityInstance createInstance() {
        List<Component> newComponents = new ArrayList<>();
        EntityInstance instance = new EntityInstance(this, newComponents);
        for(Component c : components) {
            Component newC = c.clone();
            newC.setEntity(instance);
            newC.seal();
            newComponents.add(newC);
        }
        instance.init();
        return instance;
    }

}
