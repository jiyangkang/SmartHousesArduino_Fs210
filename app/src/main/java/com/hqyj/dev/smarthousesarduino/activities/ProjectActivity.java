package com.hqyj.dev.smarthousesarduino.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hqyj.dev.smarthousesarduino.R;
import com.hqyj.dev.smarthousesarduino.activities.adapters.ViewPagerAdapter;
import com.hqyj.dev.smarthousesarduino.activities.fragments.FragmentCtrl;
import com.hqyj.dev.smarthousesarduino.activities.fragments.FragmentVoir;
import com.hqyj.dev.smarthousesarduino.modules.Module;
import com.hqyj.dev.smarthousesarduino.modules.ModuleCtrlList;
import com.hqyj.dev.smarthousesarduino.services.AnalysisService;
import com.hqyj.dev.smarthousesarduino.services.SoapService;
import com.hqyj.dev.smarthousesarduino.services.UartService;
import com.hqyj.dev.smarthousesarduino.services.UdpService;
import com.hqyj.dev.smarthousesarduino.system.SystemConfig;
import com.hqyj.dev.smarthousesarduino.tools.CodeTools;
import com.hqyj.dev.smarthousesarduino.tools.DataTool;
import com.hqyj.dev.smarthousesarduino.tools.MathTools;
import com.hqyj.dev.smarthousesarduino.views.DrawBottom;
import com.hqyj.dev.smarthousesarduino.views.DrawTitle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by jiyangkang on 2016/6/5 0005.
 */
public class ProjectActivity extends FragmentActivity{


    private DrawBottom drawBottom;
    private FragmentCtrl fragmentCtl;
    private FragmentVoir fragmentVoir;
    private List<Fragment> fragmentList;
    private ViewPager viewPager;

    private Context mContext;
    private DrawTitle drawTitle;
    private IntentFilter intentFilter;
    private  MyReciever myReciever;

    private final String TAG = ProjectActivity.class.getSimpleName();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    createDialog(mContext);
                    break;
                case 2:
                    String n = String.format("%s 控制命令超限",msg.getData().getString(TAG));

                    Toast.makeText(mContext, n, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置全屏和无标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_project);
        mContext = ProjectActivity.this;
        initView();

        drawTitle.setProjectName(String.format("华清远见：%s", SystemConfig.getSystemConfig().getProjectName()));

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragmentList));

        Intent intentAnal = new Intent(this, AnalysisService.class);
        startService(intentAnal);

        if (SystemConfig.getSystemConfig().isSoap()){
            Intent intentSoap = new Intent(this, SoapService.class);
            startService(intentSoap);
        }

        if (SystemConfig.getSystemConfig().isUart()){
            Intent intentUart = new Intent(this, UartService.class);
            startService(intentUart);
        }

        if (SystemConfig.getSystemConfig().isUdp()){
            Intent intentUdp = new Intent(this, UdpService.class);
            startService(intentUdp);
        }

        myReciever = new MyReciever();
        intentFilter = new IntentFilter();
        intentFilter.addAction(DataTool.ERRORINPUT);
        registerReceiver(myReciever, intentFilter);
    }

    private void initView() {

        drawTitle = (DrawTitle) findViewById(R.id.draw_title);

        drawBottom = (DrawBottom) findViewById(R.id.project_bottom);
        drawBottom.setSetting(false);
        drawBottom.setOnCodeClicked(new DrawBottom.OnCodeClicked() {
            @Override
            public void onCodeClicked() {
                handler.sendEmptyMessage(1);
            }
        });

        fragmentCtl = new FragmentCtrl();
        fragmentVoir = new FragmentVoir();
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentVoir);
        fragmentList.add(fragmentCtl);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (SystemConfig.getSystemConfig().isSoap()){
            Intent intentSoap = new Intent(this, SoapService.class);
            stopService(intentSoap);
        }

        if (SystemConfig.getSystemConfig().isUart()){
            Intent intentUart = new Intent(this, UartService.class);
            stopService(intentUart);
        }

        if (SystemConfig.getSystemConfig().isUdp()){
            Intent intentUdp = new Intent(this, UdpService.class);
            stopService(intentUdp);
        }
        unregisterReceiver(myReciever);
    }

    private Dialog alertDialog;
    private EditText editText1;
    private ImageView imageView;
    private TextView textView;
    private void createDialog(final Context context){
        if (SystemConfig.getSystemConfig().getCodeBit() == null){
            alertDialog = new Dialog(context);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            alertDialog.getWindow().setContentView(R.layout.dialog_code);
            editText1 = (EditText) alertDialog.getWindow().findViewById(R.id.edit_code);
            imageView = (ImageView) alertDialog.getWindow().findViewById(R.id.img_code);

            Button button = (Button) alertDialog.getWindow().findViewById(R.id.btn_code);

            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onClick(View v) {
                    String str = editText1.getText().toString();
                    if (str.equalsIgnoreCase("null") || str.length() > 4){
                        Toast.makeText(context, "输入信息有误，请重新输入！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(SystemConfig.getSystemConfig().getDevice());
                    stringBuilder.replace(0, 4, String.format("%04d", Integer.parseInt(str)));
                    SystemConfig.getSystemConfig().setDevice(stringBuilder.toString());
                    CodeTools codeTools = new CodeTools(Integer.parseInt(str));

                    codeTools.setOnBitmapCreate(new CodeTools.OnBitmapCreate() {
                        @Override
                        public void onBitmapCreate(Drawable bitmap) {

                            SystemConfig.getSystemConfig().setCodeBit(bitmap);
                            imageView.setImageDrawable(bitmap);
                        }
                    });
                }
            });
        } else {
            alertDialog = new Dialog(context);
            alertDialog.show();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            alertDialog.getWindow().setContentView(R.layout.dialog_code1);
            textView = (TextView) alertDialog.getWindow().findViewById(R.id.txt_code);
            imageView = (ImageView) alertDialog.getWindow().findViewById(R.id.img_code1);

            String device = SystemConfig.getSystemConfig().getDevice().substring(0,4);

            textView.setText(String.format("当前设备为：%s。\n二维码如下：",device));
            imageView.setImageDrawable(SystemConfig.getSystemConfig().getCodeBit());

        }
    }

    private class MyReciever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case DataTool.ERRORINPUT:
                    Message msg = new Message();
                    Bundle b = new Bundle();
                    b.putString(TAG, intent.getStringExtra(DataTool.ERRORINPUT));
                    msg.setData(b);
                    msg.what = 2;
                    handler.sendMessage(msg);
                    break;
            }
        }
    }

}
