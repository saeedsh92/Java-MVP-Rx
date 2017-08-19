package ss.com.mvprx.home.model.repo.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import ss.com.mvprx.home.model.NewsViewModel;

/**
 * @author S.Shahini
 * @since 8/13/17
 */
@Entity
public class News {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String source;
    private String date;
    private String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public NewsViewModel toViewModel() {
        NewsViewModel newsViewModel = new NewsViewModel();
        newsViewModel.setTitle(title);
        newsViewModel.setDescription(description);
        newsViewModel.setUrl(url);
        newsViewModel.setImageUrl(imageUrl);
        newsViewModel.setAuthor(author);
        newsViewModel.setDate(date);
        return newsViewModel;
    }
}
