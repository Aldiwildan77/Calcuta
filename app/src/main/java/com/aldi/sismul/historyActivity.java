package com.aldi.sismul;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class historyActivity extends AppCompatActivity {

    private static final String TAG = "historyActivity";

    DatabaseHelper databaseHelper;

    private ListView listView;
    private Button delete;
    private ArrayList<String> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        databaseHelper = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.listView);
        delete = (Button) findViewById(R.id.deleteHistory);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHistory();
            }
        });

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Menampilkan data di History");

        Cursor data = databaseHelper.getData();
        while (data.moveToNext()) {
            listData.add(data.getString(0) + " = " + data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }

    private void deleteHistory() {
        databaseHelper.removeData();
        Log.d(TAG, "deleteHistory: History Terhapus");

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        ((ArrayAdapter) adapter).notifyDataSetChanged();

        // refresh activity
        recreate();
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
