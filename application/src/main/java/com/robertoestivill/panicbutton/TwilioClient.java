package com.robertoestivill.panicbutton;

import android.util.Log;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TwilioClient {

  private static final String TAG = TwilioClient.class.getSimpleName();

  private static final String BASE_URL =
      "https://api.twilio.com/2010-04-01/Accounts/" + BuildConfig.TWILIO_ACCOUNT;

  private OkHttpClient httpClient;

  public TwilioClient(OkHttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public void call() {
    Log.d(TAG, "Sending call request...");

    // TODO fix Url parameter
    String url = "https://someurl.com";

    RequestBody formBody = new FormBody.Builder()
        .add("From", BuildConfig.PHONE_NUMBER_FROM)
        .add("To", BuildConfig.PHONE_NUMBER_TO)
        .add("Url", url)
        .build();

    Request.Builder builder = new Request.Builder()
        .url(BASE_URL + "/Calls.json")
        .post(formBody);

    execute(builder);
  }

  public void sms() {
    Log.d(TAG, "Sending SMS request...");

    RequestBody formBody = new FormBody.Builder()
        .add("From", BuildConfig.PHONE_NUMBER_FROM)
        .add("To", BuildConfig.PHONE_NUMBER_TO)
        .add("Body", "PANIC BUTTON SMS")
        .build();

    Request.Builder builder = new Request.Builder()
        .url(BASE_URL + "/Messages.json")
        .post(formBody);

    execute(builder);
  }

  private void execute(Request.Builder builder) {
    String credential = Credentials.basic(
        BuildConfig.TWILIO_API_KEY,
        BuildConfig.TWILIO_API_SECRET);

    Request request = builder
        .addHeader("Authorization", credential)
        .build();

    httpClient
        .newCall(request)
        .enqueue(new Callback() {

          @Override public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
              Log.d(TAG, "Twilio request succeed.");
            } else {
              Log.d(TAG, "Twilio request failed. Make sure you have the correct configuration");
              Log.d(TAG, response.code() + " " + response.message());
              Log.d(TAG, response.body().string());
            }
          }

          @Override public void onFailure(Call call, IOException e) {
            // TODO handle retries
            Log.e(TAG, "Twilio request failed.", e);
          }
        });
  }
}
