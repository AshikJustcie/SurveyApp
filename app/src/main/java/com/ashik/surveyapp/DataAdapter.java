package com.ashik.surveyapp;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<DataModel> arrayListF;
    private Context context;
    private ItemClickListener clickListener;

    // data is passed into the constructor
    public DataAdapter(Context context, List<DataModel> arrayListF) {
        this.context = context;
        this.arrayListF = arrayListF;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.recitem_survey, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final DataModel arrayList = arrayListF.get(position);

        int count = arrayList.getCount();
        String date = arrayList.getDate();

        String id = "Survey No " + position;
        holder.surveyNo.setText(id);
        holder.date.setText(date);

        if (count == 5) {
            holder.status.setText("Completed");
        }
        else {
            holder.status.setText("In Progress");
        }


        if (holder.dotsLayout.getChildCount() > 0)
            holder.dotsLayout.removeAllViews();

        ImageView dots[] = new ImageView[5];

        for (int i = 0; i < 5 ; i++) {
            dots[i] = new ImageView(context);

            if (i < count) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(context,R.drawable.active_dots));
            }
            else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(context,R.drawable.inactive_dots));
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4,0,4,0);
            holder.dotsLayout.addView(dots[i],layoutParams);
        }


    }

    // total number of cells
    @Override
    public int getItemCount() {
        return arrayListF.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView surveyNo, date, status;
        LinearLayout dotsLayout;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            surveyNo = itemView.findViewById(R.id.surveyNo);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);

            dotsLayout = itemView.findViewById(R.id.dotContainer);

            cardView = itemView.findViewById(R.id.parentLay);
            cardView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());

        }

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}
