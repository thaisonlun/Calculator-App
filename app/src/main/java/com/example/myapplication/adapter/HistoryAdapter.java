package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.HistoryDAO;
import com.example.myapplication.model.HistoryItem;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private final List<HistoryItem> historyList;
    private final Context context;
    private final HistoryDAO historyDAO;

    public HistoryAdapter(Context context, List<HistoryItem> list, HistoryDAO dao) {
        this.context = context;
        this.historyList = list;
        this.historyDAO = dao;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvExpression;
        public final TextView tvResult;
        public final TextView tvTimestamp;

        public ViewHolder(View itemView) {
            super(itemView);
            tvExpression = itemView.findViewById(R.id.tvExpression);
            tvResult = itemView.findViewById(R.id.tvResult);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryItem item = historyList.get(position);
        holder.tvExpression.setText(item.getExpression());
        holder.tvResult.setText(item.getResult());
        holder.tvTimestamp.setText(item.getTimestamp());

        holder.itemView.setOnLongClickListener(v -> {
            historyDAO.deleteHistoryItem(item.getId());
            historyList.remove(position);
            notifyItemRemoved(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
