package com.viveksb007.libgenio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by viveksb007 on 16/11/17.
 */

public class BooksAdapter extends BaseAdapter {

    private ArrayList<Book> bookList;
    private Context context;

    public BooksAdapter(Context context, ArrayList<Book> bookList) {
        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int i) {
        return bookList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.book_list_item, viewGroup, false);
        }
        final Book book = bookList.get(i);
        TextView title = view.findViewById(R.id.tv_title);
        TextView author = view.findViewById(R.id.tv_author);
        TextView fileSize = view.findViewById(R.id.tv_file_size);
        TextView fileExtension = view.findViewById(R.id.tv_file_extension);
        TextView pages = view.findViewById(R.id.tv_pages);
        TextView language = view.findViewById(R.id.tv_language);
        TextView year = view.findViewById(R.id.tv_year);
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        fileSize.setText("Size : " + book.getSize());
        fileExtension.setText("Format : " + book.getExtension());
        pages.setText("Pages : " + book.getPages());
        language.setText("Language : " + book.getLanguage());
        year.setText("Year : " + book.getYear());
        ImageButton downloadBookImage = view.findViewById(R.id.img_download);
        downloadBookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked : " + book.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
