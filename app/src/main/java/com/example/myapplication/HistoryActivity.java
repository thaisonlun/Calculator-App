package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.myapplication.adapter.HistoryAdapter;
import com.example.myapplication.database.HistoryDAO;
import com.example.myapplication.model.HistoryItem;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private HistoryDAO historyDAO;
    private HistoryAdapter adapter;
    private List<HistoryItem> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Initialize database access
        historyDAO = new HistoryDAO(this);
        historyDAO.open();

        // Load history and setup adapter
        historyList = historyDAO.getAllHistory();
        adapter = new HistoryAdapter(this, historyList, historyDAO);
        recyclerView.setAdapter(adapter);

        // Setup back button
        FloatingActionButton fabBack = findViewById(R.id.fabBack);
        fabBack.setOnClickListener(v -> finish());

        // Setup clear history button
        FloatingActionButton fabClearHistory = findViewById(R.id.fabClearHistory);
        fabClearHistory.setOnClickListener(v -> showClearHistoryDialog());
    }

    private void showClearHistoryDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.clear_history)
                .setMessage(R.string.clear_history_confirmation)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    historyDAO.clearHistory();
                    historyList.clear();
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (historyDAO != null) {
            historyDAO.close();
        }
    }
}
