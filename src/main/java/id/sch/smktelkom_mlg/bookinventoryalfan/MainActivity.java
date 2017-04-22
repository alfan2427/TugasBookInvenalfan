package id.sch.smktelkom_mlg.bookinventoryalfan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sch.smktelkom_mlg.bookinventoryalfan.activity.Book;
import id.sch.smktelkom_mlg.bookinventoryalfan.activity.Main2Activity;
import id.sch.smktelkom_mlg.bookinventoryalfan.adapter.BooksAdapter;
import id.sch.smktelkom_mlg.bookinventoryalfan.adapter.DividerDecoration;
import id.sch.smktelkom_mlg.bookinventoryalfan.helper.HelperFunction;


public class MainActivity extends AppCompatActivity {
    public int TO_FROM = 1;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.recyclerBook)RecyclerView recyclerBook;
    @BindView(R.id.fab)FloatingActionButton btnAdd;
    private List<Book> bookList = new ArrayList<>();
    private BooksAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Books Catalog");

        mAdapter = new BooksAdapter(this, bookList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerBook.setLayoutManager(mLayoutManager);
        recyclerBook.setItemAnimator(new DefaultItemAnimator());
        recyclerBook.addItemDecoration(new DividerDecoration(this));

        recyclerBook.setAdapter(mAdapter);
        recyclerBook.addOnItemTouchListener(new HelperFunction.RecyclerTouchListener(this, recyclerBook, new HelperFunction.ClickListener(){
            @Override
            public void onClick(View view, int position){
                //implement later
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("bookEdit", bookList.get(position));
                startActivity(i);
            }

            @Override
            public void onLongClick(View view,final int position){
                //implement later
                final Book book = bookList.get(position);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete " + book.getBook_title() + " ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO Auto-generate method stub
                                bookList.remove(book);
                                mAdapter.notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                dialog.cancel();
                            }
                        })
                        .create();
                dialog.show();
            }
        }));

        prepareBookData();

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent app = new Intent (MainActivity.this, Main2Activity.class);
                startActivityForResult(app, TO_FROM);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == TO_FROM){
            Book bookForm = (Book) data.getExtras().getSerializable("book");
            bookList.add(bookForm);
            Toast.makeText(this, "Book " + bookForm.getBook_title() + " successfully added", Toast.LENGTH_SHORT).show();
            mAdapter.notifyDataSetChanged();
        }
    }



    private void prepareBookData() {
        Book book = new Book("9780439064873", "Harry Potter And The Chamber Of Secrets", "J.K. Rowling", 2000, "Fantasy, Thriller, Mystery", "This is some synopsis");
        bookList.add(book);

        book = new Book("9780316015844", "Twilight (The Twilight Saga, Book 1)", "Stephanie Meyer", 2006, "Fantasy, Drama, Romance", "This is some synopsis");
        bookList.add(book);

        mAdapter.notifyDataSetChanged(); }
}