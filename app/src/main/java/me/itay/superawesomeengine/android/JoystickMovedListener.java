package me.itay.superawesomeengine.android;

/**
 * Created by Itay on 4/18/2017.
 */

public interface JoystickMovedListener {

    public void OnMoved(int pan, int tilt);
    public void OnReleased();

}
