import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:alt_sms_autofill/alt_sms_autofill.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String? _comingSms = 'Welcome to flutter';

  Future<void> initSmsListener() async {
    String? comingSms;
    try {
      comingSms = await AltSmsAutofill().listenForSms;
    } on PlatformException {
      comingSms = 'Failed to get Sms.';
    }
    if (!mounted) return;

    setState(() {
      _comingSms = comingSms;
    });
  }

  @override
  void dispose() {
    AltSmsAutofill().unregisterListener();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        appBar: AppBar(
          centerTitle: true,
          title: const Text('SMS auto fill'),
        ),
        body: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Center(
              child: Text('Running on: $_comingSms\n'),
            ),
            TextButton(
              child: Text('Listen for sms code'),
              onPressed: initSmsListener,
            ),
          ],
        ),
      ),
    );
  }
}
