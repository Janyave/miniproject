package com.netease.ecos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.netease.ecos.R;
import com.netease.ecos.model.ActivityModel;

import java.util.ArrayList;

/**
 * Created by Think on 2015/7/29.
 */
public class ContactListAdapter extends BaseAdapter {

    private Context mcontext;
    private LayoutInflater layoutInflater;
    private ArrayList<ActivityModel.ContactWay> contactWaysList;
    public static ActivityModel.ContactWay[] contactWays = new ActivityModel.ContactWay[]{ActivityModel.ContactWay.QQ, ActivityModel.ContactWay.QQ群, ActivityModel.ContactWay.微信, ActivityModel.ContactWay.电话};

    public ContactListAdapter(Context context) {
        this.mcontext = context;
        layoutInflater = LayoutInflater.from(mcontext);
        contactWaysList = new ArrayList<>();
    }

    public void addItem(ArrayList<ActivityModel.ContactWay> contactWayArrayList) {
        /*then, add a new contactWay in the list.
         * at last, execute the adapter.notifyDataSetChanged();
         */
        this.contactWaysList = contactWayArrayList;
        ActivityModel.ContactWay contactWay = ActivityModel.ContactWay.QQ;
        contactWay.setValue("");
        this.contactWaysList.add(contactWay);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactViewHolder contactViewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.new_contact_item, null);
            contactViewHolder = new ContactViewHolder();
            contactViewHolder.typeSpinner = (Spinner) convertView.findViewById(R.id.contactTypeSpinner);
            contactViewHolder.detailEditText = (EditText) convertView.findViewById(R.id.contactDetailEdTx);
            convertView.setTag(contactViewHolder);
        } else
            contactViewHolder = (ContactViewHolder) convertView.getTag();
        ArrayAdapter<ActivityModel.ContactWay> adapter = new ArrayAdapter<ActivityModel.ContactWay>(mcontext, android.R.layout.simple_list_item_1, contactWays);
        contactViewHolder.typeSpinner.setAdapter(adapter);
        contactViewHolder.detailEditText.setText(contactWaysList.get(position).getValue());
        for (int i = 0; i < contactWays.length; i++) {
            if (contactWaysList.get(position) == contactWays[i]) {
                contactViewHolder.typeSpinner.setSelection(i);
                break;
            }
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return contactWaysList.size();
    }

    class ContactViewHolder {
        Spinner typeSpinner;
        EditText detailEditText;
    }
}
