package gedoor.kunfei.lunarreminder.UI.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import gedoor.kunfei.lunarreminder.R;

import static gedoor.kunfei.lunarreminder.LunarReminderApplication.mContext;

/**
 * Created by GKF on 2017/4/2.
 */

public class MySimpleAdapter extends SimpleAdapter {
    ArrayList<HashMap<String, String>> listitem;

    public MySimpleAdapter(Context context, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.listitem = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        String mId = listitem.get(position).get("id");
        TextView start = (TextView) view.findViewById(R.id.event_item_date);
        TextView title = (TextView) view.findViewById(R.id.event_item_title);
        if (mId == "") {
            start.setTextSize(30);
            title.setBackground(mContext.getResources().getDrawable(R.color.colorTransparent));
            title.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
            title.setTextSize(30);
        } else {
            start.setTextSize(16);
            title.setBackground(mContext.getResources().getDrawable(R.color.colorLunar));
            title.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            title.setTextSize(16);
        }

        return view;
    }
}
