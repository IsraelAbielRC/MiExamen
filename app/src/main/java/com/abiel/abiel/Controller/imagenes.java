package com.abiel.abiel.Controller;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import com.abiel.abiel.R;
import com.abiel.abiel.tab;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class imagenes extends Fragment {

    int PICK_IMAGE = 100;
    Uri imgUri;
    Button _btnGaleria;
    GridView _gvColecccion;
    List<Uri> listaImg = new ArrayList<>();
    GridViewAdapter _collectionAdapter;
    public imagenes() {

    }
    private void abrirGaleria() {
        Intent _intent = new Intent();
        _intent.setType("image/*");
        _intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        _intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(_intent,"Selecciona las imagenes"),PICK_IMAGE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ClipData _clipData = data.getClipData();
        if(resultCode == tab.RESULT_OK && requestCode == PICK_IMAGE ){
            if(_clipData == null){
                imgUri = data.getData();
                listaImg.add(imgUri);
            }
            else {
                for (int i =0; i < _clipData.getItemCount(); i++){
                    listaImg.add((_clipData.getItemAt(i).getUri()));
                }
            }
        }
        _collectionAdapter = new GridViewAdapter(getContext(),listaImg);
        _gvColecccion.setAdapter(_collectionAdapter);
    }
    public static imagenes newInstance(String param1, String param2) {
        imagenes fragment = new imagenes();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _btnGaleria = (Button) view.findViewById(R.id.btnGaleria);
        _gvColecccion = (GridView)  view.findViewById(R.id.gvColeccion);
        _btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirGaleria();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_imagenes, container, false);
    }
}