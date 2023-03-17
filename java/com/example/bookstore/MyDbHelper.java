package com.example.bookstore;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME="BookStore.db";
    private static final int DB_VERSION =1;

    private static final String BOOKS_TABLE="books";
    private static final String BOOK_ID="book_id";
    private static final String BOOK_AUTHOR="book_auth";
    private static final String BOOK_TITLE="book_title";
    private static final String BOOK_PAGES="book_pages";




    public MyDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creation_query="create table books (book_id integer primary key autoincrement , " +
                "book_auth text, book_title text, book_pages integer);";
        db.execSQL(creation_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table  if exists books");
    onCreate(db);
    }

    public void addBook(Book book){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db =  getWritableDatabase();

        cv.put(BOOK_AUTHOR,book.getAuthor());
        cv.put(BOOK_PAGES,book.getNb_pages());
        cv.put(BOOK_TITLE,book.getTitle());

        long result= db.insert(BOOKS_TABLE,null,cv);
        String message="";
        message = (result != -1) ? "Book added !" : "Error: couldn't add the book";
        //if (result==-1) message="Error: couldn't add the book";
        //else message="Book added !";
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

        db.close();

    }
    public void updateBook(String id,String title,String author,String pages){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db=getWritableDatabase();
        cv.put(BOOK_AUTHOR,author);
        cv.put(BOOK_TITLE,title);
        cv.put(BOOK_PAGES,Integer.valueOf(pages));

        //update table set BookTitle="title" where book_id = id;
        //...
        long result = db.update(BOOKS_TABLE,cv,"book_id=?",new String[]{id});
        String  message="";
        message=(result==-1)?"update failed":"book updated !";
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        db.close();

    }
    public void deleteBook(String id){
        SQLiteDatabase db = getWritableDatabase();
        long result = db.delete(BOOKS_TABLE,"book_id=?",new String[]{id});
        String message="";

        message= (result==-1)?"error couldn't delete this book": "Book deleted";
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

        db.close();
    }
    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from books");
        db.close();
    }

    public ArrayList<Book> DbToArrayList(){
        //insert all the elements in the table into an arraylist
        SQLiteDatabase db= getReadableDatabase();
        ArrayList<Book> books=new ArrayList<>();

        String query="select *  from books";

        //we need a cursor cuz this query returns many lines
        Cursor cr= db.rawQuery(query,null);
        //point the cursor to the first element
        cr.moveToFirst();

        while(!cr.isAfterLast()){
            Book book = new Book();
            @SuppressLint("Range") String id=cr.getString(cr.getColumnIndex(BOOK_ID));
            if(id!=null) book.setBook_id(Integer.parseInt(id));

            @SuppressLint("Range") String title=cr.getString(cr.getColumnIndex(BOOK_TITLE));
            if(title!=null) book.setTitle(title);

            @SuppressLint("Range") String author=cr.getString(cr.getColumnIndex(BOOK_AUTHOR));
            if(author!=null) book.setAuthor(author);

            @SuppressLint("Range") String pages=cr.getString(cr.getColumnIndex(BOOK_PAGES));
            if(pages!=null) book.setNb_pages(Integer.parseInt(pages));
            books.add(book);
            cr.moveToNext();
        }
        db.close();
        return books;
    }
}
