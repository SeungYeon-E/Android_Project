package com.aoslec.honey.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.aoslec.honey.Adapter.PaymentHistoryRecyclerAdapter_s;
import com.aoslec.honey.Bean.PaymentHistory_s;
import com.aoslec.honey.Common.CommonInfo_s;
import com.aoslec.honey.NetworkTask.PaymentHistoryNetworkTask_s;
import com.aoslec.honey.R;

import java.util.ArrayList;

public class PaymentHistoryActivity_s extends AppCompatActivity {

    String urlAddr = null;
    ArrayList<PaymentHistory_s> history;
    PaymentHistoryRecyclerAdapter_s rAdapter;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
//    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history_s);

        recyclerView = findViewById(R.id.payment_history_recycler_view_s);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        adapter = new PaymentHistoryRecyclerAdapter_s();
//        recyclerView.setAdapter(adapter);

    }

    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    public void connectGetData(){
        try{

            urlAddr = CommonInfo_s.hostRootAddr + "Payment_History_Select_Info.jsp?Client_cId=" + CommonInfo_s.userID;
            PaymentHistoryNetworkTask_s networkTask = new PaymentHistoryNetworkTask_s(PaymentHistoryActivity_s.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            history = (ArrayList<PaymentHistory_s>) obj;

            layoutManager = new LinearLayoutManager(PaymentHistoryActivity_s.this);
            recyclerView.setLayoutManager(layoutManager);

            rAdapter = new PaymentHistoryRecyclerAdapter_s(PaymentHistoryActivity_s.this, R.layout.payment_history_card_layout_s, history);
            recyclerView.setAdapter(rAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}