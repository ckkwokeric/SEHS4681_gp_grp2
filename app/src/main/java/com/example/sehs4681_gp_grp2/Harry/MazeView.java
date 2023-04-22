package com.example.sehs4681_gp_grp2.Harry;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeView extends View {

    private Paint ballPaint;
    private Paint wallPaint;
    private Paint winPaint;

    private float ballRadius = 25f;
    private float ballX = ballRadius * 2;
    private float ballY = ballRadius * 2;

    private List<RectF> wallList;
    private int wallCount = 15; // Number of walls to generate

    private RectF winRect;

    private int screenWidth;
    private int screenHeight;

    private MazeViewListener listener;

    private boolean gameWon = false;

    public void setMazeViewListener(MazeViewListener listener) {
        this.listener = listener;
    }

    public MazeView(Context context) {
        super(context);
        init();
    }

    public MazeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MazeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ballPaint = new Paint();
        ballPaint.setColor(Color.BLUE);

        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);

        winPaint = new Paint();
        winPaint.setColor(Color.GREEN);

        winRect = new RectF(getWidth() - 150, getHeight() - 150, getWidth() - 50, getHeight() - 50);
        wallList = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
        float winRectWidth = 100; // Set the desired width of the winRect
        float winRectHeight = 100; // Set the desired height of the winRect
        float winRectLeft = screenWidth - winRectWidth;
        float winRectTop = (screenHeight - winRectHeight) / 2;
        float winRectRight = screenWidth;
        float winRectBottom = winRectTop + winRectHeight;
        winRect = new RectF(winRectLeft, winRectTop, winRectRight, winRectBottom);
        generateWalls(wallCount);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMaze(canvas);
        canvas.drawCircle(ballX, ballY, ballRadius, ballPaint);
    }

    private void drawMaze(Canvas canvas) {
        for (RectF wall : wallList) {
            canvas.drawRect(wall, wallPaint);
        }
        canvas.drawRect(winRect, winPaint);
    }

    private void generateWalls(int count) {
        wallList.clear();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            RectF newWall;
            do {
                float left = random.nextInt(screenWidth - 200) + 50;
                float top = random.nextInt(screenHeight - 200) + 50;
                float right = left + random.nextInt(200) + 100;
                float bottom = top + random.nextInt(200) + 100;
                newWall = new RectF(left, top, right, bottom);
            } while (isOverlapping(newWall) || isTooCloseToStart(newWall) || isTooCloseToWin(newWall));
            wallList.add(newWall);
        }
    }

    private boolean isTooCloseToStart(RectF wall) {
        RectF startArea = new RectF(0, 0, ballRadius * 4, ballRadius * 4);
        return RectF.intersects(startArea, wall);
    }

    private boolean isTooCloseToWin(RectF wall) {
        RectF winArea = new RectF(screenWidth - 200, screenHeight - 200, screenWidth, screenHeight);
        return RectF.intersects(winArea, wall);
    }

    private boolean isOverlapping(RectF newWall) {
        for (RectF existingWall : wallList) {
            if (RectF.intersects(existingWall, newWall)) {
                return true;
            }
        }
        // Check for overlap with the win area
        if (RectF.intersects(winRect, newWall)) {
            return true;
        }
        return false;
    }

    public interface MazeViewListener {
        void onWin();
    }

    public void updateBallPosition(float deltaX, float deltaY) {
        float speedFactor = 2;
        float newX = ballX + deltaX * speedFactor;
        float newY = ballY + deltaY * speedFactor;

        // If the game is won, do not update the ball's position
        if (gameWon) {
            return;
        }

        // Boundary constraints
        if (newX - ballRadius < 0) {
            newX = ballRadius;
        } else if (newX + ballRadius > screenWidth) {
            newX = screenWidth - ballRadius;
        }

        if (newY - ballRadius < 0) {
            newY = ballRadius;
        } else if (newY + ballRadius > screenHeight) {
            newY = screenHeight - ballRadius;
        }

        // Collision detection
        RectF nextBallRect = new RectF(newX - ballRadius, newY - ballRadius, newX + ballRadius, newY + ballRadius);
        boolean collision = false;
        for (RectF wall : wallList) {
            if (RectF.intersects(wall, nextBallRect)) {
                collision = true;
                break;
            }
        }
        if (!collision) {
            ballX = newX;
            ballY = newY;
        }

        // Win condition
        if (RectF.intersects(winRect, nextBallRect)) {
            if (listener != null) {
                gameWon = true;
                listener.onWin();
            }
            return;
        }

        // Redraw the view
        invalidate();
    }
}