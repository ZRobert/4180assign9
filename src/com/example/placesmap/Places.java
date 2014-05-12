//In class 9
//Places.java
//Robert Payne
package com.example.placesmap;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class Places extends Activity implements SendData {
	ArrayList<Place> places;
	Location location;
	String lat;
	String lng;
	String radius;
	String type;
	String sensor;
	GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places);
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		mMap.setMyLocationEnabled(true);
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

		if (getIntent().getExtras() != null) {

			lat = (String) getIntent().getExtras().get("lat");
			lng = (String) getIntent().getExtras().get("lng");
			radius = (String) getIntent().getExtras().get("radius");
			type = (String) getIntent().getExtras().get("type");
			sensor = (String) getIntent().getExtras().getString("sensor");

		}

		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(Double.valueOf(lat), Double.valueOf(lng)), 13));

		new ASyncGetPlaces(this).execute(new String[] { lat, lng, radius, type,
				sensor });
		Log.d("Places", "called Async");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.places, menu);
		return true;
	}

	public void addToList(Place place) {
		// TODO Auto-generated method stub
		places.add(place);
	}

	@Override
	public void receiveArrayList(ArrayList<Place> places) {
		// TODO Auto-generated method stub
		this.places = places;
		for (int i = 0; i < places.size(); i++) {
			if (places.get(i).getName().equals("")) {
				mMap.addMarker(new MarkerOptions().position(new LatLng(Double
						.valueOf(places.get(i).getLat()), Double.valueOf(places
						.get(i).getLng()))));
			} else {
				mMap.addMarker(new MarkerOptions().position(
						new LatLng(Double.valueOf(places.get(i).getLat()),
								Double.valueOf(places.get(i).getLng()))).title(
						places.get(i).getName()));
			}

		}
	}

}
