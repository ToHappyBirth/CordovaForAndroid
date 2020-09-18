package com.msh.toastdemo;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.msh.toastdemo.android.activity.SunmiPrintLoading;

/**
 * This class echoes a string called from JavaScript.
 */
//类名需与toast_plugin.js中调用插件名一致
public class ToastDemo extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        //action与toast_plugin.js中的方法名一致
        if (action.equals("showToast")) {
            String message = args.getString(0);
            this.doSomeThing(message, callbackContext);
            return true;
        }
        return false;
    }

    private void doSomeThing(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);//js调用成功后返回信息
            //环境变量用cordova.getContext()
//            Toast.makeText(cordova.getContext(), message, Toast.LENGTH_SHORT).show();
            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        //启动新的原生Activity
                        Intent intent = new Intent();
                        intent.setClass(cordova.getActivity(), SunmiPrintLoading.class);
                        if (message != null && message.length() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("message", message); //传值
                            intent.putExtras(bundle);
                            cordova.startActivityForResult(ToastDemo.this, intent, 1);
                        } else { //不带参数的跳转
                            cordova.getActivity().startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            callbackContext.error("Expected one non-empty string argument.");//js调用失败后返回信息
        }
    }
}