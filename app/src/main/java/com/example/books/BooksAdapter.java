package com.example.books;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    ArrayList<Book> books;

    public BooksAdapter(ArrayList<Book> books) {
        this.books = books;

    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.book_list_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int i) {
        Book book = books.get(i);
        holder.bind(book);

    }


    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvAuthors;
        TextView tvDate;
        TextView tvPublishers;


        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthors = itemView.findViewById(R.id.tvAuthor);
            tvDate = itemView.findViewById(R.id.tvPublishedDate);
            tvPublishers = itemView.findViewById(R.id.tvPublisher);


        }

        public void bind(Book book) {
            tvTitle.setText(book.title);
            String authors = "";
            int i = 0;
            for (String author : book.authors) {
                authors += author;
                i++;
                if (i < book.authors.length) {
                    authors += ",";
                }
            }
            tvAuthors.setText(authors);
            tvDate.setText(book.publishedDate);
            tvPublishers.setText(book.publishers);
        }


    }


}
