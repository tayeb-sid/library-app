package com.example.bookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

//adapt my view to an arrayList of books

public class CustomAdapter extends ArrayAdapter {

    public CustomAdapter(Context context, ArrayList<Book> books) {
        super(context,R.layout.custom_row ,books);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get my custom row that i've created
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.custom_row,parent,false);

        //get the item from the list
        Book book = (Book) getItem(position);

        //get the elements from the view
        TextView row_id= customView.findViewById(R.id.row_id);
        TextView row_author= customView.findViewById(R.id.row_author);
        TextView row_title= customView.findViewById(R.id.row_title);
        TextView row_nbPages= customView.findViewById(R.id.row_nbPages);

        //set the value of each element to the elements of list

        row_id.setText(String.valueOf(book.getBook_id()));
        row_author.setText(book.getAuthor());
        row_title.setText(book.getTitle());
        row_nbPages.setText(String.valueOf(book.getNb_pages()));

        //return the custom row
        return customView;


    }
}
