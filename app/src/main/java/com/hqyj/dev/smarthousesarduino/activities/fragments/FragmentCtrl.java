package com.hqyj.dev.smarthousesarduino.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.hqyj.dev.smarthousesarduino.R;
import com.hqyj.dev.smarthousesarduino.activities.adapters.GridViewCtrlAdapter;
import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.ModuleCtrlList;
import com.hqyj.dev.smarthousesarduino.tools.MathTools;
import com.hqyj.dev.smarthousesarduino.tools.StringTools;

/**
 * Created by jiyangkang on 2016/6/2 0002.
 */
public class FragmentCtrl extends Fragment {

//    private final String TAG = FragmentCtrl.class.getSimpleName();

    private View mView;
    private EditText editText;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_ctrl, container, false);
        initView();
        return mView;
    }

    private void initView() {
        editText = (EditText) mView.findViewById(R.id.edt_command);
        GridView gridView = (GridView) mView.findViewById(R.id.grid_ctrl_fragment);
        GridViewCtrlAdapter gridViewCtrlAdapter = new GridViewCtrlAdapter(mView.getContext());
        gridView.setAdapter(gridViewCtrlAdapter);
        gridViewCtrlAdapter.setOnSubmitReceived(new GridViewCtrlAdapter.OnSubmitReceived() {
            @Override
            public void onSubmitReceived(int id) {
                Module module = ModuleCtrlList.getModuleCtrlList().getModuleHashMap().get(id);
                if (!editText.getText().toString().equalsIgnoreCase("null")) {
//                    if (MathTools.changeIntoByte(editText.getText().toString()) == null) {
//                        Toast.makeText(mView.getContext(), "请输入正确的命令控制字，例如:30！", Toast.LENGTH_SHORT).show();
//                    } else {
//                        module.sendCMD(MathTools.changeIntoByte(editText.getText().toString()));
//                    }
                    module.sendCMD(editText.getText().toString());
                }
            }
        });
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
