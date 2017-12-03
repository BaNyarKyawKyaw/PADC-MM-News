package com.bnkk.padcmmnews.events;

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

    public static class NewsDataLoadedEvent{
        // when data is loaded

        private int loadedPageIndex;
        private List<NewsVO> loadedNews;

        public NewsDataLoadedEvent(int loadedPageIndex, List<NewsVO> loadedNews) {
            this.loadedPageIndex = loadedPageIndex;
            this.loadedNews = loadedNews;
        }

        public int getLoadedPageIndex() {
            return loadedPageIndex;
        }

        public List<NewsVO> getLoadedNews() {
            return loadedNews;
        }
    }
}
