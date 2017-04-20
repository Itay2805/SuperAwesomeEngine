package me.itay.superawesomeengine.engine.toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import java.util.ArrayList;
import java.util.List;

import me.itay.superawesomeengine.engine.entities.Camera;

/**
 * Created by Itay on 4/20/2017.
 */

public class Bounds {

    public static List<Vector3f> getBoundsBox(Vector3f lowBound, Vector3f highBound) {
        List<Vector3f> bounds = new ArrayList<>();

        float width = (float) (Math.abs(lowBound.x) + Math.abs(highBound.x));
        float height = (float) (Math.abs(lowBound.y) + Math.abs(highBound.y));

        bounds.add(lowBound);
        bounds.add(Vector3f.add(lowBound, new Vector3f(width, 0, 0), null));
        bounds.add(Vector3f.add(lowBound, new Vector3f(width, height, 0), null));
        bounds.add(Vector3f.add(lowBound, new Vector3f(0, height, 0), null));

        bounds.add(highBound);
        bounds.add(Vector3f.sub(highBound, new Vector3f(width, 0, 0), null));
        bounds.add(Vector3f.sub(highBound, new Vector3f(width, height, 0), null));
        bounds.add(Vector3f.sub(highBound, new Vector3f(0, height, 0), null));

        return bounds;
    }

    public static boolean inBounds(Matrix4f projection, Matrix4f transformationMatrix, List<Vector3f> bounds) {
        for(Vector3f bound : bounds) {
            Vector4f transformation = Matrix4f.transform(transformationMatrix, new Vector4f(bound.x, bound.y, bound.z, 1.0f), null);
            if(Maths.inFrustum(projection, new Vector3f(transformation))) return true;
        }
        return false;
    }

}
