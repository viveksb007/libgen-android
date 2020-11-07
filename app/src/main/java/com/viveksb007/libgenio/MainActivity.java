package com.viveksb007.libgenio;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String BASE_URL = "http://libgen.rs/search.php?req=";
    private List<Book> bookList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BooksAdapter booksAdapter;
    boolean doubleBackToExitPressedOnce = false;
    private ProgressBar progressBar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.book_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        booksAdapter = new BooksAdapter(this, bookList);
        recyclerView.setAdapter(booksAdapter);

        progressBar = findViewById(R.id.progress_bar_cyclic);
        progressBar.setVisibility(View.GONE);
        searchView = findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                progressBar.setVisibility(View.VISIBLE);
                findBooks(query);
                progressBar.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void findBooks(final String query) {
        new Thread(() -> {
            try {
                bookList.clear();
                Document doc = Jsoup.connect(BASE_URL + query).get();
                bookList.addAll(new BookExtractor().extractBooksFromDocument(doc));

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (bookList.size() == 0)
                            Toast.makeText(MainActivity.this, "Nothing Found.", Toast.LENGTH_SHORT).show();
                        else {
                            updateListView();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateListView() {
        booksAdapter.notifyDataSetChanged();
        Log.v(TAG, bookList.size() + "");
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
