package com.bnkk.padcmmnews.components;

import android.content.Context;
import android.text.style.IconMarginSpan;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bnkk.padcmmnews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by E5-575G on 11/25/2017.
 */

public class EmptyViewPod extends RelativeLayout {

    @BindView(R.id.iv_empty)
    ImageView ivEmpty;

    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    public EmptyViewPod(Context context) {
        super(context);
    }

    public EmptyViewPod(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyViewPod(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    public void setEmptyData(int emptyImageID, String emptyMsg) {
        ivEmpty.setImageResource(emptyImageID);
        tvEmpty.setText(emptyMsg);
    }

    public void setEmptyData(String emptyMsg) {
        tvEmpty.setText(emptyMsg);
    }
}
