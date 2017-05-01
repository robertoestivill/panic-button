`Panic Button` is an Android application that targets [Android Things](https://developer.android.com/things/hardware/index.html) that integrates with [Twilio](https://twilio.com) to perform phone calls or send SMS's when a physical button is pressed.

This project is based on the [SimplePio](https://github.com/androidthings/sample-simplepio) sample open sourced by Google.


## Application

The application supports a `short press` and a `long press` of the hardware button. 

The `short press` will trigger an SMS, while a `long press` will trigger a phone call request.

<a href="https://www.youtube.com/watch?v=t-7_qHqTxUA" target="_blank">
  <img src="https://img.youtube.com/vi/t-7_qHqTxUA/0.jpg" height="200" />
</a>

## Hardware

The hardware and circuit is the exact same than the [SimplePio](https://github.com/androidthings/sample-simplepio) project.

<a href="https://github.com/androidthings/sample-simplepio/raw/master/button/edison_schematics.png" target="_blank">
  <img src="https://github.com/androidthings/sample-simplepio/raw/master/button/edison_schematics.png" height="200" />
</a>
<a href="https://github.com/androidthings/sample-simplepio/raw/master/button/rpi3_schematics.png" target="_blank">
  <img src="https://github.com/androidthings/sample-simplepio/raw/master/button/rpi3_schematics.png" height="200" />
</a>


## Twilio account

For this project you will need a [Twilio](https://twilio.com) account. 
The free/trial account provides enough resources to experiment and play around with this application.

You need to setup a [Twilio number](https://www.twilio.com/console/phone-numbers/incoming) with phone calls and SMS capabilities. Also, an [API Key](https://www.twilio.com/console/dev-tools/api-keys/) needs to be created, so you don't expose your general account token.

You will need the following information from your account:

- AccountSid
- API key
- API Secret
- Phone number with call and SMS capabilities.
- Verified phone number to act as the receiver. 


## Configuration

Open the `gradle.properties` file and enter your configuration.


```
PANIC_BUTTON_PHONE_NUMBER_FROM="{ORIGIN PHONE NUMBER}"
PANIC_BUTTON_PHONE_NUMBER_TO="{TARGET PHONE NUMBER}"
PANIC_BUTTON_TWILIO_ACCOUNT="{TWILIO ACCOUNT SID}"
PANIC_BUTTON_TWILIO_API_KEY="{TWILIO API KEY}"
PANIC_BUTTON_TWILIO_API_SECRET="{YOUR TWILIO API SECRET}"
```

The `application/build.gradle` will read this properties and make them available to the app through `BuildConfig` fields.

Note that values in the file must be enclosed by quotes.

```
PANIC_BUTTON_PHONE_NUMBER_FROM="+1234567890"
...
```

## Run it

Make sure your device is on and connect to it through ADB

```
$ adb connect {YOUR DEVICE IP}
* daemon not running. starting it now on port 5037 *
* daemon started successfully *
connected to {YOUR DEVICE IP}:5555

```

Build the application and install it on the device 

```
$ ./gradlew installDebug
```

Manually start the main activity (this will happen automatically if the device is rebooted)

```
$ adb shell am start com.robertoestivill.panicbutton/.PanicButtonActivity
```

## What's next?

- Fix `Url` paremter in the Calls Twilio request;
- Throtle multiple clicks to avoid multiple simultaneous requests
- Retry logic on `IOException`'s
- Evaluate `long press` vs `Double/Triple press` actions

## License

```
MIT License

Copyright (c) 2017 Roberto Estivill

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

