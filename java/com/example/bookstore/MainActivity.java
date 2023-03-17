package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private  ListView listView;
    FloatingActionButton addBtn;
    ArrayList<Book> books;
    MyDbHelper db = new MyDbHelper(MainActivity.this);
    TextView emptyText;
    ImageView emptyImg;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn=findViewById(R.id.add_btn);

        addBtn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this,AddBookActivity.class);
            startActivity(i);

        });

        listView = findViewById(R.id.listView);
        emptyImg=findViewById(R.id.emptyImg);
        emptyText = findViewById(R.id.emptyText);



        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i  = new Intent(MainActivity.this,updateActivity.class);
            //send each book's data to the update page
            i.putExtra("book_id",String.valueOf(books.get(position).getBook_id()));
            i.putExtra("book_title",books.get(position).getTitle() );
            i.putExtra("book_author",books.get(position).getAuthor() );
            i.putExtra("book_pages",String.valueOf(books.get(position).getNb_pages()));

            startActivity(i);
        });



    }

    protected void onResume(){
        super.onResume();
        books= db.DbToArrayList();
        ListAdapter myAdapter=new CustomAdapter(MainActivity.this,books);
        listView.setAdapter( myAdapter);

        //if there are no elements we show the empty icon
        if(books.isEmpty()){
            emptyText.setVisibility(View.VISIBLE);
            emptyImg.setVisibility(View.VISIBLE);
        }
        else{
            emptyText.setVisibility(View.GONE);
            emptyImg.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //setup our custom menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    //like a listener for the menu items
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //if clicked item is delete all icon
        if(item.getItemId() == R.id.deleteAll){
            if(listView.getChildCount()!=0) confirmDialog();
            else Toast.makeText(this,"No books",Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }

    public void refresh(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public  void confirmDialog(){
        //dialog box
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        //title + message
        builder.setTitle("Delete All ?");
        builder.setMessage("Are you sure you want to delete all the books ?");
        //yes btn
        builder.setPositiveButton("Yes", (dialog, which) -> {
            MyDbHelper db = new MyDbHelper(MainActivity.this);
            db.deleteAll();
            refresh();
            Toast.makeText(this,"All books deleted",Toast.LENGTH_SHORT).show();
        });
        //no btn
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}