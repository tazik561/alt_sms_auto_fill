#import "AltSmsAutofillPlugin.h"
#if __has_include(<alt_sms_autofill/alt_sms_autofill-Swift.h>)
#import <alt_sms_autofill/alt_sms_autofill-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "alt_sms_autofill-Swift.h"
#endif

@implementation AltSmsAutofillPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAltSmsAutofillPlugin registerWithRegistrar:registrar];
}
@end
