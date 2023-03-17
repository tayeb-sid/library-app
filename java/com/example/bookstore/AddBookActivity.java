package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

public class AddBookActivity extends AppCompatActivity {
    EditText title,author,nbPages;
    Button addBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        title = findViewById(R.id.bookTitle);
        author=findViewById(R.id.author);
        nbPages=findViewById(R.id.nbPages);

        addBtn=findViewById(R.id.addBtn);


        addBtn.setOnClickListener(v -> {
            String titleInput= title.getText().toString().trim();
            String authorInput= author.getText().toString().trim();
            String nbPagesInput= nbPages.getText().toString().trim();


            if(titleInput.isEmpty() || nbPagesInput.isEmpty() || authorInput.isEmpty()) Toast.makeText(this,"Please Fill all the fields",Toast.LENGTH_SHORT).show();
            else{

            Book book = new Book(Integer.valueOf(nbPagesInput),titleInput,authorInput);
            MyDbHelper dbHelper = new MyDbHelper(AddBookActivity.this);

            dbHelper.addBook(book);
            title.setText("");
            author.setText("");
            nbPages.setText("");

            }


        });
    }
}