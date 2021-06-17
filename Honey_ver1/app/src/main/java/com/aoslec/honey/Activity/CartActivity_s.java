package com.aoslec.honey.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.aoslec.honey.Adapter.CartRecyclerAdapter_s;
import com.aoslec.honey.Bean.Cart_s;
import com.aoslec.honey.Common.CommonInfo_s;
import com.aoslec.honey.NetworkTask.CartNetworkTask_s;
import com.aoslec.honey.R;

import java.util.ArrayList;

public class CartActivity_s extends AppCompatActivity {

    String urlAddr = null;
    ArrayList<Cart_s> cartS;
    CartRecyclerAdapter_s adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_s);

        listView = findViewById(R.id.cart_cart_lv_s);

        urlAddr = CommonInfo_s.hostRootAddr + "Cart_All_List.jsp?" + "cId=" + CommonInfo_s.userID;
        Log.v("message","url"+urlAddr);

    }

    //수정되면 또 실행함 꼭 필요
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData(){
        try{
            CartNetworkTask_s networkTask = new CartNetworkTask_s(CartActivity_s.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            cartS = (ArrayList<Cart_s>) obj;

            adapter = new CartRecyclerAdapter_s(CartActivity_s.this, R.layout.cart_card_layout_s, cartS);
            listView.setAdapter(adapter);

//            listView.setOnItemClickListener(onItemClickListener);
//            listView.setOnItemLongClickListener(onItemLongClickListener);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}