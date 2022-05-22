package com.flowcoding.recyclerviewproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private ArrayList<ExampleItem> mExampleList;
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter; // provide only as many items as we need
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        createExampleList();
        buildRecyclerView();

        buttonInsert = findViewById(R.id.button_insert);
        buttonRemove = findViewById(R.id.button_remove);
        editTextInsert = findViewById(R.id.edittext_insert);
        editTextRemove = findViewById(R.id.edittext_remove);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextInsert.getText().toString());
                insertItem(position);
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextInsert.getText().toString());
                removeItem(position);
            }
        });

    }

    public void insertItem(int position)
    {
        mExampleList.add(position, new ExampleItem(
                                    R.drawable.ic_android
                                    , "new item at position " + position
                                    , "this is line 2"));
        mAdapter.notifyItemInserted(position);

    }

    public void removeItem (int position)
    {
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItem (int position, String text)
    {
        mExampleList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }

    private void buildRecyclerView() {

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position)
            {
                Toast.makeText(getBaseContext(), "You've Selected This Item", Toast.LENGTH_SHORT).show();
                changeItem(position, "⭐");
            }
        });
    }

    private void createExampleList() {
        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem(R.drawable.ic_android, "Line 1", "Line 2"));
        mExampleList.add(new ExampleItem(R.drawable.ic_audio, "Line 3", "Line 4"));
        mExampleList.add(new ExampleItem(R.drawable.ic_sun, "Line 5", "Line 6"));

    }
}
