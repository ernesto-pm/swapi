package com.example.ernesto.swapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ernesto on 2/16/18.
 */

public class StarwarsAdapter extends ArrayAdapter<Character>{

    private Context context;

    public StarwarsAdapter(@NonNull Context context, int resource, @NonNull List<Character> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.starwars_character_layout, parent, false);
        }

        Character character = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView birth_year = (TextView) convertView.findViewById(R.id.birth_year);

        name.setText(character.name);
        birth_year.setText(character.birthYear);

        convertView.setBackgroundResource(R.color.colorPrimary);

        return convertView;
    }
}


