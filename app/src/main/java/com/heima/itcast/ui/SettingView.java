package com.heima.itcast.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heima.itcast.R;

/**
 * @author glsite.com
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class SettingView extends RelativeLayout {

    private TextView mTv_setting_title;
    private TextView mTv_setting_des;
    private CheckBox mCb_setting_check;
    private String mTitle;
    private String mDes_on;
    private String mDes_off;

    public SettingView(Context context) {
        super(context);
        init();

    }

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        mTitle = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "title");
        mDes_on = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "des_on");
        mDes_off = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "des_off");
    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //将布局填充到控件
        View view = View.inflate(getContext(), R.layout.settingview, this);
        mTv_setting_title = view.findViewById(R.id.tv_setting_title);
        mTv_setting_des = view.findViewById(R.id.tv_setting_des);
        mCb_setting_check = view.findViewById(R.id.cb_setting_check);


    }
    public void setTitle(String title){

        mTv_setting_title.setText(mTitle);
    }
    public void setDes(String des){

    }
    public void setCheck(boolean isChecked){
        if (isChecked){

            mTv_setting_des.setText(mDes_on);
        }else {
            mTv_setting_des.setText(mDes_off);
        }
        mCb_setting_check.setChecked(isChecked);
    }
    public boolean getIsChecked(){
        return mCb_setting_check.isChecked();
    }


}
