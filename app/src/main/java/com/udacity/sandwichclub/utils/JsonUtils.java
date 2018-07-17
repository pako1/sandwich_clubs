package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        List<String> ingredientsList = new ArrayList<>();
        List<String> secondNamesList = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(json);
            JSONObject nameObject = rootObject.getJSONObject("name");
            String nameOfSandwich = nameObject.getString("mainName");
            JSONArray secondNameArray = nameObject.getJSONArray("alsoKnownAs");
            if (secondNameArray != null) {
                for (int i = 0; i < secondNameArray.length(); i++) {
                    secondNamesList.add(secondNameArray.getString(i));
                }
            }
            String placeOfOrigin = rootObject.getString("placeOfOrigin");
            String description = rootObject.getString("description");
            String sandwichImage = rootObject.getString("image");
            JSONArray ingrediantsArray = rootObject.getJSONArray("ingredients");
            if (ingrediantsArray != null) {
                for (int i = 0; i < ingrediantsArray.length(); i++) {
                    ingredientsList.add(ingrediantsArray.getString(i));
                }
            }
            return new Sandwich(nameOfSandwich, secondNamesList, placeOfOrigin, description, sandwichImage, ingredientsList);
        } catch (JSONException e) {
            Log.e("JsonUtils", "problem by parsing JSON sandwich");
        }
        return null;
    }
}
