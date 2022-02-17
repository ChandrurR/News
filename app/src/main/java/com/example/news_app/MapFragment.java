package com.example.news_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment implements OnMapReadyCallback
{

    GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); // background task
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap)
    {
        map = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        LatLng latLng = new LatLng(12.9225476, 80.10);
//        googleMap.addMarker(new MarkerOptions().position(latLng).title("Its Chennai").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
//
//        latLng = new LatLng(12.8983,  80.2063);
//        googleMap.addMarker(new MarkerOptions().position(latLng).title("Global hospital chennai").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

        getLocationFused();
    }

    public void getLocationFused()
    {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnFailureListener(requireActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireActivity(), "Unable to find location.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            extractLoc(location);
                        }
                    }
                });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {

                Location location=new Location(LocationManager.FUSED_PROVIDER);
                location.setLatitude(latLng.latitude);
                location.setLongitude(latLng.longitude);
                extractLoc(location);
            }
        });
    }

    public void extractLoc(Location location) {
        double lat = location.getLatitude();
        double longi = location.getLongitude();
        LatLng latLng = new LatLng(lat, longi);
        map.addMarker(new MarkerOptions().position(latLng).title(getName(location)).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                ((MainActivity)requireContext()).loadHome(getName(location).substring(0,getName(location).indexOf(",")));
                return false;
            }
        });
    }
    public String getName(Location location){

        Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());
        String result = "Its my location";
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    location.getLatitude(), location.getLongitude(), 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append(", ");
                }
                sb.append(address.getLocality()).append(", ");
                sb.append(address.getPostalCode()).append(", ");
                sb.append(address.getCountryName());
                result = sb.toString();
            }
        } catch (IOException e) {

        }
        return result;
    }
}
