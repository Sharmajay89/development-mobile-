package com.cws.cwsbaseapplication.model.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cws.cwsbaseapplication.R;
import com.cws.cwsbaseapplication.model.pojo.ActionDetails;

import java.util.ArrayList;

public class SampleRecyclerViewAdapter extends RecyclerView.Adapter<SampleRecyclerViewAdapter.SampleViewHolders>
{
    private ArrayList<ActionDetails> itemList;
    private Context context;

    public SampleRecyclerViewAdapter(Context context, ArrayList<ActionDetails> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public SampleViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.dashboard_item, null);
        return new SampleViewHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(SampleViewHolders holder, int position) {
        holder.mActionName.setText(itemList.get(position).getActionName());
        if((position+1)%getItemCount()/2 == 0){
            holder.mCardView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }else{
            holder.mCardView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }

    class SampleViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mActionName;
        public CardView mCardView;

        public SampleViewHolders(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            mActionName = (TextView) itemView.findViewById(R.id.mActionName);
            mCardView = (CardView) itemView.findViewById(R.id.mCardView);
        }

        @Override
        public void onClick(View view)
        {
            Toast.makeText(view.getContext(),
                    "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public int getItemCount()
    {
        return this.itemList.size();
    }
}