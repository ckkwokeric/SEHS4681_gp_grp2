package com.example.sehs4681_gp_grp2.Mark;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sehs4681_gp_grp2.HomeFragment;
import com.example.sehs4681_gp_grp2.R;

public class MazeView_Mark extends View implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private int cellX;
    private int cellY;

    private int winningX = 4;
    private int winningY = 0;

    private boolean gameWon = false;

    private int[][] maze = {
            {0, 0, 0, 1, 0},
            {0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0}
    };

    private Paint wallPaint;
    private Paint playerPaint;

    Button nextLevelButton;

    public MazeView_Mark(Context context, AttributeSet attrs) {
        super(context, attrs);

        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);

        playerPaint = new Paint();
        playerPaint.setColor(Color.BLUE);

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        cellX = 0;
        cellY = 4;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float cellWidth = (float) getWidth() / maze[0].length;
        float cellHeight = (float) getHeight() / maze.length;

        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[y].length; x++) {
                if (maze[y][x] == 1) {
                    canvas.drawRect(x * cellWidth, y * cellHeight, (x + 1) * cellWidth, (y + 1) * cellHeight, wallPaint);
                }
            }
        }

        canvas.drawCircle((cellX + 0.5f) * cellWidth, (cellY + 0.5f) * cellHeight, Math.min(cellWidth, cellHeight) * 0.25f, playerPaint);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (gameWon) {
            return;
        }

        float x = event.values[0];
        float y = event.values[1];

        if (Math.abs(x) > Math.abs(y)) {
            if (x > 5) {
                if (cellX > 0 && maze[cellY][cellX - 1] == 0) {
                    cellX--;
                }
            } else if (x < -5) {
                if (cellX < maze[cellY].length - 1 && maze[cellY][cellX + 1] == 0) {
                    cellX++;
                }
            }
        } else {
            if (y > 5) {
                if (cellY < maze.length - 1 && maze[cellY + 1][cellX] == 0) {
                    cellY++;
                }
            } else if (y < -5) {
                if (cellY > 0 && maze[cellY - 1][cellX] == 0) {
                    cellY--;
                }
            }
        }

        invalidate();

        if (cellX == winningX && cellY == winningY) {
            if (cellX == winningX && cellY == winningY) {
                Toast.makeText(getContext(), "You won!", Toast.LENGTH_SHORT).show();
                gameWon = true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // You may handle changes in the accuracy of the sensor here if needed
    }
}