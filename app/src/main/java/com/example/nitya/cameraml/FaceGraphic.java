package com.example.nitya.cameraml;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.nitya.cameraml.Helper.GraphicOverlay;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;

public class FaceGraphic extends GraphicOverlay.Graphic {

    private final Paint rectPaint;
    FirebaseVisionFace face;

    private static final int TEXT_COLOR= Color.RED;
    public static final float STROKE_WIDTH=5.0f;

    public FaceGraphic(GraphicOverlay overlay, FirebaseVisionFace face) {
        super(overlay);
        this.face=face;

        rectPaint=new Paint();
        rectPaint.setColor(TEXT_COLOR);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(STROKE_WIDTH);

        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas) {

        RectF rect=new RectF(face.getBoundingBox());
        rect.left=translateX(rect.left);
        rect.top=translateY(rect.top);
        rect.right=translateX(rect.right);
        rect.bottom=translateY(rect.bottom);

        canvas.drawRect(rect,rectPaint);

    }
}
