package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ConcentrationAdapter extends RecyclerView.Adapter<ConcentrationAdapter.ConcentrationViewHolder> {

    private Context context;
    private ArrayList<ConcentrationAccount> arrayList;
    private ConcentrationAdapter.OnItemClickListener mListener;

    // 생성자 수정
    public ConcentrationAdapter(Context context, ArrayList<ConcentrationAccount> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ConcentrationAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ConcentrationAdapter.ConcentrationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ch_item, parent, false);
        return new ConcentrationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcentrationViewHolder holder, int position) {
        // 텍스트뷰에 의미 있는 텍스트 설정
        holder.studentname.setText(arrayList.get(position).getStudnetname());
        holder.studentnum.setText(arrayList.get(position).getStudentnum());
        holder.high_count.setText(arrayList.get(position).getHigh_count());
        holder.low_count.setText(arrayList.get(position).getLow_count());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ConcentrationViewHolder extends RecyclerView.ViewHolder {

        TextView studentname, studentnum, high_count, low_count;

        public ConcentrationViewHolder(@NonNull View itemView) {
            super(itemView);
            this.studentname = itemView.findViewById(R.id.student_name2);
            this.studentnum = itemView.findViewById(R.id.student_num2);
            this.high_count = itemView.findViewById(R.id.student_high2);
            this.low_count = itemView.findViewById(R.id.student_low2);
        }
    }
}
