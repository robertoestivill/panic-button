package com.robertoestivill.panicbutton;

import android.app.Activity;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import android.os.Bundle;
import android.util.Log;

import com.robertoestivill.panicbutton.util.BoardDefaults;
import java.io.IOException;
import okhttp3.OkHttpClient;

public class PanicButtonActivity extends Activity {

  private static final String TAG = PanicButtonActivity.class.getSimpleName();

  private Gpio mButtonGpio;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    OkHttpClient httpClient = new OkHttpClient();
    TwilioClient twilio = new TwilioClient(httpClient);
    GpioEventCallback callback = new GpioEventCallback(twilio);

    try {
      String pinName = BoardDefaults.getGPIOForButton();
      mButtonGpio = new PeripheralManagerService().openGpio(pinName);
      mButtonGpio.setDirection(Gpio.DIRECTION_IN);
      mButtonGpio.setEdgeTriggerType(Gpio.EDGE_BOTH);
      mButtonGpio.registerGpioCallback(callback);
    } catch (IOException e) {
      Log.e(TAG, "Error on PeripheralIO API", e);
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (mButtonGpio != null) {
      Log.i(TAG, "Closing Button GPIO pin");
      try {
        mButtonGpio.close();
      } catch (IOException e) {
        Log.e(TAG, "Error on PeripheralIO API", e);
      } finally {
        mButtonGpio = null;
      }
    }
  }
}
