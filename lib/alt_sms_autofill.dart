
import 'dart:async';

import 'package:flutter/services.dart';

class AltSmsAutofill {
  static AltSmsAutofill _singleton;
  factory AltSmsAutofill() => _singleton ??= AltSmsAutofill._();
  AltSmsAutofill._();
  static const MethodChannel _channel = const MethodChannel('alt_sms_autofill');

  Future<String> get listenForSms async {
    final String version = await _channel.invokeMethod('listenForSms');
    return version;
  }

  Future<void> unregisterListener() async {
    await _channel.invokeMethod('unregisterListener');
  }
}
