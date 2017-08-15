package ss.com.mvprx.home.model.repo.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ss.com.mvprx.home.model.NewsViewModel;

/**
 * @author S.Shahini
 * @since 8/13/17
 */
@Database(entities = {News.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
