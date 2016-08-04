package com.hqyj.dev.smarthousesarduino.activities.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hqyj.dev.smarthousesarduino.R;
import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.ModuleCtrlList;
import com.hqyj.dev.smarthousesarduino.modules.ModuleVoirList;
import com.hqyj.dev.smarthousesarduino.views.DrawVoirView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jiyangkang on 2016/6/6 0006.
 */
public class GridViewVoirAdapter extends BaseAdapter{

    private final String TAG = GridViewVoirAdapter.class.getSimpleName();

    private HashMap<Integer, Module> moduleHashMap;
    private Context mContext;
    private List<Integer> moduleList;
    private LayoutInflater mInflater;

    public GridViewVoirAdapter(Context context){
        moduleHashMap =  ModuleVoirList.getModuleVoirList().getModuleHashMap();
        mContext = context;
//        mInflater = LayoutInflater.from(mContext);
        moduleList = new ArrayList<>();
        for (Object o : moduleHashMap.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            moduleList.add((Integer) entry.getKey());
//            Log.d(TAG, "GridViewVoirAdapter: " + entry.getKey());
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
            mInflater = LayoutInflater.from(parent.getContext());
            convertView = mInflater.inflate(R.layout.gridview_voir_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.drawVoirView = (DrawVoirView) convertView.findViewById(R.id.voir_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.drawVoirView.setModule(moduleHashMap.get(moduleList.get(position)));
        viewHolder.drawVoirView.invalidate();


        return convertView;
    }

    private class ViewHolder{
        DrawVoirView drawVoirView;
    }
}
