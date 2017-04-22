package id.sch.smktelkom_mlg.bookinventoryalfan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.sch.smktelkom_mlg.bookinventoryalfan.R;


public class Main2Activity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.isbn) EditText isbn;
    @BindView(R.id.title) EditText title;
    @BindView(R.id.author) EditText author;
    @BindView(R.id.tahun) EditText tahun;
    @BindView(R.id.genre) EditText genre;
    @BindView(R.id.synopsis) EditText synopsis;
    @BindView(R.id.save) Button save;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Toast.makeText(Main2Activity.this, "Valid Data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            book = (Book) bundle.getSerializable("bookEdit");
            isbn.setText(book.getISBN());
            tahun.setText(book.getPublished_year() + "");
            author.setText(book.getBook_author());
            title.setText(book.getBook_title());
            synopsis.setText(book.getBook_synopsis());
            genre.setText(book.getBook_genre());
            save.setEnabled(false);
            getSupportActionBar().setTitle(book.getBook_title());
        } else {
            book = new Book();

        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    book.setISBN(isbn.getText().toString());
                    book.setBook_title(title.getText().toString());
                    book.setBook_author(author.getText().toString());
                    book.setPublished_year(Integer.parseInt(tahun.getText().toString()));
                    book.setBook_genre(genre.getText().toString());
                    book.setBook_synopsis(synopsis.getText().toString().equals("") ? "-" :
                            synopsis.getText().toString());

                    Intent i = new Intent();
                    i.putExtra("book", book);
                    setResult(RESULT_OK, i);
                    finish();
                }
                }
            });
        }

    private boolean validate() {
        boolean valid = true;

        String i = isbn.getText().toString();
        String t = title.getText().toString();
        String a = author.getText().toString();
        String th = tahun.getText().toString();

        if(i.isEmpty()){
            isbn.setError("Enter ISBN");
            valid=false;
        }else{
            isbn.setError(null);
        }

        if(t.isEmpty()){
            title.setError("Enter BOOK TITLE");
            valid=false;
        }else{
            title.setError(null);
        }

        if(a.isEmpty()){
            author.setError("Enter Author");
            valid=false;
        }else{
            author.setError(null);
        }
        if(th.isEmpty() || th.length() <4){
            tahun.setError("Enter PUBLISHED YEAR or must yyyy year");
            valid=false;
        }else{
            tahun.setError(null);
        }

        return valid;
    }

}

