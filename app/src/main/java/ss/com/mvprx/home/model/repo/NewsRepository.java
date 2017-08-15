package ss.com.mvprx.home.model.repo;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ss.com.mvprx.home.model.NewsApiResponse;
import ss.com.mvprx.home.model.repo.local.LocalNewsDataSource;
import ss.com.mvprx.home.model.repo.local.News;
import ss.com.mvprx.home.model.repo.remote.RemoteNewsDataSource;
import ss.com.mvprx.storage.DatabaseManager;

/**
 * @author S.Shahini
 * @since 8/12/17
 */

public class NewsRepository implements NewsDataSource, Observer<NewsApiResponse> {
    private static final String TAG = "NewsRepository";
    private Context context;


    public NewsRepository(Context context) {
        this.context = context;
    }

    private LocalNewsDataSource localNewsDataSource;
    private RemoteNewsDataSource remoteNewsDataSource;
    private ObservableEmitter<NewsApiResponse> responseEmitter;

    @Override
    public Observable<NewsApiResponse> getNews() {
        downloadData();

        localNewsDataSource = new LocalNewsDataSource(context);
        return Observable.create(new ObservableOnSubscribe<NewsApiResponse>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<NewsApiResponse> emitter) throws Exception {
                NewsRepository.this.responseEmitter = emitter;
                localNewsDataSource.getNews().
                        subscribeOn(Schedulers.newThread()).
                        observeOn(AndroidSchedulers.mainThread())
                        .subscribe(NewsRepository.this);
            }
        });
    }

    private void downloadData() {
        remoteNewsDataSource = new RemoteNewsDataSource();
        remoteNewsDataSource.getNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(NewsRepository.this);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull NewsApiResponse newsApiResponse) {
        Log.i(TAG, "onNext Called: Data Source is => " + newsApiResponse.getDataSourceType());
        switch (newsApiResponse.getDataSourceType()) {
            case LOCAL:
                if (newsApiResponse.getNewsViewModels() != null &&
                        !newsApiResponse.getNewsViewModels().isEmpty()) {
                    Log.i(TAG, "onNext: " + responseEmitter.isDisposed());
                    responseEmitter.onNext(newsApiResponse);
                }
                break;
            case REMOTE:
                List<News> newsList = new ArrayList<News>();
                for (int i = 0; i < newsApiResponse.getNewsViewModels().size(); i++) {
                    News news = new News();
                    news.setTitle(newsApiResponse.getNewsViewModels().get(i).getTitle());
                    news.setDescription(newsApiResponse.getNewsViewModels().get(i).getDescription());
                    news.setAuthor(newsApiResponse.getNewsViewModels().get(i).getAuthor());
                    news.setDate(newsApiResponse.getNewsViewModels().get(i).getDate());
                    news.setUrl(newsApiResponse.getNewsViewModels().get(i).getUrl());
                    news.setImageUrl(newsApiResponse.getNewsViewModels().get(i).getImageUrl());
                    newsList.add(news);
                }
                DatabaseManager.getAppDatabase(context).newsDao().deleteAll();
                DatabaseManager.getAppDatabase(context).newsDao().insertAll(newsList);
                responseEmitter.onComplete();
                break;
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        responseEmitter.onError(e);
    }

    @Override
    public void onComplete() {
        responseEmitter.onComplete();
    }
}
