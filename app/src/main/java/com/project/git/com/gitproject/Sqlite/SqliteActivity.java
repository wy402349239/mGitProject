package com.project.git.com.gitproject.Sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;

public class SqliteActivity extends BaseActivity {

    private LinearLayout mLayout;
    private DbHelper mHelper;
    private SQLiteDatabase mDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_layout);
        setCj();
        mLayout = findViewById(R.id.activity_root_linear);
        initData();
        addView();
    }

    /**
     * 初始化数据库工具类
     */
    private void initData(){
        mHelper = new DbHelper(SqliteActivity.this);
        mDataBase = mHelper.getWritableDatabase();
    }

    /**
     * 添加view并注册监听
     */
    private void addView(){
        Button nButton = new Button(SqliteActivity.this);
        nButton.setPadding(0, 30, 0, 30);
        nButton.setText("Add Data");
        nButton.setTextColor(Color.BLACK);
        nButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 30);
        mLayout.addView(nButton, params);
        final TextView nText = new TextView(SqliteActivity.this);
        nText.setBackgroundColor(0x66CCCCCC);
        nText.setPadding(20, 20, 20, 20);
        nText.setTextColor(Color.BLACK);
        nText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        mLayout.addView(nText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues value = new ContentValues();
                value.put(DbHelper.NAME, "add");
                value.put(DbHelper.AGE, 0);
                mDataBase.insert(DbHelper.TABLE_NAME, null, value);//插入一条数据
                Cursor nCursor = mDataBase.query(DbHelper.TABLE_NAME, new String[]{DbHelper.NAME, DbHelper.AGE},
                        DbHelper.AGE + " > ?", new String[]{"-1"}, null, null, DbHelper.AGE + " desc");
                int nameIndex = nCursor.getColumnIndex(DbHelper.NAME);
                int ageIndex = nCursor.getColumnIndex(DbHelper.AGE);
                String nCurrent = "";
                while (nCursor.moveToNext()){//查询数据并重新拼接
                    nCurrent = nCurrent + "\r\n" + nCursor.getString(nameIndex) + " --- " + nCursor.getString(ageIndex);
                }
                nText.setText(nCurrent);//显示
            }
        });
    }
}
