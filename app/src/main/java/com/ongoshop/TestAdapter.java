package com.ongoshop;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    List<TestPOJO> list;
    public TestAdapter(Context mContext, List<TestPOJO> list) {
        this.mContext = mContext;
        this.list = list;
    }
    @NonNull

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_test, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hol, int position) {//0,1,2,3..
        Holder h = (Holder) hol;
        final TestPOJO testPOJO = list.get(position);
      /*  if(position%2==0)
        {
            h.rel_back.setBackgroundColor(Color.parseColor("#5fb709"));
        }
        else
        {
            h.rel_back.setBackgroundColor(Color.parseColor("#000000"));
        }*/
        h.tvName.setText("Name : " + testPOJO.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class Holder  extends  RecyclerView.ViewHolder{
        TextView tvName;
        RelativeLayout rel_back;
        public Holder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            rel_back = itemView.findViewById(R.id.rel_back);
        }
    }
}
