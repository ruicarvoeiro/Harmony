package com.rossana.android.a261117ebookreader;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by rui_c on 20/01/2018.
 */

class AmOnSwipeTouchListener implements View.OnTouchListener {
    private final GestureDetector gestureDetector;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
    public AmOnSwipeTouchListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 50;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean resultado = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffY)<Math.abs(diffX)) {
                    if (SWIPE_THRESHOLD < Math.abs(diffX) && SWIPE_VELOCITY_THRESHOLD< Math.abs(velocityX)) {
                        if (diffX < 0) {
                            onSwipeLeft();
                        } else {
                            onSwipeRight();
                        }
                        resultado = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY < 0) {
                        onSwipeTop();
                    } else {
                        onSwipeBottom();
                    }
                    resultado = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return resultado;
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }
    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }

}
