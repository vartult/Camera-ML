package com.example.nitya.cameraml.Helper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

public class TextGraphic extends GraphicOverlay.Graphic {

    private static final int TEXT_COLOR= Color.GREEN;
    private static final float TEXT_SIZE=50.0f;
    public static final float STROKE_WIDTH=5.0f;

    private final Paint rectPaint,textPaint;
    private final FirebaseVisionText.Line text;

    public TextGraphic(GraphicOverlay overlay,FirebaseVisionText.Line text) {
        super(overlay);
        Log.i("kaaaaaaaaaaaaaaaaam","anotherrrrrrrrrrrrrr kaaaaaaaaaaaaammmmm");
        this.text=text;

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

        if (text==null)
        {
            throw new IllegalStateException("NUll text found");
        }

        RectF rect=new RectF(text.getBoundingBox());
        rect.left=translateX(rect.left);
        rect.top=translateY(rect.top);
        rect.right=translateX(rect.right);
        rect.bottom=translateY(rect.bottom);

        canvas.drawRect(rect,rectPaint);

        canvas.drawText(text.getText(),rect.left,rect.bottom,textPaint);



    }

}
