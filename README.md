# alt_sms_autofill

Alt Sms Autofill.

## Getting Started

This project is for getting new arrived sms and shows on application. Sms completely return into application and you can split it as you want.

![](https://raw.githubusercontent.com/tazik561/alt_sms_auto_fill/main/images/alt_sms_autofill.gif?raw=true) 

## Usage

  

Add it to your pubspec.yaml file:

  

```yaml

dependencies:

alt_sms_autofill: version

```

  

In your library add the following import:

  

```dart

import 'package:alt_sms_autofill/alt_sms_autofill.dart';

```

  

Here is an example how to use:

```dar
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
      home: Scaffold(
        appBar: AppBar(
          title: const Text('AltAutoFill example app'),
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
```  

## parameters

* `listenForCode()` to listen for the SMS code from the native plugin when SMS is received.
* `unregisterListener()` to unregister the broadcast receiver, need to be called on your `dispose`.

#ff packages pub publish
