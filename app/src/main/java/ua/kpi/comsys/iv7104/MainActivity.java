package ua.kpi.comsys.iv7104;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {
    public static final int IDM_OPEN = 101;
    public static final int IDM_DEL = 102;
    private FilmAdapter filmAdapter;
    private ListView lv;
    EditText input;
    ArrayList<Integer> posters = new ArrayList<>();
    ArrayList<Movie> movies;
    ArrayList<String> titles = new ArrayList<>()
                        ,years = new ArrayList<>()
                        ,types = new ArrayList<>()
                        ,genres = new ArrayList<>()
                        ,directors = new ArrayList<>()
                        ,actors = new ArrayList<>()
                        ,counties = new ArrayList<>()
                        ,languages = new ArrayList<>()
                        ,prods = new ArrayList<>()
                        ,releases = new ArrayList<>()
                        ,runTimes = new ArrayList<>()
                        ,awards = new ArrayList<>()
                        ,ratings = new ArrayList<>()
                        ,plots = new ArrayList<>();
    ArrayList<String> newTitles = new ArrayList<>()
            ,newYears = new ArrayList<>()
            ,newTypes = new ArrayList<>();
    String title = "Empty"
            ,year = "Empty"
            ,type = "Empty";


    int position_gl = 0;

    public static int getResourceId(Context context, String name) {
        name = name.toLowerCase();
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public void removeFromList(int position) {
        titles.remove(position);
        years.remove(position);
        types.remove(position);
        posters.remove(position);
        filmAdapter = new FilmAdapter(this, titles, years, types, posters);
        lv.setAdapter(filmAdapter);
    }

    public void onClickSearch(View view) {
        String text = input.getText().toString().toLowerCase();
        ArrayList<String> titles_search = new ArrayList<>();
        ArrayList<String> years_search = new ArrayList<>();
        ArrayList<String> types_search = new ArrayList<>();
        ArrayList<Integer> posters_search = new ArrayList<>();

        if (text.length() == 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please, input text", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            for (int i = 0; i < titles.size(); i++) {
                if(titles.get(i)!= null){
                    String temp = titles.get(i).toLowerCase();
                    if (temp.contains(text)) {
                        titles_search.add(titles.get(i));
                        years_search.add(years.get(i));
                        types_search.add(types.get(i));
                        posters_search.add(posters.get(i));
                    }
                }
            }
            if (titles_search.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(), "There are no results", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                lv = (ListView) findViewById(R.id.list_view);
                filmAdapter = new FilmAdapter(this, titles_search, years_search, types_search, posters_search);
                lv.setAdapter(filmAdapter);
            }
        }
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, AddFilmActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            movies = ParseJSON.readInfo(this);
            for (Movie movie : movies){
                titles.add(movie.getTitle());
                years.add(movie.getYear());
                types.add(movie.getType());
                posters.add(getResourceId(this, movie.getPoster().replace(".jpg", "")));
                genres.add(movie.getGenre());
                directors.add(movie.getDirector());
                actors.add(movie.getActors());
                counties.add(movie.getCountry());
                languages.add(movie.getLanguage());
                prods.add(movie.getProduction());
                releases.add(movie.getReleased());
                runTimes.add(movie.getRuntime());
                awards.add(movie.getAwards());
                ratings.add(movie.getRating());
                plots.add(movie.getPlot());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < newTitles.size(); i++){
            titles.add(newTitles.get(i));
            years.add(newYears.get(i));
            types.add(newTypes.get(i));
        }

        lv = findViewById(R.id.list_view);
        input = findViewById(R.id.inputSearch);

        filmAdapter = new FilmAdapter(this, titles, years, types, posters);
        lv.setAdapter(filmAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            position_gl = position;
            registerForContextMenu(view);
        }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CharSequence message;
        switch (item.getItemId()) {
            case IDM_OPEN:
                Intent intent = new Intent(MainActivity.this, AboutFilmActivity.class);
                intent.putExtra("position", position_gl);
                startActivity(intent);
                break;
            case IDM_DEL:
                removeFromList(position_gl);
                message = "Film is deleted from the list";
                Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, IDM_OPEN, Menu.NONE, "About");
        menu.add(Menu.NONE, IDM_DEL, Menu.NONE, "Delete");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //title = getIntent().getStringExtra("title");
        //year = getIntent().getStringExtra("year");
        //type = getIntent().getStringExtra("type");
        newTitles.add(getIntent().getStringExtra("title"));
        newYears.add(getIntent().getStringExtra("year"));
        newTypes.add(getIntent().getStringExtra("type"));

        for(int i = 0; i < newTitles.size(); i++){
            titles.add(newTitles.get(i));
            years.add(newYears.get(i));
            types.add(newTypes.get(i));
        }

        posters.add(R.drawable.background);
        lv = findViewById(R.id.list_view);
        filmAdapter = new FilmAdapter(this, titles, years, types, posters);
        lv.setAdapter(filmAdapter);
    }
}