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
import android.widget.Toast;

import com.abiel.abiel.R;
import com.abiel.abiel.tab;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class imagenes extends Fragment {

    int PICK_IMAGE = 100;
    Uri imgUri;
    Button _btnGaleria,_btnSave;
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
    private  void SaveImages(){
        if(!Libreria.isConneted(getContext()))
        {
            Toast.makeText(getContext(),"Verifica la señal de internet.",Toast.LENGTH_LONG).show();
            return;
        }
        if(listaImg.size() > 0){
            for (int i =0; i < listaImg.size(); i++){
                Uri _fileUri = listaImg.get(i);
                FirebaseApp.initializeApp(getContext());
                StorageReference _folder = FirebaseStorage.getInstance().getReference().child("Imagenes");
                final  StorageReference _fileName =  _folder.child("file"+_fileUri.getLastPathSegment());
                _fileName.putFile(_fileUri).addOnSuccessListener(tSnapShop -> _fileName.getDownloadUrl().addOnSuccessListener(uri -> {
                    HashMap<String, String> _hashMap = new HashMap<>();
                    _hashMap.put("Link", String.valueOf(uri));
                    Toast.makeText(getContext(),"Se Guardo "+ _fileName,Toast.LENGTH_SHORT).show();
                }));
            }
        }
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
        try {
            listaImg.clear();
            _btnGaleria = (Button) view.findViewById(R.id.btnGaleria);
            _btnSave= (Button) view.findViewById(R.id.btnSave);
            _gvColecccion = (GridView)  view.findViewById(R.id.gvColeccion);
            _btnGaleria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    abrirGaleria();
                }
            });
            _btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SaveImages();
                }
            });
        }
        catch (Exception ex){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_imagenes, container, false);
    }
}