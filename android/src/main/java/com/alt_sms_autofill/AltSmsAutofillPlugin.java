package com.alt_sms_autofill;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.lang.ref.WeakReference;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;


/** AltSmsAutofillPlugin */
public class AltSmsAutofillPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.RequestPermissionsResultListener{

  private MethodChannel channel;
  private Activity activity;
  private int myPermissionCode = 1;
  private boolean permissionGranted = false;
  private Result result;
  private MySMSBroadcastReceive broadcastReceiver;


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "alt_sms_autofill");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull final Result result) {
    this.result = result;
    if (call.method.equals("listenForSms")) {
      checkPermission(activity);
      if (!permissionGranted) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.RECEIVE_SMS},
                myPermissionCode);
      } else {
        broadcastReceiver = new MySMSBroadcastReceive(new WeakReference<>(AltSmsAutofillPlugin.this));
        broadcastReceiver.bindListener(smsListener);
        activity.registerReceiver(broadcastReceiver,new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));

      }
    } else  if(call.method.equals("unregisterListener")){
      try {
        activity.unregisterReceiver(broadcastReceiver);
      } catch (Exception ex) {
      }

    }else{
      result.notImplemented();
    }
  }

  private SmsListener smsListener = new SmsListener(){
    @Override
    public void messageReceived(String messages) {
      result.success(messages);
    }
  };

  private void checkPermission(Activity context) {
    permissionGranted = ContextCompat.checkSelfPermission(context,
            Manifest.permission.RECEIVE_SMS) ==
            PackageManager.PERMISSION_GRANTED;
  }
  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
    binding.addRequestPermissionsResultListener(this);

  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    activity = null;
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
    binding.addRequestPermissionsResultListener(this);
  }

  @Override
  public void onDetachedFromActivity() {

  }

  @Override
  public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    if (requestCode == 1 && grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      broadcastReceiver = new MySMSBroadcastReceive(new WeakReference<>(AltSmsAutofillPlugin.this));
      broadcastReceiver.bindListener(smsListener);
      activity.registerReceiver(broadcastReceiver,new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));      return true;
    } else {
      return false;
    }
  }


  private static class MySMSBroadcastReceive extends BroadcastReceiver {
    private static SmsListener mListener;
    final WeakReference<AltSmsAutofillPlugin> plugin;

    private MySMSBroadcastReceive(WeakReference<AltSmsAutofillPlugin> plugin) {
      this.plugin = plugin;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
      if(Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())){
        if (plugin.get() == null) {
          return;
        } else {
          plugin.get().activity.unregisterReceiver(this);
        }
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        String concatMsg="";
        for (SmsMessage sms : messages){
          String message = sms.getMessageBody();
          concatMsg = concatMsg + message;
        }
        mListener.messageReceived(concatMsg);

      }


    }

    public void bindListener(SmsListener listener) {
      mListener = listener;
    }
  }
}

