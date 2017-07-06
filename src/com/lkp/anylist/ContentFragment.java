package com.lkp.anylist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lkp.todolist.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_CONTENT_TYPE = "content_type";





    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static final String G_TEXT = "g_text";

    private static final String C_TEXT1 = "c_text1";

    List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

    ExAdapter adapter;
    ExpandableListView exList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContentFragment.
     */
    public static ContentFragment newInstance(int sectionNumber) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public static ContentFragment newInstance(String contentType) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT_TYPE,contentType );
        fragment.setArguments(args);
        return fragment;
    }


    public ContentFragment() {
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
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_content, container, false);
        final DBHelper helper = new DBHelper(getActivity());


        for (int i = 0; i < 7; i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            switch (i) {
                case 0:
                    curGroupMap.put(G_TEXT, "个人");
                    break;
                case 1:
                    curGroupMap.put(G_TEXT, "工作");
                    break;
                case 2:
                    curGroupMap.put(G_TEXT, "学习");
                    break;
                case 3:
                    curGroupMap.put(G_TEXT,"购物");
                    break;
                case 4:
                    curGroupMap.put(G_TEXT,"旅行");
                    break;
                case 5:
                    curGroupMap.put(G_TEXT,"家庭");
                    break;
                case 6:
                    curGroupMap.put(G_TEXT,"想看的电影");
                    break;

            }

            Cursor cursor = helper.query();
            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        Map<String, String> curChildMap = new HashMap<String, String>();

                        int numContentColumn = cursor.getColumnIndex("content");
                        String contentData = cursor.getString(numContentColumn);

                        int numIdColumn =cursor.getColumnIndex("_id");
                        int idData = cursor.getInt(numIdColumn);

                        int numPriorityColumn = cursor.getColumnIndex("priority");
                        String priorityData = "";
                        if (numPriorityColumn != -1) {
                            priorityData = cursor.getString(numPriorityColumn);
                        }
//                            stringBuilder.append(priorityData + ":" + contentData + "\n");

                        int numFenLei = cursor.getColumnIndex("fenLei");
                        String fenlei = cursor.getString(numFenLei);
                        if((i==0&&fenlei.equals("个人"))||(i==1&&fenlei.equals("工作"))
                                ||(i==2&&fenlei.equals("学习"))||(i==3&&fenlei.equals("购物"))
                                ||( i==4&&fenlei.equals("旅行"))||(i==5&&fenlei.equals("家庭"))
                               ||(i==6&&fenlei.equals("想看的电影")) )
                                 {
                            curChildMap.put(C_TEXT1, priorityData + ":" + contentData + "\n");
                            curChildMap.put("ID", idData + "");
                            children.add(curChildMap);
                        }
                    } while (cursor.moveToNext());
                }


            }
            cursor.close();
            childData.add(children);
        }
        adapter=new ExAdapter(getActivity());
        exList = (ExpandableListView) rootview.findViewById(R.id.list);
        exList.setAdapter(adapter);
        exList.setGroupIndicator(null);
        exList.setDivider(null);




        if( getArguments().getString(ARG_CONTENT_TYPE)==null){

            return  rootview;
        }
        else {
            switch (getArguments().getString(ARG_CONTENT_TYPE)) {
                case "个人":
                    exList.expandGroup(0);
                    break;
                case "工作":
                    exList.expandGroup(1);
                    break;
                case "学习":
                    exList.expandGroup(2);
                    break;
                case "购物":
                    exList.expandGroup(3);
                    break;
                case "旅行":
                    exList.expandGroup(4);
                    break;
                case "家庭":
                    exList.expandGroup(5);
                    break;
                case "想看的电影":
                    exList.expandGroup(6);
                    break;

            }

        }




        return  rootview;
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
        //instanceof测试它左边的对象是否是它右边的类的实例
        if(activity instanceof FirstActivity) {
            ((FirstActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
        else {

        }
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


    class ExAdapter extends BaseExpandableListAdapter {
        Activity exlistview;
        //构造函数
        public ExAdapter(Activity elv) {
            super();
            exlistview = elv;
        }
        //获取一个显示的视图给定组
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listview, null);
            }
            TextView title = (TextView) view.findViewById(R.id.content_001);
            title.setText(getGroup(groupPosition).toString());

            ImageView image=(ImageView) view.findViewById(R.id.tubiao);
            if(isExpanded)
                image.setBackgroundResource(R.drawable.btn_browser2);
            else image.setBackgroundResource(R.drawable.btn_browser);

            return view;
        }

        //获取给定组相关的数据
        public Object getGroup(int groupPosition) {
            return groupData.get(groupPosition).get(G_TEXT).toString();
        }
        //获取第一级列表的列数
        public int getGroupCount() {
            return groupData.size();
        }
        //获取一个视图显示在给定的组的孩子的数据
        public View getChildView(final int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                //填充视图
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.child, null);
            }
            String text = childData.get(groupPosition).get(childPosition).get(C_TEXT1).toString();
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            checkBox.setText(text);

            String id = childData.get(groupPosition).get(childPosition).get("ID");
            final int idInt=Integer.parseInt(id);
            CompoundButton.OnCheckedChangeListener myListener = new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkBox.isChecked()) {

                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                        builder.setTitle("删除对话框").setMessage("是否删除？").setPositiveButton("是",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        final DBHelper helper = new DBHelper(getActivity());
                                        helper.deleteData(idInt);
                                        List<Map<String, String>> list = childData.get(groupPosition);
                                        list.remove(childPosition);
                                        adapter.notifyDataSetChanged();
                                        checkBox.setChecked(false);
                                        Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_SHORT).show();
                                    }
                                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //对话框消失
                                dialog.dismiss();
                                checkBox.setChecked(false);
                            }
                        });
                        builder.create().show();


                    }
                }
            };
            checkBox.setOnCheckedChangeListener(myListener);

            return view;
        }
        //获取给定组的孩子的ID
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }
        //获取与孩子在给定的组相关的数据
        public Object getChild(int groupPosition, int childPosition) {
            return childData.get(groupPosition).get(childPosition).get(C_TEXT1).toString();
        }

        //获取组在给定的位置编号
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        //获取指定组中孩子的数量
        public int getChildrenCount(int groupPosition) {
            return childData.get(groupPosition).size();
        }
        //**************************************
        public boolean hasStableIds() {
            return true;
        }
        //是否可以被选择
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

}
