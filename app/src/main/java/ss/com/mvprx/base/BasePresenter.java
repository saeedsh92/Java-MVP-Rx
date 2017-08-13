package ss.com.mvprx.base;

/**
 * @author S.Shahini
 * @since 8/12/17
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void detachView();
}
