
# cordova-plugin-android-sms-receiver

  

[![npm](https://img.shields.io/npm/v/cordova-plugin-SmsReceiver.svg)](https://www.npmjs.com/package/cordova-plugin-android-sms-receiver)

![Platform](https://img.shields.io/badge/platform-android-lightgrey.svg)

  

This plugin is used to detect OTP using Automatic SMS Verification with the SMS Retriever API.

  
  

## Installation

  

```

cordova plugin add cordova-plugin-android-sms-receiver

```

  

## Supported Platforms

  

- Android

  

## Usage

  

This plugin works with internet connection and without internet if language package available on device.

  

### startSmsListener

  

```js

SmsReceiver.startSmsListener(onSMSReceiverStarted, onSMSReceiverFailed, onSMSReceived, onSMSReceiverTimeOut)

```

`onSMSReceiverStarted` - {Function()} Calls when SMS Retrival API has started.

`onSMSReceiverFailed` - {Function(err: String)} Calls when SMS Retrival failed to start.

`onSMSReceived` - {Function(message: String)} Calls when SMS received.

`onSMSReceiverTimeOut` - {Function()} Calls when SMS Retrival API gets timed out and its usually takes 5 minutes.

  

### getHashKey()

  

```js

SmsReceiver.getHashKey(): Promise<String>

```

  

Returns `11-character hash string` for the app which will we used for constructing verification message.

  

### getPhoneNumber ![Platform](https://img.shields.io/badge/Not%20supported%20for%20now-red.svg)

  

```js

SmsReceiver.getPhoneNumber(): Promise<String>

```

  

Opens a dialog to select your mobile numbers saved in phone and returns selected phone number.

`NOTE: Works when the MainActivity extended with FragmentActivity.`

  

## Construct a verification message
The verification message that you will send to the user's device. This message must:

 - Be no longer than 140 bytes
 - Begin with the prefix <#>
 - Contain a one-time code that the client sends back to your server to complete the verification flow (see Generating a one-time code)
 - End with an 11-character hash string that identifies your app (see Computing your app's hash string)

Otherwise, the contents of the verification message can be whatever you choose. It is helpful to create a message from which you can easily extract the one-time code later on. For example, a valid verification message might look like the following:
```
<#> Your ExampleApp code is: 123ABC78
FA+9qCX9VSu
```

(For more information visit [__here__](https://developers.google.com/identity/sms-retriever/verify))

## Android Quirks

  

### Requirements

  

- cordova-android v5.0.0

- Android API level 22
  

### Further Readings
  

-  https://developers.google.com/identity/sms-retriever/overview
-  https://developers.google.com/identity/sms-retriever/request

- https://github.com/pravinkumarputta/smsreceiver

## Author

  

### Pravinkumar Putta

  

- https://github.com/pravinkumarputta

  
  

## LICENSE

  

**cordova-plugin-android-sms-receiver** is licensed under the MIT Open Source license. For more information, see the LICENSE file in this repository.