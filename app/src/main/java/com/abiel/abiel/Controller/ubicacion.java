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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abiel.abiel.Models.UbicacionModel;
import com.abiel.abiel.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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


public class ubicacion extends Fragment implements OnMapReadyCallback , GoogleMap.OnMapClickListener {
    FirebaseFirestore _db;
    List<UbicacionModel> _ubicaciones;
    UbicacionModel _ubicacion;
    GoogleMap mapa;
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
                if(!Libreria.isConneted(getContext()))
                {
                    Toast.makeText(getContext(),"Verifica la señal de internet.",Toast.LENGTH_LONG).show();
                    return;
                }
                    Toast.makeText(getContext(),"" + location.getLongitude() + " " + location.getAltitude(),Toast.LENGTH_SHORT).show();

                    Map<String,Object> _location = new HashMap<>();
                    _location.put("Long", location.getLongitude());
                    _location.put("Lat", location.getLatitude());

                    _db.collection("Ubicaciones").add(_location).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getContext(),"Se agrego ubicación " + documentReference.getId(),Toast.LENGTH_SHORT).show();
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

                                if(!Libreria.isConneted(getContext()))
                                {
                                    Toast.makeText(getContext(),"Verifica la señal de internet.",Toast.LENGTH_LONG).show();
                                    return;
                                }
                                _ubicaciones.clear();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    _ubicacion = new UbicacionModel();
                                    _ubicacion.SetLongitud(document.getDouble("Long"));
                                    _ubicacion.SetAltitud(document.getDouble("Lat"));
                                    onMapClick(new LatLng(_ubicacion.GetAltitud(), _ubicacion.GetLongitud()));
                                    _ubicaciones.add(_ubicacion);
                                }
                                Toast.makeText(getContext(),"Numero de Elementos " + _ubicaciones.size(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        };
        int pcheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 * 30, 1, lListener);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try{
            _db = FirebaseFirestore.getInstance();
            _ubicaciones = new ArrayList<>();
            solicitarPermisos();
            getUbicacion();
        }
        catch (Exception ex){

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ubicacion, container, false);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());
        if(status == ConnectionResult.SUCCESS){
            SupportMapFragment smf= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);
            smf.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        LatLng casa = new LatLng( 19.7726965,-98.9737864);
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.getUiSettings().setZoomControlsEnabled(false);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(casa, 15));

        mapa.addMarker(new MarkerOptions()
                .position(casa)
                .title("Casa")
                .snippet("Mi Casa")
                .icon(BitmapDescriptorFactory
                        .fromResource(android.R.drawable.ic_menu_compass))
                .anchor(0.5f, 0.5f));
        mapa.setOnMapClickListener(this);
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mapa.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
    }
}