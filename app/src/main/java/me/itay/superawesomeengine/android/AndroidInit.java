package me.itay.superawesomeengine.android;

import org.newdawn.slick.util.FileSystemLocation;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

import java.io.File;

/**
 * Created by Itay on 4/18/2017.
 */

public class AndroidInit {

    public static void init() {
        // Set to android logging (logcat)
        Log.setLogSystem(new AndroidLogSystem());

        // add asset folder location
        ResourceLoader.addResourceLocation(new FileSystemLocation(new File("file:///android_asset/")));
    }

}
