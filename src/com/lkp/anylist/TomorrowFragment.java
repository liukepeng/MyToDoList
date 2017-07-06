package com.lkp.anylist;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lkp.todolist.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TomorrowFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TomorrowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TomorrowFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_SECTION_NUMBER = "section_number";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    private ListAdapter adapter;
    private List<TodoInfo> PriorityDataContentData =new ArrayList<TodoInfo>();

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment TomorrowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TomorrowFragment  newInstance(int sectionNumber) {
        TomorrowFragment fragment = new TomorrowFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TomorrowFragment() {
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
        View rootview = inflater.inflate(R.layout.fragment_tomorrow,container,false);
        SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");
        final DBHelper helper = new DBHelper(getActivity());
        Cursor cursor = helper.query();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int timeColumn = cursor.getColumnIndex("time");
                    String timeData = cursor.getString(timeColumn);

                    Calendar calendar=Calendar.getInstance();
                    calendar.add(Calendar.DATE,1);
                    Date date = calendar.getTime();

                    if (timeData.substring(0,10).equals(df.format(date))){
                        int numContentColumn = cursor.getColumnIndex("content");
                        String contentData = cursor.getString(numContentColumn);


                        int numIdColumn =cursor.getColumnIndex("_id");
                        int idData = cursor.getInt(numIdColumn);

                        int numPriorityColumn = cursor.getColumnIndex("priority");

                        String priorityData = "";
                        if (numPriorityColumn != -1) {
                            priorityData = cursor.getString(numPriorityColumn);
                        }
                        String s=priorityData+":"+contentData;
                        TodoInfo todoInfo=new TodoInfo();
                        todoInfo.setContent(s);
                        todoInfo.setId(idData);
                        PriorityDataContentData.add(todoInfo);

                    }


                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        listView=(ListView)rootview.findViewById(R.id.listView);
        adapter = new ListAdapter<TodoInfo>(getActivity(),R.layout.simplecheckbox, PriorityDataContentData);


        listView.setAdapter(adapter);
        return rootview;
    }

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
