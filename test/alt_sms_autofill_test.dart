import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:alt_sms_autofill/alt_sms_autofill.dart';

void main() {
  const MethodChannel channel = MethodChannel('alt_sms_autofill');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await AltSmsAutofill.platformVersion, '42');
  });
}
