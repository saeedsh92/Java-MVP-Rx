package ss.com.mvprx.home.model.repo.local;

import android.content.Context;
import android.util.Log;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ss.com.mvprx.home.model.NewsApiResponse;
import ss.com.mvprx.home.model.NewsViewModel;
import ss.com.mvprx.home.model.repo.DataSource;
import ss.com.mvprx.home.model.repo.NewsDataSource;
import ss.com.mvprx.storage.DatabaseManager;

/**
 * @author S.Shahini
 * @since 8/13/17
 */

public class LocalNewsDataSource implements NewsDataSource {
    private static final String TAG = "LocalNewsDataSource";
    private Context context;

    public LocalNewsDataSource(Context context) {
        this.context = context;
    }

    @Override
    public Observable<NewsApiResponse> getNews() {
        return Observable.create(new ObservableOnSubscribe<NewsApiResponse>() {
            @Override
            public void subscribe(final @NonNull ObservableEmitter<NewsApiResponse> observableEmitter) throws Exception {
                DatabaseManager.getAppDatabase(context).newsDao().getAll().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new FlowableSubscriber<List<News>>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<News> newsList) {
                        Log.i(TAG, "Local DataSource onNext ");
                        if (!newsList.isEmpty()) {
                            NewsApiResponse newsApiResponse = new NewsApiResponse();
                            newsApiResponse.setNewsSource(newsList.get(0).getSource());
                            ArrayList<NewsViewModel> newsViewModels = new ArrayList<>();
                            for (int i = 0; i < newsList.size(); i++) {
                                newsViewModels.add(newsList.get(i).toViewModel());
                            }
                            newsApiResponse.setDataSourceType(DataSource.Type.LOCAL);
                            newsApiResponse.setNewsViewModels(newsViewModels);
                            observableEmitter.onNext(newsApiResponse);
                        } else {
                            observableEmitter.onNext(new NewsApiResponse());
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i(TAG, "onError: "+t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });
            }
        });

    }
}
