package com.aoslec.honey.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.honey.Bean.Cart;
import com.aoslec.honey.Common.CommonInfo;
import com.aoslec.honey.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder> {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Cart> data = null;
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_name;
        public TextView tv_phone;
        public WebView webView;

        public ViewHolder(View itemView) {
            super(itemView);

            webView.setBackgroundColor(0);

//            RecyclerView.Recycler view Click Event -> detail view
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
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Snackbar.make(v, details[position], Snackbar.LENGTH_LONG).setAction("",null).show();
                }
            });*/
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_card_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
//        holder.tv_name.setText(data.get(position).getName());
//        holder.tv_phone.setText(data.get(position).getPhone());
//        holder.webView.loadData(htmlData(data.get(position).getImage()),"text/html; charset=utf-8", "UTF-8");
//        Log.v("message", data.get(position).getPhone());

    }

    private String htmlData(String image){
        String content =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
                        "<html><head>"+
                        "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />"+
                        "<head><body>"+
                        "<img src=\"http://"+ CommonInfo.hostIP+":8080/phonebook/img/";
        content += image + "\" alt=\"이미지\" width=\"100%\" height=\"100%\"></body></html>";

        return content;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public CartRecyclerAdapter(Context mContext, int layout, ArrayList<Cart> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
    }

}
