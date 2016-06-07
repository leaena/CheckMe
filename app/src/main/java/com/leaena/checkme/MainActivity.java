package com.leaena.checkme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int EDIT_REQUEST_CODE = 10;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    TodoDBHelper todoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todoDB = new TodoDBHelper(this);
        items = todoDB.getAllItems();
        lvItems = (ListView) findViewById(R.id.lvItems);

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    //one onActivityResult to rule them all
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            updateListItem(i.getStringExtra("edit_text"), i.getIntExtra("edit_text_pos", 0));
        }
    }

    public void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                todoDB.deleteItem(pos);
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                String itemText = items.get(pos);
                launchEditView(itemText, pos);
            }
        });
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        todoDB.insertItem(itemText);
    }

    //launches EditItemActivity for one item string value
    public void launchEditView(String itemText, int pos) {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        // add text to be edited to intent
        i.putExtra("edit_text", itemText);
        i.putExtra("edit_text_pos", pos);
        //start an activity with a request code to filter return result
        startActivityForResult(i, EDIT_REQUEST_CODE);
    }

    public void updateListItem(String editText, int pos) {
        //update item at position
        items.set(pos, editText);
        //let the list view know it needs to change how it looks
        itemsAdapter.notifyDataSetChanged();
        todoDB.updateItem(pos, editText);
    }
}
