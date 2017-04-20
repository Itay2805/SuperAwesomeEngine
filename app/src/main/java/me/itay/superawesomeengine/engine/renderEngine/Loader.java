package me.itay.superawesomeengine.engine.renderEngine;

import android.util.Log;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import me.itay.superawesomeengine.MainActivity;
import me.itay.superawesomeengine.engine.GL;
import me.itay.superawesomeengine.engine.models.RawModel;

import static android.opengl.GLES30.*;

/**
 * Created by Itay on 4/19/2017.
 */

public class Loader {

    public static final int POSITIONS_INDEX         = 0;
    public static final int TEXTURE_COORDS_INDEX    = 1;
    public static final int NORMALS_INDEX           = 2;

    private static List<Integer> vaos = new ArrayList<>();
    private static List<Integer> vbos = new ArrayList<>();
    private static List<Integer> textures = new ArrayList<>();

    public static RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeEverythingInAttributeList(positions, textureCoords, normals);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public static int loadTexture(String filename) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", MainActivity.getContext().getAssets().open("res/" + filename + ".png"));
            glGenerateMipmap(GL_TEXTURE_2D);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        }catch(Exception e) {
            e.printStackTrace();
        }
        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }

    private static int createVAO() {
        int vaoID = GL.glGenVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);
        return vaoID;
    }

    private static void storeEverythingInAttributeList(float[] positions, float[] textureCoords, float[] normals) {
        int vboID = GL.glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeEverythingInFloatBuffer(positions, textureCoords, normals);
        glBufferData(GL_ARRAY_BUFFER, buffer.limit() * 4, buffer, GL_STATIC_DRAW);

        // positions
        glVertexAttribPointer(POSITIONS_INDEX, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(POSITIONS_INDEX);

        // textureCoords
        glVertexAttribPointer(TEXTURE_COORDS_INDEX, 2, GL_FLOAT, false, 0, positions.length * 4);
        glEnableVertexAttribArray(TEXTURE_COORDS_INDEX);

        // normals
        glVertexAttribPointer(NORMALS_INDEX, 3, GL_FLOAT, false, 0, (positions.length + textureCoords.length) * 4);
        glEnableVertexAttribArray(NORMALS_INDEX);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private static FloatBuffer storeEverythingInFloatBuffer(float[] positions, float[] textureCoords, float[] normals) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(positions.length + textureCoords.length + normals.length);
        buffer.put(positions);
        buffer.put(textureCoords);
        buffer.put(normals);
        buffer.flip();
        return buffer;
    }

    private static void unbindVAO() {
        glBindVertexArray(0);
    }

    private static void bindIndicesBuffer(int[] indices) {
        int vboID = GL.glGenBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer.limit() * 4, buffer, GL_STATIC_DRAW);
    }

    public static IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static void cleanUp() {
        for(int vao : vaos) {
            GL.glDeleteVertexArrays(vao);
        }

        for(int vbo : vbos) {
            GL.glDeleteBuffers(vbo);
        }

        for(int texture : textures) {
            GL.glDeleteTextures(texture);
        }
    }

}
