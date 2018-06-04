package com.example.dhyatmika.fp_layout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.TrainViewHolder> {

    ArrayList<String> trainList;

    // inner class that represents the Java object of a single row
    // From here, we bind the elements we have in station_detail to a variable
    public static class TrainViewHolder extends RecyclerView.ViewHolder {

        TextView trainFromTextView;
        TextView trainToTextView;
        TextView etaTextView;

        public TrainViewHolder(View itemView) {
            super(itemView);

            // this is where we bind elements to the variable for accessing on the adapter
            trainFromTextView = itemView.findViewById(R.id.trainFromTextView);
            trainToTextView = itemView.findViewById(R.id.trainToTextView);
            etaTextView = itemView.findViewById(R.id.etaTextView);
        }
    }

    public TrainAdapter(ArrayList<String> trainList) {
        // get an arrayList that determines the content of the adapter
        this.trainList = trainList;
    }

    @NonNull
    @Override
    public TrainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // we need to "inflate" our layout for each row, and then pass it along
        // to our view holder
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.train_detail, parent, false);
        TrainViewHolder viewHolder = new TrainViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrainViewHolder holder, int position) {

        // we customize our row (ViewHolder) here

        // ----------------------- ATTENTION ----------------------
        // THIS HAS NOT BEEN MODIFIED, PLEASE MODIFY USING TRAIN MODEL
        holder.trainFromTextView.setText(trainList.get(position));
        holder.trainToTextView.setText(trainList.get(position));
        holder.etaTextView.setText(trainList.get(position));
    }

    @Override
    public int getItemCount() {
        return trainList.size();
    }

}
