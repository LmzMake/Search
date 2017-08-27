package com.bwie.search.home.view.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.search.R;
import com.bwie.search.home.modul.utils.MyListView;
import com.bwie.search.home.modul.utils.RecordSQLiteOpenHelper;
import com.bwie.search.home.modul.utils.ResultBean;
import com.bwie.search.home.modul.utils.ToastUtils;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.widget.Toast.makeText;

public class SearchActivity extends AppCompatActivity {
    //天气接口
    public String url = "http://v.juhe.cn/weather/index?format=2&cityname=%E5%8C%97%E4%BA%AC&key=6256cfe655673f2122227b2e6d3fa4ec";
    //输入框
    private EditText edittext;
    //    设置
    private ImageView setting;
    //QQ登陆
    private ImageView qq;
    //搜索
    private ImageView search;
    //数据库保存搜索历史
    private TextView tv_tip;
    private MyListView listView;
    private TextView tv_clear;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    private SQLiteDatabase db;
    private BaseAdapter adapter;
    private Button xunfei;
    //调用QQ接口的方法获取QQ信息
    private List<String> mList = new ArrayList<String>();
    private boolean zt1;
    private SharedPreferences qq2;
    private boolean qq1;
    private ImageView phone;
    private ImageView weibo;
    private TextView mT1;
    private ImageView TouX;
    public Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mGson = new Gson();
        initView();
        //qq登陆
        initQQ();
        //数据库保存历史记录
        initSqlit();
        initSqlite();
        //讯飞语音
        xunfei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XFvoice();
            }
        });
    }

    /**
     * 清除历史记录
     */
    private void initSqlite() {
        // 清空搜索历史
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });
        // 搜索框的键盘搜索键点击回调
        edittext.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    boolean hasData = hasData(edittext.getText().toString().trim());
                    if (!hasData) {
                        insertData(edittext.getText().toString().trim());
                        queryData("");
                    }
                    // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    Toast toast = makeText(SearchActivity.this, "已经保存到历史记录", Toast.LENGTH_SHORT);
                    ToastUtils.showMyToast(toast, 80);
                }
                return false;
            }
        });
        // 搜索框的文本变化实时监听
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tv_tip.setText("搜索历史");
                } else {
                    tv_tip.setText("搜索结果");
                }
                String tempName = edittext.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                edittext.setText(name);
                Toast toast = makeText(SearchActivity.this, name, Toast.LENGTH_SHORT);
                ToastUtils.showMyToast(toast, 80);

                Intent intent = new Intent(SearchActivity.this, ListShearActivity.class);
                intent.putExtra("aaa", name);
                startActivity(intent);
                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
            }
        });
        // 第一次进入查询所有的历史记录
        queryData("");
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空历史记录的数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    /**
     * 初始化历史记录的控件
     */
    private void initSqlit() {
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        listView = (MyListView) findViewById(R.id.listView);
        tv_clear = (TextView) findViewById(R.id.tv_clear);
    }

    /**
     * 初始化控件 / 跳转设置界面 /  跳转搜索界面
     */
    private void initView() {
        edittext = (EditText) findViewById(R.id.edittext);
        setting = (ImageView) findViewById(R.id.setting);
        xunfei = (Button) findViewById(R.id.xunfei);
        phone = (ImageView) findViewById(R.id.phone);
        weibo = (ImageView) findViewById(R.id.weibo);
        mT1 = (TextView) findViewById(R.id.nc);
        TouX = (ImageView) findViewById(R.id.TouX);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 点击搜索跳转webview
         */
        search = (ImageView) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sreach = edittext.getText().toString().trim();
                if (sreach.equals("")) {
                    //判断输入框内容是空如果是提示用户输入搜索内容
                    Toast toast = makeText(SearchActivity.this, "请填写搜索内容", Toast.LENGTH_SHORT);
                    //调用ToastUtils的showMytoast方法设置Toast时间
                    ToastUtils.showMyToast(toast, 80);
                } else {
                    //输入框内容不为空跳转webview
                    Intent intent = new Intent(SearchActivity.this, WebActivity.class);
                    intent.putExtra("sreach", sreach);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * QQ登陆
     */
    private void initQQ() {

        qq = (ImageView) findViewById(R.id.qq);
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, QQLoginActivity.class));
                finish();
            }
        });

        //回调用户信息 完成QQ登陆
        SharedPreferences zt = SearchActivity.this.getSharedPreferences("ZT", MODE_PRIVATE);
        zt1 = zt.getBoolean("zt", false);
        qq2 = SearchActivity.this.getSharedPreferences("QQ", MODE_PRIVATE);
        qq1 = qq2.getBoolean("状态", false);
        if (qq1 == true) {
            String touxiang = qq2.getString("头像", null);

            //获取QQ头像并设置成圆形头像
            Glide.with(this).load(touxiang)
                    .bitmapTransform(new CropCircleTransformation(this))
                    .into(TouX);
            //获取QQ昵称
            String nc = qq2.getString("昵称", null);
            //获取昵称并显示在TextView上
            mT1.setText(nc);
            qq.setVisibility(View.INVISIBLE);
            phone.setVisibility(View.INVISIBLE);
            weibo.setVisibility(View.INVISIBLE);
        } else if (zt1 == true) {
            String sj = zt.getString("sj", null);
            mT1.setText(sj);
            Toast toast = makeText(SearchActivity.this, "已登录", Toast.LENGTH_SHORT);
            ToastUtils.showMyToast(toast, 80);
            phone.setVisibility(View.INVISIBLE);
            weibo.setVisibility(View.INVISIBLE);
            qq.setVisibility(View.INVISIBLE);

        } else if (zt1 == false) {
            phone.setVisibility(View.VISIBLE);
            weibo.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 讯飞语音
     */
    private void XFvoice() {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, null);
        //2.设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        mDialog.setListener(mRecognizerDialogListener);
        //4.显示dialog，接收语音输入
        mDialog.show();
    }

    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        /**
         * @param recognizerResult 语音识别结果
         * @param b true表示是标点符号
         */
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {

            if (b) {
                return;
            }
            ResultBean resultBean = mGson.fromJson(recognizerResult.getResultString(), ResultBean.class);
            List<ResultBean.WsBean> ws = resultBean.getWs();
            String w = "";
            for (int i = 0; i < ws.size(); i++) {
                List<ResultBean.WsBean.CwBean> cw = ws.get(i).getCw();
                for (int j = 0; j < cw.size(); j++) {
                    w += cw.get(j).getW();
                }
            }
            //把语音添加到EditText上
            edittext.setText(w);
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    };
    /**
     * 再按一次退出APP
     */
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}