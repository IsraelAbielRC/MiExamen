package com.abiel.abiel.Controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abiel.abiel.Models.UbicacionModel;
import com.abiel.abiel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ubicacion extends Fragment {
    FirebaseFirestore _db;
    List<UbicacionModel> _ubicaciones;
    UbicacionModel _ubicacion;
    public ubicacion() {

    }
    public static ubicacion newInstance(String param1, String param2) {
        ubicacion fragment = new ubicacion();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void solicitarPermisos() {
        int pcheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if (pcheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private void getUbicacion() {
        LocationManager lManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
        LocationListener lListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                    Toast.makeText(getContext(),"" + location.getLongitude() + " " + location.getAltitude(),Toast.LENGTH_SHORT).show();
                    Geocoder _gCoder = new Geocoder(getContext(), Locale.getDefault());
                    Map<String,Object> _location = new HashMap<>();
                    _location.put("Long", location.getLongitude());
                    _location.put("Alt", location.getAltitude());

                    _db.collection("Ubicaciones").add(_location).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getContext(),"Se Creo Documento " + documentReference.getId(),Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),"Ocurrio el un error " + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    _db.collection("Ubicaciones").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                _ubicaciones.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    _ubicacion = new UbicacionModel();
                                    _ubicacion.SetLongitud(document.getDouble("Long"));
                                    _ubicacion.SetAltitud(document.getDouble("Alt"));
                                    _ubicaciones.add(_ubicacion);
                                }
                                Toast.makeText(getContext(),"Numero de Elementos " + _ubicaciones.size(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        };
        int pcheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, lListener);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _db = FirebaseFirestore.getInstance();
        _ubicaciones = new ArrayList<>();
        solicitarPermisos();
        getUbicacion();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ubicacion, container, false);
    }
}