package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> itemsList;
    protected Button addItem;
    protected EditText writeItem;
    protected RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addItem = findViewById(R.id.btnAdd);
        writeItem = findViewById(R.id.insertItem);
        rvItems = findViewById(R.id.rvItems);

        loadData();

        ItemsAdapter.OnLongClickListener OnLongClickListener = new ItemsAdapter.OnLongClickListener(){

            @Override
            public void onLongClickListener(int pos) {
                itemsList.remove(pos);
                itemsAdapter.notifyItemRemoved(pos);
                // displays message at the bottom of the display.
                Toast.makeText(MainActivity.this, "Item Removed", Toast.LENGTH_SHORT).show();
                saveData();
            }
        };

        itemsAdapter = new ItemsAdapter(itemsList, OnLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getString = writeItem.getText().toString();
                itemsList.add(getString);

                itemsAdapter.notifyItemInserted(itemsList.size() -1);
                writeItem.setText("");
                Toast.makeText(MainActivity.this, "Item Inserted", Toast.LENGTH_SHORT).show();
                saveData();
            }
        });
    }

    // creates a file that will store current list
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    // lead the data to the view by reading each lines of the file.
    private void loadData(){
        try {
            itemsList = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException e){
            e.printStackTrace();
            // info displayed in the console if catched an exception.
            Log.e("MainActivity", "Error reading items", e);
            itemsList = new ArrayList<>();
        }
    }

    // writes the current data from the arraylist and saves to the file.
    private void saveData(){
        try {
            FileUtils.writeLines(getDataFile(), itemsList);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("MainActivity", "Error Writing items", e);
        }
    }
}