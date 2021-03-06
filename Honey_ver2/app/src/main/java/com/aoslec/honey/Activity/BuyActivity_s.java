package com.aoslec.honey.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aoslec.honey.Adapter.CartListviewAdapter_s;
import com.aoslec.honey.Bean.Cart_s;
import com.aoslec.honey.Bean.UserAddress_s;
import com.aoslec.honey.Common.CommonInfo_s;
import com.aoslec.honey.NetworkTask.CartNetworkTask_s;
import com.aoslec.honey.NetworkTask.UserAddressNetworkTask_s;
import com.aoslec.honey.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BuyActivity_s extends AppCompatActivity {

    String urlAddr = null;
    ArrayList<UserAddress_s> userAddr;
    Intent intent;

    int iTotalPrice;
    EditText buy_address1_et, buy_address2_et, buy_postNum_et, buy_Requests_et;
    Button buy_postNum_btn, buy_deliveryOrder_btn;
    RadioGroup radioGroup;
    TextView buy_priceResult_tv, buy_deliveryTip_tv, buy_deliveryPrice_tv;
    DecimalFormat myFormatter = new DecimalFormat("###,###");
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
    long mNow;
    Date mDate;

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_s);

        intent = getIntent();
        iTotalPrice = intent.getIntExtra("TotalPrice", 0);

        buy_postNum_et = findViewById(R.id.buy_postNum_et_s);
        buy_address1_et = findViewById(R.id.buy_address1_et_s);
        buy_address2_et = findViewById(R.id.buy_address2_et_s);
        buy_Requests_et = findViewById(R.id.buy_Requests_et_s);
        buy_postNum_btn = findViewById(R.id.buy_postNum_btn_s);

        radioGroup = findViewById(R.id.buy_payment_rg_s);

        buy_priceResult_tv = findViewById(R.id.buy_priceResult_tv_s);
        buy_deliveryTip_tv = findViewById(R.id.buy_deliveryTip_tv_s);
        buy_deliveryPrice_tv = findViewById(R.id.buy_deliveryPrice_tv_s);

        buy_deliveryOrder_btn = findViewById(R.id.buy_deliveryOrder_btn_s);
        buy_deliveryOrder_btn.setOnClickListener(onClickListener);

        buy_priceResult_tv.setText(myFormatter.format(iTotalPrice) + "???");

        if(iTotalPrice>10000 && iTotalPrice<30000){
            buy_deliveryPrice_tv.setText(myFormatter.format(iTotalPrice + 3000) + "???");
            buy_deliveryTip_tv.setText("3,000???");
            intent.putExtra("TotalOrderPrice", (iTotalPrice+3000));
        }else {
            buy_deliveryPrice_tv.setText(myFormatter.format(iTotalPrice) + "???");
            buy_deliveryTip_tv.setText("????????????");
            intent.putExtra("TotalOrderPrice", iTotalPrice);
        }

        buy_postNum_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyActivity_s.this, AddressAPIActivity_s.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
            }
        });
        connectGetData();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        buy_postNum_et.setText(data.substring(0,5));
                        buy_address1_et.setText(data.substring(7));
                        buy_address2_et.setText(null);
                        buy_address2_et.requestFocus();
                    }
                }
                break;
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            if (buy_postNum_et.length() == 0 || buy_address1_et.length() == 0 || buy_address2_et.length() == 0){
                Snackbar.make(v, "????????? ????????? ?????????!", Snackbar.LENGTH_SHORT).show();
                buy_address2_et.requestFocus();
            }else if (radioButton.getId() == R.id.buy_card_rb_s){
                Snackbar.make(v, "????????????!", Snackbar.LENGTH_SHORT).show();
//                intent = new Intent(BuyActivity_s.this, BuyActivity_s.class);//????????????
//                intent.putExtra("BuyPostNum", buy_postNum_et.getText());
//                intent.putExtra("BuyAddress1", buy_address1_et.getText());
//                intent.putExtra("BuyAddress2", buy_address2_et.getText());
//                intent.putExtra("BuyRequests", buy_Requests_et.getText());
//                startActivity(intent);
            }else if (radioButton.getId() == R.id.buy_noBankBook_rb_s){
                String buyNumber = getTime();
                String InsertResult = connectInsertData(buyNumber);
                if(InsertResult.length()>=1){
                    intent = new Intent(BuyActivity_s.this, BuyNoBankBookActivity_s.class);
                    intent.putExtra("BuyPriceResult", buy_priceResult_tv.getText());
                    intent.putExtra("BuyDeliveryTip", buy_deliveryTip_tv.getText());
                    intent.putExtra("BuyDeliveryPrice", buy_deliveryPrice_tv.getText());
                    intent.putExtra("BuyNumber", buyNumber);
                    startActivity(intent);
                    finish();
                }else{
                    Snackbar.make(v, "????????? ??????????????????.", Snackbar.LENGTH_SHORT).show();
                }

            }
        }
    };

    //???????????? ??? ????????? ??? ??????
    protected void onResume() {
        super.onResume();
    }

    private void connectGetData(){

        urlAddr = CommonInfo_s.hostRootAddr + "User_Address_Info.jsp?" + "cId=" + CommonInfo_s.userID;

        try{
            UserAddressNetworkTask_s networkTask = new UserAddressNetworkTask_s(BuyActivity_s.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            userAddr = (ArrayList<UserAddress_s>) obj;

            buy_postNum_et.setText(userAddr.get(0).getcPostNum());
            buy_address1_et.setText(userAddr.get(0).getcAddress1());
            buy_address2_et.setText(userAddr.get(0).getcAddress2());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String connectInsertData(String buyNum){

        urlAddr = CommonInfo_s.hostRootAddr + "Buy_Order_Insert_Return.jsp?" + "Client_cId=" + CommonInfo_s.userID
                + "&buyNum=" + buyNum + "&buyPostNum=" + buy_postNum_et.getText() + "&buyAddress1=" + buy_address1_et.getText()
                + "&buyAddress2=" + buy_address2_et.getText() + "&buyRequests=" + buy_Requests_et.getText()
                + "&buyDeliveryPrice=" + buy_deliveryPrice_tv.getText();

        String result = null;
        try {
            CartNetworkTask_s networkTask = new CartNetworkTask_s(BuyActivity_s.this, urlAddr, "insert");
            Object obj = networkTask.execute().get();
            result = (String) obj;

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
        //??????????????? 1 ????????? ??????
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}