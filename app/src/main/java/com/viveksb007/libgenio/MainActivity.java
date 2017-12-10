package com.viveksb007.libgenio;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String BASE_URL = "http://libgen.io/search.php?req=";
    private ArrayList<Book> bookList = new ArrayList<>();
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
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void findBooks(final String query) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bookList.clear();
                    Document doc = Jsoup.connect(BASE_URL + query).get();
                    if (doc == null) return;
                    Elements elements = doc.getElementsByTag("tr");
                    for (Element element : elements) {
                        if ("top".equals(element.attr("valign")) && ("#C6DEFF".equals(element.attr("bgcolor")) || "".equals(element.attr("bgcolor")))) {
                            Elements bookElements = element.children();
                            Book book = new Book();
                            book.setID(bookElements.get(0).text());
                            Elements authors = bookElements.get(1).children();
                            StringBuilder author = new StringBuilder();
                            for (Element authorElements : authors) {
                                author.append(authorElements.text());
                                author.append(", ");
                            }
                            author.setLength(author.length() - 2);
                            book.setAuthor(author.toString());
                            book.setTitle((bookElements.get(2).children()).get(0).text());
                            book.setPublisher(bookElements.get(3).text());
                            book.setYear(bookElements.get(4).text());
                            book.setPages(bookElements.get(5).text());
                            book.setLanguage(bookElements.get(6).text());
                            book.setSize(bookElements.get(7).text());
                            book.setExtension(bookElements.get(8).text());
                            book.setDownloadLink((bookElements.get(9).children()).get(0).attr("href"));
                            bookList.add(book);
                            //book.logBook();
                            /*
                            Log.v(TAG, bookElements.get(0).text());
                            Log.v(TAG, (bookElements.get(1).children()).get(0).text());
                            Log.v(TAG, (bookElements.get(2).children()).get(0).text());
                            Log.v(TAG, bookElements.get(3).text());
                            Log.v(TAG, bookElements.get(4).text());
                            Log.v(TAG, bookElements.get(5).text());
                            Log.v(TAG, bookElements.get(6).text());
                            Log.v(TAG, bookElements.get(7).text());
                            Log.v(TAG, bookElements.get(8).text());
                            Log.v(TAG, (bookElements.get(9).children()).get(0).attr("href"));
                            */
                        }
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (bookList.size() == 0)
                                Toast.makeText(MainActivity.this, "Nothing Found.", Toast.LENGTH_SHORT).show();
                            else {
                                updateListView();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
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
