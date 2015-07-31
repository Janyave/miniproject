package com.netease.ecos.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.ecos.R;
import com.netease.ecos.views.XListView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonageActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonageActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonageActivityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private XListView lv_personage_activity;
    private int personage_activity_num = 3;

    public void setPersonage_activity_num(int personage_activity_num) {
        this.personage_activity_num = personage_activity_num;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonageActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonageActivityFragment newInstance(String param1, String param2) {
        PersonageActivityFragment fragment = new PersonageActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PersonageActivityFragment() {
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
        View root = inflater.inflate(R.layout.fragment_personage_activity, container, false);

        lv_personage_activity = (XListView) root.findViewById(R.id.lv_personage_activity);
        lv_personage_activity.setAdapter(new MyAdapter(root.getContext()));

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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class MyAdapter extends BaseAdapter{

        private LayoutInflater mInflater = null;


        public MyAdapter(Context context){
            mInflater = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return personage_activity_num;
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
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_personage_activity, null);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.tv_personage_item_activity_title);
                holder.cover = (ImageView) convertView.findViewById(R.id.iv_personage_item_activity_cover);
                holder.date = (TextView) convertView.findViewById(R.id.tv_personage_item_activity_date);
                holder.tag = (TextView) convertView.findViewById(R.id.tv_personage_item_activity_tag);
                holder.location = (TextView) convertView.findViewById(R.id.tv_personage_item_activity_location);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            /** 填充数据 */
//            holder.cover.setBackgroundResource();
//            holder.title.setText();
//            holder.date.setText();
//            holder.tag.setText();
//            holder.location.setText();

            return convertView;

        }

        public final class ViewHolder {
            public TextView title;
            public TextView date;
            public ImageView cover;
            public TextView tag;
            public TextView location;

        }
    }
}
