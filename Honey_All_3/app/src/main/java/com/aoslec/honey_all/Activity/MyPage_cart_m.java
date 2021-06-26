package com.aoslec.honey_all.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aoslec.honey_all.Adapter.MyPageAdapter;
import com.aoslec.honey_all.Adapter.MyPageCart;
import com.aoslec.honey_all.Bean.MyPageCartBean_m;
import com.aoslec.honey_all.NetworkTask.MyPageCartTask_m;
import com.aoslec.honey_all.R;

import java.util.ArrayList;

//0625 해당 페이지 추가함
//item 페이지는 tip_list_item.xml
public class MyPage_cart_m extends AppCompatActivity {

    /**
     * 레이아웃
     **/
    Button button_cart_move, button_payment_move, button_infomation;
    //    ImageView imageView_userImage;
    TextView imageView_userImage;  // 이미지 불러오면 TextView 제거하고 주석처리한 imageView 복구
    TextView textview_id;
    ListView listView;

    /**
     * 타입
     **/
    String userId, urlAddr;
    Intent intent = null;
    ArrayList<MyPageCartBean_m> MyPageCartBean_ms;
    MyPageCart myPageCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_cart_m);

        button_cart_move = findViewById(R.id.button_cart_move);
        button_payment_move = findViewById(R.id.button_payment_move);
        button_infomation = findViewById(R.id.button_infomation);
        imageView_userImage = findViewById(R.id.imageView_userImage);
        textview_id = findViewById(R.id.textview_id);
        listView = findViewById(R.id.listView_tip);

        listView.setOnItemClickListener(onItemClickListener); //0625 꿀팁리스트 클릭시 메뉴페이지 이동

        Intent intentGat = getIntent();
        userId = intentGat.getStringExtra("userId");
        textview_id.setText(userId);//넘겨받은 아이디값

        button_cart_move.setOnClickListener(onClickListener);
        button_payment_move.setOnClickListener(onClickListener);
        button_infomation.setOnClickListener(onClickListener);


        urlAddr = "http://" + MainActivity.myIP + ":8080/honny_tip_m/MyPageCart.jsp?userId=" + MainActivity.cId; //0625
        connectGetData();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_cart_move: //내 카트로 넘어가기 //0625 인텐트 삭제함
                    intent = new Intent(MyPage_cart_m.this, CartActivity_s.class);
                    startActivity(intent);
                    break;
                case R.id.button_payment_move: //내 결재내역으로 넘어가기 //0625 인텐트 삭제함
                    intent = new Intent(MyPage_cart_m.this, PaymentHistoryActivity_s.class);
                    startActivity(intent);
                    break;
                case R.id.button_infomation: // 내 상세정보 페이지로 넘어가기 (동시에, 수정가능) //0625 인텐트 삭제함
                    intent = new Intent(MyPage_cart_m.this, MyPage.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    //수정되면 또 실행함 꼭 필요
    public void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData() {
        try {
            Log.v("logm", urlAddr);
            MyPageCartTask_m networkTask = new MyPageCartTask_m(MyPage_cart_m.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            MyPageCartBean_ms = (ArrayList<MyPageCartBean_m>) obj;

            myPageCart = new MyPageCart(MyPage_cart_m.this, R.layout.tip_list_item, MyPageCartBean_ms);
            listView.setAdapter(myPageCart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 꿀팁내역 0625 상세페이지
     **/
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent1 = new Intent(MyPage_cart_m.this, TipActivity_y.class);
            intent1.putExtra("mCode", MyPageCartBean_ms.get(position).getmCode());
            intent1.putExtra("mName", MyPageCartBean_ms.get(position).getmName());
            startActivity(intent1);
        }
    };
}