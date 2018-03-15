package com.example.android.popularmovies.UI;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.Database.MovieContract;
import com.example.android.popularmovies.Object.Movie;
import com.example.android.popularmovies.R;
import com.squareup.picasso.Picasso;

import static com.example.android.popularmovies.Utils.Utils.IMAGE_SIZE;
import static com.example.android.popularmovies.Utils.Utils.IMAGE_URL;
import static com.example.android.popularmovies.Utils.Utils.MOVIE;

/**
 * Created by Alessandro on 19/02/2018.
 */

public class DetailActivity extends AppCompatActivity {

    public Movie movieList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /**
         *  Get object movie position
         */
        Bundle data = getIntent().getExtras();
        movieList = data.getParcelable(MOVIE);

        updateUi();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_favorite);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertFavorite();
            }
        });

    }

    /**
     * It used for update the UI of the Detail Movie
     */
    private void updateUi() {
        ImageView imageBackdrop = (ImageView) findViewById(R.id.image_backdrop);
        Picasso.with(this)
                .load(IMAGE_URL
                        .concat(IMAGE_SIZE[5])
                        .concat(movieList.getBackdrop()))
                .into(imageBackdrop);

        TextView title = (TextView) findViewById(R.id.title_tv);
        title.setText(movieList.getTitle());

        TextView date = (TextView) findViewById(R.id.date_tv);
        date.setText(movieList.getDate());

        TextView rating = (TextView) findViewById(R.id.rating_tv);
        rating.setText(String.valueOf(movieList.getRating()) + "\n" + "/10");

        TextView language = (TextView) findViewById(R.id.language_tv);
        language.setText(movieList.getLanguage());

        TextView plot = (TextView) findViewById(R.id.plot_tv);
        plot.setText(movieList.getPlot());
    }

    /**
     * Add a Movie in Database --> Fragment --> Favorite
     */
    private void insertFavorite() {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movieList.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_RATING, movieList.getRating());
        values.put(MovieContract.MovieEntry.COLUMN_DATE, movieList.getDate());
        values.put(MovieContract.MovieEntry.COLUMN_IMAGE_POSTER, movieList.getImage());
        values.put(MovieContract.MovieEntry.COLUMN_IMAGE_BACKDROP, movieList.getBackdrop());
        values.put(MovieContract.MovieEntry.COLUMN_PLOT, movieList.getPlot());
        values.put(MovieContract.MovieEntry.COLUMN_LANGUAGE, movieList.getLanguage());
        getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
        Toast.makeText(this, "Movie saved in database", Toast.LENGTH_SHORT).show();
    }

}