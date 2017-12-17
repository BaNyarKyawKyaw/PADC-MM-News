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

    private boolean isListEndReached = false;
    //private int visibleItemCount, pastVisibleItems, totalItemCount;
    //private int previousDy, currentDy;  // Dy means delta Y

    private OnSmartScrollListener mSmartScrollListener;

    public SmartScrollListener(OnSmartScrollListener mSmartScrollListener) {
        this.mSmartScrollListener = mSmartScrollListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        /*
        currentDy = dy;
        if (currentDy > previousDy) {
            // from top to bottom
            isListEndReached = true;
        } else if (currentDy < previousDy) {
            // from bottom to top
            isListEndReached = false;
        }
        */

        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int pastVisibleItems = ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();

        if ((visibleItemCount + pastVisibleItems) < totalItemCount) {
            isListEndReached = false;
        }

        //previousDy = currentDy;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
        super.onScrollStateChanged(recyclerView, scrollState);
        /*
        if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount
                    && !isListEndReached) {
                isListEndReached = true;
                mSmartScrollListener.onListEndReached();
            }
        }
        */

        if (scrollState == RecyclerView.SCROLL_STATE_IDLE
                && ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition()
                == recyclerView.getAdapter().getItemCount() - 1
                && isListEndReached) {
            isListEndReached = true;
            mSmartScrollListener.onListEndReached();
        }
    }
}
