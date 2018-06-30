package me.evancornish.flixster;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.parceler.Parcels;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.evancornish.flixster.models.Config;
import me.evancornish.flixster.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>
{
    ArrayList<Movie> movies;
    Config config;
    Context context;

    public void setConfig(Config config)
    {
        this.config=config;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View movieView=inflater.inflate(R.layout.item_movie,parent,false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie=movies.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());
        boolean isPortrait=context.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT;
        String imageUrl=isPortrait?config.getImageUrl(config.getPosterSize(),movie.getPosterPath()):config.getImageUrl(config.getBackdropSize(),movie.getBackdropPath());
        int placeholderId=isPortrait?R.drawable.flicks_movie_placeholder:R.drawable.flicks_backdrop_placeholder;
        ImageView imageView=isPortrait?holder.ivPosterImage:holder.ivBackdropImage;
        Glide.with(context).load(imageUrl).apply(RequestOptions.placeholderOf(placeholderId)).apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(15, 0))).apply(RequestOptions.errorOf(placeholderId)).into(imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public MovieAdapter(ArrayList<Movie> movies)
    {
        this.movies=movies;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverview;

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            if(position!=RecyclerView.NO_POSITION)
            {
                Movie movie=movies.get(position);
                Intent intent=new Intent(context,MovieDetailsActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }

        public ViewHolder(View itemView)
        {
            super(itemView);
            ivPosterImage=itemView.findViewById(R.id.ivPosterImage);
            ivBackdropImage=itemView.findViewById(R.id.ivBackdropImage);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvOverview=itemView.findViewById(R.id.tvOverview);
            itemView.setOnClickListener(this);
        }
    }
}
