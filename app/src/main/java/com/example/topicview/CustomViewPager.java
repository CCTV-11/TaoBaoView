package com.example.topicview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * 作者： CCTV-1
 * 创建日期：2024/8/14 10
 * 描述：
 */
public class CustomViewPager extends ViewPager {
    public static int mOffset;

    public CustomViewPager(Context context) {
        super(context);
        init();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 设置页面间的偏移量，这里设置为屏幕宽度的1/3
        mOffset = getResources().getDisplayMetrics().widthPixels / 2;
        setPageTransformer(true, new CustomPageTransformer());
    }

    private class CustomPageTransformer implements PageTransformer {
        @Override
        public void transformPage(View page, float position) {
            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 0) {
                page.setAlpha(1);
                page.setTranslationX(0);
                page.setScaleX(1);
                page.setScaleY(1);
            } else if (position <= 1) {
                page.setAlpha(1);
                page.setTranslationX(-mOffset * position);
                page.setScaleX(1);
                page.setScaleY(1);
            } else {
                page.setAlpha(0);
            }
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 重新测量每个子视图的大小，以便它们可以部分重叠
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        // 调整每个子视图的位置，使得第二页的一部分显示在第一页的末尾
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i == 1) {
                // 第二页向左偏移
                child.layout(-mOffset, 0, child.getMeasuredWidth() - mOffset, child.getMeasuredHeight());
            } else {
                // 其他页正常布局
                child.layout(l, t, r, b);
            }
        }
    }

    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        super.setPageTransformer(reverseDrawingOrder, transformer);
    }

}
