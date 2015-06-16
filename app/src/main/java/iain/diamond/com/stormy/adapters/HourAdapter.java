package iain.diamond.com.stormy.adapters;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.InjectView;
import iain.diamond.com.stormy.R;
import iain.diamond.com.stormy.weather.Hour;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {

  private Hour[] hours;

  public HourAdapter(Hour[] hours) {
    this.hours = hours;
  }

  @Override
  public HourViewHolder onCreateViewHolder(ViewGroup parent, int i) {
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.hourly_list_item, parent, false);
    HourViewHolder viewHolder = new HourViewHolder(view);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(HourViewHolder holder, int position) {
    holder.bindHour(hours[position]);
  }

  @Override
  public int getItemCount() {
    return hours.length;
  }

  public class HourViewHolder extends RecyclerView.ViewHolder {

    public TextView timeLabel;
    public TextView summaryLabel;
    public TextView temperatureLabel;
    public ImageView iconImageView;

    public HourViewHolder(View itemView) {
      super(itemView);

      timeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
      summaryLabel= (TextView) itemView.findViewById(R.id.summaryLabel);
      temperatureLabel= (TextView) itemView.findViewById(R.id.temperatureLabel);
      iconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
    }

    public void bindHour(Hour hour) {
      timeLabel.setText(hour.getHour());
      summaryLabel.setText(hour.getSummary());
      temperatureLabel.setText(hour.getTemperatureCelciusWithDecimal());
      iconImageView.setImageResource(hour.getIconId());
    }
  }
}
