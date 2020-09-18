package com.msh.toastdemo.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

/*
 * 本Activity采用动态代码布局
 * */
public class PluginLoadActivity extends Activity {

    private RelativeLayout mMainLy;

    String json = "{\"title\": \"人民食堂\",\"form\": {\"formfood\": \"菜品\",\"formmeal\": \"餐别\",\"formweight\": \"留样量\",\"formtime\": \"时间\",\"formoperator\": \"留样人\"},\"data\": [{\"foodname\": \"香菇滑鸡\",\"meal\": \"早餐\",\"weight\": \"150g\",\"time\": \"2020/09/10 10:10\",\"operator\": \"新燕燕\"},{\"foodname\": \"辣子鸡丁\",\"meal\": \"午餐\",\"weight\": \"100g\",\"time\": \"2020/09/10 12:10\",\"operator\": \"心燕燕\"}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏TitleBar
        initViews();
    }

    private static final int btnId = 0x123432;

    private void initViews() {
        //创建视图布局
        RelativeLayout relativeLayout = new RelativeLayout(this);
        setContentView(relativeLayout);
        //创建Button按钮
        Button btn = new Button(this);
        btn.setText("ShowPrint");
        btn.setId(btnId);
        btn.setPadding(20, 20, 20, 20);
        btn.setOnClickListener(onClickListener);

        //为Button创建布局
        RelativeLayout rl = new RelativeLayout(this);
        RelativeLayout.LayoutParams lpRl = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl.setGravity(Gravity.CENTER);
        rl.addView(btn, lpRl);

        //将以上布局放入最外层父视图
        RelativeLayout.LayoutParams lpParent = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(rl, lpParent);
    }

    //按钮监听事件
    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case btnId:
                    toNextActivity();
                    break;
                default:
                    break;
            }
        }
    };

    private void toNextActivity() {
        Intent intent = new Intent();
        intent.putExtra("message", json);
        intent.setClass(PluginLoadActivity.this, SunmiPrintLoading.class);
        startActivity(intent);
    }

}
