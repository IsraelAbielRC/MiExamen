package com.abiel.abiel.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abiel.abiel.Models.MovieModel;
import com.abiel.abiel.R;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewMovieAdapter extends BaseAdapter {
    Context context;
    int resource;
    List<MovieModel> movieList;
    Context _context;
    LayoutInflater _layoutInflater;
    public GridViewMovieAdapter(@NonNull Context context, int resource, @NonNull List<MovieModel> objects) {
        this._context = context;
        this.resource = resource;
        this.movieList = objects;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            _layoutInflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
            view = _layoutInflater.inflate(R.layout.itemmovie,null);
        }
        ImageView _img = view.findViewById(R.id.imgmovie);
        TextView _Detail = view.findViewById(R.id.moviedata);
        _Detail.setText(movieList.get(i).getDetail());
        String _link = movieList.get(i).getLink();
        Picasso.get().load(_link).placeholder(R.drawable.ic_warning).error(R.drawable.ic_warning).into(_img);
        return view;
    }
}
