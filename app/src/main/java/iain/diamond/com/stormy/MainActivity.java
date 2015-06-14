package iain.diamond.com.stormy;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends ActionBarActivity {

  public static final String TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    String apiKey = "77638bfbd0bacbeeeb549d151a80a047";
    double latitude = 56.4640;
    double longitude = -2.9700;
    String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
            "/" + latitude + "," + longitude;

    if (isNetworkAvailable()) {
      OkHttpClient client = new OkHttpClient();
      final Request request = new Request.Builder()
              .url(forecastUrl)
              .build();

      Call call = client.newCall(request);
      call.enqueue(new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {

        }

        @Override
        public void onResponse(Response response) throws IOException {
          if (response.isSuccessful()) {
            Log.v(TAG, response.body().toString());
          } else {
            alertUserAboutError();
          }
        }
      });
    } else {
      Toast.makeText(this, "No Network!", Toast.LENGTH_LONG).show();
    }
  }

  private boolean isNetworkAvailable() {
    ConnectivityManager manager = (ConnectivityManager)
            getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
    boolean isAvailable = false;
    if (networkInfo != null && networkInfo.isConnected()) {
      isAvailable = true;
    }
    return isAvailable;
  }

  private void alertUserAboutError() {
    AlertDialogFragment dialog = new AlertDialogFragment();
    dialog.show(getFragmentManager(), "error_dialog");
  }
}
