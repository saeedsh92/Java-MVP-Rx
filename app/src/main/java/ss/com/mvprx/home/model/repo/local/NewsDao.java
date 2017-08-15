package ss.com.mvprx.home.model.repo.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;


/**
 * @author S.Shahini
 * @since 8/13/17
 */
@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    Flowable<List<News>> getAll();

    @Insert()
    void insertAll(List<News> news);

    @Query("DELETE FROM news")
    void deleteAll();
}
