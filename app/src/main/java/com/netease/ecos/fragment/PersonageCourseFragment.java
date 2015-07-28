package com.netease.ecos.fragment;

import android.app.Activity;

import android.app.ListActivity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import com.netease.ecos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonageCourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonageCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonageCourseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView lv_schedule;
    private ArrayList<HashMap<String, Object>> listItem;
    private int schedule_set_num = 2;
    private TextView tv_schedule = null;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonageCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonageCourseFragment newInstance(String param1, String param2) {
        PersonageCourseFragment fragment = new PersonageCourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PersonageCourseFragment() {
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

    public void setSechedule_set_num(int list_num){
        schedule_set_num = list_num;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_personage_course, container, false);

        lv_schedule = (ListView) root.findViewById(R.id.lv_schedule_set);
        tv_schedule = (TextView) root.findViewById(R.id.tv_schedule_set);
        MyAdapter adapter = new MyAdapter(root.getContext());
        lv_schedule.setAdapter(adapter);
        tv_schedule.setText("课程集("+schedule_set_num+")");
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private ArrayList<HashMap<String, Object>> getDate(){

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < schedule_set_num; i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "第"+i+"个课程");
            map.put("img", R.drawable.img_default);
            listItem.add(map);
        }
        return listItem;

    }

    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return getDate().size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_schedule_set, null);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.personage_schedule_set_title);
                holder.cover = (ImageView) convertView.findViewById(R.id.personage_schedule_set_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.cover.setBackgroundResource((Integer)getDate().get(position).get("img"));
            holder.title.setText(getDate().get(position).get("ItemTitle").toString());

            return convertView;

        }


        public final class ViewHolder {
            public TextView title;
            public ImageView cover;
        }
    }

}

