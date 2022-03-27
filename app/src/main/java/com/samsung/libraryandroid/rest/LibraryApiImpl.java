package com.samsung.libraryandroid.rest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.samsung.libraryandroid.MainActivity;
import com.samsung.libraryandroid.domain.Author;
import com.samsung.libraryandroid.domain.Book;
import com.samsung.libraryandroid.domain.Genre;
import com.samsung.libraryandroid.domain.mapper.AuthorMapper;
import com.samsung.libraryandroid.domain.mapper.BookMapper;
import com.samsung.libraryandroid.domain.mapper.GenreMapper;
import com.samsung.libraryandroid.fakedb.LibraryFakeDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LibraryApiImpl implements LibraryApi{

    public static final String BASE_URL = "http://192.168.1.12:8080";
    private final Context context;

    public LibraryApiImpl(Context context) {
        this.context = context;
    }

    @Override
    public void fillBook() {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = BASE_URL + "/book";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            LibraryFakeDb.BOOK_LIST.clear();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = response.getJSONObject(i);

                                Genre genre = new GenreMapper().genreFromBookJsonArray(jsonObject);

                                Author author = new AuthorMapper().authorFromBookJsonArray(jsonObject);

                                Book book = new BookMapper().bookFromJsonArray(jsonObject, author, genre);
                                LibraryFakeDb.BOOK_LIST.add(book);
                            }
                            Log.d("BOOK_LIST", LibraryFakeDb.BOOK_LIST.toString());
                            ((MainActivity) context).update();
                        } catch (JSONException e) {

                            Log.d("BOOK_LIST", e.getMessage());
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(jsonArrayRequest);
    }
}
