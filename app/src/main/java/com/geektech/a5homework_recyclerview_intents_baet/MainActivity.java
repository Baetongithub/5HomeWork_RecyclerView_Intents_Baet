package com.geektech.a5homework_recyclerview_intents_baet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickListener {

    public RecyclerView recyclerView;
    private MainAdapter adapter;
    public List<Title> list;
    public static String KEY = "key";
    public static int REQUEST_CODE = 1;
    public static int REQUEST_CODE2 = 2;
    int position;

    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent getDateIntent = getIntent();

        if (getDateIntent.hasExtra(Intent.EXTRA_TEXT)){
            date = getDateIntent.getStringExtra(Intent.EXTRA_TEXT);
        }

        init();
    }


    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new Title("Item " + i + " Список заметок"));
        }

        adapter = new MainAdapter(list, date);
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT
            | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            int positionDrag = viewHolder.getAdapterPosition();
            int positionTarget = target.getAdapterPosition();
            Collections.swap(adapter.list, positionDrag, positionTarget);
            adapter.notifyItemMoved(positionDrag, positionTarget);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            adapter.list.remove(viewHolder.getAdapterPosition());
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    };

    @Override
    public void onItemClick(Title title, int pos) {
        this.position = pos;
        Intent intent = new Intent(this, ActivityTwo.class);
        intent.putExtra(KEY, title);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE2 && resultCode == RESULT_OK && data != null) {
            Title title = (Title) data.getSerializableExtra(ActivityTwo.KEY2);
            adapter.addItem(title);
        }

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Title title = (Title) data.getSerializableExtra(ActivityTwo.KEY2);
            adapter.setElement(title, position);
        }
    }

    public void buttonOpenMain(View view) {
        Intent intentOpen = new Intent(MainActivity.this, ActivityTwo.class);
        startActivityForResult(intentOpen, REQUEST_CODE2);
    }
}
