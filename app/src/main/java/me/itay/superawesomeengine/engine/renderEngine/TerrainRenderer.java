package me.itay.superawesomeengine.engine.renderEngine;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;
import java.util.Map;

import me.itay.superawesomeengine.engine.entities.EntityInstance;
import me.itay.superawesomeengine.engine.entities.builtin.CTransformation;
import me.itay.superawesomeengine.engine.models.RawModel;
import me.itay.superawesomeengine.engine.models.TexturedModel;
import me.itay.superawesomeengine.engine.shaders.StaticShader;
import me.itay.superawesomeengine.engine.shaders.TerrainShader;
import me.itay.superawesomeengine.engine.terrains.Terrain;
import me.itay.superawesomeengine.engine.textures.ModelTexture;
import me.itay.superawesomeengine.engine.toolbox.Maths;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_INT;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES30.glBindVertexArray;

/**
 * Created by Itay on 4/20/2017.
 */

public class TerrainRenderer {

    private TerrainShader shader;

    private final CTransformation defaultTransform = new CTransformation();

    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(List<Terrain> terrains) {
        for(Terrain terrain : terrains) {
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            glDrawElements(GL_TRIANGLES, terrain.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
            unbindTextureModel();
        }
    }

    private void prepareTerrain(Terrain terrain) {
        RawModel rawModel = terrain.getModel();
        glBindVertexArray(rawModel.getVaoID());
        glEnableVertexAttribArray(Loader.POSITIONS_INDEX);
        glEnableVertexAttribArray(Loader.TEXTURE_COORDS_INDEX);
        glEnableVertexAttribArray(Loader.NORMALS_INDEX);
        ModelTexture texture = terrain.getTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
    }

    private void unbindTextureModel() {
        glDisableVertexAttribArray(Loader.POSITIONS_INDEX);
        glDisableVertexAttribArray(Loader.TEXTURE_COORDS_INDEX);
        glDisableVertexAttribArray(Loader.NORMALS_INDEX);
        glBindVertexArray(0);
    }

    private void loadModelMatrix(Terrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
        shader.loadTransformationMatrix(transformationMatrix);
    }

}
