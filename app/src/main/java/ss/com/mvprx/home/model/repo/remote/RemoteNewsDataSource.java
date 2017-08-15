package ss.com.mvprx.home.model.repo.remote;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ss.com.mvprx.home.model.NewsApiResponse;
import ss.com.mvprx.home.model.repo.NewsDataSource;
import ss.com.mvprx.server.NewsApiService;

/**
 * @author S.Shahini
 * @since 8/13/17
 */

public class RemoteNewsDataSource implements NewsDataSource {
    @Override
    public Observable<NewsApiResponse> getNews() {
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

        return service.listRepos("bloomberg", ss.com.mvprx.BuildConfig.API_KEY);
    }
}
