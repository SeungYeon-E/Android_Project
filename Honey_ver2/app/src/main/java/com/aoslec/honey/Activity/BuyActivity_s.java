package com.aoslec.honey.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.ArrayList;

public class BuyActivity_s extends AppCompatActivity {

    String urlAddr = null;
    ArrayList<UserAddress_s> userAddr;

    int iTotalPrice;
    EditText buy_address1_et, buy_address2_et, buy_postNum_et;
    Button buy_postNum_btn, buy_deliveryOrder_btn;
    RadioGroup radioGroup;
    TextView buy_priceResult_tv, buy_deliveryTip_tv, buy_deliveryPrice_tv;
    DecimalFormat myFormatter = new DecimalFormat("###,###");

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_s);

        Intent intent = getIntent();
        iTotalPrice = intent.getIntExtra("TotalPrice", 0);

        buy_postNum_et = findViewById(R.id.buy_postNum_et_s);
        buy_address1_et = findViewById(R.id.buy_address1_et_s);
        buy_address2_et = findViewById(R.id.buy_address2_et_s);
        buy_postNum_btn = findViewById(R.id.buy_postNum_btn_s);

        radioGroup = findViewById(R.id.buy_payment_rg_s);

        buy_priceResult_tv = findViewById(R.id.buy_priceResult_tv_s);
        buy_deliveryTip_tv = findViewById(R.id.buy_deliveryTip_tv_s);
        buy_deliveryPrice_tv = findViewById(R.id.buy_deliveryPrice_tv_s);

        buy_deliveryOrder_btn = findViewById(R.id.buy_deliveryOrder_btn_s);
        buy_deliveryOrder_btn.setOnClickListener(onClickListener);

        buy_priceResult_tv.setText(myFormatter.format(iTotalPrice) + "원");

        if(iTotalPrice>10000 && iTotalPrice<30000){
            buy_deliveryPrice_tv.setText(myFormatter.format(iTotalPrice + 3000) + "원");
            buy_deliveryTip_tv.setText("3,000원");
        }else {
            buy_deliveryPrice_tv.setText(myFormatter.format(iTotalPrice) + "원");
            buy_deliveryTip_tv.setText("무료배송");
        }

        buy_postNum_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyActivity_s.this, AddressAPIActivity_s.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
            }
        });
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
                Snackbar.make(v, "주소를 입력해 주세요!", Snackbar.LENGTH_SHORT).show();
                buy_address2_et.requestFocus();
            }else if (radioButton.getId() == R.id.buy_card_rb_s){
                Snackbar.make(v, "카드결제!", Snackbar.LENGTH_SHORT).show();
            }else if (radioButton.getId() == R.id.buy_noBankBook_rb_s){
                Snackbar.make(v, "무통장입금!", Snackbar.LENGTH_SHORT).show();
            }
        }
    };

    //수정되면 또 실행함 꼭 필요
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData(){

        urlAddr = CommonInfo_s.hostRootAddr + "User_Address_Info.jsp?" + "cId=" + CommonInfo_s.userID;
        Log.v("message","url = " + urlAddr);

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
}