package com.example.ernesto.swapi;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.ListFragment;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class StarwarsListFragment extends ListFragment {

    private RequestQueue mQueue;

    public StarwarsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        return inflater.inflate(R.layout.fragment_starwars_list, container, false);
    }

    private void makeRequest(final StarwarsAdapter adapter, String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                // success
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //JSONObject data = response.getJSONObject("data");
                            //JSONArray jsonArray = data.getJSONArray("results");

                            JSONArray jsonArray = response.getJSONArray("results");
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
        mQueue.add(request);
    }

    private StarwarsAdapter getAdapter() {
        final StarwarsAdapter adapter = new StarwarsAdapter(getActivity(), R.layout.starwars_character_layout, new ArrayList<Character>());

        for(int i =0; i<10; i++) {
            StringBuffer sb = new StringBuffer();
            sb.append("https://swapi.co/api/people/?page=");
            sb.append(i+"");
            sb.append("&format=json");
            makeRequest(adapter, sb.toString());
        }

        /*
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://swapi.co/api/people/?page=1&format=json", null,
                // success
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //JSONObject data = response.getJSONObject("data");
                            //JSONArray jsonArray = data.getJSONArray("results");

                            JSONArray jsonArray = response.getJSONArray("results");
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
        mQueue.add(request);
        */

        return adapter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StarwarsAdapter adapter = getAdapter();
        setListAdapter(adapter);
    }
}
