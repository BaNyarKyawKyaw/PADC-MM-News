package com.bnkk.padcmmnews.components;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by E5-575G on 11/29/2017.
 */

public class SmartScrollListener extends RecyclerView.OnScrollListener {

    public interface OnSmartScrollListener {
        void onListEndReached();
    }

    private int visibleItemCount, pastVisibleItems, totalItemCount;
    private boolean isListEndReached = false;
    private int previousDy, currentDy;  // Dy means delta Y

    private OnSmartScrollListener mSmartScrollListener;

    public SmartScrollListener(OnSmartScrollListener mSmartScrollListener) {
        this.mSmartScrollListener = mSmartScrollListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        currentDy = dy;
        if (currentDy > previousDy) {
            // from top to bottom
        } else if (currentDy < previousDy) {
            // from bottom to top
            isListEndReached = false;
        }

        visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        totalItemCount = recyclerView.getLayoutManager().getItemCount();
        pastVisibleItems = ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();

        previousDy = currentDy;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
        super.onScrollStateChanged(recyclerView, scrollState);
        if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount
                    && !isListEndReached) {
                isListEndReached = true;
                mSmartScrollListener.onListEndReached();
            }
        }
    }
}
