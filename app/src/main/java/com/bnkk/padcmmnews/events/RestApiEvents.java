package com.bnkk.padcmmnews.events;

import android.content.Context;

import com.bnkk.padcmmnews.data.vo.NewsVO;

import java.util.List;

/**
 * Created by E5-575G on 12/3/2017.
 */

public class RestApiEvents {

    public static class EmptyResponseEvent {
        // errors when response is empty
    }

    public static class ErrorInvokingAPIEvent {
        // errors when invoking API

        private String errorMsg;

        public ErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class NewsDataLoadedEvent {
        // when data is loaded

        private int loadedPageIndex;
        private List<NewsVO> loadedNews;
        private Context context;

        public NewsDataLoadedEvent(int loadedPageIndex, List<NewsVO> loadedNews, Context context) {
            this.loadedPageIndex = loadedPageIndex;
            this.loadedNews = loadedNews;
            this.context = context;
        }

        public int getLoadedPageIndex() {
            return loadedPageIndex;
        }

        public List<NewsVO> getLoadedNews() {
            return loadedNews;
        }

        public Context getContext() {
            return context;
        }
    }
}
