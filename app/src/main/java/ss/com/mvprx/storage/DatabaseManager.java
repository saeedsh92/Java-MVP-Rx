package ss.com.mvprx.storage;

import android.arch.persistence.room.Room;
import android.content.Context;

import ss.com.mvprx.home.model.repo.local.AppDatabase;

/**
 * @author S.Shahini
 * @since 8/14/17
 */

public class DatabaseManager {
    private static AppDatabase appDatabase;
    public static AppDatabase getAppDatabase(Context context){
        if (appDatabase==null){
            appDatabase= Room.databaseBuilder(context,AppDatabase.class,"news").allowMainThreadQueries().build();
        }
        return appDatabase;
    }
}
