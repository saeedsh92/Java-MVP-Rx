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
import ss.com.mvprx.component.httpclient.HttpClientInjector;
import ss.com.mvprx.home.model.NewsApiResponse;
import ss.com.mvprx.home.model.repo.local.LocalNewsDataRepository;
import ss.com.mvprx.home.model.repo.local.News;
import ss.com.mvprx.home.model.repo.remote.RemoteNewsDataRepository;
import ss.com.mvprx.storage.DatabaseManager;

/**
 * @author S.Shahini
 * @since 8/12/17
 */

public class NewsRepositoryImpl implements NewsDataSource, Observer<NewsApiResponse> {
    private static final String TAG = "NewsRepository";
    private Context context;


    public NewsRepositoryImpl(Context context) {
        this.context = context;
    }

    private ObservableEmitter<NewsApiResponse> responseEmitter;

    @Override
    public Observable<NewsApiResponse> getNews() {
        downloadData(new RemoteNewsDataRepository(HttpClientInjector.inject()));
        return loadDataFromDatabase(new LocalNewsDataRepository(context));
    }

    private void downloadData(RemoteNewsDataRepository remoteNewsDataSource) {
        remoteNewsDataSource.getNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(NewsRepositoryImpl.this);
    }

    private Observable<NewsApiResponse> loadDataFromDatabase(final LocalNewsDataRepository localNewsDataRepository) {
        return Observable.create(new ObservableOnSubscribe<NewsApiResponse>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<NewsApiResponse> emitter) throws Exception {
                NewsRepositoryImpl.this.responseEmitter = emitter;
                localNewsDataRepository.getNews().
                        subscribeOn(Schedulers.newThread()).
                        observeOn(AndroidSchedulers.mainThread())
                        .subscribe(NewsRepositoryImpl.this);
            }
        });
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
                List<News> newsList = new ArrayList<>();
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
                responseEmitter.onNext(newsApiResponse);
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

    }
}
