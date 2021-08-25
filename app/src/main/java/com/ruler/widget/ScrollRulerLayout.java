package com.ruler.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;

/**
 * Created to : RulerView的载体.
 * GitHub -> https://github.com/WangcWj/AndroidScrollRuler
 * 提交issues联系作者.
 *
 * @author WANG
 * @date 2019/3/21
 */
public class ScrollRulerLayout extends ViewGroup implements ScrollSelected {
    private static final String TAG = "ScrollRulerLayout";
    private RulerView mRulerView;
    private ImageView mCenterPointer;
    private Paint mLinePaint;
    private int mLineWidth;
    private int mPadding;
    private int mPointerHeight;

    public ScrollRulerLayout(Context context) {
        this(context, null);
    }

    public ScrollRulerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollRulerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLineWidth = dp2px(1);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setColor(Color.parseColor("#00000000"));
        mPadding = dp2px(10);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.icon_center_pointer, options);
        mPointerHeight = options.outHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (null != mRulerView) {
            mRulerView.measure(widthMeasureSpec, heightMeasureSpec);
        }
        if (null != mCenterPointer) {
            LayoutParams layoutParams = mCenterPointer.getLayoutParams();
            mCenterPointer.measure(layoutParams.width, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (null != mRulerView) {
//            MarginLayoutParams lp = (MarginLayoutParams) mRulerView.getLayoutParams();
            int left = getPaddingLeft();
            int top = getPaddingTop();
            int right = getPaddingRight() + getMeasuredWidth();
            int bottom = getPaddingBottom() + getMeasuredHeight() - mLineWidth;
            mRulerView.layout(left, top, right, bottom);
        }
        if (null != mCenterPointer) {
            int measuredWidth = mCenterPointer.getMeasuredWidth();
            int width = getWidth() + getPaddingLeft() + getPaddingRight();
            int left = (width - measuredWidth) / 2;
            int right = (width + measuredWidth) / 2;
            int lineWidth = mRulerView.getBiggerLineWidth() / 2;
            int top = getHeight() - mPointerHeight;
            Log.d(TAG, "top = " + top + "---" + mPointerHeight);
            mCenterPointer.layout(left + lineWidth, top, right + lineWidth, top + mPointerHeight);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), mLinePaint);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRulerView = new RulerView(getContext());
        mCenterPointer = new ImageView(getContext());
        mCenterPointer.setImageResource(R.drawable.icon_center_pointer);
        MarginLayoutParams layoutParams = new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = mPadding;
        layoutParams.rightMargin = mPadding;
        mRulerView.setLayoutParams(layoutParams);
        mCenterPointer.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        mRulerView.setScrollSelected(this);
        addView(mRulerView);
        addView(mCenterPointer);
    }

    public void setScope(int start, int end, int offSet) {
        if (null != mRulerView) {
            mRulerView.setScope(start, end, offSet);
        }
    }

    public void setRulerViewMargin(int left, int top, int right, int bottom) {
        MarginLayoutParams layoutParams = (MarginLayoutParams) mRulerView.getLayoutParams();
        setRulerViewMargin(layoutParams, left, top, right, bottom);
    }

    public void setCurrentItem(int index) {
        if (null != mRulerView) {
            mRulerView.setCurrentItem(index);
        }
    }

    public void setRulerViewMargin(MarginLayoutParams layoutParams, int left, int top, int right, int bottom) {
        layoutParams.leftMargin = left;
        layoutParams.topMargin = top;
        layoutParams.rightMargin = right;
        layoutParams.bottomMargin = bottom;
        mRulerView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onDetachedFromWindow() {
        mRulerView.destroy();
        mRulerView = null;
        mCenterPointer = null;
        super.onDetachedFromWindow();
    }

    private int dp2px(float dp) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dp + 0.5f);
    }


    @Override
    public void selected(String selected) {
        Toast.makeText(getContext(), selected, Toast.LENGTH_SHORT).show();
    }
}
