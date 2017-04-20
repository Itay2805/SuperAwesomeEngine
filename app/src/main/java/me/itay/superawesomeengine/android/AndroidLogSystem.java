package me.itay.superawesomeengine.android;

import android.util.Log;

import org.newdawn.slick.util.LogSystem;

/**
 * The default implementation that just spits the messages out to logcat
 *
 * @author Itay Almog
 */
public class AndroidLogSystem implements LogSystem {

    /**
     * Log an error
     *
     * @param message The message describing the error
     * @param e The exception causing the error
     */
    public void error(String message, Throwable e) {
        error(message);
        error(e);
    }

    /**
     * Log an error
     *
     * @param e The exception causing the error
     */
    public void error(Throwable e) {
        error(e.getMessage());
        error(Log.getStackTraceString(e));
    }

    /**
     * Log an error
     *
     * @param message The message describing the error
     */
    public void error(String message) {
        Log.e("SlickUtils", message);
    }

    /**
     * Log a warning
     *
     * @param message The message describing the warning
     */
    public void warn(String message) {
        Log.w("SlickUtils", message);
    }

    /**
     * Log an information message
     *
     * @param message The message describing the infomation
     */
    public  void info(String message) {
        Log.i("SlickUtils", message);
    }

    /**
     * Log a debug message
     *
     * @param message The message describing the debug
     */
    public void debug(String message) {
        Log.d("SlickUtils", message);
    }

    /**
     * Log a warning with an exception that caused it
     *
     * @param message The message describing the warning
     * @param e The cause of the warning
     */
    public void warn(String message, Throwable e) {
        warn(message);
        warn(Log.getStackTraceString(e));
    }
}