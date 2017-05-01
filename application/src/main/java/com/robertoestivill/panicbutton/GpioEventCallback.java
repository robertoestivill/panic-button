package com.robertoestivill.panicbutton;

import android.util.Log;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import java.io.IOException;

public class GpioEventCallback extends GpioCallback {

  private static final String TAG = GpioEventCallback.class.getSimpleName();
  private static final long LONG_PRESS_DELAY_MS = 600;

  private TwilioClient twilio;
  private long pressStartedAt;

  public GpioEventCallback(TwilioClient twilio) {
    this.twilio = twilio;
  }

  @Override public boolean onGpioEdge(Gpio gpio) {
    // TODO long press vs Double/Triple press support

    boolean gpioValue;
    try {
      gpioValue = gpio.getValue();
    } catch (IOException e) {
      Log.e(TAG, "Error reading PIO value.", e);
      return true;
    }

    // if gpioValue == false, press event started (button pushed)
    if (!gpioValue) {
      pressStartedAt = System.currentTimeMillis();
    } else {
      // if gpioValue == true, press event finished (button released)
      if (System.currentTimeMillis() - pressStartedAt < LONG_PRESS_DELAY_MS) {
        twilio.sms();
      } else {
        twilio.call();
      }
    }

    // TODO improve event handling to avoid multiple calls to Twilio.
    return true;
  }
}