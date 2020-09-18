package com.msh.toastdemo.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.msh.toastdemo.android.utils.MResource;
import com.msh.toastdemo.android.utils.SunmiPrintHelper;
import com.msh.toastdemo.android.utils.ThreadPoolManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class SunmiPrintLoading extends Activity {

    private ProgressBar pgbar;
    private ImageView img_Loader;
    private TextView txt_Loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(MResource.getIdByName(this, "layout", "activity_sunmi_print_loading"));
        init();
        StartNewLoading();
    }

    /**
     * 初始化
     */
    public void init() {
        //初始化layout
        pgbar = findViewById(MResource.getIdByName(this, "id", "pgb_Loading"));
        img_Loader = findViewById(MResource.getIdByName(this, "id", "img_Loader"));
        txt_Loading = findViewById(MResource.getIdByName(this, "id", "txt_Loading"));
        //此处代码可在BaseApplication中初始化,目的为在此项目中该服务只初始化一次
        SunmiPrintHelper.getInstance().initSunmiPrinterService(SunmiPrintLoading.this);
    }

    private void StartNewLoading() {
        try {
            Intent intent = getIntent();
            String json = intent.getStringExtra("message");
            this.jsonLength = json.length();
            multiPrint(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //第一个参数为TimerTask的对象，通过实现其中的run()方法可以周期的执行某一个任务；第二个参数表示延迟的时间，即多长时间后开始执行；第三个参数表示执行的周期
        timer.schedule(timerTask, 3 * 1000, 1000 * 10);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                pgbar.setVisibility(View.GONE);
                img_Loader.setVisibility(View.VISIBLE);
                txt_Loading.setText("打印完成!");
            }
        }
    };

    //定时器
    private Timer timer = new Timer();
    private int jsonLength = 0;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = Message.obtain();
            message.what = 100;
            handler.sendMessage(message);
        }
    };

    //关闭Service
    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
//            SunmiPrintHelper.getInstance().deInitSunmiPrinterService(SunmiPrintLoading.this);
            handler.removeCallbacksAndMessages(null);
            timer.cancel();
            SunmiPrintLoading.this.finish();
        }
    };

    /*
     * 新起线程运行打印Service
     * */
    private void multiPrint(String json) throws JSONException {
//        final JSONObject jsonObject = new JSONObject(json);
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
//                SunmiPrintHelper.getInstance().printFoodSample(jsonObject);
            }
        });
        //延迟5s后发送
        handler.postDelayed(myRunnable, 1000 * 5);
    }
}