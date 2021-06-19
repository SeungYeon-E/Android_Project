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

import com.aoslec.honey.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;

public class BuyActivity_s extends AppCompatActivity {

    int iTotalPrice;
    EditText buy_address1_et, buy_postNum_et;
    Button buy_postNum_btn;
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
        buy_postNum_btn = findViewById(R.id.buy_postNum_btn_s);

        radioGroup = findViewById(R.id.buy_payment_rg_s);
        radioGroup.setOnCheckedChangeListener(checkedChangeListener);

        buy_priceResult_tv = findViewById(R.id.buy_priceResult_tv_s);
        buy_deliveryTip_tv = findViewById(R.id.buy_deliveryTip_tv_s);
        buy_deliveryPrice_tv = findViewById(R.id.buy_deliveryPrice_tv_s);

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

    RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            String str = "";
            RadioButton radioButton = findViewById(checkedId);
            str = radioButton.getText().toString();
            Toast.makeText(BuyActivity_s.this, str + "is checked.", Toast.LENGTH_SHORT).show();
        }
    };
}