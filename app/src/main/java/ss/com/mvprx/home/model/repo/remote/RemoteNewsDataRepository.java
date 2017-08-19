package ss.com.mvprx.home.model.repo.remote;


import java.util.HashMap;

import io.reactivex.Observable;
import ss.com.mvprx.BuildConfig;
import ss.com.mvprx.component.httpclient.HttpClient;
import ss.com.mvprx.component.httpclient.HttpRequest;
import ss.com.mvprx.home.model.NewsApiResponse;
import ss.com.mvprx.home.model.repo.NewsDataSource;

/**
 * @author S.Shahini
 * @since 8/13/17
 */

public class RemoteNewsDataRepository implements NewsDataSource {
    private HttpClient httpClient;

    public RemoteNewsDataRepository(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Observable<NewsApiResponse> getNews() {
        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("source", "techcrunch");
        urlParams.put("apikey", BuildConfig.API_KEY);
        return httpClient.makeRequest(HttpRequest.Method.GET, "articles", urlParams, NewsApiResponse.class).send();

    }
}
