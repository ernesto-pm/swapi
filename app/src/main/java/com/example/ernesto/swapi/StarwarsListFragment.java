package com.example.ernesto.swapi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StarwarsListFragment extends ListFragment {


    public StarwarsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_starwars_list, container, false);
    }

    private StarwarsAdapter getAdapter() {
        final StarwarsAdapter adapter = new StarwarsAdapter(getActivity(), R.layout.starwars_character_layout, new ArrayList<Character>());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://swapi.co/api/people/?page=1&format=json", null,
                // success
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject data = response.getJSONObject("data");
                            JSONArray jsonArray = data.getJSONArray("results");

                            for(int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Character character = new Character();
                                character.name = jsonObject.getString("name");
                                character.birthYear = jsonObject.getString("birth_year");

                                adapter.add(character);
                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                // error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        return adapter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StarwarsAdapter adapter = getAdapter();
        setListAdapter(adapter);
    }
}
