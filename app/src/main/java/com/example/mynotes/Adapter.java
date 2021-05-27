package com.example.mynotes;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private static final int EDIT_NOTE_MENU_ITEM_ID = 101;
    private static final int DELETE_NOTE_MENU_ITEM_ID = 102;

    private final Context context;
    private final ArrayList<Note> notes;
    private final DeleteOnClick deleteOnClick;

    public Adapter(Context context, ArrayList<Note> notes, DeleteOnClick deleteOnClick) {
        this.deleteOnClick = deleteOnClick;
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view, deleteOnClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView text;
        TextView date;
        DeleteOnClick deleteOnClick;

        public ViewHolder(@NonNull View itemView, final DeleteOnClick deleteOnClick) {
            super(itemView);
            this.deleteOnClick = deleteOnClick;
            this.itemView = itemView;
            text = itemView.findViewById(R.id.note_item);
            date = itemView.findViewById(R.id.date);

        }

        void bind(Note note) {
            date.setText(note.getDate());
            text.setText(note.getText());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = note.getId();
                    Intent intent = new Intent(context, ViewNoteActivity.class);
                    intent.putExtra("Record_id", id);
                    context.startActivity(intent);
                }
            });

            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.add(Menu.NONE, EDIT_NOTE_MENU_ITEM_ID, Menu.NONE, "Edit...");
                    menu.add(Menu.NONE, DELETE_NOTE_MENU_ITEM_ID, Menu.NONE, "Delete");
                    menu.findItem(EDIT_NOTE_MENU_ITEM_ID).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = note.getId();
                            Intent intent = new Intent(context, EditNoteActivity.class);
                            intent.putExtra("Record_id", id);
                            context.startActivity(intent);
                            return false;
                        }
                    });
                    menu.findItem(DELETE_NOTE_MENU_ITEM_ID).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int position = getAdapterPosition();
                            deleteOnClick.deleteRecord(position);
                            return false;
                        }
                    });
                }
            });
        }
    }
    public interface DeleteOnClick {
        void deleteRecord(int id);
    }
}
