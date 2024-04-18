
package utd.cs.myapplication2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.location.Geocoder;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class Navigate extends AppCompatActivity {
    private MapController mapController;
    private MapView mapView;
    private TextView edt;

    Double Latitude = null;
    Double Longitude = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);

        // Important! Set the user agent to prevent issues with downloading map tiles.
        Configuration.getInstance().setUserAgentValue(getPackageName());

        //Get text from Intent
        Intent intent = getIntent();
        // get the passed value from source activity to the getCountry variable
        String getCountry = intent.getStringExtra("countryName");

        edt = (TextView) findViewById(R.id.txtCountry);
        edt.setText(getCountry);
        //inbuild java object
        Geocoder gc = new Geocoder(this);

        List<Address> address;
        LatLng p1 = null;
        //
        try {
            address = gc.getFromLocationName(getCountry, 5);
            Address location = address.get(0);
            Latitude = location.getLatitude();
            Longitude = location.getLongitude();

            System.out.println("Getting location!");
            System.out.println(location.getLatitude());
            System.out.println(location.getLongitude());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Find the map view from the layout
        mapView = findViewById(R.id.map);
        // Set the map tile source (choose from various sources, this uses the default OSM source)
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapView.setBuiltInZoomControls(true);
        mapView.setBuiltInZoomControls(true); //display the zoom button
        mapView.setMultiTouchControls(true); //turn on the multiple touch feature
        mapController = (MapController) mapView.getController();
        // Set the initial map center and zoom level
        mapController.setZoom(7);
        //GeoPoint startPoint = new GeoPoint(51.5074, -0.1278); // London, UK
        GeoPoint startPoint = new GeoPoint(Latitude, Longitude); // INDIA
        //GeoPoint startPoint = new GeoPoint(-40.900557, 174.885971);// NEW ZEALAND
        mapController.setCenter(startPoint);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume(); // Call this method to refresh the map when the activity resumes
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause(); // Call this method to pause the map rendering when the activity is paused
    }
}

