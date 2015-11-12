package fr.gaulupeau.apps.Poche.data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

import fr.gaulupeau.apps.InThePoche.R;
import fr.gaulupeau.apps.Poche.entity.Article;

/**
 * @author Victor Häggqvist
 * @since 10/19/15
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Article> articles;
    private OnItemClickListener listener;
    private String originalUrlHost;

    public ListAdapter(List<Article> articles, OnItemClickListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClickListener listener;
        TextView title;
        TextView url;
        TextView favourite;
        TextView read;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            title = (TextView) itemView.findViewById(R.id.title);
            url = (TextView) itemView.findViewById(R.id.url);
            favourite = (TextView) itemView.findViewById(R.id.favourite);
            read = (TextView) itemView.findViewById(R.id.read);
            itemView.setOnClickListener(this);
        }

        public void bind(Article article) {
            String originalUrlText = new String(article.getUrl());
            try {
                URL originalUrl = new URL(originalUrlText);
                originalUrlHost = originalUrl.getHost();
            } catch (Exception e) {
                //
            }
            title.setText(article.getTitle());
            url.setText(originalUrlHost);
            if (article.getFavorite()){
                favourite.setText("★");
            }
            if (article.getArchive()){
                read.setText("☑");
            }
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
