package com.jay.hometuition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    //6.2
    private ArrayList<ExampleItem> mExampleList;

    //1
    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        //4
        public ImageView imageView;
        public TextView textViewText;
        private View.OnClickListener onItemClickListener;

        public void setItemClickListener(View.OnClickListener clickListener) {
            onItemClickListener = clickListener;
        }

        //2
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) itemView.getTag();
//            int position = viewHolder.getAdapterPosition();


            //4
            imageView = itemView.findViewById(R.id.imageViewTutor);
            textViewText = itemView.findViewById(R.id.textViewTutor);



//            txt.setText(position);

        }


    }

    //6.1
    public ExampleAdapter(ArrayList<ExampleItem> exampleList, onClickInterface onClickInterface){
        mExampleList = exampleList;

    }


    //3
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //5
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(view);
        return exampleViewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, final int i) {
        //7
        ExampleItem currentItem = mExampleList.get(i);
        exampleViewHolder.imageView.setImageResource(currentItem.getmImageResource());
        exampleViewHolder.textViewText.setText(currentItem.getmName());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }




}
