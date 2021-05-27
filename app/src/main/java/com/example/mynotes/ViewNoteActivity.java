package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewNoteActivity extends AppCompatActivity {

    TextView date;
    TextView text;
    DBExpert dbExpert;

    private int record_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        dbExpert = new DBExpert(this);
        SQLiteDatabase database = dbExpert.getReadableDatabase();

        text = findViewById(R.id.view_note);
        date = findViewById(R.id.view_note_date);

        Bundle extra = getIntent().getExtras();
        record_id = extra.getInt("Record_id");
        String selection = "_id = " + record_id;

        Cursor cur = database.query(DBExpert.TABLE, null, selection, null, null, null, null);
        if (cur != null && cur.moveToFirst()) {
            date.setText(cur.getString(cur.getColumnIndexOrThrow(DBExpert.KEY_DATES)));
            text.setText(cur.getString(cur.getColumnIndexOrThrow(DBExpert.KEY_NOTES)));
            cur.close();
            database.close();
        }
    }

    public void editNote(View view) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("Record_id", record_id);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

}