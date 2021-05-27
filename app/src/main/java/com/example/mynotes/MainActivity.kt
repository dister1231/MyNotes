package com.example.mynotes

import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity(contentLayoutId: Int) : AppCompatActivity(contentLayoutId), Adapter.DeleteOnClick {

    private val dbExpert = DBExpert(this)
    private val notes = ArrayList<Note>()
    private var adapter = Adapter(this, notes, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listOfNotes: RecyclerView = findViewById(R.id.recycler)
        listOfNotes.layoutManager = LinearLayoutManager(this)

        val cursor: Cursor = dbExpert.readAllData()

        while (cursor.moveToNext()) {
            val obj = Note(cursor.getInt(cursor.getColumnIndexOrThrow(DBExpert.KEY_ID)),
                                cursor.getString(cursor.getColumnIndexOrThrow(DBExpert.KEY_NOTES)),
                                cursor.getString(cursor.getColumnIndexOrThrow(DBExpert.KEY_DATES)))
            notes.add(obj)
        }
        cursor.close()

        notes.sort()

        adapter = Adapter(this, notes, this)
    }

    override fun deleteRecord(position: Int) {
        dbExpert.deleteRecord(notes[position].id)
        notes.remove(notes[position])
        adapter.notifyItemRemoved(position)
    }
}

//Java version

//package com.example.mynotes;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.view.View;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//public class MainActivity extends AppCompatActivity implements Adapter.DeleteOnClick {
//
//    DBExpert dbExpert;
//    RecyclerView listOfNotes;
//    ArrayList<Note> notes = new ArrayList<>();
//    Adapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        dbExpert = new DBExpert(this);
//        listOfNotes = findViewById(R.id.recycler);
//
//        listOfNotes.setLayoutManager(new LinearLayoutManager(this));
//
//        Cursor cursor = dbExpert.readAllData();
//
//        while (cursor.moveToNext()) {
//
//            Note obj = new Note(cursor.getInt(cursor.getColumnIndexOrThrow(DBExpert.KEY_ID)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(DBExpert.KEY_NOTES)),
//                    cursor.getString(cursor.getColumnIndexOrThrow(DBExpert.KEY_DATES)));
//
//            notes.add(obj);
//        }
//        cursor.close();
//
//        Collections.sort(notes);
//        adapter = new Adapter(this, notes, this);
//        listOfNotes.setAdapter(adapter);
//    }
//
//    public void addNewNote(View view) {
//        startActivity(new Intent(this, EditNoteActivity.class));
//    }
//
//    @Override
//    public void deleteRecord(int position) {
//        dbExpert.deleteRecord(notes.get(position).getId());
//        notes.remove(position);
//        adapter.notifyItemRemoved(position);
//    }
//}