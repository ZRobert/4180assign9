//In class 9
//ASyncGetPlaces.java
//Robert Payne
package com.example.placesmap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONException;
import com.example.placesmap.PlaceJSONParser.PlaceJSONParserUtil;

import android.os.AsyncTask;
import android.util.Log;

public class ASyncGetPlaces extends AsyncTask<String, Void, ArrayList<Place>> {
	Places placesActivity;

	public ASyncGetPlaces(Places places) {
		// TODO Auto-generated constructor stub
		this.placesActivity = places;
	}

	@Override
	protected ArrayList<Place> doInBackground(String... params) {
		// https://maps.googleapis.com/maps/api/place/nearbysearch/
		// json?location=-33.8670522,151.1957362&radius=500&
		// types=food&name=harbour&sensor=false&key=AddYourOwnKeyHere
		ArrayList<Place> places = null;

		String urlBase = "https://maps.googleapis.com/maps/api/place/radarsearch/json?"
				+ "location="
				+ params[0]
				+ ","
				+ params[1]
				+ "&radius="
				+ params[2]
				+ "&types="
				+ params[3]
				+ "&sensor="
				+ params[4]
				+ "&key=" + "AIzaSyDzjiTO0OHBzlXH_8aCsHBAbg18h05mu6k";
		Log.d("URL:", urlBase);

		try {
			StringBuilder sb = null;
			URL url = new URL(urlBase);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			int statusCode = con.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
			}
			try {
				places = PlaceJSONParserUtil.parsePlaces(sb.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return places;
	}

	@Override
	protected void onPostExecute(ArrayList<Place> result) {
		// TODO Auto-generated method stub
		placesActivity.receiveArrayList(result);
	}

}
