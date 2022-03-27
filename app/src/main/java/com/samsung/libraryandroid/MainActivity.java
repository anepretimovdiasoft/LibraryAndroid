package com.samsung.libraryandroid;

import static com.samsung.libraryandroid.fakedb.LibraryFakeDb.BOOK_LIST;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.samsung.libraryandroid.adapter.BookAdapter;
import com.samsung.libraryandroid.rest.LibraryApi;
import com.samsung.libraryandroid.rest.LibraryApiImpl;

public class MainActivity extends AppCompatActivity {

    public static final String MSG_NAME = "bookFromListByPos";

    private AppCompatButton fab;

    private FragmentTransaction transaction;

    private BookAdapter bookAdapter;

    private RecyclerView rvBooks;

    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback;

    private final LibraryApi libraryApi = new LibraryApiImpl(this);

    @Override
    protected void onResume() {
        super.onResume();

        libraryApi.fillBook();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvBooks = findViewById(R.id.rv_books);
        bookAdapter = new BookAdapter(this, BOOK_LIST);
        rvBooks.setAdapter(bookAdapter);
    }

    public void update() {

        bookAdapter.notifyDataSetChanged();
    }

}