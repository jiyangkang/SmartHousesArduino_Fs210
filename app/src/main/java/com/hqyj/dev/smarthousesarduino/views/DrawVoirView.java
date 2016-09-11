package com.hqyj.dev.smarthousesarduino.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.hqyj.dev.smarthousesarduino.R;
import com.hqyj.dev.smarthousesarduino.modules.Module;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class DrawVoirView extends View {

    private boolean isDefault = true;

    private Module module = null;

    private Rect dstRect;

    private Rect titleRect, titleShadowRect;
    private Rect bodyRect, bodyShadowRect;

    private Bitmap bitmapPin;
    private Rect pinRect, orPinRect;

    private Context mContext;

    private Bitmap bitmap1, bitmap2;
    private Rect orBimap1, orBitmap2, dstBitmap1;
    private Paint mPaint;

    private Rect showRect;

    private Bitmap bitmapDefault = null;
    private Rect bitmapDefaultRect;
    private Rect bitmapDefaultdstRect;

    private String valueToShow = "显示数据";
    private String name = "华清远见";

    private int degree = 0;


//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case 1:
//                    invalidate();
//                    break;
//            }
//        }
//    };


    public void setModule(Module module) {
        this.module = module;
        bitmapDefault = null;
        name = this.module.getName();
        if (this.module.getBitmapToshow() != null){
            bitmap1 = BitmapFactory.decodeResource(mContext.getResources(), module.getBitmapToshow()[0]);
            bitmap2 = BitmapFactory.decodeResource(mContext.getResources(), module.getBitmapToshow()[1]);
            orBimap1 = new Rect(0,0,bitmap1.getWidth(),bitmap1.getHeight());
            orBitmap2 = new Rect(0,0,bitmap2.getWidth(),bitmap2.getHeight());


        } else {
            bitmap1 = bitmap2 = null;
        }

        this.module.setOnValueReceived(new Module.OnValueReceived() {
            @Override
            public void onvalueReceived(String value, int degree) {
//                Log.d("VALUE+++++", "onvalueReceived: " + value);
                valueToShow = value;
                setDegree(degree);
//                handler.sendEmptyMessage(1);
                postInvalidate();
            }
        });
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public DrawVoirView(Context context) {
        this(context, null);
    }

    public DrawVoirView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawVoirView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        int width = context.getResources().getDisplayMetrics().widthPixels;
        dstRect = new Rect(0, 0, width / 5, width / 5);
        mPaint = new Paint();
        bitmapPin = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin);


        bitmapDefault = BitmapFactory.decodeResource(context.getResources(), R.drawable.hqyj);
        int bW = bitmapDefault.getWidth();
        int bH = bitmapDefault.getHeight();
        bitmapDefaultRect = new Rect(0, 0, bW, bH);
        int bdH = dstRect.width() * bH / bW;
        bitmapDefaultdstRect = new Rect(dstRect.left, dstRect.top + (dstRect.height() - bdH)/2,
                dstRect.right, dstRect.top + 3 * bdH/2);

        int wPin = bitmapPin.getWidth();
        int hPin = bitmapPin.getHeight();
        orPinRect = new Rect(0, 0, wPin, hPin);
        int orWPin = dstRect.width() / 5;
        int orHPin = orWPin * hPin / wPin;
        pinRect = new Rect(0, 0, orWPin, orHPin);

        titleRect = new Rect(0, pinRect.top + pinRect.height() / 4, dstRect.right - 4, pinRect.bottom);
        titleShadowRect = new Rect(2, pinRect.top + pinRect.height() / 4 + 2, dstRect.right, pinRect.bottom + 2);
        bodyRect = new Rect(titleRect.left, titleRect.bottom, titleRect.right, dstRect.bottom - 5);
        bodyShadowRect = new Rect(titleRect.left + 2, titleRect.bottom + 2, dstRect.right, dstRect.bottom);

        showRect = new Rect(dstRect.left, dstRect.bottom - dstRect.height()/6,dstRect.right, dstRect.bottom);
        dstBitmap1 = new Rect(bodyRect.left+bodyRect.width()/4,bodyRect.top+5, bodyRect.right - bodyRect.width()/4, showRect.top );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.argb(0xaf, 0x0a, 0x0a, 0x0a));
        canvas.drawRect(titleShadowRect, mPaint);
        mPaint.setColor(Color.argb(0xaf, 0x0a, 0xff, 0x0a));
        canvas.drawRect(titleRect, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawBitmap(bitmapPin, orPinRect, pinRect, mPaint);
        mPaint.setColor(Color.argb(0xff, 0xa3, 0xa3, 0xa3));
        canvas.drawRect(bodyShadowRect, mPaint);
        mPaint.setColor(Color.argb(0xff, 0xf3, 0xf3, 0xf3));
        canvas.drawRect(bodyRect, mPaint);

        mPaint.setAntiAlias(true);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(titleRect.width() / 11);
        mPaint.setFakeBoldText(true);

        mPaint.setColor(Color.argb(0xff, 0x0a, 0x0a, 0x0a));
        canvas.drawText(name,
                titleRect.left + titleRect.width() / 8+ 2,
                titleRect.top + titleRect.height() / 2 + mPaint.getTextSize() / 2 + 2, mPaint);
        mPaint.setColor(Color.argb(0xff, 0xff, 0xff, 0xff));
        canvas.drawText(name,
                titleRect.left + titleRect.width() / 8,
                titleRect.top + titleRect.height() / 2 + mPaint.getTextSize() / 2, mPaint);
        if (bitmapDefault == null) {
            if ((bitmap1 != null) && (bitmap2 != null)) {
                canvas.drawBitmap(bitmap2, orBitmap2, dstBitmap1, mPaint);
                canvas.save();
                canvas.rotate(120, dstBitmap1.left + dstBitmap1.width() / 2, dstBitmap1.top + dstBitmap1.height() / 2);
                canvas.drawBitmap(bitmap1, orBimap1, dstBitmap1, mPaint);
                canvas.restore();
            } else {
                mPaint.setTextSize(dstRect.width() / 4);
                mPaint.setColor(Color.argb(0xff, 0x0a, 0xfa, 0x0a));
                String showName = name.charAt(0) + "";
                canvas.drawText(showName, dstBitmap1.left + dstBitmap1.width() / 2 - showName.length() * mPaint.getTextSize() / 2,
                        dstBitmap1.top + dstBitmap1.height() / 2 + mPaint.getTextSize() / 2, mPaint);
            }

            mPaint.setColor(Color.argb(0xff, 0x00, 0x00, 0x00));
            mPaint.setTextSize(dstRect.width() / 11);
            canvas.drawText(valueToShow, showRect.left + mPaint.getTextSize(),
                    showRect.top + showRect.height() / 2 + mPaint.getTextSize() / 2, mPaint);
        }else {
            canvas.drawBitmap(bitmapDefault, bitmapDefaultRect, bitmapDefaultdstRect, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = getPaddingLeft() + getPaddingRight() + dstRect.width();
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = getPaddingLeft() + getPaddingRight() + dstRect.width();
        }

        setMeasuredDimension(width, height);
    }
}
