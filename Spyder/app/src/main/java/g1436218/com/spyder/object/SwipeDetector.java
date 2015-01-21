package g1436218.com.spyder.object;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Joey on 09/01/2015.
 */
public class SwipeDetector implements View.OnTouchListener {

    public static enum Movement {
        LR,
        RL,
        TB,
        BT,
        None
    }

    private final int MIN_DISTANCE = 100;
    private Movement mSwipeDetected = Movement.None;
    private float downX, downY, upX, upY;

    public boolean swipeDetected() {
        return mSwipeDetected != Movement.None;
    }

    public Movement getMovement() {
        return mSwipeDetected;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                //Log.d("ActionDown", "(" + downX + ", " + downY + ")");
                mSwipeDetected = Movement.None;
                return false;
            }
            case MotionEvent.ACTION_MOVE: {
                upX = event.getX();
                upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // horizontal swipe detection
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    if (deltaX < 0) {
                        mSwipeDetected = Movement.LR;
                        return true;
                    }
                    if (deltaX > 0) {
                        mSwipeDetected = Movement.RL;
                        return true;
                    }
                } else {
                    // vertical swipe detection
                    if (Math.abs(deltaY) > MIN_DISTANCE) {
                        if (deltaY < 0) {
                            mSwipeDetected = Movement.TB;
                            return false;
                        }
                        if (deltaY > 0) {
                            mSwipeDetected = Movement.BT;
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
