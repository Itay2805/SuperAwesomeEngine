package me.itay.superawesomeengine.engine;

import android.opengl.GLES30;

/**
 * Created by Itay on 4/19/2017.
 */

public class GL {

    public static int glGenVertexArrays() {
        int[] vao = new int[1];
        GLES30.glGenVertexArrays(1, vao, 0);
        return vao[0];
    }

    public static int glGenBuffers() {
        int[] vbo = new int[1];
        GLES30.glGenBuffers(1, vbo, 0);
        return vbo[0];
    }

    public static void glDeleteVertexArrays(int vao) {
        GLES30.glDeleteVertexArrays(1, new int[] { vao }, 0);
    }

    public static void glDeleteTextures(int texture) {
        GLES30.glDeleteTextures(1, new int[] { texture }, 0);
    }

    public static void glDeleteBuffers(int vbo) {
        GLES30.glDeleteBuffers(1, new int[] { vbo }, 0);
    }

}
