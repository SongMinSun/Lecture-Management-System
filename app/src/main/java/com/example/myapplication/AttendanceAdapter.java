package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendViewHolder> {

    private ArrayList<AttendanceAccount> arrayList;
    private Context context;
    private OnItemClickListener mListener; // 인터페이스 변수 추가

    public AttendanceAdapter(ArrayList<AttendanceAccount> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public AttendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ah_item, parent, false);
        return new AttendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendViewHolder holder, int position) {
        holder.studentname.setText(arrayList.get(position).getStudentname());
        holder.studentnum.setText(arrayList.get(position).getStudentnum());
        holder.attend.setText(arrayList.get(position).getAttend());

        // 아이템 뷰가 클릭되었을 때의 동작
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(adapterPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class AttendViewHolder extends RecyclerView.ViewHolder {
        TextView studentname, studentnum, attend;

        public AttendViewHolder(@NonNull View itemView) {
            super(itemView);
            this.studentname = itemView.findViewById(R.id.studentName);
            this.studentnum = itemView.findViewById(R.id.studentNum);
            this.attend = itemView.findViewById(R.id.attend);
        }
    }
}
