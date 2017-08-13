package ss.com.mvprx.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import ss.com.mvprx.R;
import ss.com.mvprx.home.adapter.NewsAdapter;
import ss.com.mvprx.home.model.NewsViewModel;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {
    private static final String TAG = "HomeActivity";
    private ProgressBar progressBar;
    private HomeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupViews();
        presenter = new HomePresenter();
    }

    private void setupViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_home);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showNews(ArrayList<NewsViewModel> newsViewModels) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_home_news);
        NewsAdapter newsAdapter = new NewsAdapter(this, newsViewModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public void setProgressIndicator(boolean mustShow) {
        if (mustShow) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
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
}
