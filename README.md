# alt_sms_autofill

Alt Sms Autofill.

## Getting Started

This project is for getting sms that on application. Sms completaly return into application and you can splite 

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
class _MyAppState extends State<MyApp> {
  String _commingSms = 'Unknown';

  Future<void> initPlatformState() async {
    String commingSms;
    try {
      commingSms = await AltSmsAutofill().listenForSms;
    } on PlatformException {
      commingSms = 'Failed to get Sms.';
    }
    if (!mounted) return;

    setState(() {
      _commingSms = commingSms;
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
              child: Text('Running on: $_commingSms\n'),
            ),
            TextButton(
              child: Text('Listen for sms code'),
              onPressed: initPlatformState,
            ),
          ],
        ),
      ),
    );
  }
```  


- `listenForCode()` to listen for the SMS code from the native plugin when SMS is received.
- `unregisterListener()` to unregister the broadcast receiver, need to be called on your `dispose`.
