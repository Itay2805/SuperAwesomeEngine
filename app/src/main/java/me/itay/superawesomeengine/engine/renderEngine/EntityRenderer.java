package me.itay.superawesomeengine.engine.renderEngine;

import android.app.Activity;
import android.content.Entity;
import android.util.DisplayMetrics;
import android.util.Log;

import org.lwjgl.util.vector.Matrix4f;

import java.util.List;
import java.util.Map;

import me.itay.superawesomeengine.MainActivity;
import me.itay.superawesomeengine.engine.entities.Camera;
import me.itay.superawesomeengine.engine.entities.EntityInstance;
import me.itay.superawesomeengine.engine.entities.builtin.CTexturedModel;
import me.itay.superawesomeengine.engine.entities.builtin.CTransformation;
import me.itay.superawesomeengine.engine.models.RawModel;
import me.itay.superawesomeengine.engine.models.TexturedModel;
import me.itay.superawesomeengine.engine.shaders.StaticShader;
import me.itay.superawesomeengine.engine.textures.ModelTexture;
import me.itay.superawesomeengine.engine.toolbox.Maths;

import static android.opengl.GLES30.*;

/**
 * Created by Itay on 4/19/2017.
 */

public class EntityRenderer {

    private StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<EntityInstance>> entities) {
        for(TexturedModel model : entities.keySet()) {
            prepareTextureModel(model);
            List<EntityInstance> instances = entities.get(model);
            for(EntityInstance entity : instances) {
                prepareInstance(entity);
                glDrawElements(GL_TRIANGLES, model.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
            }
            unbindTextureModel();
        }
    }

    private void prepareTextureModel(TexturedModel model) {
        RawModel rawModel = model.getModel();
        glBindVertexArray(rawModel.getVaoID());
        ModelTexture texture = model.getTexture();
        if(texture.isTransparent()) {
            MasterRenderer.disableCulling();
        }
        shader.loadFakeLightingVariable(texture.isFakeLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
    }

    private void unbindTextureModel() {
        MasterRenderer.enableCulling();
        glBindVertexArray(0);
    }

    private void prepareInstance(EntityInstance entity) {
        CTransformation transformation = entity.getComponent(CTransformation.class);
        Matrix4f transformationMatrix = transformation.getTransformationMatrix();
        shader.loadTransformationMatrix(transformationMatrix);
    }

}
