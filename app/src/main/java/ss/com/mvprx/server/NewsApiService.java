package ss.com.mvprx.server;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ss.com.mvprx.home.model.NewsApiResponse;

/**
 * @author S.Shahini
 * @since 8/12/17
 */

public interface NewsApiService {
    @GET("articles")
    Observable<NewsApiResponse> listRepos(@Query("source") String source, @Query("apikey") String key);
}
