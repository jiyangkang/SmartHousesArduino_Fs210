package com.hqyj.dev.smarthousesarduino.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hqyj.dev.smarthousesarduino.R;
import com.hqyj.dev.smarthousesarduino.modules.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class DrawCtrlView extends View {

    private final String TAG = DrawCtrlView.class.getSimpleName();

    private boolean isHaveSelectBar = false;
    private int ctrlCount = 1;

    private Rect dstRect;

    private Rect titleRect, titleShadowRect;
    private Rect bodyRect, bodyShadowRect;

    private Bitmap bitmapPin;
    private Rect pinRect, orPinRect;

    private Rect selectBarRect, selecterRect;


    private String name = "默认节点";

    private String submit = "提交";

    private Rect button1, button2, button3, button4, button5, button6, button7, button8, button9;
    private Paint button1P, button2P, button3P, button4P, button5P, button6P, button7P, button8P, button9P;
    private String button1Text, button2Text, button3Text, button4Text, button5Text, button6Text, button7Text, button8Text, button9Text;

    private Rect[] rects;

    private Rect stateR;

    private String state;

    private List<String> cmdList;

    private Module module;

    private Paint mPaint;

    private Context mContext;


    public void setModule(Module module) {
        this.module = module;
        name = module.getName();
        ctrlCount = module.getCtrlCount();
        isHaveSelectBar = module.isHaveSelectBar();

        HashMap<String, Byte> cmdHashMap = module.getCmdHash();

        if (cmdHashMap != null) {
            ctrlCount = cmdHashMap.size();
            for (Object o : cmdHashMap.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                cmdList.add((String) entry.getKey());
//                Log.d(TAG, String.format("%s:+:%02X", entry.getKey(), entry.getValue()));
            }
        }
        setButtonRect();

        this.module.setOnValueReceived(new Module.OnValueReceived() {
            @Override
            public void onvalueReceived(String value, int degree) {
                state = String.format("当前状态：%s", value);
                postInvalidate();
            }
        });

        postInvalidate();
    }

    private void setButtonRect() {
        switch (ctrlCount) {
            case 2:

                button1 = new Rect(bodyRect.left + bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 3 / 8,
                        bodyRect.left + bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 5 / 8);

                button2 = new Rect(bodyRect.right - bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 3 / 8,
                        bodyRect.right - bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 5 / 8);
                button1Text = cmdList.get(0);
                button2Text = cmdList.get(1);
                break;
            case 3:
                button1 = new Rect(bodyRect.left + bodyRect.width() / 4,
                        bodyRect.top + bodyRect.height() * 4 / 10,
                        bodyRect.left + bodyRect.width() * 3 / 4,
                        bodyRect.top + bodyRect.height() * 6 / 10);

                button2 = new Rect(bodyRect.left + bodyRect.width() / 4,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.left + bodyRect.width() * 3 / 4,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button3 = new Rect(bodyRect.left + bodyRect.width() / 4,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.left + bodyRect.width() * 3 / 4,
                        bodyRect.top + bodyRect.height() * 9 / 10);
                button1Text = cmdList.get(0);
                button2Text = cmdList.get(1);
                button3Text = cmdList.get(2);
                break;
            case 4:
                button1 = new Rect(bodyRect.left + bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() / 7,
                        bodyRect.left + bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 3 / 7);

                button2 = new Rect(bodyRect.right - bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() / 7,
                        bodyRect.right - bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 3 / 7);

                button3 = new Rect(bodyRect.left + bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 4 / 7,
                        bodyRect.left + bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 6 / 7);

                button4 = new Rect(bodyRect.right - bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 4 / 7,
                        bodyRect.right - bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 6 / 7);

                button1Text = cmdList.get(0);
                button2Text = cmdList.get(1);
                button3Text = cmdList.get(2);
                button4Text = cmdList.get(3);
                break;
            case 5:
                button1 = new Rect(bodyRect.left + bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.left + bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button2 = new Rect(bodyRect.right - bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.right - bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button3 = new Rect(bodyRect.left + bodyRect.width() / 3,
                        bodyRect.top + bodyRect.height() * 4 / 10,
                        bodyRect.left + bodyRect.width() * 2 / 3,
                        bodyRect.top + bodyRect.height() * 6 / 10);

                button4 = new Rect(bodyRect.left + bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.left + bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 9 / 10);

                button5 = new Rect(bodyRect.right - bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.right - bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 9 / 10);
                button1Text = cmdList.get(0);
                button2Text = cmdList.get(1);
                button3Text = cmdList.get(2);
                button4Text = cmdList.get(3);
                button5Text = cmdList.get(4);
                break;
            case 6:
                button1 = new Rect(bodyRect.left + bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.left + bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button2 = new Rect(bodyRect.right - bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.right - bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button3 = new Rect(bodyRect.left + bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 4 / 10,
                        bodyRect.left + bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 6 / 10);

                button4 = new Rect(bodyRect.right - bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 4 / 10,
                        bodyRect.right - bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 6 / 10);

                button5 = new Rect(bodyRect.left + bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.left + bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 9 / 10);

                button6 = new Rect(bodyRect.right - bodyRect.width() * 5 / 12,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.right - bodyRect.width() / 12,
                        bodyRect.top + bodyRect.height() * 9 / 10);

                button1Text = cmdList.get(0);
                button2Text = cmdList.get(1);
                button3Text = cmdList.get(2);
                button4Text = cmdList.get(3);
                button5Text = cmdList.get(4);
                button6Text = cmdList.get(5);
                break;
            case 7:
                button1 = new Rect(bodyRect.left + bodyRect.width() / 10,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.left + bodyRect.width() * 3 / 10,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button2 = new Rect(bodyRect.left + bodyRect.width() * 4 / 10,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.left + bodyRect.width() * 6 / 10,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button3 = new Rect(bodyRect.left + bodyRect.width() * 7 / 10,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.left + bodyRect.width() * 9 / 10,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button4 = new Rect(bodyRect.left + bodyRect.width() * 4 / 10,
                        bodyRect.top + bodyRect.height() * 4 / 10,
                        bodyRect.left + bodyRect.width() * 6 / 10,
                        bodyRect.top + bodyRect.height() * 6 / 10);

                button5 = new Rect(bodyRect.left + bodyRect.width() / 10,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.left + bodyRect.width() * 3 / 10,
                        bodyRect.top + bodyRect.height() * 9 / 10);

                button6 = new Rect(bodyRect.left + bodyRect.width() * 4 / 10,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.left + bodyRect.width() * 6 / 10,
                        bodyRect.top + bodyRect.height() * 9 / 10);

                button7 = new Rect(bodyRect.left + bodyRect.width() * 7 / 10,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.left + bodyRect.width() * 9 / 10,
                        bodyRect.top + bodyRect.height() * 9 / 10);
                button1Text = cmdList.get(0);
                button2Text = cmdList.get(1);
                button3Text = cmdList.get(2);
                button4Text = cmdList.get(3);
                button5Text = cmdList.get(4);
                button6Text = cmdList.get(5);
                button7Text = cmdList.get(6);
                break;
            case 8:
                button1 = new Rect(bodyRect.left + bodyRect.width() / 10,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.left + bodyRect.width() * 3 / 10,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button2 = new Rect(bodyRect.left + bodyRect.width() * 4 / 10,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.left + bodyRect.width() * 6 / 10,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button3 = new Rect(bodyRect.left + bodyRect.width() * 7 / 10,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.left + bodyRect.width() * 9 / 10,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button4 = new Rect(bodyRect.left + bodyRect.width() / 4,
                        bodyRect.top + bodyRect.height() * 4 / 10,
                        bodyRect.left + bodyRect.width() * 9 / 20,
                        bodyRect.top + bodyRect.height() * 6 / 10);

                button5 = new Rect(bodyRect.left + bodyRect.width() * 11 / 20,
                        bodyRect.top + bodyRect.height() * 4 / 10,
                        bodyRect.left + bodyRect.width() * 3 / 4,
                        bodyRect.top + bodyRect.height() * 6 / 10);

                button6 = new Rect(bodyRect.left + bodyRect.width() / 10,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.left + bodyRect.width() * 3 / 10,
                        bodyRect.top + bodyRect.height() * 9 / 10);

                button7 = new Rect(bodyRect.left + bodyRect.width() * 4 / 10,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.left + bodyRect.width() * 6 / 10,
                        bodyRect.top + bodyRect.height() * 9 / 10);

                button8 = new Rect(bodyRect.left + bodyRect.width() * 7 / 10,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.left + bodyRect.width() * 9 / 10,
                        bodyRect.top + bodyRect.height() * 9 / 10);
                button1Text = cmdList.get(0);
                button2Text = cmdList.get(1);
                button3Text = cmdList.get(2);
                button4Text = cmdList.get(3);
                button5Text = cmdList.get(4);
                button6Text = cmdList.get(5);
                button7Text = cmdList.get(6);
                button8Text = cmdList.get(7);
                break;
            case 9:

                button1 = new Rect(bodyRect.left + bodyRect.width() / 10,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.left + bodyRect.width() * 3 / 10,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button2 = new Rect(bodyRect.left + bodyRect.width() * 4 / 10,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.left + bodyRect.width() * 6 / 10,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button3 = new Rect(bodyRect.left + bodyRect.width() * 7 / 10,
                        bodyRect.top + bodyRect.height() / 10,
                        bodyRect.left + bodyRect.width() * 9 / 10,
                        bodyRect.top + bodyRect.height() * 3 / 10);

                button4 = new Rect(bodyRect.left + bodyRect.width() / 10,
                        bodyRect.top + bodyRect.height() * 4 / 10,
                        bodyRect.left + bodyRect.width() * 3 / 10,
                        bodyRect.top + bodyRect.height() * 6 / 10);

                button5 = new Rect(bodyRect.left + bodyRect.width() * 4 / 10,
                        bodyRect.top + bodyRect.height() * 4 / 10,
                        bodyRect.left + bodyRect.width() * 6 / 10,
                        bodyRect.top + bodyRect.height() * 6 / 10);

                button6 = new Rect(bodyRect.left + bodyRect.width() * 7 / 10,
                        bodyRect.top + bodyRect.height() * 4 / 10,
                        bodyRect.left + bodyRect.width() * 9 / 10,
                        bodyRect.top + bodyRect.height() * 6 / 10);

                button7 = new Rect(bodyRect.left + bodyRect.width() / 10,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.left + bodyRect.width() * 3 / 10,
                        bodyRect.top + bodyRect.height() * 9 / 10);

                button8 = new Rect(bodyRect.left + bodyRect.width() * 4 / 10,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.left + bodyRect.width() * 6 / 10,
                        bodyRect.top + bodyRect.height() * 9 / 10);

                button9 = new Rect(bodyRect.left + bodyRect.width() * 7 / 10,
                        bodyRect.top + bodyRect.height() * 7 / 10,
                        bodyRect.left + bodyRect.width() * 9 / 10,
                        bodyRect.top + bodyRect.height() * 9 / 10);
                button1Text = cmdList.get(0);
                button2Text = cmdList.get(1);
                button3Text = cmdList.get(2);
                button4Text = cmdList.get(3);
                button5Text = cmdList.get(4);
                button6Text = cmdList.get(5);
                button7Text = cmdList.get(6);
                button8Text = cmdList.get(7);
                button9Text = cmdList.get(8);
                break;
            case 1:
            default:
                button1 = new Rect(bodyRect.left + bodyRect.width() / 4,
                        bodyRect.top + bodyRect.height() * 3 / 8,
                        bodyRect.left + bodyRect.width() * 3 / 4,
                        bodyRect.top + bodyRect.height() * 5 / 8);
                button1Text = submit;
                break;
        }
    }

    private int buttonColorbg;
    private int buttonColorfo;

    public DrawCtrlView(Context context) {
        this(context, null);
    }

    public DrawCtrlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawCtrlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        int width = context.getResources().getDisplayMetrics().widthPixels;
        dstRect = new Rect(0, 0, width / 5, width / 5);
        mPaint = new Paint();
        buttonColorfo = Color.argb(0xff, 0xad, 0xad, 0xad);
        buttonColorbg = Color.argb(0xff, 0x2f, 0x2f, 0x2f);
        button1P = new Paint();
        button1P.setColor(buttonColorfo);
        button2P = new Paint();
        button2P.setColor(buttonColorfo);
        button3P = new Paint();
        button3P.setColor(buttonColorfo);
        button4P = new Paint();
        button4P.setColor(buttonColorfo);
        button5P = new Paint();
        button5P.setColor(buttonColorfo);
        button6P = new Paint();
        button6P.setColor(buttonColorfo);
        button7P = new Paint();
        button7P.setColor(buttonColorfo);
        button8P = new Paint();
        button8P.setColor(buttonColorfo);
        button9P = new Paint();
        button9P.setColor(buttonColorfo);
        bitmapPin = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin);


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

        selectBarRect = new Rect(dstRect.left + dstRect.width() / 11, dstRect.bottom - dstRect.height() / 10, dstRect.right - dstRect.width() / 11, dstRect.bottom - dstRect.height() / 15);
        selecterRect = new Rect(selectBarRect.left, selectBarRect.top - selectBarRect.height() / 2, selectBarRect.left + 16, selectBarRect.bottom + selectBarRect.height() / 2);
        cmdList = new ArrayList<>();

        stateR = new Rect(dstRect.left, dstRect.bottom, dstRect.right, dstRect.bottom + dstRect.height() / 5);
        state = String.format("当前状态：%s", "null");
        setButtonRect();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.argb(0xaf, 0x0a, 0x0a, 0x0a));
        canvas.drawRect(titleShadowRect, mPaint);
        mPaint.setColor(Color.argb(0xaf, 0xff, 0x63, 0x47));
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
                titleRect.left + titleRect.width() / 8 + 2,
                titleRect.top + titleRect.height() / 2 + mPaint.getTextSize() / 2 + 2, mPaint);
        mPaint.setColor(Color.argb(0xff, 0xff, 0xff, 0xff));
        canvas.drawText(name,
                titleRect.left + titleRect.width() / 8,
                titleRect.top + titleRect.height() / 2 + mPaint.getTextSize() / 2, mPaint);

        if (isHaveSelectBar) {
            mPaint.setColor(Color.argb(0xff, 0x0a, 0x0a, 0xff));
            canvas.drawRect(selectBarRect, mPaint);
            mPaint.setColor(Color.argb(0xff, 0xff, 0x0a, 0xff));
            canvas.drawRect(selecterRect, mPaint);
        }

        if (button1 != null) {
            drawButton(button1, button1P, canvas, button1Text);
        }
        if (button2 != null) {
            drawButton(button2, button2P, canvas, button2Text);
        }
        if (button3 != null) {
            drawButton(button3, button3P, canvas, button3Text);
        }
        if (button4 != null) {
            drawButton(button4, button4P, canvas, button4Text);
        }
        if (button5 != null) {
            drawButton(button5, button5P, canvas, button5Text);
        }
        if (button6 != null) {
            drawButton(button6, button6P, canvas, button6Text);
        }
        if (button7 != null) {
            drawButton(button7, button7P, canvas, button7Text);
        }
        if (button8 != null) {
            drawButton(button8, button8P, canvas, button8Text);
        }
        if (button9 != null) {
            drawButton(button9, button9P, canvas, button9Text);
        }

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(22);
        canvas.drawText(state, stateR.left, stateR.top + stateR.height() / 2, mPaint);

    }

    private void drawButton(Rect rect, Paint buttonPaint, Canvas canvas, String text) {
        mPaint.setColor(buttonColorbg);
        canvas.drawRect(rect, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(rect.left - 2, rect.top - 2, rect.right - 2, rect.bottom - 2, buttonPaint);
        mPaint.setTextSize(rect.width() / 4);
        mPaint.setColor(Color.argb(0xff, 0x1f, 0x1f, 0x1f));
        canvas.drawText(text,
                rect.left + rect.width() / 2 - text.length() * mPaint.getTextSize() / 2,
                rect.top + rect.height() / 2 + mPaint.getTextSize() / 2, mPaint);
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
            height = getPaddingTop() + getPaddingBottom() + dstRect.height() * 5 / 4;
        }
        setMeasuredDimension(width, height);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (button1 != null && isXYinRect(x, y, button1)) {
                    button1P.setColor(buttonColorbg);
                } else if (button2 != null && isXYinRect(x, y, button2)) {
                    button2P.setColor(buttonColorbg);
                } else if (button3 != null && isXYinRect(x, y, button3)) {
                    button3P.setColor(buttonColorbg);
                } else if (button4 != null && isXYinRect(x, y, button4)) {
                    button4P.setColor(buttonColorbg);
                } else if (button5 != null && isXYinRect(x, y, button5)) {
                    button5P.setColor(buttonColorbg);
                } else if (button6 != null && isXYinRect(x, y, button6)) {
                    button6P.setColor(buttonColorbg);
                } else if (button7 != null && isXYinRect(x, y, button7)) {
                    button7P.setColor(buttonColorbg);
                } else if (button8 != null && isXYinRect(x, y, button8)) {
                    button8P.setColor(buttonColorbg);
                } else if (button9 != null && isXYinRect(x, y, button9)) {
                    button9P.setColor(buttonColorbg);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (button1 != null && isXYinRect(x, y, button1)) {
                    button1P.setColor(buttonColorfo);
                    if (onSubmitClicked != null) {
                        if (cmdList.size()!=0){
                        onSubmitClicked.onSubmitClicked(module.getId(), cmdList.get(0));
                        }else {
                            onSubmitClicked.onSubmitClicked(module.getId(), null);
                        }
//                            Log.d(TAG, "onTouchEvent: " + module.getId());
                    }

                } else if (button2 != null && isXYinRect(x, y, button2)) {
                    button2P.setColor(buttonColorfo);
                    if (onSubmitClicked != null) {
                        onSubmitClicked.onSubmitClicked(module.getId(), cmdList.get(1));
//                            Log.d(TAG, "onTouchEvent: " + module.getId());
                    }
                } else if (button3 != null && isXYinRect(x, y, button3)) {
                    button3P.setColor(buttonColorfo);
                    if (onSubmitClicked != null) {
                        onSubmitClicked.onSubmitClicked(module.getId(), cmdList.get(2));
//                            Log.d(TAG, "onTouchEvent: " + module.getId());
                    }
                } else if (button4 != null && isXYinRect(x, y, button4)) {
                    button4P.setColor(buttonColorfo);
                    if (onSubmitClicked != null) {
                        onSubmitClicked.onSubmitClicked(module.getId(), cmdList.get(3));
//                            Log.d(TAG, "onTouchEvent: " + module.getId());
                    }
                } else if (button5 != null && isXYinRect(x, y, button5)) {
                    button5P.setColor(buttonColorfo);
                    if (onSubmitClicked != null) {
                        onSubmitClicked.onSubmitClicked(module.getId(), cmdList.get(4));
//                            Log.d(TAG, "onTouchEvent: " + module.getId());
                    }
                } else if (button6 != null && isXYinRect(x, y, button6)) {
                    button6P.setColor(buttonColorfo);
                    if (onSubmitClicked != null) {
                        onSubmitClicked.onSubmitClicked(module.getId(), cmdList.get(5));
//                            Log.d(TAG, "onTouchEvent: " + module.getId());
                    }
                } else if (button7 != null && isXYinRect(x, y, button7)) {
                    button7P.setColor(buttonColorfo);
                    if (onSubmitClicked != null) {
                        onSubmitClicked.onSubmitClicked(module.getId(), cmdList.get(6));
//                            Log.d(TAG, "onTouchEvent: " + module.getId());
                    }
                } else if (button8 != null && isXYinRect(x, y, button8)) {
                    button8P.setColor(buttonColorfo);
                    if (onSubmitClicked != null) {
                        onSubmitClicked.onSubmitClicked(module.getId(), cmdList.get(7));
//                            Log.d(TAG, "onTouchEvent: " + module.getId());
                    }
                } else if (button9 != null && isXYinRect(x, y, button9)) {
                    button9P.setColor(buttonColorfo);
                    if (onSubmitClicked != null) {
                        onSubmitClicked.onSubmitClicked(module.getId(), cmdList.get(8));
//                            Log.d(TAG, "onTouchEvent: " + module.getId());
                    }
                } else {
                    if (button1 != null) {
                        button1P.setColor(buttonColorfo);
                    }
                    if (button2 != null) {
                        button2P.setColor(buttonColorfo);
                    }
                    if (button3 != null) {
                        button3P.setColor(buttonColorfo);
                    }
                    if (button4 != null) {
                        button4P.setColor(buttonColorfo);
                    }
                    if (button5 != null) {
                        button5P.setColor(buttonColorfo);
                    }
                    if (button6 != null) {
                        button6P.setColor(buttonColorfo);
                    }
                    if (button7 != null) {
                        button7P.setColor(buttonColorfo);
                    }
                    if (button8 != null) {
                        button8P.setColor(buttonColorfo);
                    }
                    if (button9 != null) {
                        button9P.setColor(buttonColorfo);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isHaveSelectBar) {
                    if (x > selectBarRect.left + selecterRect.width() / 2 &&
                            x < selectBarRect.right - selecterRect.width() / 2 &&
                            y > selecterRect.top &&
                            y < selecterRect.bottom) {
                        int min = 0;
                        int max = 180;
                        int t = (int) ((max - min) * (x - selectBarRect.left - selecterRect.width() / 2) / (selectBarRect.width() - selecterRect.width()));
                        if (module != null) {
                            module.sendCMD(new byte[]{(byte) (t & 0xff)});
                        }
                        selecterRect.offsetTo((int) (x - selecterRect.width() / 2), selecterRect.top);
                    }
                }
                break;
        }
        postInvalidate();
        return true;
    }

    private boolean isXYinRect(float x, float y, Rect rect) {
        return x > rect.left && x < rect.right && y > rect.top && y < rect.bottom;
    }

    private OnSubmitClicked onSubmitClicked = null;

    public void setOnSubmitClicked(OnSubmitClicked onSubmitClicked) {
        this.onSubmitClicked = onSubmitClicked;
    }

    public interface OnSubmitClicked {
        void onSubmitClicked(int id, String whichClicked);
    }
}
