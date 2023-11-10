package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder>{

    private ArrayList<ClassAccount> arrayList;
    private Context context;

    public ClassAdapter(ArrayList<ClassAccount> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classitem, parent, false);
        ClassViewHolder holder = new ClassViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.cm_btn.setText(arrayList.get(position).getClassname());
    }

    @Override
    public int getItemCount() {
        //삼항 연산자
        return (arrayList != null ? arrayList.size():0);
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView cm_btn;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cm_btn = itemView.findViewById(R.id.cname);
        }
    }
}
