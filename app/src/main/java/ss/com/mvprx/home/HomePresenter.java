package ss.com.mvprx.home;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ss.com.mvprx.home.model.NewsApiResponse;
import ss.com.mvprx.server.NewsApiService;

/**
 * @author S.Shahini
 * @since 8/13/17
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View view;

    @Override
    public void attachView(HomeContract.View view) {
        this.view = view;
        loadNews();
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void loadNews() {
        view.setProgressIndicator(true);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ss.com.mvprx.BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        NewsApiService service = retrofit.create(NewsApiService.class);

        service.listRepos("techcrunch", ss.com.mvprx.BuildConfig.API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<NewsApiResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull NewsApiResponse newsApiResponse) {
                        view.showNews(newsApiResponse.getNewsViewModels());
                        view.setProgressIndicator(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onNewsClick() {

    }
}
