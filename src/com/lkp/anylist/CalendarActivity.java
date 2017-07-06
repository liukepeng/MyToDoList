package com.lkp.anylist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lkp.todolist.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CalendarActivity extends Activity {
    private int pYear;
    private int pMouth;
    private int pDay;
    private int pHour;
    private int pMinute;
    private CalendarView calendar;
    private ImageButton calendarLeft;
    private TextView calendarCenter;
    private ImageButton calendarRight;
    private SimpleDateFormat format;
    private Date dDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar);
//        DatePicker datePicker=(DatePicker)findViewById(R.id.datePicker);
//        Calendar calendar=Calendar.getInstance();
//        pYear=calendar.get(Calendar.YEAR);
//       pMouth=calendar.get(Calendar.MONTH);
//        pDay=calendar.get(Calendar.DAY_OF_MONTH);

//        datePicker.init(pYear,pMouth,pDay, new DatePicker.OnDateChangedListener(){
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                pYear=year;
//                pMouth=monthOfYear;
//                pDay=dayOfMonth;
//
//            }
//        });
        format = new SimpleDateFormat("yyyy/MM/dd");
        //获取日历控件对象
        calendar = (CalendarView)findViewById(R.id.calendar);
        calendar.setSelectMore(false); //单选

        calendarLeft = (ImageButton)findViewById(R.id.calendarLeft);
        calendarCenter = (TextView)findViewById(R.id.calendarCenter);
        calendarRight = (ImageButton)findViewById(R.id.calendarRight);
        try {
            //设置日历日期
            Date date = format.parse("2015/01/01");
            calendar.setCalendarData(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //获取日历中年月 ya[0]为年，ya[1]为月
        String[] ya = calendar.getYearAndmonth().split("/");
        calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
        calendarLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击上一月 同样返回年月
                String leftYearAndmonth = calendar.clickLeftMonth();
                String[] ya = leftYearAndmonth.split("/");
                calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
            }
        });

        calendarRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击下一月
                String rightYearAndmonth = calendar.clickRightMonth();
                String[] ya = rightYearAndmonth.split("/");
                calendarCenter.setText(ya[0]+"年"+ya[1]+"月");
            }
        });

        //设置控件监听，可以监听到点击的每一天
        calendar.setOnItemClickListener(new CalendarView.OnItemClickListener() {

            @Override
            public void OnItemClick(Date selectedStartDate,
                                    Date selectedEndDate, Date downDate) {
                dDate=downDate;
                if(calendar.isSelectMore()){
                    Toast.makeText(getApplicationContext(), format.format(selectedStartDate) + "到" + format.format(selectedEndDate), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), format.format(downDate), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Calendar calendar=Calendar.getInstance();
        TimePicker timePicker=(TimePicker)findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        pHour=calendar.get(Calendar.HOUR_OF_DAY);
        pMinute=calendar.get(Calendar.MINUTE);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
            public  void onTimeChanged(TimePicker view,int hourOfDay,int minute){
                pHour=hourOfDay;
                pMinute=minute;
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
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



    private String format(int x){
       String s=""+x;
        if (s.length()==1) s="0"+s;
        return s;
    }
    public final static String SHIJIAN = "com.lkp.todolost.DAT-EPICKER";
    public void btnNewAdd(View view){
        Intent  intent =getIntent();
//        DatePicker datePicker=(DatePicker)findViewById(R.id.datePicker);
//        TimePicker timePicker=(TimePicker)findViewById(R.id.timePicker);
//        String shijian=(new StringBuilder().append(pYear).append("/").append(format(pMouth + 1))
//        .append("/").append(format(pDay)).append(" ")
//                .append(format(pHour)).append(":").append(format(pMinute))).toString();

        if(dDate==null){
            SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            String shijian = (new StringBuilder().append(df.format(date)).append(" ")
                    .append(format(pHour)).append(":").append(format(pMinute))).toString();
            intent.putExtra(SHIJIAN,shijian);
        }

        else {
            String shijian = (new StringBuilder().append(format.format(dDate)).append(" ")
                    .append(format(pHour)).append(":").append(format(pMinute))).toString();
            intent.putExtra(SHIJIAN, shijian);
        }


//        intent.putExtra(SHIJIAN,shijian);

//       startActivity(intent);
        CalendarActivity.this.setResult(RESULT_OK, intent);
        CalendarActivity.this.finish();


    }
}

