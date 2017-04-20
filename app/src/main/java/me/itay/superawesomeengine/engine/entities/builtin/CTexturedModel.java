package me.itay.superawesomeengine.engine.entities.builtin;

import me.itay.superawesomeengine.engine.entities.Component;
import me.itay.superawesomeengine.engine.models.TexturedModel;

/**
 * Created by Itay on 4/19/2017.
 */

public class CTexturedModel extends Component {

    private TexturedModel model;

    public CTexturedModel(TexturedModel model) {
        super(false, false);
        this.model = model;
    }

    public CTexturedModel() {
        super(false, false);
        addDepend(CRawModel.class);
        addDepend(CModelTexture.class);
    }

    @Override
    public void init() {
        super.init();

        if(model == null) {
            CRawModel rawModel = getComponent(CRawModel.class);
            CModelTexture texture = getComponent(CModelTexture.class);
            model = new TexturedModel(rawModel.getModel(), texture.getTexture());
        }
    }

    @Override
    public Component clone() {
        return new CTexturedModel(model);
    }

    public TexturedModel getModel() {
        return model;
    }

}
