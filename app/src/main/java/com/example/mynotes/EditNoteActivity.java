package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditNoteActivity extends AppCompatActivity {

    EditText editNote;
    TextView date;
    DBExpert dbExpert;
    FloatingActionButton deleteButton;
    int recordId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editNote = findViewById(R.id.edit_note);
        date = findViewById(R.id.edit_text_date);
        deleteButton = (FloatingActionButton) findViewById(R.id.delete_note_button);

        dbExpert = new DBExpert(this);
        SQLiteDatabase database = dbExpert.getReadableDatabase();

        date.setText(getDate());

        Bundle extra = getIntent().getExtras();

        if (!(extra == null)) {

            recordId = extra.getInt("Record_id");
            String selection = "_id = " + recordId;
            Cursor cur = database.query(DBExpert.TABLE, null, selection, null, null, null, null);

            if (cur != null && cur.moveToFirst()) {

                date.setText(cur.getString(cur.getColumnIndexOrThrow(DBExpert.KEY_DATES)));
                editNote.setText(cur.getString(cur.getColumnIndexOrThrow(DBExpert.KEY_NOTES)));
                cur.close();
                database.close();

            }
        }
        else {
            deleteButton.setVisibility(View.GONE);
        }
    }

    public void saveNote(View view) {
        if (recordId == -1){
            recordId = dbExpert.addRecord(String.valueOf(editNote.getText()), getDate());
        }else {
            dbExpert.upgradeRecord(recordId, String.valueOf(editNote.getText()), getDate());
        }
        Intent intent = new Intent(this, ViewNoteActivity.class);
        intent.putExtra("Record_id", recordId);
        startActivity(intent);
    }

    public void deleteNote(View view) {
        dbExpert.deleteRecord(recordId);
        startActivity(new Intent(this, MainActivity.class));
    }

    private static String getDate() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }


}