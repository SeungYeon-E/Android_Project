package com.aoslec.honey.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aoslec.honey.Adapter.PaymentDetailRecyclerAdapter_s;
import com.aoslec.honey.Adapter.PaymentHistoryRecyclerAdapter_s;
import com.aoslec.honey.Bean.PaymentDetail_s;
import com.aoslec.honey.Bean.PaymentHistory_s;
import com.aoslec.honey.Common.CommonInfo_s;
import com.aoslec.honey.NetworkTask.CartNetworkTask_s;
import com.aoslec.honey.NetworkTask.PaymentDetailNetworkTask_s;
import com.aoslec.honey.NetworkTask.PaymentHistoryNetworkTask_s;
import com.aoslec.honey.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class PaymentDetailActivity_s extends AppCompatActivity {

    String urlAddr = null;
    ArrayList<PaymentDetail_s> detail;
    PaymentDetailRecyclerAdapter_s rAdapter;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    String buyNum;
    TextView payment_Detail_day_tv, payment_Detail_buyNum_tv, payment_Detail_address_tv, payment_Detail_request_tv;
    TextView payment_Detail_status_tv, payment_Detail_result_tv;
    Button payment_Detail_cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail_s);

        recyclerView = findViewById(R.id.payment_Detail_recycler_view_s);

        payment_Detail_day_tv = findViewById(R.id.payment_Detail_day_tv_s);
        payment_Detail_buyNum_tv = findViewById(R.id.payment_Detail_buyNum_tv_s);
        payment_Detail_address_tv = findViewById(R.id.payment_Detail_address_tv_s);
        payment_Detail_request_tv = findViewById(R.id.payment_Detail_request_tv_s);
        payment_Detail_status_tv = findViewById(R.id.payment_Detail_status_tv_s);
        payment_Detail_result_tv = findViewById(R.id.payment_Detail_result_tv_s);

        payment_Detail_cancel_btn = findViewById(R.id.payment_Detail_cancel_btn_s);

        Intent intent = getIntent();
        buyNum = intent.getStringExtra("BuyNum");

        payment_Detail_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setMessage("????????? ?????????????????????????")
                        .setCancelable(false)
                        .setNegativeButton("NO", null)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String deleteResult = connectDeleteData();
                                if(!deleteResult.equals(null)){
                                    Snackbar.make(v, "????????? ?????????????????????.", Snackbar.LENGTH_SHORT).show();
                                }else{
                                    Snackbar.make(v, "?????? ?????? ??????????????????.", Snackbar.LENGTH_SHORT).show();
                                }
                                finish();
                            }
                        })
                        .show();
            }
        });

        connectGetData();
    }

//    protected void onResume() {
//        super.onResume();
//        connectGetData();
//    }

    protected void connectGetData(){
        try{

            urlAddr = CommonInfo_s.hostRootAddr + "Payment_Detail_All_List.jsp?buyNum=" + buyNum;
            Log.v("message", "urlAddr:" + urlAddr);

            PaymentDetailNetworkTask_s networkTask = new PaymentDetailNetworkTask_s(PaymentDetailActivity_s.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            detail = (ArrayList<PaymentDetail_s>) obj;

            layoutManager = new LinearLayoutManager(PaymentDetailActivity_s.this);
            recyclerView.setLayoutManager(layoutManager);

            rAdapter = new PaymentDetailRecyclerAdapter_s(PaymentDetailActivity_s.this, R.layout.payment_detail_card_layout_s, detail);
            recyclerView.setAdapter(rAdapter);

            payment_Detail_day_tv.setText("???????????? : " + detail.get(0).getBuyDay());
            payment_Detail_buyNum_tv.setText("???????????? : " + buyNum);
            payment_Detail_address_tv.setText("????????? : " + detail.get(0).getBuyPostNum() + ", " + detail.get(0).getBuyAddress1() + " " + detail.get(0).getBuyAddress2());
            payment_Detail_result_tv.setText("??? ?????? : " + detail.get(0).getBuyDeliveryPrice());

            if (detail.get(0).getBuyCencelDay().equals("null")) {
                payment_Detail_cancel_btn.setVisibility(View.VISIBLE);
                payment_Detail_status_tv.setText("?????? ??????");
                payment_Detail_status_tv.setTextColor(0xFF000000);
            }else {
                payment_Detail_cancel_btn.setVisibility(View.INVISIBLE);
                payment_Detail_status_tv.setText("?????? ?????? : " + detail.get(0).getBuyCencelDay());
                payment_Detail_status_tv.setTextColor(0xFFFF0000);
            }

            if (detail.get(0).getBuyRequests().equals("null")) {
                payment_Detail_request_tv.setVisibility(View.INVISIBLE);
            }else {
                payment_Detail_request_tv.setVisibility(View.VISIBLE);
                payment_Detail_request_tv.setText("?????? ?????? : " + detail.get(0).getBuyRequests());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String connectDeleteData(){
        urlAddr = CommonInfo_s.hostRootAddr + "Payment_Canceldate_Update_Return.jsp?" + "buyNum=" + buyNum;
        String result = null;
        try {
            CartNetworkTask_s networkTask = new CartNetworkTask_s(PaymentDetailActivity_s.this, urlAddr, "delete");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
        //??????????????? 1 ????????? ??????
    }

}