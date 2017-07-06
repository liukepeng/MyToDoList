package com.lkp.anylist;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.lkp.todolist.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_SECTION_NUMBER = "section_number";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private CalendarView calendar;
    private ImageButton calendarLeft;
    private TextView calendarCenter;
    private ImageButton calendarRight;
    private SimpleDateFormat format;

    private int pYear;
    private int pMouth;
    private int pDay;
    private int pHour;
    private int pMinute;
    private Date dDate;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(int sectionNumber) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.fragment_calendar, container, false);
        format = new SimpleDateFormat("yyyy/MM/dd");
        //获取日历控件对象
        calendar = (CalendarView)rootview.findViewById(R.id.calendar);
        calendar.setSelectMore(false); //单选

        calendarLeft = (ImageButton)rootview.findViewById(R.id.calendarLeft);
        calendarCenter = (TextView)rootview.findViewById(R.id.calendarCenter);
        calendarRight = (ImageButton)rootview.findViewById(R.id.calendarRight);
        try {
            //设置日历日期
            Date date = format.parse("2015/01/01");
            calendar.setCalendarData(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
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

        //设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）

        calendar.setOnItemClickListener(new CalendarView.OnItemClickListener() {

            @Override
            public void OnItemClick(Date selectedStartDate,
                                    Date selectedEndDate, Date downDate) {
                dDate=downDate;
                if(calendar.isSelectMore()){
                    Toast.makeText(getActivity(), format.format(selectedStartDate) + "到" + format.format(selectedEndDate), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), format.format(downDate), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Calendar calendar=Calendar.getInstance();
        pYear=calendar.get(Calendar.YEAR);
        pMouth=calendar.get(Calendar.MONTH);
        pDay=calendar.get(Calendar.DAY_OF_MONTH);




        TimePicker timePicker=(TimePicker)rootview.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        pHour=calendar.get(Calendar.HOUR_OF_DAY);
        pMinute=calendar.get(Calendar.MINUTE);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
            public  void onTimeChanged(TimePicker view,int hourOfDay,int minute){
                pHour=hourOfDay;
                pMinute=minute;
            }

        });


        ImageButton newadd= (ImageButton) rootview.findViewById(R.id.newadd);
       View.OnClickListener listener0 = null;
        listener0 = new View.OnClickListener() {
            public void onClick(View v) {

                    Intent intent=new Intent(getActivity(),SecondActivity.class);
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
                    startActivity(intent);

            }
        };

        newadd.setOnClickListener(listener0);


        // Inflate the layout for this fragment
      return   rootview;
    }

    private String format(int x){
        String s=""+x;
        if (s.length()==1) s="0"+s;
        return s;
    }

    public final static String SHIJIAN = "com.lkp.todolost.DAT-EPICKER";


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((FirstActivity)activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
