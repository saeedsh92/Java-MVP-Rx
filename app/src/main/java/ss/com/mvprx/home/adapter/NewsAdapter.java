package ss.com.mvprx.home.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
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
import ss.com.mvprx.util.imageloading.ImageLoadingService;

/**
 * @author S.Shahini
 * @since 8/13/17
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context context;
    private List<NewsViewModel> newsViewModels;
    private NewsAdapter.OnNewsItemClick onNewsItemClick;
    private ImageLoadingService imageLoadingService;

    public NewsAdapter(Context context, ArrayList<NewsViewModel> newsViewModels
            , NewsAdapter.OnNewsItemClick onNewsItemClick, ImageLoadingService imageLoadingService) {
        this.context = context;
        this.newsViewModels = newsViewModels;
        this.onNewsItemClick = onNewsItemClick;
        this.imageLoadingService = imageLoadingService;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_news, parent, false), imageLoadingService);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bindNews(newsViewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return newsViewModels.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private ImageView newsImageView;
        private TextView newsTitleTextView;
        private TextView newsDescriptionTextView;
        private TextView dateTextView;
        private TextView authorTextView;
        private ImageLoadingService imageLoadingService;

        public NewsViewHolder(View itemView, ImageLoadingService imageLoadingService) {
            super(itemView);
            this.newsImageView = (ImageView) itemView.findViewById(R.id.iv_news_newsImage);
            this.newsTitleTextView = (TextView) itemView.findViewById(R.id.tv_news_title);
            this.newsDescriptionTextView = (TextView) itemView.findViewById(R.id.tv_news_description);
            this.dateTextView = (TextView) itemView.findViewById(R.id.tv_news_date);
            this.authorTextView = (TextView) itemView.findViewById(R.id.tv_news_author);
            this.imageLoadingService = imageLoadingService;
        }

        public void bindNews(final NewsViewModel newsViewModel) {
            imageLoadingService.loadImage(newsViewModel.getImageUrl(), newsImageView);
            newsTitleTextView.setText(newsViewModel.getTitle());
            newsDescriptionTextView.setText(newsViewModel.getDescription());
            dateTextView.setText(newsViewModel.getDate());
            authorTextView.setText(newsViewModel.getAuthor());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNewsItemClick.onNewsItemClick(newsViewModel);
                }
            });
        }

    }

    public interface OnNewsItemClick {
        void onNewsItemClick(NewsViewModel newsViewModel);
    }
}
