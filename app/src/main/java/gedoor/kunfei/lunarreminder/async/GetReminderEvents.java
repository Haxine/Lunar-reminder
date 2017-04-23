package gedoor.kunfei.lunarreminder.async;


import android.annotation.SuppressLint;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import gedoor.kunfei.lunarreminder.ui.BaseActivity;
import gedoor.kunfei.lunarreminder.util.ACache;
import gedoor.kunfei.lunarreminder.util.ChineseCalendar;
import gedoor.kunfei.lunarreminder.util.EventTimeUtil;

import static gedoor.kunfei.lunarreminder.LunarReminderApplication.listEvent;

/**
 * 获取提醒事件
 */

public class GetReminderEvents extends CalendarAsyncTask {
    private static final String TAG = "AsyncGetEvents";
    private String calendarId;
    private List<Event> events;

    public GetReminderEvents(BaseActivity activity, String calendarId) {
        super(activity);
        this.calendarId = calendarId;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void doInBackground() throws IOException {
        if (activity.showAllEvents) {
            Events events = client.events().list(calendarId).setSingleEvents(true).setOrderBy("startTime")
                    .execute();
            this.events = events.getItems();
        } else {
            ChineseCalendar cc = new ChineseCalendar(Calendar.getInstance());
            cc.add(Calendar.DATE, 1);
            DateTime startDT = new DateTime(new EventTimeUtil(cc).getDateTime());
            cc.add(ChineseCalendar.CHINESE_YEAR, 1);
            cc.add(Calendar.DATE, -1);
            DateTime endDT = new DateTime(new EventTimeUtil(cc).getDateTime());
            Events events = client.events().list(calendarId).setSingleEvents(true).setOrderBy("startTime")
                    .setTimeMin(startDT).setTimeMax(endDT).execute();
            this.events = events.getItems();
        }

    }

    @Override
    protected void onPostExecute(Boolean success) {
        ACache mCache = ACache.get(activity);
        Gson gson = new Gson();
        String strEvents = gson.toJson(events);
        mCache.put("events", strEvents);
        listEvent = gson.fromJson(strEvents, new TypeToken<ArrayList<LinkedHashMap<String, ?>>>() {
        }.getType());
        new LoadReminderEventList(activity).execute();
    }

}