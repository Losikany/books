package com.example.books;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class ApiUtil {
    private ApiUtil() {
    }


    //constant for base url for the google books API
    private static final String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String QUERY_PARAMETER_KEY = "q";
    // public static final String KEY = "key";
    // public static final String API_KEY="AIzaSyAjGNuZEde4TRovhOrTP7nMfYJG274s5k8";
    private final static String ID = "id";
    private final static String TITLE = "title";
    private final static String SUBTITLE = "subTitle";
    private final static String AUTHORS = "authors";
    private final static String PUBLISHERS = "publishers";
    private final static String PUBLISHED_DATE = "publishedDate";
    private final static String ITEMS = "items";
    private final static String VOLUMEINFO = "volumeInfo";
    private static ArrayList<Book> books = new ArrayList<Book>();

    //querry function for the url title
    static URL buildurl(String title) {

        //declaring url and converting into a string

        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon().appendQueryParameter(QUERY_PARAMETER_KEY, title).build();

//creating a new url based on the url string using try block
        try {
            url = new URL(uri.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    //method connecting to the url
    static String getJson(URL url) throws IOException {
//establishing a conenction to the url passed
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        //reaching data
        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            //delimit large to small pattern
            scanner.useDelimiter("\\A");

            //checking if there is data in our scanner

            boolean hasData = scanner.hasNext();
            if (hasData) {
                return scanner.next();

            } else {
                return null;
            }
        } catch (Exception e) {

            Log.d("Error", e.toString());
            return null;

        } finally {
            connection.disconnect();
        }
    }

    static ArrayList<Book> getBooksFromJSON(String json) {
        Log.d("Parse", json);
        try {
            JSONObject jsonBooks = new JSONObject(json);
            JSONArray arrayBooks = jsonBooks.getJSONArray(ITEMS);
            int numberOfBooks = arrayBooks.length();
            Log.d("No Of Books", String.valueOf(numberOfBooks));
            for (int i = 0; i < numberOfBooks; i++) {
                JSONObject bookJSON = arrayBooks.getJSONObject(i);
                JSONObject volumeInfoJSON = bookJSON.getJSONObject(VOLUMEINFO);

                int authorNum = volumeInfoJSON.getJSONArray(AUTHORS).length();
                String[] authors = new String[authorNum];
                for (int j = 0; j < authorNum; j++) {
                    authors[j] = volumeInfoJSON.getJSONArray(AUTHORS).get(j).toString();
                }
                Book book = new Book(bookJSON.getString(ID), volumeInfoJSON.getString(TITLE),
                        (volumeInfoJSON.isNull(SUBTITLE) ? "" :
                                volumeInfoJSON.getString(SUBTITLE)), authors,
                        volumeInfoJSON.optString(PUBLISHERS, "No Publishers"),
                        volumeInfoJSON.optString(PUBLISHED_DATE, "Publish Date Unknown"));
                books.add(book);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Parse", String.valueOf(books.size()));
        return books;
    }

}