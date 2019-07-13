package com.peter.guardianangel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peter.guardianangel.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private Context mContext;
    private List<String> mData = new ArrayList<>();

    public MessageAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
        return new ViewHolder(view, true);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mData != null && position < getItemCount()) {
            String message = mData.get(position);

            holder.tv_message.setText(message);
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_message;

        public ViewHolder(View itemView, boolean isItem){
            super(itemView);
            if (isItem){
                tv_message = itemView.findViewById(R.id.listview_item_tv_content);
            }
        }
    }
}
