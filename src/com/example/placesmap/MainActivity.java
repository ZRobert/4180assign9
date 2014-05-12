//In class 9
//MainActivity.java
//Robert Payne
package com.example.placesmap;

//android api key 
//AIzaSyCUouZp1Oq9-wXbilTwHWXr2mwnAZwWFAE
//browser api key
//AIzaSyDzjiTO0OHBzlXH_8aCsHBAbg18h05mu6k
import java.util.ArrayList;

import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
	ListView listView;
	LocationManager locationMngr;
	Location location;
	Geocoder geocoder;
	ArrayList<String> options = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		options.add("doctor");
		options.add("dentist");
		options.add("food");
		options.add("park");
		options.add("movie_theater");
		options.add("gas_station");
		options.add("lodging");
		options.add("atm");
		options.add("gym");
		options.add("library");
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getBaseContext(), Places.class);
				i.putExtra("lat", Double.toString(location.getLatitude()));
				i.putExtra("lng", Double.toString(location.getLongitude()));
				i.putExtra("radius", "200");
				i.putExtra("type", options.get(position));
				i.putExtra("sensor", "true");
				startActivity(i);

			}
		});

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				options);
		listView.setAdapter(adapter);
		geocoder = new Geocoder(this);

		locationMngr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		location = locationMngr
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = locationMngr
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}

		LocationListener simpleLocationListener = new LocationListener() {
			int count = 0;

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}

			public void onLocationChanged(Location location) {
				String loc = location.getLatitude() + ", "
						+ location.getLongitude();
				if (count++ > 10) {
					locationMngr.removeUpdates(this);
				}
			}

		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void onResume() {
		super.onResume();
		if (!locationMngr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("GPS not enabled")
					.setMessage("Would like to enable the GPS settings")
					.setCancelable(true)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									Intent i = new Intent(
											Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivity(i);
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
									finish();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		} else {
			LocationHelper locationHelper = new LocationHelper(locationMngr,
					new Handler(), this);
			locationHelper.getCurrentLocation(3000);
		}
	}

}
