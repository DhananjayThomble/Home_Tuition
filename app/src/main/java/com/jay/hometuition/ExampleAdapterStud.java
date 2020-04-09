package com.jay.hometuition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapterStud extends RecyclerView.Adapter<ExampleAdapterStud.ExampleViewHolder> {
    //6.2
    private ArrayList<ExampleItemStudent> mExampleList;

    //1
    public class ExampleViewHolder extends RecyclerView.ViewHolder {

        //4
        public ImageView imageView;
        public TextView textViewDetail;
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
            imageView = itemView.findViewById(R.id.cardImageViewStud);
            textViewDetail = itemView.findViewById(R.id.cardTxtStud);

//            txt.setText(position);

        }


    }

    //6.1
    public ExampleAdapterStud(ArrayList<ExampleItemStudent> exampleList, onClickInterfaceStud onClickInterface){
        mExampleList = exampleList;

    }


    //3
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //5
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item_student, parent, false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(view);
        return exampleViewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, final int i) {
        //7
        ExampleItemStudent currentItem = mExampleList.get(i);

        //Fetch data from ExampleItemStudent and store it in var
        String mName1, mClass1, mSchool1, mGender1, mBoard1, mMarks1, mSubject1, mCity1;
        mName1 = currentItem.getmName();
        mClass1 = currentItem.getmClass();
        mSchool1 = currentItem.getmSchool();
        mGender1 = currentItem.getmGender();
        mBoard1 = currentItem.getmBoard();
        mMarks1 = currentItem.getmMarks();
        mSubject1 = currentItem.getmSubject();
        mCity1 = currentItem.getmCity();


        exampleViewHolder.imageView.setImageResource(currentItem.getmImageResource());
        exampleViewHolder.textViewDetail.setText("Name:- "+mName1 + "\n" + "Class:- "+mClass1 + "\n" + "School:- "+mSchool1 + "\n" +
                                            "Gender:- "+mGender1 + "\n" + "Board:- "+mBoard1 + "\n" +
                                            "Marks:- "+mMarks1 + "\n" + "Subject:- "+mSubject1 + "\n" + "City:- "+mCity1);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }




}
