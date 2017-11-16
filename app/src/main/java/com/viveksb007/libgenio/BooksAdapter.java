package com.viveksb007.libgenio;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {

    private ArrayList<Book> bookList;
    private Context context;

    public BooksAdapter(Context context, ArrayList<Book> bookList) {
        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.title.setText(book.getTitle());
        holder.author.setText("By : " + book.getAuthor());
        holder.fileSize.setText("Size : " + book.getSize());
        holder.fileExtension.setText("Format : " + book.getExtension());
        holder.pages.setText("Pages : " + book.getPages());
        holder.language.setText("Language : " + book.getLanguage());
        holder.year.setText("Year : " + book.getYear());
        holder.overflowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflowButton);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.book_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_download:
                    Toast.makeText(context, "Download Book", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_share:
                    Toast.makeText(context, "Share Book Link", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, author, pages, fileExtension, fileSize, language, year;
        public ImageButton overflowButton;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_title);
            author = view.findViewById(R.id.tv_author);
            pages = view.findViewById(R.id.tv_pages);
            fileExtension = view.findViewById(R.id.tv_file_extension);
            fileSize = view.findViewById(R.id.tv_file_size);
            overflowButton = view.findViewById(R.id.overflow_button);
            language = view.findViewById(R.id.tv_language);
            year = view.findViewById(R.id.tv_year);
        }

    }
}
