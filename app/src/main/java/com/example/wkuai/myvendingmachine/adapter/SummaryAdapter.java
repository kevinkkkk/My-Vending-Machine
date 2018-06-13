package com.example.wkuai.myvendingmachine.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.wkuai.myvendingmachine.R;
import com.example.wkuai.myvendingmachine.models.PurchaseHistory;

import java.util.List;

public class SummaryAdapter extends ArrayAdapter<PurchaseHistory> {
    List<PurchaseHistory> purchaseHistories;

    public SummaryAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public void setHistoryList(List<PurchaseHistory> historyList){
        this.purchaseHistories = historyList;
    }

    @Override
    public int getCount() {
        return purchaseHistories == null ? 0 : purchaseHistories.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HistoryHolder holder;
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.adapter_summary, parent, false);
            holder = new HistoryHolder(view);
            view.setTag(holder);
        } else {
            holder = (HistoryHolder) view.getTag();
        }

        PurchaseHistory history = purchaseHistories.get(position);
        holder.purcahseId.setText(history.getPurcahseId() + "");
        holder.itemName.setText(history.getItemName());
        holder.itemPrice.setText(history.getItemPrice() + "");
        holder.quarters.setText(history.getQuarter() + "");
        holder.dimes.setText(history.getDime() + "");
        holder.nickels.setText(history.getNickel() + "");
        holder.inserted.setText(history.getTotalInsert() + "");
        holder.changes.setText(history.getChanges() + "");

        return view;
    }

    class HistoryHolder{
        TextView purcahseId;
        TextView itemName;
        TextView itemPrice;
        TextView quarters;
        TextView dimes;
        TextView nickels;
        TextView inserted;
        TextView changes;

        public HistoryHolder(View v){
            purcahseId = (TextView) v.findViewById(R.id.purchase_id);
            itemName = (TextView) v.findViewById(R.id.item_name);
            itemPrice = (TextView) v.findViewById(R.id.item_price);
            quarters = (TextView) v.findViewById(R.id.quarter_counter);
            dimes = (TextView) v.findViewById(R.id.dime_counter);
            nickels = (TextView) v.findViewById(R.id.nickel_counter);
            inserted = (TextView) v.findViewById(R.id.total_received);
            changes = (TextView) v.findViewById(R.id.changes);
        }
    }
}
