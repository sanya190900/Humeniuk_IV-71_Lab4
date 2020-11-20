package ua.kpi.comsys.iv7104;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FilmAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    ArrayList<String> titles, years, types;
    ArrayList<Integer> posters;

    FilmAdapter(Context context, ArrayList<String> titles, ArrayList<String> years, ArrayList<String> types, ArrayList<Integer> posters){
        this.titles = titles;
        this.years = years;
        this.types = types;
        this.posters = posters;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.list_item, null);

        ImageView image = convertView.findViewById(R.id.imageViewIcon);
        image.setImageResource(posters.get(position));

        TextView titleTextView = convertView.findViewById(R.id.textViewTitle);
        titleTextView.setText(titles.get(position));

        TextView dateTextView = convertView.findViewById(R.id.textViewDate);
        dateTextView.setText(years.get(position));

        TextView typeTextView = convertView.findViewById(R.id.textViewType);
        typeTextView.setText(types.get(position));

        return convertView;
    }
}