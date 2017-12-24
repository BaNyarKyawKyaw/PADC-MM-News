package com.bnkk.padcmmnews.activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bnkk.padcmmnews.R;
import com.bnkk.padcmmnews.adapters.NewsAdapter;
import com.bnkk.padcmmnews.components.EmptyViewPod;
import com.bnkk.padcmmnews.components.SmartRecyclerView;
import com.bnkk.padcmmnews.components.SmartScrollListener;
import com.bnkk.padcmmnews.data.models.NewsModel;
import com.bnkk.padcmmnews.data.vo.NewsVO;
import com.bnkk.padcmmnews.delegates.NewsItemDelegate;
import com.bnkk.padcmmnews.events.RestApiEvents;
import com.bnkk.padcmmnews.events.TapNewsEvent;
import com.bnkk.padcmmnews.persistence.NewsContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListActivity extends BaseActivity
        implements NewsItemDelegate, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int NEWS_LIST_LOADER_ID = 1001;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.rv_news_list)
    SmartRecyclerView srvNews;

    @BindView(R.id.vp_empty_news)
    EmptyViewPod vpEmptyNews;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private SmartScrollListener mSmartScrollListener;
    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */

                //drawerLayout.openDrawer(GravityCompat.START);
                Intent intent = LoginRegisterActivity.newIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        srvNews.setEmptyView(vpEmptyNews);
        srvNews.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        mNewsAdapter = new NewsAdapter(getApplicationContext(), this);
        srvNews.setAdapter(mNewsAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReached() {

                Snackbar.make(srvNews, "Loading new data", Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(true);

                NewsModel.getObjInstance().loadMoreNews(getApplicationContext());
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NewsModel.getObjInstance().forceRefreshNews(getApplicationContext());
            }
        });

        srvNews.addOnScrollListener(mSmartScrollListener);

        getSupportLoaderManager().initLoader(NEWS_LIST_LOADER_ID, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        List<NewsVO> newsList = NewsModel.getObjInstance().getNews();
        if (!newsList.isEmpty()) {
            mNewsAdapter.setNewData(newsList);
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTapComment() {

    }

    @Override
    public void onTapSendTo() {

    }

    @Override
    public void onTapFavourite() {

    }

    @Override
    public void onTapStatistics() {

    }

    @Override
    public void onTapNews(NewsVO news) {
        Intent intent = NewsDetailsActivity.newIntent(getApplicationContext(), news.getNewsId());
        startActivity(intent);
    }

    /*
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {

        mNewsAdapter.appendNewData(event.getLoadedNews());
        swipeRefreshLayout.setRefreshing(false);

    }
    */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(RestApiEvents.ErrorInvokingAPIEvent event) {
        Snackbar.make(srvNews, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(),
                NewsContract.NewsEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToFirst()) {

            List<NewsVO> newsList = new ArrayList<>();

            do {
                NewsVO news = NewsVO.parseFromCursor(getApplicationContext(), data);
                newsList.add(news);
            } while (data.moveToNext());

            mNewsAdapter.setNewData(newsList);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
