package com.mathroda.dashcoin.core.util;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.mathroda.dashcoin.R;

public class CustomMarkerView extends MarkerView {
    private float yValue = 0;
    private final View tvContent;
    public CustomMarkerView (Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = (View) findViewById(R.id.circleIndicator);
    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        yValue = e.getY();
        tvContent.setVisibility(View.VISIBLE);
        super.refreshContent(e, highlight);
    }

    public float getYEntry(){
        return yValue;
    }


    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -(getWidth() / 2));
        }

        return mOffset;
    }}