package com.hqyj.dev.smarthousesarduino.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.hqyj.dev.smarthousesarduino.R;
import com.hqyj.dev.smarthousesarduino.activities.fragments.FragmentCtrl;
import com.hqyj.dev.smarthousesarduino.activities.fragments.FragmentVoir;
import com.hqyj.dev.smarthousesarduino.system.SystemConfig;
import com.hqyj.dev.smarthousesarduino.tools.SAXPraserHelper;
import com.hqyj.dev.smarthousesarduino.views.DrawBottom;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends Activity {
    private final String TAG = MainActivity.class.getSimpleName();

    private SystemConfig systemConfig;

    private ListView listViewProject, listViewCtrl, listViewVoir;

    private List<String> project;
    private List<String> ctrlNode;
    private List<String> voirNode;

    private Context mContext;
    private ProgressDialog dialog;

    private DrawBottom drawBottom;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    dialog.dismiss();
                    listViewProject.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, project));
                    listViewCtrl.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, ctrlNode));
                    listViewVoir.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, voirNode));
                    break;
                case 2:
                    creatDialog();
                    break;
                case 3:
                    Intent intent = new Intent(MainActivity.this, ProjectActivity.class);
                    startActivity(intent);
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
        setContentView(R.layout.activity_main);

        systemConfig = SystemConfig.getSystemConfig();
        mContext = MainActivity.this;
        intiView();

        ConfigThread configThread = new ConfigThread();
        configThread.start();
        dialog = ProgressDialog.show(mContext, null, "正在配置系统");
    }

    private void intiView() {

        listViewProject = (ListView) findViewById(R.id.list_main);
        listViewCtrl = (ListView) findViewById(R.id.list_ctrl_node);
        listViewVoir = (ListView) findViewById(R.id.list_voir_node);
        project = new ArrayList<>();
        ctrlNode = new ArrayList<>();
        voirNode = new ArrayList<>();
        drawBottom = (DrawBottom) findViewById(R.id.main_bottom);
        drawBottom.setSetting(true);
        drawBottom.setOnCodeClicked(new DrawBottom.OnCodeClicked() {
            @Override
            public void onCodeClicked() {
                handler.sendEmptyMessage(2);
            }
        });
        drawBottom.setOnSubmitClicked(new DrawBottom.OnSubmitClicked() {
            @Override
            public void onSubmitClicked() {
                handler.sendEmptyMessage(3);
            }
        });
    }

    private class ConfigThread extends Thread {
        @Override
        public void run() {
            super.run();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser;
            try {

                parser = factory.newSAXParser();
                XMLReader xmlReader = parser.getXMLReader();
                SAXPraserHelper helper = new SAXPraserHelper();
                xmlReader.setContentHandler(helper);
                InputStream stream = getResources().openRawResource(R.raw.system_config);
                InputSource inputSource = new InputSource(stream);
                xmlReader.parse(inputSource);
                project = helper.getProject();
                ctrlNode = helper.getCtrlNode();
                voirNode = helper.getVoirNode();

                List<Fragment> fragments = new ArrayList<>();
                fragments.add(new FragmentCtrl());
                fragments.add(new FragmentVoir());
                SystemConfig.getSystemConfig().setFragmentList(fragments);


                handler.sendEmptyMessage(1);
            } catch (ParserConfigurationException | SAXException | IOException e) {
//                e.printStackTrace();
                Log.d(TAG, String.format("%s", "XML解析错误"));
            }

        }
    }


    private Dialog alertDialog;
    private EditText editText;
    private ImageView imageView;

    private void creatDialog() {
        alertDialog = new Dialog(mContext);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        alertDialog.getWindow().setContentView(R.layout.dialog_code);
        editText = (EditText) alertDialog.getWindow().findViewById(R.id.edit_code);
        imageView = (ImageView) alertDialog.getWindow().findViewById(R.id.img_code);

        Button button = (Button) alertDialog.getWindow().findViewById(R.id.btn_code);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                if (str.equalsIgnoreCase("null") || str.length() > 4) {
                    Toast.makeText(mContext, "输入信息有误，请重新输入！", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(SystemConfig.getSystemConfig().getDevice());
                stringBuilder.replace(0, 4, String.format("%04d", Integer.parseInt(str)));
                SystemConfig.getSystemConfig().setDevice(stringBuilder.toString());
//                CodeTools codeTools = new CodeTools(Integer.parseInt(str));
//
//                codeTools.setOnBitmapCreate(new CodeTools.OnBitmapCreate() {
//                    @Override
//                    public void onBitmapCreate(Drawable bitmap) {
//
//                        SystemConfig.getSystemConfig().setCodeBit(bitmap);
//                        imageView.setImageDrawable(bitmap);
//                    }
//                });



                Bitmap b = BitmapFactory.decodeResource(getResources(), getQRDrawable(Integer.parseInt(str)));

                SystemConfig.getSystemConfig().setCodeBit(new BitmapDrawable(b));
                imageView.setImageBitmap(b);
            }
        });

    }

    private int getQRDrawable(int id) {
        int a = R.drawable.house_arduino01;

        int offset = id - 1;

        return a +offset;
    }
}
