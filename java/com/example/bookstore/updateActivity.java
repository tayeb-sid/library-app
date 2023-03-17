package com.example.bookstore;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class updateActivity extends AppCompatActivity {
    EditText titleInput,authorInput,pagesInput;
    String title,author,pages,id;
    Button updateBtn,deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        titleInput=findViewById(R.id.bookTitle3);
        authorInput=findViewById(R.id.author3);
        pagesInput=findViewById(R.id.nbPages3);
        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn=findViewById(R.id.deleteBtn);

        setUpdatePageFields();

        ActionBar ab = getSupportActionBar();
        if(ab!=null)ab.setTitle(title);

        updateBtn.setOnClickListener(v->{
            MyDbHelper db = new MyDbHelper(updateActivity.this);

            author=authorInput.getText().toString().trim();
            title=titleInput.getText().toString().trim();
            pages=pagesInput.getText().toString().trim();

            db.updateBook(id,title,author,pages);
            goToMainActivity();

        });
        deleteBtn.setOnClickListener(v -> {
            confirmDialog();
        });



    }

    public void setUpdatePageFields(){

        if(getIntent().hasExtra("book_author")&&getIntent().hasExtra("book_title")&&getIntent().hasExtra("book_id")&&getIntent().hasExtra("book_pages")){
        //retreive the intent sent from the main activity

            id=getIntent().getStringExtra("book_id");
            title=getIntent().getStringExtra("book_title");
            author=getIntent().getStringExtra("book_author");
            pages=getIntent().getStringExtra("book_pages");

            //set the fields
            titleInput.setText(title);
            authorInput.setText(author);
            pagesInput.setText(pages);

        }
        else {
            Toast.makeText(this,"No data found",Toast.LENGTH_SHORT).show();
        }
    }
    public void goToMainActivity(){
        Intent  i = new Intent(updateActivity.this,MainActivity.class);
        startActivity(i);
    }

    public  void confirmDialog(){
        //dialog box
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        //title + message
        builder.setTitle("Delete "+title+" ?");
        builder.setMessage("Are you sure you want to delete "+title+" ?");
        //yes btn
        builder.setPositiveButton("Yes", (dialog, which) -> {
            MyDbHelper db = new MyDbHelper(updateActivity.this);
            db.deleteBook(id);
            goToMainActivity();
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