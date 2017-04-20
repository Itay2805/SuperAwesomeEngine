package org.newdawn.slick.opengl;

import static android.opengl.GLES10.GL_NO_ERROR;
import static android.opengl.GLES10.glGetError;

/**
 * A collection of utilities to allow aid interaction with the GL provider
 *
 * @author kevin
 * @author Itay Almog
 */
public final class GLUtils {

    /**
     * Check that we're in the right place to be doing GL operations
     */
    public static void checkGLContext() {
        if(glGetError() != GL_NO_ERROR) {
            throw new RuntimeException("OpenGL based resources (images, fonts, sprites etc) must be loaded as part of init() or the game loop. They cannot be loaded before initialisation.");
        }
    }
}