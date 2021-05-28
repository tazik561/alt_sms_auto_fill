package com.alt_sms_autofill;

import android.content.Intent;

public interface SmsListener {
    public void messageReceived(String messages);
}