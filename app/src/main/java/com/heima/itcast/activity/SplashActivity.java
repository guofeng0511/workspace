package com.heima.itcast.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.heima.itcast.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import utils.StreamUitls;

public class SplashActivity extends AppCompatActivity {
    protected static final int MSG_UPDATE_DIALOG = 1;
    protected static final int MSG_ENTER_HOME = 2;
    protected static final int MSG_SERVER_ERROR = 3;
    protected static final int MSG_URL_ERROR = 4;
    protected static final int MSG_IO_ERROR = 5;
    protected static final int MSG_JSON_ERROR = 6;
    private TextView mTv_splash_vesion;
    private String mCode;
    private String mApkurl;
    private String mDes;
    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_DIALOG:
                    showdialog();
                    break;
                case MSG_ENTER_HOME:
                    entHome();
                    break;
                case MSG_SERVER_ERROR:
                    Toast.makeText(SplashActivity.this,"错误码"+MSG_SERVER_ERROR,Toast.LENGTH_SHORT).show();
                    entHome();
                    break;
                case MSG_URL_ERROR:
                    Toast.makeText(SplashActivity.this,"错误码"+MSG_URL_ERROR,Toast.LENGTH_SHORT).show();
                    entHome();
                    break;
                case MSG_IO_ERROR:
                    Toast.makeText(SplashActivity.this,"错误码"+MSG_IO_ERROR,Toast.LENGTH_SHORT).show();
                    entHome();
                    break;
                case MSG_JSON_ERROR:
                    Toast.makeText(SplashActivity.this,"错误码"+MSG_JSON_ERROR,Toast.LENGTH_SHORT).show();
                    entHome();
                    break;


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mTv_splash_vesion = findViewById(R.id.tv_splash_vesion);
        mTv_splash_vesion.setText("版本号"+getAppVesion());

        //去服务端获取应用版本号
        update();

    }

    private void showdialog() {
        //弹出更新对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        builder.setCancelable(false);
        //设置标题
        builder.setTitle("有最新版本："+mCode);
        //设置图片
        builder.setIcon(R.drawable.ic_launcher_background);
        //设置描述信息
        builder.setMessage(mDes);
        builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                // TODO 点击升级应用
                //TODO 联网下载应用第三方
                //下载完成调用系统的
                            /*Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            //intent.setType("application/vnd.android.package-archive");
                            //intent.setData(Uri.fromFile(new File("/mnt/sdcard/mobliesafe64_2.0.apk")));
                            intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/mobliesafe64_2.0.apk")), "application/vnd.android.package-archive");
                            //当当前的activity退出的时候，会调用之前activity的onActivityResult方法
                            startActivityForResult(intent, 0);*/

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //直接跳转主界面
                entHome();

            }
        });
        //显示
        //builder.create().show();
        builder.show();
    }

    private void entHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }




    private void update() {
        //联网
        new Thread(){
            @Override
            public void run() {
                Message message = Message.obtain();

                //1.获取连接地址
                URL url = null;
                try {
                    url = new URL("http://192.168.0.102:8080/updateinfo.html");
                    //2.连接操作
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();//http协议  httpclient;
                    //3.设置超时时间
                    conn.setConnectTimeout(5000);//连接超时时间
                    //conn.setReadTimeout(5000);//读取超时时间
                    //4.设置请求方式
                    conn.setRequestMethod("GET");//post
                    //5.获取服务器返回的状态码

                    int response_code = conn.getResponseCode();
                    if (response_code == 200) {
                        //解析数据
                        InputStream inputStream = conn.getInputStream();
                        String stream = StreamUitls.parserStream(inputStream);
                       // System.out.println(stream);
                        JSONObject jsonObject = new JSONObject(stream);
                        mCode = jsonObject.getString("code");
                        mApkurl = jsonObject.getString("apkurl");
                        mDes = jsonObject.getString("des");
                        System.out.println("*******code"+mCode+"%%%%%%%%%"+getAppVesion());
                        if (mCode .equals(getAppVesion())) {
                           //相等不更新进入home
                            message.what = MSG_ENTER_HOME;
                            mHandler.sendMessage(message);
                        } else {
                            //发送消息不相等弹出对话框
                            System.out.println("########woshiduihu");
                            message.what = MSG_UPDATE_DIALOG;
                            mHandler.sendMessage(message);

                        }

                    }else{
                        System.out.println("连接网络失败");
                    }

                } catch (MalformedURLException e) {
                    message.what = MSG_SERVER_ERROR;
                    mHandler.sendMessage(message);
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    message.what = MSG_URL_ERROR;
                    mHandler.sendMessage(message);
                    e.printStackTrace();
                } catch (IOException e) {
                    message.what = MSG_IO_ERROR;
                    mHandler.sendMessage(message);
                    e.printStackTrace();
                } catch (JSONException e) {
                    message.what = MSG_JSON_ERROR;
                    mHandler.sendMessage(message);

                    e.printStackTrace();
                }


            }
        }.start();
    }

    /*
*
* 获取版本号
* */
    private String getAppVesion() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(getPackageName(), 0);
            //获取包名
            String packageName = info.packageName;
            String versionName = info.versionName;
            System.out.println("**************"+versionName);
           // String versionCode = String.valueOf(info.versionCode);

            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }


    }
}
