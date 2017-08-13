package ss.com.mvprx.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ss.com.mvprx.R;
import ss.com.mvprx.home.model.NewsViewModel;
import ss.com.mvprx.util.imageloading.ImageLoadingServiceInjector;

/**
 * @author S.Shahini
 * @since 8/13/17
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context context;
    private List<NewsViewModel> newsViewModels;

    public NewsAdapter(Context context, ArrayList<NewsViewModel> newsViewModels) {
        this.context = context;
        this.newsViewModels = newsViewModels;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_news, parent, false));
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bindNews(newsViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return newsViewModels.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView newsImageView;
        private TextView newsTitleTextView;
        private TextView newsDescriptionTextView;
        private TextView dateTextView;
        private TextView authorTextView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            newsImageView = (ImageView) itemView.findViewById(R.id.iv_news_newsImage);
            newsTitleTextView = (TextView) itemView.findViewById(R.id.tv_news_title);
            newsDescriptionTextView = (TextView) itemView.findViewById(R.id.tv_news_description);
            dateTextView = (TextView) itemView.findViewById(R.id.tv_news_date);
            authorTextView = (TextView) itemView.findViewById(R.id.tv_news_author);
        }

        public void bindNews(NewsViewModel newsViewModel) {
            ImageLoadingServiceInjector.getImageLoadingService(itemView.getContext()).loadImage(newsViewModel.getImageUrl(), newsImageView);
            newsTitleTextView.setText(newsViewModel.getTitle());
            newsDescriptionTextView.setText(newsViewModel.getDescription());
            dateTextView.setText(newsViewModel.getDate());
            authorTextView.setText(newsViewModel.getAuthor());
        }
    }
}
