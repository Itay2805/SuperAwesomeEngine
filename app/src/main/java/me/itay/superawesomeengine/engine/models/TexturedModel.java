package me.itay.superawesomeengine.engine.models;

import me.itay.superawesomeengine.engine.textures.ModelTexture;

/**
 * Created by Itay on 4/19/2017.
 */

public class TexturedModel {

    private RawModel model;
    private ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture) {
        this.model = model;
        this.texture = texture;
    }

    public RawModel getModel() {
        return model;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TexturedModel that = (TexturedModel) o;

        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        return texture != null ? texture.equals(that.texture) : that.texture == null;

    }

    @Override
    public int hashCode() {
        int result = model != null ? model.hashCode() : 0;
        result = 31 * result + (texture != null ? texture.hashCode() : 0);
        return result;
    }
}
