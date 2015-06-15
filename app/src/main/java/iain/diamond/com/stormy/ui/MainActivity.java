package iain.diamond.com.stormy.ui;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import iain.diamond.com.stormy.R;
import iain.diamond.com.stormy.weather.Current;
import iain.diamond.com.stormy.weather.Forecast;

public class MainActivity extends Activity {

  public static final String TAG = MainActivity.class.getSimpleName();

  private Forecast forecast;
  @InjectView(R.id.temperatureLabel) TextView temperatureValue;
  @InjectView(R.id.timeLabel) TextView timeLabel;
  @InjectView(R.id.humidityValue) TextView humidityValue;
  @InjectView(R.id.precipValue) TextView precipValue;
  @InjectView(R.id.summaryLabel) TextView summaryLabel;
  @InjectView(R.id.iconImageView) ImageView iconView;
  @InjectView(R.id.refreshImageView) ImageView refreshView;
  @InjectView(R.id.progressBar) ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.inject(this);

    progressBar.setVisibility(View.INVISIBLE);

    final double latitude = 56.4640;
    final double longitude = -2.9700;

    getForecast(latitude, longitude);

    refreshView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getForecast(latitude, longitude);
      }
    });
  }

  private void getForecast(double latitude, double longitude) {

    String apiKey = "77638bfbd0bacbeeeb549d151a80a047";
    final String forecastUrl = "https://api.forecast.io/forecast/" + apiKey +
            "/" + latitude + "," + longitude;

    if (isNetworkAvailable()) {
      toggleRefresh();
      OkHttpClient client = new OkHttpClient();
      final Request request = new Request.Builder()
              .url(forecastUrl)
              .build();

      Call call = client.newCall(request);
      call.enqueue(new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              toggleRefresh();
            }
          });
          alertUserAboutError();
        }

        @Override
        public void onResponse(Response response) throws IOException {
          runOnUiThread(new Runnable() {
            @Override
            public void run() {
              toggleRefresh();
            }
          });

          if (response.isSuccessful()) {
            String jsonData = response.body().string();
            try {
              forecast = parseForecastDetails(jsonData);
              runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  updateDisplay();

                }
              });
            } catch (JSONException e) {
              Toast.makeText(MainActivity.this, "Invalid Weather Data", Toast.LENGTH_LONG).show();
            }
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

  private Forecast parseForecastDetails(String jsonData) throws JSONException {
    Forecast forecast = new Forecast();

    forecast.setCurrent(getCurrentDetails(jsonData));

    return forecast;
  }

  private Current getCurrentDetails(String jsonData) throws JSONException {
    JSONObject forecast = new JSONObject(jsonData);
    JSONObject currently = forecast.getJSONObject("currently");

    Current cw = new Current();
    cw.setIcon(currently.getString("icon"));
    cw.setTime(currently.getLong("time"));
    cw.setHumidity(currently.getDouble("humidity"));
    cw.setTemperature(currently.getDouble("temperature"));
    cw.setPrecipChance(currently.getDouble("precipProbability"));
    cw.setSummary(currently.getString("summary"));
    cw.setTimeZone(forecast.getString("timezone"));

    return cw;
  }

  private void updateDisplay() {
    Current currentWeather = forecast.getCurrent();

    temperatureValue.setText(currentWeather.getCelciusTemperatureWithDecimal());
    timeLabel.setText("At "+currentWeather.getFormattedTime()+" it will be");
    humidityValue.setText("" + currentWeather.getHumidity());
    precipValue.setText("" + currentWeather.getPrecipChance() + "%");
    summaryLabel.setText(currentWeather.getSummary());

    Drawable drawable = getResources().getDrawable(currentWeather.getIconId());
    iconView.setImageDrawable(drawable);
  }

  private void toggleRefresh() {
    if (progressBar.getVisibility() == View.VISIBLE) {
      progressBar.setVisibility(View.INVISIBLE);
      refreshView.setVisibility(View.VISIBLE);
    } else {
      progressBar.setVisibility(View.VISIBLE);
      refreshView.setVisibility(View.INVISIBLE);
    }
  }

  private void alertUserAboutError() {
    AlertDialogFragment dialog = new AlertDialogFragment();
    dialog.show(getFragmentManager(), "error_dialog");
  }
}
