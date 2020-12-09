package com.abiel.abiel.Controller;

import android.Manifest;
import android.content.pm.PackageManager;
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

import com.abiel.abiel.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ubicacion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ubicacion extends Fragment {


    public ubicacion() {

    }

    // TODO: Rename and change types and number of parameters
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
            }
        };
        int pcheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, lListener);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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