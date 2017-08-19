package ss.com.mvprx.home;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ss.com.mvprx.home.model.NewsApiResponse;
import ss.com.mvprx.home.model.repo.NewsRepositoryImpl;

/**
 * @author S.Shahini
 * @since 8/13/17
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View view;
    private NewsRepositoryImpl newsRepositoryImpl;
    private Disposable subscription;

    public HomePresenter(NewsRepositoryImpl newsRepositoryImpl) {
        this.newsRepositoryImpl = newsRepositoryImpl;
    }

    @Override
    public void attachView(HomeContract.View view) {
        this.view = view;
        loadNews();
    }

    @Override
    public void detachView() {
        this.view = null;
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }

    }

    @Override
    public void loadNews() {
        view.setProgressIndicator(true);
        newsRepositoryImpl.getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<NewsApiResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        subscription = d;
                    }

                    @Override
                    public void onNext(final @NonNull NewsApiResponse newsApiResponse) {
                        view.showNews(newsApiResponse.getNewsViewModels());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.setProgressIndicator(false);

                    }
                });
    }

    @Override
    public void onNewsClick() {
        view.showNewsDetail();
    }
}
