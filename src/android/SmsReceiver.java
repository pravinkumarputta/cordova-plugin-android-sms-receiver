package com.pravinkumarp;

import android.content.Intent;
import android.util.Log;

import com.pravinkumarputta.android.smsreceiver.SMSBroadcastReceiver;
import com.pravinkumarputta.android.smsreceiver.SMSReceiver;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class SmsReceiver extends CordovaPlugin {
    // API lists
    private final static String TAG = "SmsReceiver";
    private final static String START_SMS_LISTENER = "startSmsListener";
    private final static String GET_HASH_KEY = "getHashKey";
    private final static String GET_PHONE_NUMBER = "getPhoneNumber";

    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        switch (action) {
            case "echo": {
                String message = args.getString(0);
                this.echo(message, callbackContext);
                break;
            }
            case START_SMS_LISTENER:
                this.startSmsListener(callbackContext);
                break;
            case GET_HASH_KEY:
                this.getHashKey(callbackContext);
                break;
            case GET_PHONE_NUMBER:
                this.getPhoneNumber(callbackContext);
                break;
            default:
                return false;
        }
        return true;
    }

    private void echo(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success("Success");
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void startSmsListener(CallbackContext callbackContext) {
        SMSBroadcastReceiver.OTPReceiveListener smsReceiverCallback = new SMSBroadcastReceiver.OTPReceiveListener() {
            @Override
            public void onSMSReceiverStarted() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("callback", "onSMSReceiverStarted");

                    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, jsonObject);
                    pluginResult.setKeepCallback(true);
                    callbackContext.sendPluginResult(pluginResult);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSMSReceiverFailed(Exception exception) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("callback", "onSMSReceiverFailed");
                    jsonObject.put("data", exception.getLocalizedMessage());

                    PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, jsonObject);
                    callbackContext.sendPluginResult(pluginResult);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSMSReceived(String message) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("callback", "onSMSReceived");
                    jsonObject.put("data", message);

                    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, jsonObject);
                    callbackContext.sendPluginResult(pluginResult);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSMSReceiverTimeOut() {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("callback", "onSMSReceiverTimeOut");

                    PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, jsonObject);
                    callbackContext.sendPluginResult(pluginResult);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        SMSReceiver smsReceiver = new SMSReceiver(cordova.getActivity(), smsReceiverCallback);
        smsReceiver.startSmsListener();
    }

    private void getHashKey(CallbackContext callbackContext) {
        String hashKey = SMSReceiver.getHashKey(cordova.getActivity());
        callbackContext.success(hashKey);
    }

    private void getPhoneNumber(CallbackContext callbackContext) {
        this.callbackContext = callbackContext;

        SMSReceiver.requestForPhoneNumber(cordova.getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        try {
            String phoneNumber = SMSReceiver.getPhoneNumberFromResult(requestCode, resultCode, intent);
            if (phoneNumber != null) {
                callbackContext.success(phoneNumber);
            } else {
                callbackContext.error("Phone number not selected.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}