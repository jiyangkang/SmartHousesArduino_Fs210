package com.hqyj.dev.smarthousesarduino.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jiyangkang on 2016/6/5 0005.
 */
public class DrawBottom extends View {

    private Rect dstRect, dstRect1;

    private Paint mPaint, codePaint, submitPaint;

    private Rect codeRect, codeRect1, submit, submit1;
    private boolean isSetting = true;

    private boolean iscodeSelect = false;
    private boolean isSubmitSelect = false;

    public void setSetting(boolean setting) {
        isSetting = setting;
    }

    public DrawBottom(Context context) {
        this(context, null);
    }

    public DrawBottom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        mPaint = new Paint();
        codePaint = new Paint();
        submitPaint = new Paint();
        dstRect = new Rect(0, 0, width, height / 9);
        dstRect1 = new Rect(0, 0, width - 3, height / 9 - 3);

        codeRect = new Rect(dstRect.right - dstRect.width() / 6, dstRect.top + 2, dstRect.right - 4, dstRect.top + dstRect.height() / 2);
        codeRect1 = new Rect(dstRect.right - dstRect.width() / 6, dstRect.top + 2, dstRect.right - 6, dstRect.top + dstRect.height() / 2 - 2);
        submit = new Rect(codeRect.left - codeRect.width() - 4, dstRect.top + 2, codeRect.left - 4, dstRect.top + dstRect.height() / 2);
        submit1 = new Rect(codeRect.left - codeRect.width() - 4, dstRect.top + 2, codeRect.left - 6, dstRect.top + dstRect.height() / 2 - 2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.argb(0xaf, 0x00, 0x00, 0x00));
        canvas.drawRect(dstRect, mPaint);
        mPaint.setColor(Color.argb(0xff, 0x00, 0xa0, 0x60));
        canvas.drawRect(dstRect1, mPaint);

        mPaint.setColor(Color.argb(0xaf, 0x00, 0x00, 0x00));
        mPaint.setTextSize(18);
        mPaint.setFakeBoldText(true);
        String projectName = "华清远见研发中心:dev.hqyj.com  技术支持QQ：2306275952";
        canvas.drawText(projectName,
                dstRect.left,
                dstRect.top + mPaint.getTextSize() + 2, mPaint);
        mPaint.setColor(Color.argb(0xff, 0xff, 0xff, 0xff));
        canvas.drawText(projectName,
                dstRect.left,
                dstRect.top + mPaint.getTextSize(), mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(codeRect, mPaint);

        codePaint.setAntiAlias(true);
        if (iscodeSelect) {
            codePaint.setColor(Color.BLACK);
        } else {
            codePaint.setColor(Color.GRAY);
        }
        canvas.drawRect(codeRect1, codePaint);


        if (isSetting) {
            canvas.drawRect(submit, mPaint);
            submitPaint.setAntiAlias(true);
            if (isSubmitSelect){
                submitPaint.setColor(Color.BLACK);
            }else {
                submitPaint.setColor(Color.GRAY);
            }
            canvas.drawRect(submit1, submitPaint);

            mPaint.setColor(Color.argb(0xff, 0xff, 0xff, 0xff));
            mPaint.setTextSize(14);
            String getSubmit = "进入主页面";
            canvas.drawText(getSubmit,
                    submit.left + submit.width() / 2 - getSubmit.length() * mPaint.getTextSize() / 2,
                    submit.top + mPaint.getTextSize() + 2, mPaint);
        }


        mPaint.setColor(Color.argb(0xff, 0xff, 0xff, 0xff));

        mPaint.setTextSize(14);
        String getCode = "获取二维码";
        canvas.drawText(getCode,
                codeRect.left + codeRect.width() / 2 - getCode.length() * mPaint.getTextSize() / 2,
                codeRect.top + mPaint.getTextSize() + 2, mPaint);

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
            height = getPaddingTop() + dstRect.height() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    private OnCodeClicked onCodeClicked;

    public void setOnCodeClicked(OnCodeClicked onCodeClicked) {
        this.onCodeClicked = onCodeClicked;
    }

    public interface OnCodeClicked {
        void onCodeClicked();
    }

    private OnSubmitClicked onSubmitClicked;

    public void setOnSubmitClicked(OnSubmitClicked onSubmitClicked) {
        this.onSubmitClicked = onSubmitClicked;
    }

    public interface OnSubmitClicked {
        void onSubmitClicked();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x > codeRect.left && x < codeRect.right && y > codeRect.top && y < codeRect.bottom) {
                    iscodeSelect = true;
                } else if (isSetting && x > submit.left && x < submit.right && y > submit.top && y < submit.bottom) {
                    isSubmitSelect = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (x > codeRect.left && x < codeRect.right && y > codeRect.top && y < codeRect.bottom) {
                    iscodeSelect = false;
                    if (onCodeClicked != null) {
                        onCodeClicked.onCodeClicked();
                    }
                } else if (isSetting && x > submit.left && x < submit.right && y > submit.top && y < submit.bottom) {
                    isSubmitSelect = false;
                    if (onSubmitClicked != null) {
                        onSubmitClicked.onSubmitClicked();
                    }
                }
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }
}
