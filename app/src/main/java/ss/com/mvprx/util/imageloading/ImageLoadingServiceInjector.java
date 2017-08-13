package ss.com.mvprx.util.imageloading;

import android.content.Context;

/**
 * @author S.Shahini
 * @since 8/13/17
 */

public class ImageLoadingServiceInjector {
    public static ImageLoadingService getImageLoadingService(Context context) {
        return new PicassoImageLoadingServiceImpl(context);
    }
}
