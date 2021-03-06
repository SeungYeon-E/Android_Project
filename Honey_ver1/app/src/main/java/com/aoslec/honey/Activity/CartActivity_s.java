package com.aoslec.honey.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.aoslec.honey.Adapter.CartListviewAdapter_s;
import com.aoslec.honey.Bean.Cart_s;
import com.aoslec.honey.Common.CommonInfo_s;
import com.aoslec.honey.NetworkTask.CartNetworkTask_s;
import com.aoslec.honey.R;
import com.aoslec.honey.Swife.SwipeDismissListViewTouchListener_s;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity_s extends AppCompatActivity {

    String deleteResult, allDeleteResult;
    String urlAddr = null;
    ArrayList<Cart_s> cartS;
    CartListviewAdapter_s adapter;
    ListView listView;
    LinearLayout trueLinearLayout, falseLinearLayout;
    TextView cart_priceResult_tv, cart_delivery_tv, cart_deliveryTip_tv;
    ImageButton cart_deliveryTip_ib;
    Button cart_allDel_btn, cart_deliveryOrder_btn;
    DecimalFormat myFormatter = new DecimalFormat("###,###");
    ProgressBar cart_priceResult_pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_s);

        cart_priceResult_pb = findViewById(R.id.cart_priceResult_pb_s);

        listView = findViewById(R.id.cart_cart_lv_s);
        falseLinearLayout = findViewById(R.id.cart_contain_layout_s);
        trueLinearLayout = findViewById(R.id.cart_notContain_layout_s);
        cart_priceResult_tv = findViewById(R.id.cart_priceResult_tv_s);
        cart_delivery_tv = findViewById(R.id.cart_delivery_tv_s);
        cart_deliveryTip_tv = findViewById(R.id.cart_deliveryTip_tv_s);

        cart_deliveryTip_ib = findViewById(R.id.cart_deliveryTip_ib_s);
        cart_allDel_btn = findViewById(R.id.cart_allDel_btn_s);
        cart_deliveryOrder_btn = findViewById(R.id.cart_deliveryOrder_btn_s);

        SwipeDismissListViewTouchListener_s touchListener =
                new SwipeDismissListViewTouchListener_s(listView,
                        new SwipeDismissListViewTouchListener_s.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    deleteResult = connectDeleteData(cartS.get(position).getCartCode());
                                    cartS.remove(cartS.get(position));
                                    if(deleteResult.equals("1")){
                                        Snackbar.make(listView, "?????????????????? ??????????????????.", Snackbar.LENGTH_SHORT).show();
                                    }else {
                                        Snackbar.make(listView, "?????????????????? ?????? ??????????????????.", Snackbar.LENGTH_SHORT).show();
                                    }
                                    connectGetData();
                                }
                                adapter.notifyDataSetChanged();
                            }

                        });

        listView.setOnTouchListener(touchListener);
        listView.setOnScrollListener(touchListener.makeScrollListener());

        cart_deliveryTip_ib.setOnClickListener(onClickListener);
        cart_allDel_btn.setOnClickListener(onClickListener);
        cart_deliveryOrder_btn.setOnClickListener(onClickListener);

    }

    //???????????? ??? ????????? ??? ??????
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData(){

        urlAddr = CommonInfo_s.hostRootAddr + "Cart_All_List.jsp?" + "cId=" + CommonInfo_s.userID;
        Log.v("message","url"+urlAddr);

        try{
            CartNetworkTask_s networkTask = new CartNetworkTask_s(CartActivity_s.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            cartS = (ArrayList<Cart_s>) obj;

            if (cartS.isEmpty() == true){
                trueLinearLayout.setVisibility(View.VISIBLE);
                falseLinearLayout.setVisibility(View.INVISIBLE);
            }else {
                falseLinearLayout.setVisibility(View.INVISIBLE);
                falseLinearLayout.setVisibility(View.VISIBLE);
                adapter = new CartListviewAdapter_s(CartActivity_s.this, R.layout.cart_card_layout_s, cartS);
                listView.setAdapter(adapter);

                cart_priceResult_tv.setText(myFormatter.format(cartTotalPrice()) + "???");
                cart_priceResult_pb.setProgress(cartTotalPrice());

                if (cartTotalPrice()<10000){
                    cart_delivery_tv.setText(myFormatter.format(10000-cartTotalPrice())+"??? ??? ???????????????");
                    cart_deliveryOrder_btn.setEnabled(false);
                    cart_deliveryOrder_btn.setText("?????? ?????? ????????? ???????????????");
                    cart_deliveryTip_tv.setText("3,000???");
                }else if(cartTotalPrice()>10000 && cartTotalPrice()<30000){
                    cart_delivery_tv.setText(myFormatter.format(30000-cartTotalPrice())+"??? ??? ????????? ????????????");
                    cart_deliveryOrder_btn.setEnabled(true);
                    cart_deliveryOrder_btn.setText(cartTotalEA() + "??? " + myFormatter.format(cartTotalPrice()+3000) + "??? ???????????? ??????");
                    cart_deliveryTip_tv.setText("3,000???");
                }else {
                    cart_delivery_tv.setText("????????????");
                    cart_deliveryOrder_btn.setEnabled(true);
                    cart_deliveryOrder_btn.setText(cartTotalEA() + "??? " + myFormatter.format(cartTotalPrice()) + "??? ???????????? ??????");
                    cart_deliveryTip_tv.setText("????????????");
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // total price ?????????
    private int cartTotalPrice(){
        int totalPrice = 0;

        for(int i=0; i<cartS.size(); i++){
            totalPrice += cartS.get(i).getiPrice()*cartS.get(i).getCartEA();
        }
        return totalPrice;
    }

    // total price ?????????
    private int cartTotalEA(){
        int totalEA = 0;
        for(int i=0; i<cartS.size(); i++){
            totalEA += cartS.get(i).getCartEA();
        }
        return totalEA;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cart_deliveryTip_ib_s:
                    new AlertDialog.Builder(v.getContext())
                            .setMessage("????????? ??????!\n\n????????????                                                  "+
                                    "?????????\n30,000???~                                            ????????????\n" +
                                    "10,000???~30,000???                             3,000???\n" +
                                    "*????????????????????? 10,000????????????.")
                            .setCancelable(true)
                            .show();
                    break;
                case R.id.cart_allDel_btn_s:
                    new AlertDialog.Builder(v.getContext())
                            .setMessage("??????????????? ??????????????????????")
                            .setCancelable(false)
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    allDeleteResult = connectAllDeleteData();
                                    if(!allDeleteResult.equals(null)){
                                        Snackbar.make(v, "??????????????? ???????????????.", Snackbar.LENGTH_SHORT).show();
                                    }else{
                                        Snackbar.make(v, "??????????????? ????????????????????????.", Snackbar.LENGTH_SHORT).show();
                                    }
                                    connectGetData();
                                }
                            })
                            .show();
                    break;
                case R.id.cart_deliveryOrder_btn_s:
                    break;
            }
        }
    };

    private String connectDeleteData(int cartCode){
        urlAddr = CommonInfo_s.hostRootAddr + "Cart_Deletedate_Update_Return.jsp?" + "cartCode=" + cartCode;
        String result = null;
        try {
            CartNetworkTask_s networkTask = new CartNetworkTask_s(CartActivity_s.this, urlAddr, "delete");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
        //??????????????? 1 ????????? ??????
    }
    private String connectAllDeleteData(){
        urlAddr = CommonInfo_s.hostRootAddr + "Cart_AllDeletedate_Update_Return.jsp?Client_cId=" + CommonInfo_s.userID;
        String result = null;
        try {
            CartNetworkTask_s networkTask = new CartNetworkTask_s(CartActivity_s.this, urlAddr, "delete");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.v("message","url="+urlAddr+"result"+result);
        return result;
        //??????????????? 1 ????????? ??????
    }


}