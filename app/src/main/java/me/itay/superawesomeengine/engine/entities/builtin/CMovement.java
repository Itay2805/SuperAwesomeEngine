package me.itay.superawesomeengine.engine.entities.builtin;

import android.util.Log;

import me.itay.superawesomeengine.engine.entities.Component;

/**
 * Created by Itay on 4/19/2017.
 */

public class CMovement extends Component {

    public static float pan;
    public static float tilt;

    private CTransformation transformation;

    public CMovement() {
        super(false, true);
        addDepend(CTransformation.class);
    }

    @Override
    public void init() {
        super.init();

        transformation = getComponent(CTransformation.class);
    }

    @Override
    public void update() {
        super.update();

        transformation.increasePosition(pan * 0.02f, 0, tilt * 0.02f);
    }

    @Override
    public Component clone() {
        return new CMovement();
    }
}
