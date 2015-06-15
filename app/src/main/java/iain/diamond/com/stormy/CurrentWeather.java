package iain.diamond.com.stormy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CurrentWeather {
  private String icon;
  private long time;
  private double temperature;
  private double humidity;
  private double precipChance;
  private String summary;
  private String timeZone;

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public int getIconId() {
    // clear-day, clear-night, rain, snow, sleet, wind, fog,
    // cloudy, partly-cloudy-day, or partly-cloudy-night
    int iconId;
    switch (icon) {
      case "clear-day":
        iconId = R.drawable.clear_day;
        break;
      case "clear-night":
        iconId = R.drawable.clear_night;
        break;
      case "sunny":
        iconId = R.drawable.sunny;
        break;
      case "rain":
        iconId = R.drawable.rain;
        break;
      case "snow":
        iconId = R.drawable.snow;
        break;
      case "sleet":
        iconId = R.drawable.sleet;
        break;
      case "wind":
        iconId = R.drawable.wind;
        break;
      case "fog":
        iconId = R.drawable.fog;
        break;
      case "cloudy":
        iconId = R.drawable.cloudy;
        break;
      case "partly-cloudy-day":
        iconId = R.drawable.partly_cloudy;
        break;
      case "partly-cloudy-night":
        iconId = R.drawable.cloudy_night;
        break;
      default:
        iconId = R.drawable.clear_day;
    }
    return iconId;
  }

  public long getTime() {
    return time;
  }

  public String getFormattedTime() {
    SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
    formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
    Date dateTime = new Date(getTime() * 1000); // formatter requires time in mSecs
    return formatter.format(dateTime);
  }

  public void setTime(long time) {
    this.time = time;
  }

  public int getTemperature() {
    return (int) Math.round(temperature);
  }

  private double toCelcius(double temperature) {
    return (temperature - 32) * 5/9;
  }

  public int getCelciusTemperature() { return (int) Math.round(toCelcius(temperature)); }

  public String getCelciusTemperatureWithDecimal() {
    return String.format("%.1f", toCelcius(temperature));
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public double getHumidity() {
    return humidity;
  }

  public void setHumidity(double humidity) {
    this.humidity = humidity;
  }

  /**
   * Is it going to rain?
   * @return value as a rounded percentage
   */
  public int getPrecipChance() {
    return (int) Math.round(precipChance * 100);
  }

  public void setPrecipChance(double precipChance) {
    this.precipChance = precipChance;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }
}
