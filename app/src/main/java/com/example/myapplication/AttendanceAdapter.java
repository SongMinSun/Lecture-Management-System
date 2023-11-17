package com.example.myapplication;
// AttendanceAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendViewHolder> {

    private ArrayList<AttendanceAccount> arrayList;
    private Context context;
    private OnItemClickListener mListener;

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

        // Modify 버튼 클릭 이벤트 처리
        holder.modify_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(holder.getAdapterPosition());
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
        Button modify_Btn;

        public AttendViewHolder(@NonNull View itemView) {
            super(itemView);
            this.studentname = itemView.findViewById(R.id.studentName);
            this.studentnum = itemView.findViewById(R.id.studentNum);
            this.attend = itemView.findViewById(R.id.attend);
            this.modify_Btn = itemView.findViewById(R.id.modify_Btn);
        }
    }
}
