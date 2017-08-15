package ss.com.mvprx.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ss.com.mvprx.R;
import ss.com.mvprx.home.adapter.NewsAdapter;
import ss.com.mvprx.home.model.NewsViewModel;
import ss.com.mvprx.home.model.repo.NewsRepository;
import ss.com.mvprx.util.imageloading.ImageLoadingServiceInjector;

public class HomeActivity extends AppCompatActivity implements HomeContract.View, NewsAdapter.OnNewsItemClick {
    private static final String TAG = "HomeActivity";
    private View progressBar;
    private HomeContract.Presenter presenter;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupViews();
        presenter = new HomePresenter(new NewsRepository(this));
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar_home);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout_home);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadNews();
            }
        });
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showNews(ArrayList<NewsViewModel> newsViewModels) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_home_news);
        NewsAdapter newsAdapter = new NewsAdapter(this, newsViewModels, this, ImageLoadingServiceInjector.getImageLoadingService(getApplicationContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void setProgressIndicator(boolean mustShow) {
        if (mustShow) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showNewsDetail() {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void onNewsItemClick(NewsViewModel newsViewModel) {
        presenter.onNewsClick();
    }
}
