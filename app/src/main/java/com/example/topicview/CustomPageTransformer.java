package com.example.topicview;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * 作者： CCTV-1
 * 创建日期：2024/8/14 10
 * 描述：
 */
public class CustomPageTransformer implements ViewPager.PageTransformer{
    private static final float MIN_SCALE = 0.75f;

    @Override
    public void transformPage(View page, float position) {
        if (position < -1) { // [-Infinity, -1)
            // This page is way off-screen to the left.
            page.setAlpha(0);
        } else if (position <= 1) { // [-1, 1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = page.getHeight() * (1 - scaleFactor) / 2;
            float horzMargin = page.getWidth() * (1 - scaleFactor) / 2;
            if (position < 0) {
                page.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                page.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

            // Center the page on its new scale
            page.setTranslationY(vertMargin);
        } else { // (1, +Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0);
        }
    }
}
