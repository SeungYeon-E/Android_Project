package com.aoslec.honey_all.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.honey_all.Adapter.MenuSelectRecyclerAdapter_y;
import com.aoslec.honey_all.Bean.IngredientBean;
import com.aoslec.honey_all.NetworkTask.NetworkTaskInsertCart;
import com.aoslec.honey_all.NetworkTask.NetworkTaskNenuSelect;
import com.aoslec.honey_all.NetworkTask.NetworkTaskTip_y;
import com.aoslec.honey_all.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class SelectMenuActivity extends AppCompatActivity {

    WebView wv_menu_select_image;
    RecyclerView lv_menu_select_recylerView_y;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    TextView tv_menu_select_name_y, tv_tip_count_y, tv_tip_y;
    Button btn_cart_add_y;

    String urlAddr;
    ArrayList<IngredientBean> ingredient = null;
//    MenuSelectAdapter adapter = null;

    String code, name;
    String insertUrl;
    String tipCountUrl;

    String count;

    ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_menu);

        wv_menu_select_image = findViewById(R.id.wv_menu_select_image_y);
        lv_menu_select_recylerView_y = findViewById(R.id.lv_menu_select_recylerView_y);
        tv_tip_count_y = findViewById(R.id.tv_tip_count_y);
        tv_tip_y = findViewById(R.id.tv_tip_y);
        tv_menu_select_name_y = findViewById(R.id.tv_menu_select_name_y);
        btn_cart_add_y = findViewById(R.id.btn_cart_add_y);

        Intent intent = getIntent();
        code = intent.getStringExtra("mCode");
        name = intent.getStringExtra("mName");
        Log.v("selectmenu???", name);

        urlAddr = "http://" + MainActivity.myIP + ":8080/honey/jsp/menu_select_ingredient.jsp?code=" + code;
        wv_menu_select_image.loadDataWithBaseURL(null,getKoreanFoodImage(), "text/html", "utf-8", null);

        count = connectTipCount();
        tv_tip_count_y.setText("?????? " + count + "???");
        tv_tip_y.setOnClickListener(tipOnClickListener);
        tv_menu_select_name_y.setText(name);

        btn_cart_add_y.setOnClickListener(cartOnClickListnenr);
    }

    @Override
    public void onResume() {
        super.onResume();
        getListData();
    }

    private void getListData() {
        try {
            NetworkTaskNenuSelect networkTask = new NetworkTaskNenuSelect(SelectMenuActivity.this, urlAddr, "select");
            Object obj = networkTask.execute().get();
            ingredient = (ArrayList<IngredientBean>) obj;

            layoutManager = new LinearLayoutManager(SelectMenuActivity.this);
            lv_menu_select_recylerView_y.setLayoutManager(layoutManager);

            adapter = new MenuSelectRecyclerAdapter_y(SelectMenuActivity.this, R.layout.menu_select_list_layout, ingredient);
            lv_menu_select_recylerView_y.setAdapter(adapter);

//            // ????????????
//            helper = new ItemTouchHelper(new MenuSelectHelper((SwifeListener) adapter));
//            helper.attachToRecyclerView(lv_menu_select_recylerView_y);
//            helper = new ItemTouchHelper(new MenuSelectHelper(adapter));
//            helper.attachToListView(lv_menu_select_recylerView_y);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getKoreanFoodImage() {
        String selectFoodImage = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:300px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/foodcode" + code + "-1.png\">" +
                "</body>" +
                "</html>";
        return selectFoodImage;
    }

    View.OnClickListener tipOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SelectMenuActivity.this, TipActivity_y.class);
            intent.putExtra("mCode", code);
            intent.putExtra("mName", name);
            startActivity(intent);
        }
    };

    View.OnClickListener cartOnClickListnenr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String ingredient1 = MenuSelectRecyclerAdapter_y.selectName.get(0);
            int size = MenuSelectRecyclerAdapter_y.selectCode.size();

            if (MenuSelectRecyclerAdapter_y.selectCode.size() == 0) {
                Snackbar.make(v, "????????? ??????????????????!", Snackbar.LENGTH_SHORT).show();
            }

            for (int i = 0; i < MenuSelectRecyclerAdapter_y.selectCode.size(); i ++) {

            insertUrl = "http://" + MainActivity.myIP + ":8080/honey/jsp/ingredient_cart_insert.jsp?id=" + MainActivity.cId + "&iCode=" + MenuSelectRecyclerAdapter_y.selectCode.get(i) + "&mCode=" + code;

            String result = connectInsertData();
            }
            if (MenuSelectRecyclerAdapter_y.selectCode.size() == 1) {
                Snackbar.make(v, ingredient1 + "???????????? ????????????", Snackbar.LENGTH_SHORT).show();
                MenuSelectRecyclerAdapter_y.selectCode.clear();
                MenuSelectRecyclerAdapter_y.selectName.clear();
            } else {
                Snackbar.make(v, ingredient1 + "??? " + size + "??? ???????????? ????????????", Snackbar.LENGTH_SHORT).show();
                MenuSelectRecyclerAdapter_y.selectCode.clear();
                MenuSelectRecyclerAdapter_y.selectName.clear();
            }
        }
    };


    private String connectInsertData(){
        String result = null;
        try {
            NetworkTaskInsertCart networkTask = new NetworkTaskInsertCart(SelectMenuActivity.this, insertUrl, "insert");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String connectTipCount(){
        String count = null;
        tipCountUrl = "http://" + MainActivity.myIP + ":8080/honey/jsp/tip_count_select.jsp?code=" + code;
        try {
            NetworkTaskTip_y networkTask = new NetworkTaskTip_y(SelectMenuActivity.this, tipCountUrl, "count");
            Object obj = networkTask.execute().get();

            count = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }


}