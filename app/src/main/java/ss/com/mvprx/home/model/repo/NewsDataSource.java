package ss.com.mvprx.home.model.repo;

import java.util.List;

import io.reactivex.Observable;
import ss.com.mvprx.home.model.NewsApiResponse;
import ss.com.mvprx.home.model.NewsViewModel;

/**
 * @author S.Shahini
 * @since 8/13/17
 */

public interface NewsDataSource {

    Observable<NewsApiResponse> getNews();

}
