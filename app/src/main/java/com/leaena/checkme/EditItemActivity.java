package com.leaena.checkme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    int editItemPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String editText = getIntent().getStringExtra("edit_text");
        editItemPosition = getIntent().getIntExtra("edit_text_pos", 0);
        EditText etEditText = (EditText)findViewById(R.id.etEditText);
        etEditText.setText(editText);
    }

    public void onEditItem(View v) {
        EditText etEditText = (EditText) findViewById(R.id.etEditText);
        String editText = etEditText.getText().toString();
        Intent i = new Intent();
        //return updated text value
        i.putExtra("edit_text",editText);
        i.putExtra("edit_text_pos", editItemPosition);
        setResult(RESULT_OK, i);
        finish();
    }
}
