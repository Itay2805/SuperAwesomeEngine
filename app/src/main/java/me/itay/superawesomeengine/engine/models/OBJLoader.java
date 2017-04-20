package me.itay.superawesomeengine.engine.models;

import android.util.Log;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.itay.superawesomeengine.MainActivity;
import me.itay.superawesomeengine.engine.models.RawModel;
import me.itay.superawesomeengine.engine.renderEngine.Loader;
import me.itay.superawesomeengine.engine.toolbox.AABB;
import me.itay.superawesomeengine.engine.toolbox.Bounds;

/**
 * Created by Itay on 4/19/2017.
 */

public class OBJLoader {

    public static RawModel loadObjModel(String filename) {
        InputStreamReader r = null;
        try {
            r = new InputStreamReader(MainActivity.getContext().getAssets().open("res/" + filename + ".obj"));
        }catch(Exception e) {
            Log.e("GameEngine", "Couldn't load file!");
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(r);
        String line;
        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Vector2f> textures = new ArrayList<Vector2f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Integer> indices = new ArrayList<Integer>();
        float[] verticesArray = null;
        float[] normalsArray = null;
        float[] textureArray = null;
        int[] indicesArray = null;
        try {

            while (true) {
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                if (line.startsWith("v ")) {
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                } else if (line.startsWith("vt ")) {
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                } else if (line.startsWith("vn ")) {
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                } else if (line.startsWith("f ")) {
                    textureArray = new float[vertices.size() * 2];
                    normalsArray = new float[vertices.size() * 3];
                    break;
                }
            }

            while (line != null) {
                if (!line.startsWith("f ")) {
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");

                processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                line = reader.readLine();
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        float minX = Float.MAX_VALUE, minY = Float.MAX_VALUE, minZ = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE, maxZ = Float.MIN_VALUE;

        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;

            if(minX > vertex.x) minX = vertex.x;
            if(minY > vertex.y) minY = vertex.y;
            if(minZ > vertex.z) minZ = vertex.z;

            if(maxX < vertex.x) maxX = vertex.x;
            if(maxY < vertex.y) maxY = vertex.y;
            if(maxZ < vertex.z) maxZ = vertex.z;
        }

        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }

        RawModel model = Loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);

        float sizeX = (float)(Math.abs(minX) + Math.abs(maxX));
        float sizeY = (float)(Math.abs(minX) + Math.abs(maxX));
        float sizeZ = (float)(Math.abs(minX) + Math.abs(maxX));
        model.setBoundingBox(new AABB(new Vector3f(), new Vector3f(sizeX, sizeY, sizeZ)));
        model.setBounds(Bounds.getBoundsBox(new Vector3f(minX, minY, minZ), new Vector3f(maxX, maxY, maxZ)));
        return model;
    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[currentVertexPointer * 2] = currentTex.x;
        textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNorm.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
    }

}
