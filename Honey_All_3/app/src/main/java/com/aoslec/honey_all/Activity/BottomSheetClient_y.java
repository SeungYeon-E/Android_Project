package com.aoslec.honey_all.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.aoslec.honey_all.Bean.BottomSheetBean;
import com.aoslec.honey_all.NetworkTask.NetworkTaskBottomSheet;
import com.aoslec.honey_all.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetClient_y extends BottomSheetDialogFragment {

    TextView tv_bottomsheet_username;
    TextView tv_bottomsheet_content;

    Button btn_bottom_nexttip;

    WebView wv_bottomsheet_menuimg;

    String urlAddr = null;
    String code = null;
    String mName = null;

    BottomSheetBean bottomSheet = new BottomSheetBean();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bottom_sheet, container, false);
//        connectGetData();
        tv_bottomsheet_username = view.findViewById(R.id.tv_bottomsheet_username);
        tv_bottomsheet_content = view.findViewById(R.id.tv_bottomsheet_content);
        wv_bottomsheet_menuimg = view.findViewById(R.id.wv_bottomsheet_menuimg);

        btn_bottom_nexttip = view.findViewById(R.id.btn_bottom_nexttip);



        urlAddr = "http://" + MainActivity.myIP + ":8080/honey/jsp/bottom_select.jsp?cId=" + MainActivity.cId;

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.btn_bottom_nexttip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getView().findViewById(R.id.btn_bottom_gotip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(v.getContext(), TipActivity_y.class);
                intent.putExtra("mCode", code);
                intent.putExtra("mName", mName);
                startActivity(intent);
            }
        });
    }

    @Override // ?????? ???????????????@@@@@@@@@@@@@@@
    public void onResume() { // ????????? ??????????????? ?????????????????? ?????? ????????? ??????????????? ????????? ?????? ????????????.
        super.onResume();
        connectGetData();
    }

    private void connectGetData() {
        try {

            NetworkTaskBottomSheet networkTask = new NetworkTaskBottomSheet(getActivity(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            ArrayList<BottomSheetBean> content = (ArrayList<BottomSheetBean>) obj;

            if(content.get(0).getName().length() > 0) {
                tv_bottomsheet_username.setText(content.get(0).getName() + "???");
                tv_bottomsheet_content.setText("????????? ????????? ????????? ??????????????????? \n" + content.get(0).getName() + "????????? ????????? ????????? ??????????????????!");

                code = content.get(0).getCode();
                mName = content.get(0).getmName();
                wv_bottomsheet_menuimg.loadDataWithBaseURL(null, getMenuImage(), "text/html", "utf-8", null);
            } else {
                tv_bottomsheet_username.setText("???????????????????");
                tv_bottomsheet_content.setText("???????????? ??????????????????!!");
                btn_bottom_nexttip.setVisibility(View.INVISIBLE);
            }


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getMenuImage() {
        String image = "<html>" +
                "<head>" +
                "</head>" +
                "<body style='margin:0; padding:0; text-align:center;'>" +
                "<img style=width:100px; height:auto; src=\"http://" + MainActivity.myIP + ":8080/honey/img/foodcode" + code + ".png\">" +
                "</body>" +
                "</html>";
        return image;
    }


}