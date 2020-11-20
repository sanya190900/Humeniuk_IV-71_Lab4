package ua.kpi.comsys.iv7104;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static ua.kpi.comsys.iv7104.MainActivity.getResourceId;

public class AboutFilmActivity extends AppCompatActivity {
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> years = new ArrayList<>();
    ArrayList<String> types = new ArrayList<>();
    ArrayList<Integer> posters = new ArrayList<>();
    ArrayList<String> genres = new ArrayList<>();
    ArrayList<String> directors = new ArrayList<>();
    ArrayList<String> actors = new ArrayList<>();
    ArrayList<String> counties = new ArrayList<>();
    ArrayList<String> languages = new ArrayList<>();
    ArrayList<String> prods = new ArrayList<>();
    ArrayList<String> releases = new ArrayList<>();
    ArrayList<String> runtimes = new ArrayList<>();
    ArrayList<String> awards = new ArrayList<>();
    ArrayList<String> ratings = new ArrayList<>();
    ArrayList<String> plots = new ArrayList<>();
    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_film);
        {
            try {
                movies = ParseJSON.readInfo(this);
                for (int i = 0; i < movies.size(); i++) {
                    titles.add(movies.get(i).getTitle());
                    years.add(movies.get(i).getYear());
                    types.add(movies.get(i).getType());
                    posters.add(getResourceId(this, movies.get(i).getPoster().replace(".jpg", "")));
                    genres.add(movies.get(i).getGenre());
                    directors.add(movies.get(i).getDirector());
                    actors.add(movies.get(i).getActors());
                    counties.add(movies.get(i).getCountry());
                    languages.add(movies.get(i).getLanguage());
                    prods.add(movies.get(i).getProduction());
                    releases.add(movies.get(i).getReleased());
                    runtimes.add(movies.get(i).getRuntime());
                    awards.add(movies.get(i).getAwards());
                    ratings.add(movies.get(i).getRating());
                    plots.add(movies.get(i).getPlot());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        ImageView image_about = findViewById(R.id.image);
        TextView title_about = findViewById(R.id.title);
        TextView year_about = findViewById(R.id.year);
        TextView genre_about = findViewById(R.id.genre);
        TextView director_about = findViewById(R.id.director);
        TextView actors_about = findViewById(R.id.actors);
        TextView country_about = findViewById(R.id.country);
        TextView lang_about = findViewById(R.id.language);
        TextView prod_about = findViewById(R.id.production);
        TextView release_about = findViewById(R.id.release);
        TextView runtime_about = findViewById(R.id.runtime);
        TextView awards_about = findViewById(R.id.awards);
        TextView rating_about = findViewById(R.id.rating);
        TextView plot_about = findViewById(R.id.plot);

        int position = getIntent().getExtras().getInt("position");

        image_about.setImageResource(posters.get(position));
        title_about.setText(titles.get(position));
        year_about.setText(years.get(position));
        genre_about.setText(genres.get(position));
        director_about.setText(directors.get(position));
        actors_about.setText(actors.get(position));
        country_about.setText(counties.get(position));
        lang_about.setText(languages.get(position));
        prod_about.setText(prods.get(position));
        release_about.setText(releases.get(position));
        runtime_about.setText(runtimes.get(position));
        awards_about.setText(awards.get(position));
        rating_about.setText(ratings.get(position));
        plot_about.setText(plots.get(position));
    }
}