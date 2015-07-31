package com.netease.ecos.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader;
import com.netease.ecos.R;
import com.netease.ecos.activity.PersonageDetailActivity;
import com.netease.ecos.model.User;
import com.netease.ecos.model.UserDataService;
import com.netease.ecos.utils.RoundImageView;
import com.netease.ecos.utils.SDImageCache;

import java.util.zip.Inflater;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */

/***
 * 侧滑栏
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the ActivityModel).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;  //父控件
    /***
     * 侧边栏Layout
     */
    private View mDrawerView;

    /***
     * 侧边栏ListView
     */
    private ListView mDrawerListView;


    /*** 父控件的FragmentContainer */
    private View mFragmentContainerView;

    /*** 当前选中的item的序号，从0开始 */
    private int mCurrentSelectedPosition = 0;

    private boolean mFromSavedInstanceState;

    private boolean mUserLearnedDrawer;

    private Button btPersonageDetail = null;
    private Button btPersonageSetting = null;

    private UserDataService mUserDataService;
    private User mUserData;


    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
//        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    /**
     * 侧边栏Layout
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerView=(View)inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView = (ListView)mDrawerView.findViewById(R.id.lv_list);


        btPersonageDetail = (Button) mDrawerView.findViewById(R.id.bt_personage_name);
        btPersonageSetting = (Button) mDrawerView.findViewById(R.id.bt_personage_setting);

        btPersonageDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonageDetailActivity.class);
                startActivity(intent);
            }
        });

        btPersonageSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO on setting btn click
            }
        });

        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });


        //给mDrawerListView进行数据绑定
//        mDrawerListView.setAdapter(new ArrayAdapter<String>(getActionBar().getThemedContext(), android.R.layout.simple_list_item_activated_1, android.R.id.text1, new String[]{getString(R.string.title_section1), getString(R.string.title_section2), getString(R.string.title_section3), getString(R.string.title_section4),}));
        mDrawerListView.setAdapter(new MyAdapter(mDrawerView.getContext()));
        //设置侧边栏默认记录的选项
//        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

        //初始化用户信息
        initUserData(mDrawerView);

        return mDrawerView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.mipmap.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host ActivityModel */
                mDrawerLayout,                    /* DrawerLayout object */
                R.mipmap.ic_launcher,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * 外部接口显示NavigationDrawer
     * 设置隐藏ActionBar
     * 因此增加此接口
     */
    public void openNavigationDrawer(){
        //打开NavigationDrawer
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    /**
     * 外部接口关闭NavigationDrawer
     */
    public void closeNavigationDrawer(){
        //关闭NavigationDrawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * 点击之后关闭侧边栏，回调父类接口
     * @param position
     */
    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("ActivityModel must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("MSG", "test for on detach!");
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
//            inflater.inflate(R.menu.global, menu);
//            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyAdapter extends BaseAdapter{
        private LayoutInflater mInflater = null;

        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 4;
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
                convertView = mInflater.inflate(R.layout.item_personage_info, null);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.item_personage_title);
                holder.icon = (ImageView) convertView.findViewById(R.id.item_personage_icon);
                holder.warn_num = (TextView) convertView.findViewById(R.id.item_personage_warn_num);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            switch (position){
                case 0:
                    holder.icon.setImageResource(R.drawable.img_setting_default);
                    holder.title.setText(R.string.title_section1);
                    //if()//判断是否有消息提醒
                    holder.warn_num.setText("1");

                    break;
                case 1:
                    holder.icon.setImageResource(R.drawable.img_setting_default);
                    holder.title.setText(R.string.title_section2);
                    //if()//判断是否有消息提醒
                    holder.warn_num.setText("12");
                    break;
                case 2:
                    holder.icon.setImageResource(R.drawable.img_setting_default);
                    holder.title.setText(R.string.title_section3);
                    //if()//判断是否有消息提醒
                    holder.warn_num.setText("3");
                    break;
                case 3:
                    holder.icon.setImageResource(R.drawable.img_setting_default);
                    holder.title.setText(R.string.title_section4);
                    //if()//判断是否有消息提醒
                    holder.warn_num.setText("4");
                    break;
            }
            return convertView;

        }


        public final class ViewHolder {

            public ImageView icon;
            public TextView title;
            public TextView warn_num; //may not need

        }
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }

    private void initUserData(View v){
        mUserDataService = UserDataService.getSingleUserDataService(v.getContext());
        mUserData = mUserDataService.getUser();

        RoundImageView user_avatar = (RoundImageView) v.findViewById(R.id.iv_personage_portrait);
        Button user_name = (Button) v.findViewById(R.id.bt_personage_name);
        RoundImageView user_gender = (RoundImageView) v.findViewById(R.id.riv_personage_gender);
        TextView user_attention = (TextView) v.findViewById(R.id.tv_personage_attention);
        TextView user_fans = (TextView) v.findViewById(R.id.tv_personage_fans);
        TextView user_description = (TextView) v.findViewById(R.id.tv_personage_description);

        //设置默认图片
        user_avatar.setDefaultImageResId(R.drawable.img_default);
        //设置加载出错图片
        user_avatar.setErrorImageResId(R.drawable.img_default);
        RequestQueue queue = Volley.newRequestQueue(v.getContext());
        ImageLoader.ImageCache imageCache = new SDImageCache();
        ImageLoader imageLoader = new ImageLoader(queue, imageCache);
        user_avatar.setImageUrl(mUserData.avatarUrl, imageLoader);

        user_name.setText(mUserData.nickname);
        if(mUserData.gender == User.Gender.女) {
            user_gender.setBackgroundResource(R.drawable.img_gender_famale);
        }else {
            user_gender.setBackgroundResource(R.drawable.img_gender_male);
        }
        user_attention.setText("关注数：" + mUserData.followOtherNum);
        user_fans.setText("粉丝数：" + mUserData.fansNum);
        user_description.setText(mUserData.characterSignature);

    }
}
