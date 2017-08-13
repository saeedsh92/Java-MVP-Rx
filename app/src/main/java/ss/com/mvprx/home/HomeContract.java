package ss.com.mvprx.home;

import java.util.ArrayList;

import ss.com.mvprx.base.BasePresenter;
import ss.com.mvprx.base.BaseView;
import ss.com.mvprx.home.model.NewsViewModel;

/**
 * @author S.Shahini
 * @since 8/12/17
 */

public interface HomeContract {
    interface View extends BaseView{
        void showNews(ArrayList<NewsViewModel> newsViewModels);
        void setProgressIndicator(boolean mustShow);
    }

    interface Presenter extends BasePresenter<View>{
        void loadNews();
        void onNewsClick();
    }
}
