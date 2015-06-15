package iain.diamond.com.stormy.weather;

public class Forecast {
  private Current current;
  private Hour[] hourlyForecasts;
  private Day[] dailyForecasts;

  public Current getCurrent() {
    return current;
  }

  public void setCurrent(Current current) {
    this.current = current;
  }

  public Hour[] getHourlyForecasts() {
    return hourlyForecasts;
  }

  public void setHourlyForecasts(Hour[] hourlyForecasts) {
    this.hourlyForecasts = hourlyForecasts;
  }

  public Day[] getDailyForecasts() {
    return dailyForecasts;
  }

  public void setDailyForecasts(Day[] dailyForecasts) {
    this.dailyForecasts = dailyForecasts;
  }
}
