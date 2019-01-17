package com.example.nitya.cameraml.Helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.nitya.cameraml.Helper.GraphicOverlay;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;

public class FaceGraphic extends GraphicOverlay.Graphic {

    private final Paint rectPaint,textPaint;
    FirebaseVisionFace face;

    private static final int TEXT_COLOR= Color.RED;
    private static final float TEXT_SIZE=70.0f;
    public static final float STROKE_WIDTH=5.0f;

    String no;

    public FaceGraphic(GraphicOverlay overlay, FirebaseVisionFace face,String no) {
        super(overlay);
        this.face=face;
        this.no=no;

        rectPaint=new Paint();
        rectPaint.setColor(TEXT_COLOR);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(STROKE_WIDTH);

        textPaint=new Paint();
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextSize(TEXT_SIZE);

        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas) {

        if (face==null)
        {
            throw new IllegalStateException("NUll text found");
        }

        RectF rect=new RectF(face.getBoundingBox());
        rect.left=translateX(rect.left);
        rect.top=translateY(rect.top);
        rect.right=translateX(rect.right);
        rect.bottom=translateY(rect.bottom);

        canvas.drawRect(rect,rectPaint);

        canvas.drawText(no,rect.left,rect.bottom,textPaint);

    }
}
