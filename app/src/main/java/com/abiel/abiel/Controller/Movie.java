package com.abiel.abiel.Controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.abiel.abiel.BaseDatos.SQLiteHelper;
import com.abiel.abiel.Models.MovieModel;
import com.abiel.abiel.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Movie#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Movie extends Fragment {
    ListView moviewList;
    final String url = "https://rallycoding.herokuapp.com/api/music_albums";
    List<MovieModel> alist;
    GridViewMovieAdapter adapter;
    RequestQueue requestQueue;
    SQLiteHelper _helper;
    SQLiteDatabase db;
    public Movie() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            moviewList = view.findViewById(R.id.MovieLV);
            alist = new ArrayList<>();
            requestQueue = Volley.newRequestQueue(getContext());
            _helper = new SQLiteHelper(getContext());
            db = _helper.getWritableDatabase();
            adapter = new GridViewMovieAdapter(getContext(),R.id.MovieLV, alist);
            if(db != null) {
                GetAllData();
            }

            moviewList.setAdapter(adapter);
        }
        catch (Exception ex){}
    }
    private  void GetAllData(){
       if(Libreria.isConneted(getContext())){
           JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
               @Override
               public void onResponse(JSONArray response) {
                   JSONArray jsonArray = response;

                   try {
                       for(int i=0;i<jsonArray.length();i++)
                       {
                           JSONObject jsonObject = jsonArray.getJSONObject(i);
                           String titulo = jsonObject.getString("title");
                           String link = jsonObject.getString("image");
                           MovieModel _movie = new MovieModel(titulo," ",link);
                           _helper.InsertData(_movie);
                           alist.add(_movie);
                       }
                       db.close();
                       adapter.notifyDataSetChanged();//To prevent app from crashing when updating
                       //UI through background Thread
                   }
                   catch (Exception w)
                   {
                       Toast.makeText(getContext(),w.getMessage(),Toast.LENGTH_LONG).show();
                   }
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
               }
           });
           requestQueue.add(jsonArrayRequest);
       }
       else{
           try {
               Cursor _cursor = _helper.GetAllData();
               if(_cursor.getCount() <= 0){
                   Toast.makeText(getContext(),"Sin Registros", Toast.LENGTH_LONG).show();
               }
               else{
                   while (_cursor.moveToNext()){
                       String data = _cursor.getString(1);
                       String _link = _cursor.getString(2);
                       alist.add(new MovieModel(data,"",_link));
                   }
                   adapter.notifyDataSetChanged();
               }
           }
           catch (Exception ex){
               Toast.makeText(getContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
           }
       }
    }

    public static Movie newInstance(String param1, String param2) {
        Movie fragment = new Movie();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }
}