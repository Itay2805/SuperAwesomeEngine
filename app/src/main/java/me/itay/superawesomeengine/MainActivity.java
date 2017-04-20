package me.itay.superawesomeengine;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import me.itay.superawesomeengine.android.AndroidInit;
import me.itay.superawesomeengine.android.JoystickMovedListener;
import me.itay.superawesomeengine.android.JoystickView;
import me.itay.superawesomeengine.engine.entities.builtin.CMovement;
import me.itay.superawesomeengine.engine.renderEngine.Loader;

/**
 * Created by Itay on 4/18/2017.
 */

public class MainActivity extends Activity {

    private MainRenderer mainRenderer;
    private static Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        boolean supportES3 = (info.reqGlEsVersion >= 0x30000);
        if(supportES3) {
            AndroidInit.init();

            mainRenderer = new MainRenderer();
            MainSurfaceView mainSurfaceView = new MainSurfaceView(this);
            mainSurfaceView.setEGLContextClientVersion(3);
            mainSurfaceView.setRenderer(mainRenderer);

            JoystickView leftJoystick = new JoystickView(this);
            leftJoystick.setOnJostickMovedListener(new JoystickMovedListener() {
                @Override
                public void OnMoved(int pan, int tilt) {
                    CMovement.pan = pan;
                    CMovement.tilt = tilt;
                }

                @Override
                public void OnReleased() {
                    CMovement.pan = 0;
                    CMovement.tilt = 0;
                }
            });

            JoystickView rightJoystick = new JoystickView(this);
            rightJoystick.setOnJostickMovedListener(new JoystickMovedListener() {
                @Override
                public void OnMoved(int pan, int tilt) {
                }

                @Override
                public void OnReleased() {
                }
            });

            DisplayMetrics display = new DisplayMetrics();
            ((Activity) MainActivity.getContext()).getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(display);

            int size = display.widthPixels / 5;

            FrameLayout.LayoutParams leftLayoutParams=new FrameLayout.LayoutParams(size, size);
            leftLayoutParams.setMargins(20, display.heightPixels - size - 20, 0, 0);
            leftJoystick.setLayoutParams(leftLayoutParams);

            FrameLayout.LayoutParams rightLayoutParams=new FrameLayout.LayoutParams(size, size);
            rightLayoutParams.setMargins(display.widthPixels - size - 20, display.heightPixels - size - 20, 0, 0);
            rightJoystick.setLayoutParams(rightLayoutParams);

            FrameLayout layout = new FrameLayout(this);
            layout.addView(mainSurfaceView);
            layout.addView(leftJoystick);
            layout.addView(rightJoystick);

            this.setContentView(layout);
        }else {
            Log.e("OpenGLES 3", "Your device doesn't support ES3. (" + info.reqGlEsVersion + ")");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Loader.cleanUp();
        if(mainRenderer != null) mainRenderer.cleanUp();
    }

    public static Context getContext() {
        return context;
    }

}
