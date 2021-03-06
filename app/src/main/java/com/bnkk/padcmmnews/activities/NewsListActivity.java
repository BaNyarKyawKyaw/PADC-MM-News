package com.bnkk.padcmmnews.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bnkk.padcmmnews.MMNewsApp;
import com.bnkk.padcmmnews.R;
import com.bnkk.padcmmnews.adapters.NewsAdapter;
import com.bnkk.padcmmnews.components.EmptyViewPod;
import com.bnkk.padcmmnews.components.SmartRecyclerView;
import com.bnkk.padcmmnews.components.SmartScrollListener;
import com.bnkk.padcmmnews.data.vo.NewsVO;
import com.bnkk.padcmmnews.events.RestApiEvents;
import com.bnkk.padcmmnews.mvp.presenters.NewsListPresenter;
import com.bnkk.padcmmnews.mvp.views.NewsListView;
import com.bnkk.padcmmnews.persistence.NewsContract;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsListActivity extends BaseActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, NewsListView,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int NEWS_LIST_LOADER_ID = 1001;
    protected static final int RC_GOOGLE_SIGN_IN = 1236;

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

    @Inject
    NewsListPresenter mPresenter;

    protected GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this, this);

        MMNewsApp mmNewsApp = (MMNewsApp) getApplicationContext();
        mmNewsApp.getAppComponent().inject(this);

        mPresenter.onCreate(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                drawerLayout.openDrawer(GravityCompat.START);

                Intent intent = LoginRegisterActivity.newIntent(getApplicationContext());
                startActivity(intent);

                Date today = new Date();
                Log.d(MMNewsApp.LOG_TAG, "Today (with default format) : " + today.toString());
            }
        });
        */

        srvNews.setEmptyView(vpEmptyNews);
        srvNews.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));
        mNewsAdapter = new NewsAdapter(getApplicationContext(), mPresenter);
        srvNews.setAdapter(mNewsAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReached() {

                Snackbar.make(srvNews, "Loading new data", Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(true);

                mPresenter.onNewsListEndReach(getApplicationContext());
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onForceRefresh(getApplicationContext());
            }
        });

        srvNews.addOnScrollListener(mSmartScrollListener);

        getSupportLoaderManager().initLoader(NEWS_LIST_LOADER_ID, null, this);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("776079349956-bmb7ts23uiubbaufrs972rp5h58vgkbl.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this /*FragmentActivity*/, this /*OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            processGoogleSignInResult(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_list, menu);
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
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

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

        mPresenter.onDataLoaded(getApplicationContext(), data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void displayNewsList(List<NewsVO> newsList) {
        mNewsAdapter.setNewData(newsList);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void navigateToNewsDetails(NewsVO news) {
        Intent intent = NewsDetailsActivity.newIntent(getApplicationContext(), news.getNewsId());
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showAddNewsScreen() {
        Intent intent = AddNewsActivity.newIntent(getApplicationContext());
        startActivity(intent);
    }

    @Override
    public void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "onConnectionFailed : " + connectionResult.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.fab)
    public void onTapFab(View view) {
        //signInWithGoogle();
        mPresenter.onStartPublishingNews();
    }

    private void processGoogleSignInResult(GoogleSignInResult signInResult) {
        if (signInResult.isSuccess()) {
            // Google Sign-In was successful, authenticate with Firebase
            GoogleSignInAccount account = signInResult.getSignInAccount();
            mPresenter.onSuccessGoogleSign(account);
        } else {
            // Google Sign-In failed
            Log.e(MMNewsApp.LOG_TAG, "Google Sign-In failed.");
            Snackbar.make(srvNews, "Your Google sign-in failed.", Snackbar.LENGTH_LONG).show();
        }
    }
}
