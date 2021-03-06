package cse.dit012.lost.android.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import cse.dit012.lost.R;
import cse.dit012.lost.android.PermissionUtil;
import cse.dit012.lost.android.ui.screen.map.MapViewModel;
import cse.dit012.lost.databinding.FragmentLostMapBinding;
import cse.dit012.lost.model.MapCoordinates;
import cse.dit012.lost.model.broadcast.Broadcast;
import cse.dit012.lost.service.gps.GpsService;

/**
 * Fragment controlling everything which is displayed on the map and the map itself.
 * <p>
 * Author: Benjamin Sannholm, Bashar Oumari, Mathias Drage
 * Uses: {@link PermissionUtil}, {@link MapViewModel}, {@link MapCoordinates}, {@link Broadcast}, {@link GpsService}
 * Used by: res/layout/fragment_map_screen.xml
 */
public final class LostMapFragment extends Fragment {
    // View Binding for layout file
    private FragmentLostMapBinding layoutBinding;

    // Google map
    private MapInfoWindowFragment mapFragment;
    private GoogleMap googleMap;

    // View model for map screen
    private MapViewModel model;

    // Activity launcher used to ask for geolocation permission
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new RequestPermission(), this::onPermissionRequestResult);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Setup view from layout file
        layoutBinding = FragmentLostMapBinding.inflate(inflater, container, false);
        return layoutBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Retrieve view model for map
        model = new ViewModelProvider(requireActivity()).get(MapViewModel.class);

        initializeGoogleMap();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up layout binding when view is destroyed
        layoutBinding = null;
    }

    /**
     * Gets Google map fragment from view and initializes it.
     * When the map is ready, permission is requested and broadcasts are setup on map.
     */
    private void initializeGoogleMap() {
        mapFragment = (MapInfoWindowFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        if (mapFragment != null) {
            // Initializes map and callback is run when map is ready
            mapFragment.getMapAsync(googleMap -> {
                this.googleMap = googleMap;

                requestGeolocationPermissions();
                setupBroadcastsOnMap();
            });
        }
    }

    /**
     * If the user has not given geolocation permission, ask for permission.
     * If the user has already given permission, enable features requiring geolocation.
     */
    private void requestGeolocationPermissions() {
        if (!PermissionUtil.hasPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            enableGeolocationDependentFeatures();
            onLocationPermissionAndMapReady();
        }
    }

    /**
     * When geolocation permission request comes back after asking for it, enable features requiring geolocation if granted.
     *
     * @param granted true if the permission was granted
     */
    private void onPermissionRequestResult(boolean granted) {
        if (granted) {
            enableGeolocationDependentFeatures();
            onLocationPermissionAndMapReady();
        }
    }

    /**
     * Enables geolocation dependant features.
     */
    @SuppressLint("MissingPermission")
    private void enableGeolocationDependentFeatures() {
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    /**
     * Called when *both* map is ready and geolocation permission was given.
     */
    private void onLocationPermissionAndMapReady() {
        gotoCurrentLocation();
    }

    /**
     * Moves map camera to current location of user.
     */
    @SuppressLint("MissingPermission")
    private void gotoCurrentLocation() {
        MapCoordinates location = GpsService.gps.getLocation(requireContext());
        if (location != null) {
            googleMap.moveCamera((CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15)));
        }
    }

    /**
     * Starts listening for broadcast updates and places broadcasts on map whenever they are changed.
     */
    private void setupBroadcastsOnMap() {
        // Listen for changes to active broadcasts
        model.getActiveBroadcastsFilteredByCourse().observe(getActivity(), broadcasts -> {
            // Remove any previously placed markers
            googleMap.clear();

            // For every broadcast, place a marker on the map
            for (Broadcast broadcast : broadcasts) {
                LatLng pos = new LatLng(broadcast.getCoordinates().getLatitude(),
                        broadcast.getCoordinates().getLongitude());
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title(broadcast.getCourse().toString()));
                marker.setTag(broadcast);
            }
        });

        // When marker is pressed, open information window for corresponding broadcast
        googleMap.setOnMarkerClickListener(marker -> {
            Broadcast broadcast = (Broadcast) marker.getTag();

            InfoWindow.MarkerSpecification markerSpec = new InfoWindow.MarkerSpecification(0, 70);
            BroadcastInfoWindowFragment windowFragment = BroadcastInfoWindowFragment.newInstance(broadcast);
            InfoWindow infoWindow = new InfoWindow(marker, markerSpec, windowFragment);
            mapFragment.infoWindowManager().show(infoWindow, true);

            return true;
        });
    }
}