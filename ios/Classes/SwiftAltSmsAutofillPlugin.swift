import Flutter
import UIKit

public class SwiftAltSmsAutofillPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "alt_sms_autofill", binaryMessenger: registrar.messenger())
    let instance = SwiftAltSmsAutofillPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
