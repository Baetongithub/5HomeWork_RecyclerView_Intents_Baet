package com.geektech.a5homework_recyclerview_intents_baet;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    public List<Title> list;
    public String date;
    ItemClickListener listener;

    public MainAdapter(List<Title> list, String date) {
        this.list = list;
        this.date = date;
    }

    public void setElement(Title title, int position) {
        list.set(position, title);
        notifyDataSetChanged();
    }

    public void addItem(Title title) {
        list.add(title);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recycler_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        holder.onBind(list.get(position));
        holder.bind(date);
        holder.setListener();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        TextView deleteItem;
        TextView textViewDate;
        Title title;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = itemView.findViewById(R.id.txtView);
            deleteItem = itemView.findViewById(R.id.deleteItem);
            textViewDate = itemView.findViewById(R.id.textViewMain);
        }

        public void onBind(Title title) {
            this.title = title;
            textView.setText(title.name);
        }

        @SuppressLint("SetTextI18n")
        public void bind(String date) {
            textViewDate.setText("Date: " + date);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.deleteItem) {
                removeItem(getAdapterPosition());
            } else if (listener != null) {
                listener.onItemClick(title, getAdapterPosition());
            }
        }

        public void removeItem(int position) {
            list.remove(position);
            notifyItemRemoved(position);
        }

        public void setListener() {
            deleteItem.setOnClickListener(MainViewHolder.this);
        }
    }

    public void setOnClick(ItemClickListener mListener) {
        this.listener = mListener;
    }

    public interface ItemClickListener {
        void onItemClick(Title title, int pos);
    }
}