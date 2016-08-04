package com.hqyj.dev.smarthousesarduino.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.hqyj.dev.smarthousesarduino.R;
import com.hqyj.dev.smarthousesarduino.activities.adapters.GridViewVoirAdapter;
import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.views.DrawVoirView;

/**
 *
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class FragmentVoir extends Fragment {

    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_voirl, container, false);

        initViw();

        return mView;
    }

    private void initViw() {
        GridView gridView = (GridView) mView.findViewById(R.id.grid_voir_fragment);
        GridViewVoirAdapter gridViewVoirAdapter = new GridViewVoirAdapter(mView.getContext());
        gridView.setAdapter(gridViewVoirAdapter);
        gridView.setSelection(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
