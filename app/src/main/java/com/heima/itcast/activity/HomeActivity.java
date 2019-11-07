package com.heima.itcast.activity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.heima.itcast.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        GridView gv = findViewById(R.id.gv_home_gridview);
        gv.setAdapter(new MyAdapter());
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                //手机防盗
                        break;
                    case 1:
                //通讯卫士
                        break;
                    case 2:
                //软件管理
                        break;
                    case 3:
                        //进程管理

                        break;
                    case 4:
                        //流量统计

                        break;
                    case 5:
                    //手机杀毒
                        break;
                    case 6:
                    //缓存管理
                        break;
                    case 7:
                        //高级工具

                        break;
                    case 8:
                        //设中心
                        Intent itent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(itent);
                        break;


                }
            }
        });
    }

    private class MyAdapter extends BaseAdapter{

        int[] imageId = { R.mipmap.safe, R.mipmap.callmsgsafe, R.mipmap.app,
                R.mipmap.taskmanager, R.mipmap.netmanager, R.mipmap.trojan,
                R.mipmap.sysoptimize, R.mipmap.atools, R.mipmap.settings };
        String[] names = { "手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理",
                "高级工具", "设置中心" };


        @Override
        public int getCount() {
            return imageId.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.item_home, null);

            ImageView iv = view.findViewById(R.id.iv_itemhome_imagview);
            iv.setImageResource(imageId[position]);

            TextView tv = view.findViewById(R.id.tv_itemhome_name);
            tv.setText(names[position]);

            return view;
        }


    }
}
