package com.example.doaa.movieapp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by doaa on 12/3/2016.
 */

public class CustomListView extends ListView {

    private android.view.ViewGroup.LayoutParams params;
    private int oldCount = 0;

    public CustomListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (getCount() != oldCount)
        {
            int height = getChildAt(0).getHeight() + 1 ;
            oldCount = getCount();
            params = getLayoutParams();
            params.height = getCount() * height;
            setLayoutParams(params);
        }

        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED,0) );

        int childHeight = getMeasuredHeight() - (getListPaddingTop() + getListPaddingBottom() +  getVerticalFadingEdgeLength() * 2);

        int fullHeight = getListPaddingTop() + getListPaddingBottom() + childHeight*(getCount());

        setMeasuredDimension(getMeasuredWidth(), fullHeight);
    }
}
