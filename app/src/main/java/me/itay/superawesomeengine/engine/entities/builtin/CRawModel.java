package me.itay.superawesomeengine.engine.entities.builtin;

import me.itay.superawesomeengine.engine.entities.Component;
import me.itay.superawesomeengine.engine.models.RawModel;
import me.itay.superawesomeengine.engine.models.obj.ModelData;
import me.itay.superawesomeengine.engine.models.obj.OBJFileLoader;
import me.itay.superawesomeengine.engine.renderEngine.Loader;

/**
 * Created by Itay on 4/19/2017.
 */

public class CRawModel extends Component {

    private RawModel model;

    public CRawModel(RawModel model) {
        super(false, false);
        this.model = model;
    }

    public CRawModel(String filename) {
        super(false, false);
        ModelData data = OBJFileLoader.loadOBJ(filename);
        this.model = Loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
        model.setBounds(data.getBounds());
    }

    @Override
    public void init() {
        super.init();
    }

    public RawModel getModel() {
        return model;
    }

    @Override
    public Component clone() {
        return new CRawModel(model);
    }
}
