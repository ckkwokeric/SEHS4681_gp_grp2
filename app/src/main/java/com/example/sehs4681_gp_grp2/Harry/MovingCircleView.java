package com.example.sehs4681_gp_grp2.Harry;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MovingCircleView extends View {
    private Paint paint;
    private float centerX;
    private float centerY;
    private float radius;

    public MovingCircleView(Context context) {
        super(context);
        init();
    }

    public MovingCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MovingCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void setCircleColor(int color) {
        paint.setColor(color);
        invalidate();
    }

    public void setCirclePosition(float x, float y) {
        centerX = x;
        centerY = y;
        invalidate();
    }

    public void setCircleRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, radius, paint);
    }
}