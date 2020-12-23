var exec = require('cordova/exec');

const SMS_RECEIVER = 'SmsReceiver'
const API_LIST = {
    START_SMS_LISTENER: "startSmsListener",
    GET_HASH_KEY: "getHashKey",
    GET_PHONE_NUMBER: "getPhoneNumber",
}

function SmsReceiver() { }

SmsReceiver.prototype[API_LIST.START_SMS_LISTENER] = function (onSMSReceiverStarted, onSMSReceiverFailed, onSMSReceived, onSMSReceiverTimeOut) {
    exec((res) => {
        switch (res.callback) {
            case 'onSMSReceiverStarted':
                onSMSReceiverStarted()
                break
            case 'onSMSReceived':
                onSMSReceived(res.data)
                break
        }
    }, (err) => {
        switch (err.callback) {
            case 'onSMSReceiverFailed':
                onSMSReceiverFailed(err.data)
                break
            case 'onSMSReceiverTimeOut':
                onSMSReceiverTimeOut()
                break
        }
    }, SMS_RECEIVER, API_LIST.START_SMS_LISTENER, [])
}

SmsReceiver.prototype[API_LIST.GET_HASH_KEY] = function (success, error) {
    return new Promise((resolve, reject) => {
        exec(resolve, reject, SMS_RECEIVER, API_LIST.GET_HASH_KEY, [])
    })
}

SmsReceiver.prototype[API_LIST.GET_PHONE_NUMBER] = function (success, error) {
    return new Promise((resolve, reject) => {
        exec(resolve, reject, SMS_RECEIVER, API_LIST.GET_PHONE_NUMBER, [])
    })
}

module.exports = new SmsReceiver();
