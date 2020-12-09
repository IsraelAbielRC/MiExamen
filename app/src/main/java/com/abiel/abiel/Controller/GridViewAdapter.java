package com.abiel.abiel.Controller;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.abiel.abiel.R;
import com.abiel.abiel.tab;

import java.net.URI;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    List<Uri> _listaImg;
    Context _context;
    LayoutInflater _layoutInflater;
    public GridViewAdapter(Context _context, List<Uri>_listaImg){
        this._listaImg = _listaImg;
        this._context = _context;
    }
    @Override
    public int getCount() {
        return _listaImg.size();
    }

    @Override
    public Object getItem(int i) {
        return _listaImg.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            _layoutInflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
            view = _layoutInflater.inflate(R.layout.itemimagenes,null);
        }
        ImageView _img = view.findViewById(R.id.ivImagen);
        ImageButton _btneliminar = view.findViewById(R.id.btnEliminar);
        _img.setImageURI(_listaImg.get(i));
        _btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _listaImg.remove(i);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
