package com.satunol.satunolcodetest1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.Toast;

import com.satunol.satunolcodetest1.model.PointModel;

import java.util.List;

public class CanvasView extends View {
    Paint spot, rect;
    List<PointModel> pointModelList;
    int xPosition, yPosition, sideLength;

    public CanvasView(Context context) {
        super(context);
        init();
    }

    private void init() {
        spot = new Paint();
        spot.setColor(Color.RED);
        spot.setStyle(Paint.Style.FILL);

        rect = new Paint();
        rect.setColor(Color.parseColor("#FFC000"));
        rect.setStyle(Paint.Style.STROKE);
        rect.setStrokeWidth(4);

    }

    public void setPointList(List<PointModel> pointModelList) {
        // initializing all variables.
        this.pointModelList = pointModelList;
        invalidate();
    }

    public void setRectPosition(int x, int y, int sideLength) {
        this.xPosition = x - (sideLength / 2);
        this.yPosition = y - (sideLength / 2);
        this.sideLength = sideLength;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        if (pointModelList != null) {
            for (PointModel point : pointModelList) {
                float x = point.getX();
                float y = point.getY();
                if (x > xPosition && x < (xPosition + sideLength) && y > yPosition && y < (yPosition + (sideLength))) {
                    canvas.drawCircle(x, y, 3, spot);
                }
            }
        } else {
            Toast.makeText(getContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }
        drawRectangle(canvas);
    }

    protected void drawRectangle(Canvas canvas) {
        canvas.drawRect(xPosition,yPosition, xPosition + sideLength, yPosition + sideLength, rect);
    }

}
