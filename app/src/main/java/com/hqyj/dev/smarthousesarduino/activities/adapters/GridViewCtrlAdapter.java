package com.hqyj.dev.smarthousesarduino.activities.adapters;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hqyj.dev.smarthousesarduino.R;
import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.ModuleCtrlList;
import com.hqyj.dev.smarthousesarduino.views.DrawCtrlView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiyangkang on 2016/6/6 0006.
 */
public class GridViewCtrlAdapter extends BaseAdapter{

    private final String TAG = GridViewCtrlAdapter.class.getSimpleName();

    private HashMap<Integer, Module> moduleHashMap;
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> moduleList;


    public GridViewCtrlAdapter (Context context){
        moduleHashMap =  ModuleCtrlList.getModuleCtrlList().getModuleHashMap();
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        moduleList = new ArrayList<>();
        for (Object o : moduleHashMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            moduleList.add((Integer) entry.getKey());
        }
    }

    @Override
    public int getCount() {
        return moduleHashMap.size();
    }

    @Override
    public Object getItem(int position) {
        return moduleHashMap.get(moduleList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.gridview_ctrl_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.drawCtrlView = (DrawCtrlView) convertView.findViewById(R.id.ctrl_item);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.drawCtrlView.setOnSubmitClicked(new DrawCtrlView.OnSubmitClicked() {
            @Override
            public void onSubmitClicked(int id, String whichClicked) {
//                Log.d(TAG, "onSubmitClicked: " + id);
                if (onSubmitReceived != null){
                    onSubmitReceived.onSubmitReceived(id, whichClicked);
                }
            }
        });
        convertView.setTag(viewHolder);
        viewHolder.drawCtrlView.setModule(moduleHashMap.get(moduleList.get(position)));
        return convertView;
    }

    private OnSubmitReceived onSubmitReceived;

    public void setOnSubmitReceived(OnSubmitReceived onSubmitReceived) {
        this.onSubmitReceived = onSubmitReceived;
    }

    public interface OnSubmitReceived{
        void onSubmitReceived(int id, String whichClicked);
    }

    private class ViewHolder{
        DrawCtrlView drawCtrlView;
    }
}
