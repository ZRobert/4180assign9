//In class 9
//PlaceJSONParser.java
//Robert Payne
package com.example.placesmap;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaceJSONParser {

	static public class PlaceJSONParserUtil {

		static ArrayList<Place> parsePlaces(String in) throws JSONException {

			ArrayList<Place> places = new ArrayList<Place>();
			JSONObject placesJSONObject = new JSONObject(in.toString());
			JSONArray placesJSONArray = placesJSONObject
					.getJSONArray("results");
			for (int i = 0; i < placesJSONArray.length(); i++) {
				JSONObject placeJSONObject = placesJSONArray.getJSONObject(i);
				Place place = new Place(placeJSONObject);
				places.add(place);
			}
			return places;
		}
	}
}
