package kh.nobita.hang.model;

import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HistoryOne {
    private TextView tvTime;
    private List<HorizontalScrollView> listhistory;
    private boolean isNight;

    public HistoryOne(TextView tvTime, List<HorizontalScrollView> listhistory, boolean isNight) {
        this.tvTime = tvTime;
        this.listhistory = listhistory;
        this.isNight = isNight;
    }

    public HistoryOne(TextView tvTime, HorizontalScrollView history, boolean isNight) {
        this.tvTime = tvTime;
        this.listhistory = new ArrayList<>();
        listhistory.add(history);
        this.isNight = isNight;
    }

    public TextView getTvTime() {
        return tvTime;
    }

    public void setTvTime(TextView tvTime) {
        this.tvTime = tvTime;
    }

    public List<HorizontalScrollView> getListhistory() {
        return listhistory;
    }

    public void setListhistory(List<HorizontalScrollView> listhistory) {
        this.listhistory = listhistory;
    }

    public boolean isNight() {
        return isNight;
    }

    public void setNight(boolean night) {
        isNight = night;
    }
}
