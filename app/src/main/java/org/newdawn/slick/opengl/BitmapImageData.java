package org.newdawn.slick.opengl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * An image data provider that uses BitmapFactory to retrieve image data in a format
 * suitable for creating OpenGL textures. This implementation is used when
 * formats not natively supported by the library are required.
 *
 * @author Itay Almog
 */
public class BitmapImageData implements LoadableImageData {

    /** The bit depth of the image */
    private int depth;
    /** The height of the image */
    private int height;
    /** The width of the image */
    private int width;
    /** The width of the texture that should be created for the image */
    private int texWidth;
    /** The height of the texture that should be created for the image */
    private int texHeight;
    /** True if we should edge */
    private boolean edging = true;

    /**
     * @see org.newdawn.slick.opengl.ImageData#getDepth()
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @see org.newdawn.slick.opengl.ImageData#getHeight()
     */
    public int getHeight() {
        return height;
    }

    /**
     * @see org.newdawn.slick.opengl.ImageData#getTexHeight()
     */
    public int getTexHeight() {
        return texHeight;
    }

    /**
     * @see org.newdawn.slick.opengl.ImageData#getTexWidth()
     */
    public int getTexWidth() {
        return texWidth;
    }

    /**
     * @see org.newdawn.slick.opengl.ImageData#getWidth()
     */
    public int getWidth() {
        return width;
    }

    /**
     * @see org.newdawn.slick.opengl.LoadableImageData#loadImage(java.io.InputStream)
     */
    public ByteBuffer loadImage(InputStream fis) throws IOException {
        return loadImage(fis, true, null);
    }

    /**
     * @see org.newdawn.slick.opengl.LoadableImageData#loadImage(java.io.InputStream, boolean, int[])
     */
    public ByteBuffer loadImage(InputStream fis, boolean flipped, int[] transparent) throws IOException {
        return loadImage(fis, flipped, false, transparent);
    }

    /**
     * @see org.newdawn.slick.opengl.LoadableImageData#loadImage(java.io.InputStream, boolean, boolean, int[])
     */
    public ByteBuffer loadImage(InputStream fis, boolean flipped, boolean forceAlpha, int[] transparent) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeStream(fis);
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        ByteBuffer buffer = ByteBuffer.allocateDirect(pixels.length * 4 * 4).order(ByteOrder.nativeOrder());
        for(int pixel : pixels) {
            float red = Color.red(pixel) / 255f;
            float green = Color.green(pixel) / 255f;
            float blue = Color.blue(pixel) / 255f;
            float alpha = bitmap.hasAlpha() ? Color.alpha(pixel) / 255f : 1;
            buffer.putFloat(red);
            buffer.putFloat(green);
            buffer.putFloat(blue);
            buffer.putFloat(alpha);
        }
        buffer.flip();
        bitmap.recycle();
        return buffer;
    }

    /**
     * @see org.newdawn.slick.opengl.ImageData#getImageBufferData()
     */
    public ByteBuffer getImageBufferData() {
        throw new RuntimeException("BitmapImageData doesn't store it's image.");
    }

    /**
     * @see org.newdawn.slick.opengl.LoadableImageData#configureEdging(boolean)
     */
    public void configureEdging(boolean edging) {
        this.edging = edging;
    }
}