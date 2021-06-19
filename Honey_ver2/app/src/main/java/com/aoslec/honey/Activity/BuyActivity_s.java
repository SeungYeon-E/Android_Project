package com.aoslec.honey.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.aoslec.honey.Common.CommonInfo_s;
import com.aoslec.honey.R;

public class BuyActivity_s extends AppCompatActivity {

    String sTotalPrice;
    EditText buy_address1_et, buy_postNum_et;
    Button buy_postNum_btn;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_s);

        Intent intent = getIntent();
        sTotalPrice = intent.getStringExtra("TotalPrice");

        buy_postNum_et = findViewById(R.id.buy_postNum_et_s);
        buy_address1_et = findViewById(R.id.buy_address1_et_s);
        buy_postNum_btn = findViewById(R.id.buy_postNum_btn_s);



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
}