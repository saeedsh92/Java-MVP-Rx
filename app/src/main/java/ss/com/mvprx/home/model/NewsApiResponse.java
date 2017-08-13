package ss.com.mvprx.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author S.Shahini
 * @since 8/13/17
 */

public class NewsApiResponse {
    private String status;
    private String source;
    @SerializedName("articles")
    private ArrayList<NewsViewModel> newsViewModels;

    public ArrayList<NewsViewModel> getNewsViewModels() {
        return newsViewModels;
    }

    public void setNewsViewModels(ArrayList<NewsViewModel> newsViewModels) {
        this.newsViewModels = newsViewModels;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
