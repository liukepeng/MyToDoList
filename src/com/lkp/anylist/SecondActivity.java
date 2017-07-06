package com.lkp.anylist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.lkp.todolist.R;


public class SecondActivity extends Activity {
    private TextView myView;//文字显示
    private RadioGroup mRadioGroup;//RadioGroup对象
    private RadioButton mRadioBtn1, mRadioBtn2;//单选按钮
    private boolean isNotify;
    private String shijian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent=getIntent();
         shijian=intent.getStringExtra(CalendarActivity.SHIJIAN);
        TextView textView=(TextView)findViewById(R.id.textView);
        textView.setText(shijian);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public final static String EXTRA_MESSAGE = "com.lkp.todolost.MESSAGE";
    public final static String PRIORITY = "com.lkp.todolost.CHECKEDRADIO";
    public void add(View view) {
        Intent intent = new Intent(this, FirstActivity.class);

        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString().trim();


        //调用数据库的添加方法
        DBHelper helper = new DBHelper(SecondActivity.this);


       //获取事件优先级button的信息然后转换成String型
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.myGroup);
        int buttonID = radioGroup.getCheckedRadioButtonId();

        //获取事件分类按钮的信息
        RadioGroup radioGroup1 =(RadioGroup)findViewById(R.id.radioGroup);
        int radioGroupButtonID=radioGroup1.getCheckedRadioButtonId();


//        String shijian=intent.getStringExtra(CalendarActivity.SHIJIAN);
//        TextView textView=(TextView)findViewById(R.id.textView);
//        textView.setText(shijian);



//        intent.putExtra(EXTRA_MESSAGE, message);
//        intent.putExtra(PRIORITY, text);
if (message.equals("")){

        AlertDialog.Builder builder=new AlertDialog.Builder(SecondActivity.this);
        builder.setTitle("您有非法操作").setMessage("请检查内容是否输入")
                .setNegativeButton("好的，我知道了！", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();


}

     else   if (buttonID==-1)
//                ||((shijian==null)&&(isNotify))||(radioGroupButtonID==-1))
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(SecondActivity.this);
            builder.setTitle("您有非法操作").setMessage("请检查事件紧急情况按钮是否选上")
                    .setNegativeButton("好的，我知道了！", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
else if (shijian==null){
            AlertDialog.Builder builder= new AlertDialog.Builder(SecondActivity.this);
            builder.setTitle("您有非法操作").setMessage("请检查时间是否有设置")
                    .setNegativeButton("好的，我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
          builder.create().show();
        }
         else if (radioGroupButtonID==-1){
            AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
            builder.setTitle("您有非法操作").setMessage("请检查分类按钮是否选上")
                    .setNeutralButton("好的，我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                        }
                    });
            builder.create().show();
        }

else {
            RadioButton radioButton=  (RadioButton)findViewById(buttonID);
            String text=radioButton.getText().toString();
            RadioButton radioButton1=(RadioButton)findViewById(radioGroupButtonID);
            String fenLeiContent=radioButton1.getText().toString();

            helper.insertData(message, text, shijian, isNotify, fenLeiContent);

//
//            setResult(RESULT_OK, intent);
//            startActivity(intent);

    try {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container2, ContentFragment.newInstance(fenLeiContent))
                .commit();
    } catch (Exception e) {
        e.printStackTrace();
    }


            Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
        }

        }
public void shezhishijian(View view) {
//    EditText editText = (EditText) findViewById(R.id.edit_message);
//    cotent = editText.getText().toString();
//    RadioGroup radioGroup=(RadioGroup)findViewById(R.id.myGroup);



//    Bundle bd = new Bundle();
//    bd.putString("EditText", cotent);


    Intent intent = new Intent(this, CalendarActivity.class);

//    intent.putExtras(bd);
    startActivityForResult(intent,200);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode==200&&resultCode==RESULT_OK){

          shijian=data.getStringExtra(CalendarActivity.SHIJIAN);
          TextView textView=(TextView)findViewById(R.id.textView);
          textView.setText(shijian);
//          EditText editText = (EditText) findViewById(R.id.edit_message);
//          cotent=data.getStringExtra("EditText");
//          editText.setText(cotent);


      }

    }

public  void btnSwitch(View view){
    Switch aSwitch = (Switch) findViewById(R.id.switch1);
     isNotify=aSwitch.isChecked();
}

}