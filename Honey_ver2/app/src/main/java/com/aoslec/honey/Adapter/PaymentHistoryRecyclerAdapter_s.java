package com.aoslec.honey.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.honey.Activity.PaymentHistoryActivity_s;
import com.aoslec.honey.Bean.PaymentHistory_s;
import com.aoslec.honey.R;

import java.util.ArrayList;

public class PaymentHistoryRecyclerAdapter_s extends RecyclerView.Adapter<PaymentHistoryRecyclerAdapter_s.ViewHolder> {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<PaymentHistory_s> data = null;
    Intent intent = null;
    String urlAddr;
    String result;

    public PaymentHistoryRecyclerAdapter_s(Context mContext, int layout, ArrayList<PaymentHistory_s> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView payment_history_buyNum_tv;
        public TextView payment_history_buyAll_tv;
        public TextView payment_history_buyPrice_tv;
        public TextView payment_history_payment_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            payment_history_buyNum_tv = itemView.findViewById(R.id.payment_history_buyNum_tv_s);
            payment_history_buyAll_tv = itemView.findViewById(R.id.payment_history_buyAll_tv_s);
            payment_history_buyPrice_tv = itemView.findViewById(R.id.payment_history_buyPrice_tv_s);
            payment_history_payment_tv = itemView.findViewById(R.id.payment_history_payment_tv_s);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
//                        intent = new Intent(v.getContext(), ContentActivity.class);
//                        intent.putExtra("id", data.get(position).getId());
//                        intent.putExtra("name", data.get(position).getName());
//                        intent.putExtra("phone", data.get(position).getPhone());
//                        intent.putExtra("address", data.get(position).getAddress());
//                        intent.putExtra("email", data.get(position).getEmail());
//                        intent.putExtra("image", data.get(position).getImage());
//                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    // 뷰홀더 만드는거
    public PaymentHistoryRecyclerAdapter_s.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_history_card_layout_s, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    //뷰홀더 쓰는거
    @Override
    public void onBindViewHolder(PaymentHistoryRecyclerAdapter_s.ViewHolder holder, int position) {
        int count = Integer.parseInt(data.get(position).getCount()) - 1;
        holder.payment_history_buyNum_tv.setText("주문번호 : " + data.get(position).getBuyNum());
        holder.payment_history_buyAll_tv.setText(data.get(position).getiName() + data.get(position).getiCapacity()
                + data.get(position).getiUnit() + " 외 " + count + "개");
        holder.payment_history_buyPrice_tv.setText("금액 : " + data.get(position).getBuyDeliveryPrice());
        holder.payment_history_payment_tv.setText(data.get(position).getBuyDay());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
