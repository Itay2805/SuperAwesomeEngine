package me.itay.superawesomeengine.engine.toolbox;

import android.util.Log;

import java.util.Stack;

/**
 * Created by Itay on 4/20/2017.
 */

public class Test {

    private static Stack<Long> start = new Stack<>();

    public static void start() {
        start.push(System.currentTimeMillis());
    }

    public static void stop(String name) {
        long first = start.pop();
        long now = System.currentTimeMillis();
        Log.i("Test", name + ": " + (now - first));
    }

}
