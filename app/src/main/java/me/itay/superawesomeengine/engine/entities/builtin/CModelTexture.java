package me.itay.superawesomeengine.engine.entities.builtin;

import me.itay.superawesomeengine.engine.entities.Component;
import me.itay.superawesomeengine.engine.renderEngine.Loader;
import me.itay.superawesomeengine.engine.textures.ModelTexture;

/**
 * Created by Itay on 4/19/2017.
 */

public class CModelTexture extends Component {

    private ModelTexture texture;

    public CModelTexture(ModelTexture modelTexture) {
        super(false, false);
        this.texture = modelTexture;
    }

    public CModelTexture(int id) {
        super(false, false);
        this.texture = new ModelTexture(id);
    }

    public CModelTexture(String filename) {
        super(false, false);
        this.texture = new ModelTexture(Loader.loadTexture(filename));
    }

    @Override
    public void init() {
        super.init();
    }

    public ModelTexture getTexture() {
        return texture;
    }

    @Override
    public Component clone() {
        return new CModelTexture(texture);
    }
}
