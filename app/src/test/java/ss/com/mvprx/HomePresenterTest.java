package ss.com.mvprx;

import android.content.Context;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import ss.com.mvprx.home.HomeContract;
import ss.com.mvprx.home.HomePresenter;
import ss.com.mvprx.home.model.NewsApiResponse;
import ss.com.mvprx.home.model.repo.NewsRepositoryImpl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author S.Shahini
 * @since 8/19/17
 */
@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {
    private static NewsApiResponse newsApiResponse;

    private HomeContract.Presenter presenter;

    @Mock
    private Context context;
    @Mock
    private HomeContract.View view;

    @Mock
    private Observable<NewsApiResponse> newsApiResponseObservable;

    @Mock
    private NewsRepositoryImpl newsRepository;

    @Before
    public void init() {
        view = Mockito.mock(HomeContract.View.class);
        presenter = new HomePresenter(newsRepository);
    }

    @BeforeClass
    public static void setupClass() {

    }

    @Test
    public void loadNews() {
        presenter.attachView(view);
        when(newsRepository.getNews()).thenReturn(Observable.just(newsApiResponse));
        presenter.loadNews();
        verify(view).setProgressIndicator(true);
        verify(newsRepository).getNews();
    }
}
