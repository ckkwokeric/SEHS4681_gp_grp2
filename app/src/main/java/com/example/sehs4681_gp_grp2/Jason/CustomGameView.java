package com.example.sehs4681_gp_grp2.Jason;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

public class CustomGameView extends View {
    private CountDownTimer countDownTimer;
    private int timeLeft;
    private static final int INITIAL_TIME_LIMIT = 60; // Time limit in seconds
    private Paint circlePaint;
    private Paint targetPaint;
    private int circleRadius = 60;

    // Circles and target positions
    private PointF[] circlePositions = new PointF[2];
    private PointF[] targetPositions = new PointF[2];

    // Active pointer IDs and their positions
    private SparseArray<PointF> activePointers = new SparseArray<>();

    private boolean gameWon = false;
    private final Paint paint = new Paint();

    public CustomGameView(Context context) {
        super(context);
        init();
    }

    public CustomGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.FILL);

        targetPaint = new Paint();
        targetPaint.setColor(Color.GREEN);
        targetPaint.setStyle(Paint.Style.FILL);

        // Initialize circle and target positions
        circlePositions[0] = new PointF(200, 200);
        circlePositions[1] = new PointF(1000, 300);
        targetPositions[0] = new PointF(200, 700);
        targetPositions[1] = new PointF(800, 1000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the timer
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);
        canvas.drawText("Time left: " + timeLeft, 10, 60, paint);


        // Draw circles
        for (PointF position : circlePositions) {
            canvas.drawCircle(position.x, position.y, circleRadius, circlePaint);
        }

        // Draw targets
        for (PointF position : targetPositions) {
            canvas.drawCircle(position.x, position.y, circleRadius, targetPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int index = event.getActionIndex();
        int pointerId = event.getPointerId(index);

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            // Add new pointer
            PointF touchPoint = new PointF(event.getX(index), event.getY(index));
            activePointers.put(pointerId, touchPoint);

            updateCirclePositions(touchPoint, pointerId);
        } else if (action == MotionEvent.ACTION_MOVE) {
            // Update pointer positions
            for (int i = 0; i < event.getPointerCount(); i++) {
                pointerId = event.getPointerId(i);
                PointF touchPoint = new PointF(event.getX(i), event.getY(i));
                activePointers.put(pointerId, touchPoint);

                updateCirclePositions(touchPoint, pointerId);
            }
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_CANCEL) {
            // Remove pointer
            activePointers.remove(pointerId);
        }

        invalidate();
        return true;
    }

    private void updateCirclePositions(PointF touchPoint, int pointerId) {
        for (int i = 0; i < circlePositions.length; i++) {
            PointF position = circlePositions[i];
            if (isWithinCircle(position, touchPoint, circleRadius)) {
                position.set(touchPoint);
                break;
            }
        }

        checkWinCondition();
    }

    public interface OnGameWonListener {
        void onGameWon();
    }

    public void startTimer() {
        gameWon = false;
        timeLeft = INITIAL_TIME_LIMIT;
        countDownTimer = new CountDownTimer(INITIAL_TIME_LIMIT * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) (millisUntilFinished / 1000);
                invalidate();
            }

            @Override
            public void onFinish() {
                if (!gameWon) {
                    onGameLostListener.run();
                }
            }
        }.start();
    }

    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private Runnable onGameLostListener;

    public void setOnGameLostListener(Runnable onGameLostListener) {
        this.onGameLostListener = onGameLostListener;
    }


    private OnGameWonListener onGameWonListener;

    public void setOnGameWonListener(OnGameWonListener listener) {
        this.onGameWonListener = listener;
    }

    private boolean isWithinCircle(PointF circlePosition, PointF touchPosition, float circleRadius) {
        float dx = circlePosition.x - touchPosition.x;
        float dy = circlePosition.y - touchPosition.y;
        return dx * dx + dy * dy < circleRadius * circleRadius;
    }

    private void checkWinCondition() {
        boolean allCirclesOnTargets = true;

        for (int i = 0; i < circlePositions.length; i++) {
            if (!isWithinCircle(targetPositions[i], circlePositions[i], circleRadius)) {
                allCirclesOnTargets = false;
                break;
            }
        }

        if (allCirclesOnTargets && !gameWon) {
            gameWon = true;
            stopTimer();
            if (onGameWonListener != null) {
                onGameWonListener.onGameWon();
            }
        }
    }
}