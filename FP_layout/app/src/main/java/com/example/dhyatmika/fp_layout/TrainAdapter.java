package com.example.dhyatmika.fp_layout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dhyatmika.fp_layout.models.Train;

import java.util.ArrayList;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.TrainViewHolder> {

    ArrayList<Train> trainList;

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

    public TrainAdapter(ArrayList<Train> trainList) {
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
        Train train = trainList.get(position);
        int eta = train.getEta();
        String etaText = "ETA to this station: ";

        holder.trainFromTextView.setText("From: " + train.getLastStation());
        holder.trainToTextView.setText("To: " + train.getNextStation());

        if (eta > 0)
            holder.etaTextView.setText(etaText + Integer.toString(eta) + " minute(s)");
        else
            holder.etaTextView.setText(etaText + "Arriving in a few seconds");
    }

    @Override
    public int getItemCount() {
        return trainList.size();
    }

}
