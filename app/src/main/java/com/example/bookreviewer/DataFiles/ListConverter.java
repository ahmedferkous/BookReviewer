package com.example.bookreviewer.DataFiles;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListConverter {
    private Gson gson = new Gson();
    @TypeConverter
    public String listToJson(ArrayList<String> list) {
        return gson.toJson(list);
    }
    @TypeConverter
    public ArrayList<String> jsonToList(String json) {
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
