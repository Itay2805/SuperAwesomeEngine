package me.itay.superawesomeengine.engine.textures;

/**
 * Created by Itay on 4/19/2017.
 */

public class ModelTexture {

    private int textureID;
    private float shineDamper = 1;
    private float reflectivity = 0;

    private boolean transparent = false;
    private boolean fakeLighting = false;

    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }

    public int getTextureID() {
        return textureID;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public ModelTexture setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
        return this;
    }

    public ModelTexture setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
        return this;
    }

    public ModelTexture setTransparent(boolean transparent) {
        this.transparent = transparent;
        return this;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public boolean isFakeLighting() {
        return fakeLighting;
    }

    public ModelTexture setFakeLighting(boolean fakeLighting) {
        this.fakeLighting = fakeLighting;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelTexture texture = (ModelTexture) o;

        if (textureID != texture.textureID) return false;
        if (Float.compare(texture.shineDamper, shineDamper) != 0) return false;
        return Float.compare(texture.reflectivity, reflectivity) == 0;

    }

    @Override
    public int hashCode() {
        int result = textureID;
        result = 31 * result + (shineDamper != +0.0f ? Float.floatToIntBits(shineDamper) : 0);
        result = 31 * result + (reflectivity != +0.0f ? Float.floatToIntBits(reflectivity) : 0);
        return result;
    }
}
