package ss.com.mvprx.home.model;

import java.util.List;

/**
 * @author S.Shahini
 * @since 8/12/17
 */

public interface NewsRepository {
    void loadNews(LoadNewsCallBack loadNewsCallBack);

    interface LoadNewsCallBack{
        void onLoad(List<NewsViewModel> newsViewModels);
        void onError(String message);
    }
}
