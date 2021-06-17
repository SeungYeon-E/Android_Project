package com.aoslec.honey.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoslec.honey.Bean.Cart_s;
import com.aoslec.honey.Common.CommonInfo_s;
import com.aoslec.honey.R;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartRecyclerAdapter_s extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Cart_s> data = null;
    private LayoutInflater inflater = null;

    public CartRecyclerAdapter_s(Context mContext, int layout, ArrayList<Cart_s> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();//카운트까지 돌아
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getCartCode();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(this.layout, parent, false);

        ImageView image = convertView.findViewById(R.id.cart_menu_iv_s);
        TextView tv_mName = convertView.findViewById(R.id.cart_menuTitle_tv_s);
        TextView tv_iFullName = convertView.findViewById(R.id.cart_ingredientTitle_tv_s);
        TextView tv_iPrice = convertView.findViewById(R.id.cart_ingredientPrice_tv_s);
        EditText et_cartEA = convertView.findViewById(R.id.cart_ingredientEA_et_s);

        Glide.with(convertView)
                .load(CommonInfo_s.hostRootAddr + "img/" +  data.get(position).getmImagePath())
                .into(image);
        tv_mName.setText(data.get(position).getmName());
        tv_iFullName.setText(data.get(position).getiName() + data.get(position).getiCapacity() + data.get(position).getiUnit());

        DecimalFormat myFormatter = new DecimalFormat("###,###");
//        String formattedStringPrice = myFormatter.format(intPrice);
        tv_iPrice.setText(myFormatter.format(data.get(position).getiPrice()) + "원");

        et_cartEA.setText(Integer.toString(data.get(position).getCartEA()));

        return convertView;
    }

}
