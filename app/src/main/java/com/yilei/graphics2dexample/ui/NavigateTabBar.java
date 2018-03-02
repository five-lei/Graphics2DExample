package com.yilei.graphics2dexample.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yilei.graphics2dexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lei on 2018/2/28.
 * 自定义底部tab，动态添加对应的fragment,并存在List数组中
 */

public class NavigateTabBar extends LinearLayout implements View.OnClickListener{
    private static final String KEY_CURRENT_KEY = "com.lei.currentTag";

    private ColorStateList mNormalTextColor;//未选中tab得text颜色
    private ColorStateList mSelectTextColor;//选中得tab得text颜色
    private int mMainContainerLayoutId;//tab每个item的layoutId
    private float mTabTextSize;//tab title字体大小
    private int mTabVisibleCount = 0;//tab item可见个数

    private List<ViewHolder> mViewHolderList;//用于存储tab每个item的属性
    private FragmentActivity mFragmentActivity;//获取此容器所对应的Activity上下文

    private String mCurrentTag;//当前item的标签
    private String mRestoreTag;//之前存入的item的标签
    private int mCurrentSelectIndex;//当前tab的下标
    private int mDefaultSelectIndex = 0;//默认选中的下标

    private OnTabSelectedListener onTabSelectedListener;

    public NavigateTabBar(Context context) {
        this(context, null);
    }

    public NavigateTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigateTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigateTabBar);
        ColorStateList tabTextColor = typedArray.getColorStateList(R.styleable.NavigateTabBar_navigateTabTextColor);
        ColorStateList tabSelectTextColor = typedArray.getColorStateList(R.styleable.NavigateTabBar_navigateTabSelectTextColor);
        mMainContainerLayoutId = typedArray.getResourceId(R.styleable.NavigateTabBar_containerId, 0);
        mTabTextSize = typedArray.getDimensionPixelSize(R.styleable.NavigateTabBar_navigateTabTextSize, 0);

        mNormalTextColor = (tabTextColor !=null ? tabTextColor : context.getResources().getColorStateList(R.color.tab_normal_text));
        if(tabSelectTextColor != null){
            mSelectTextColor = tabSelectTextColor;
        }else{
            mSelectTextColor = context.getResources().getColorStateList(R.color.tab_select_text);
        }

        typedArray.recycle();
        mViewHolderList = new ArrayList<>();
    }

    /**
     * 动态添加tab的item
     * @param fragmentClass
     * @param tabParams
     */
    public void addTab(Class fragmentClass, TabParams tabParams){
        int defaultLayout = R.layout.item_navigage_tab;

        if(TextUtils.isEmpty(tabParams.titles)){
            tabParams.titles = getContext().getString(tabParams.titleStringResId);
        }

        //获取每个item的布局
        View view = LayoutInflater.from(getContext()).inflate(defaultLayout, null);
        view.setFocusable(true);

        ViewHolder holder = new ViewHolder();
        holder.tag = tabParams.titles;
        holder.tabParams = tabParams;
        holder.tabImage = (ImageView) view.findViewById(R.id.tab_icon);
        holder.tabTitle = (TextView) view.findViewById(R.id.tab_title);
        holder.tabIndex = mViewHolderList.size();
        holder.fragmentClass = fragmentClass;

        if(TextUtils.isEmpty(tabParams.titles)){
            holder.tabTitle.setVisibility(View.INVISIBLE);
        }else{
            holder.tabTitle.setText(tabParams.titles);
        }

        if(mTabTextSize != 0){
            holder.tabTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
        }

        if(mNormalTextColor != null){
            holder.tabTitle.setTextColor(mNormalTextColor);
        }

        if(tabParams.backGroundColor > 0){
            view.setBackgroundResource(tabParams.backGroundColor);
        }

        if(tabParams.iconNormalResId > 0){
            holder.tabImage.setImageResource(tabParams.iconNormalResId);
        }else{
            holder.tabImage.setVisibility(View.INVISIBLE);
        }

        if(tabParams.iconNormalResId > 0 && tabParams.iconSelectResId > 0){
            view.setTag(holder);
            view.setOnClickListener(this);
            mViewHolderList.add(holder);
        }

        //如果大于item可见个数，则不添加item到LinearLayout中
        if(mTabVisibleCount > 0 && mViewHolderList.size() > mTabVisibleCount){
            return;
        }

        //添加item到LinearLayout中，并设置比重1.0F
        addView(view, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0F));
    }

    @Override
    public void onClick(View v) {
        Object object = v.getTag();
        if(object != null && object instanceof ViewHolder){
            //通过addTab()方法中setTag获取对应的ViewHolder
            ViewHolder holder = (ViewHolder) v.getTag();
            //showFragment(holder);
            if(onTabSelectedListener != null){
                onTabSelectedListener.onTabSelected(holder);
            }
        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(mMainContainerLayoutId == 0){
            throw new RuntimeException("mFrameLayoutId can not be 0 !");
        }
        if(mViewHolderList.size() == 0){
            throw new RuntimeException("mViewHolderList size can not be 0, Please call addTab()!");
        }
        if(!(getContext() instanceof FragmentActivity)){
            throw new RuntimeException("parent Activity must is extends FragmentActivity !");
        }
        mFragmentActivity = (FragmentActivity) getContext();

        //显示默认的fragment 分首次加载和重新进入主页面但主页面没有销毁
        hideAllFragment();
        ViewHolder defaultHolder = null;
        if(!TextUtils.isEmpty(mRestoreTag)){
            //如果mRestoreTag说明不是第一次加载
            for(ViewHolder holder : mViewHolderList){
                if(TextUtils.equals(mRestoreTag, holder.tag)){
                    defaultHolder = holder;
                    mRestoreTag = null;//置为null，以便下次离开页面保存数据状态的时候重新赋值（onSaveInstanceState(Bundle outState)）
                    break;
                }
            }
        }else{
            //是第一次加载，默认显示第一个tab
            defaultHolder = mViewHolderList.get(mDefaultSelectIndex);
        }
        showFragment(defaultHolder);

    }

    /**
     * 隐藏所有的fragment
     */
    private void hideAllFragment(){
        //首先判断mViewHolderList中是否存入了ViewHolder，即是否动态添加了tab到LinearLayout中
        if(mViewHolderList == null || mViewHolderList.size() == 0){
            return;
        }

        //开始事务
        FragmentTransaction transaction = mFragmentActivity.getSupportFragmentManager().beginTransaction();
        //遍历mViewHolderList， 根据tag获取所有的fragment，依次隐藏
        for(ViewHolder viewHolder : mViewHolderList){
            Fragment fragment = mFragmentActivity.getSupportFragmentManager().findFragmentByTag(viewHolder.tag);
            if(fragment != null && !fragment.isHidden()){
                transaction.hide(fragment);
            }
        }
        //关闭事务
        transaction.commit();
    }

    /**
     * 显示当前fragment
     * @param holder
     */
    public void showFragment(ViewHolder holder){
        //开启管理fragment事务
        FragmentTransaction transaction = mFragmentActivity.getSupportFragmentManager().beginTransaction();
        //如果fragment已经显示则不往下走了，，直接返回
        if(isFragmentShown(transaction, holder.tag)){
            return;
        }
        //设置选中的item的title和图片
        setCurrSelectedByTag(holder.tag);

        //显示tag对应的fragment
        Fragment fragment = mFragmentActivity.getSupportFragmentManager().findFragmentByTag(holder.tag);
        if(fragment == null){
            //根据反射获取fragment newInstance
            //根据tag将fragment添加到transaction中
            fragment = getFragmentInstance(holder.tag);
            transaction.add(mMainContainerLayoutId, fragment, holder.tag);
        }else{
            transaction.show(fragment);
        }
        //关闭事务
        transaction.commit();

        mCurrentSelectIndex = holder.tabIndex;
    }

    /**
     * 判断fragment是否在显示中
     * @param transaction
     * @param newTag
     * @return
     */
    private boolean isFragmentShown(FragmentTransaction transaction, String newTag){
        if(TextUtils.equals(newTag, mCurrentTag)){
            return true;
        }

        if(TextUtils.isEmpty(mCurrentTag)){
            return false;
        }

        Fragment fragment = mFragmentActivity.getSupportFragmentManager().findFragmentByTag(mCurrentTag);
        //如果点击了新的tag，之前的fragment不等null并且没有被隐藏，则强制隐藏
        if(fragment != null && !fragment.isHidden()){
            transaction.hide(fragment);
        }

        return false;
    }

    /**
     * 设置选中的item的图片和文字
     * @param tag
     */
    private void setCurrSelectedByTag(String tag){
        if(TextUtils.equals(mCurrentTag, tag)){
            return;
        }
        //遍历已经存储的mViewHolderList，根据当前tag区别选中的和未选中的
        for(ViewHolder holder : mViewHolderList){
            //如果tag相同，则是选中
            if(TextUtils.equals(tag, holder.tag)){
                holder.tabTitle.setTextColor(mSelectTextColor);
                holder.tabImage.setImageResource(holder.tabParams.iconSelectResId);
            }else{
                holder.tabTitle.setTextColor(mNormalTextColor);
                holder.tabImage.setImageResource(holder.tabParams.iconNormalResId);
            }

        }
        //选中后对mCurrentTag重新赋值
        mCurrentTag = tag;
    }

    private Fragment getFragmentInstance(String tag){
        Fragment fragment = null;
        for(ViewHolder holder : mViewHolderList){
            if(TextUtils.equals(tag, holder.tag)){
                try {
                    fragment = (Fragment) Class.forName(holder.fragmentClass.getName()).newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return fragment;
    }

    public static class ViewHolder{
        public String tag;
        public TabParams tabParams;
        public ImageView tabImage;
        public TextView tabTitle;
        public int tabIndex;
        public Class fragmentClass;
    }

    public static class TabParams{
        public int backGroundColor = R.color.tab_background;//每个item的背景
        public int iconNormalResId;//没选中item image的资源Id
        public int iconSelectResId;//选中item image的资源Id
        public String titles;//tab每个item的标题
        public int titleStringResId;//每个item标题的资源Id

        //直接传入title的字符串
        public TabParams(int iconNormalResId, int iconSelectResId, String title){
            this.iconNormalResId = iconNormalResId;
            this.iconSelectResId = iconSelectResId;
            this.titles = title;
        }

        //传入title的resId
        public TabParams(int iconNormalResId, int iconSelectResId, int titleStringResId){
            this.iconNormalResId = iconNormalResId;
            this.iconSelectResId = iconSelectResId;
            this.titleStringResId = titleStringResId;
        }

        //为了点击每个item会改变背景,title传资源id
        public TabParams(int backGroundColor, int iconNormalResId, int iconSelectResId, int titleStringResId){
            this.backGroundColor = backGroundColor;
            this.iconNormalResId = iconNormalResId;
            this.iconSelectResId = iconSelectResId;
            this.titleStringResId = titleStringResId;
        }

        //为了点击每个item会改变背景，title直接传String
        public TabParams(int backGroundColor, int iconNormalResId, int iconSelectResId, String title){
            this.backGroundColor = backGroundColor;
            this.iconNormalResId = iconNormalResId;
            this.iconSelectResId = iconSelectResId;
            this.titles = title;
        }

    }

    //获取之前存入的tag，以便重新进入activity能获取到之前存储的状态
    public void onRestoreInstanceState(Bundle savedInstanceState){
        if(savedInstanceState != null){
            mRestoreTag = savedInstanceState.getString(KEY_CURRENT_KEY);
        }
    }

    //存入当前的tag，以便onAttachedToWindow时能获取之前存的item和fragment 第一次加载肯定是null，
    public void onSavedInstanceState(Bundle outState){
        outState.putString(KEY_CURRENT_KEY, mCurrentTag);
    }

    /**
     * 定义tab点击接口
     */
    public interface OnTabSelectedListener{
        void onTabSelected(ViewHolder holder);
    }

    /**
     * 设置tab点击监听器
     * @param onTabSelectedListener
     */
    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener){
        this.onTabSelectedListener = onTabSelectedListener;
    }

    /**
     * 获取当前选中的tab的下标
     * @return
     */
    public int getCurrentSelectIndex(){
        return mCurrentSelectIndex;
    }

    /**
     * 设置默认选中的tab的下标
     * @param index
     */
    public void setDefaultSelectIndex(int index){
        if(index > 0 && index < mViewHolderList.size()){
            mDefaultSelectIndex = index;
        }
    }

    /**
     * 设置未选中的title的颜色 ColorStateList
     * @param color
     */
    public void setNormalTextColor(ColorStateList color){
        this.mNormalTextColor = color;
    }

    /**
     * 设置未选中的title的颜色 int
     * @param color
     */
    public void setNormalTextColor(int color){
        this.mNormalTextColor = ColorStateList.valueOf(color);
    }

    /**
     * 获取未选中的title的字体的颜色
     * @return
     */
    public ColorStateList getNormalTextColor(){
        return mNormalTextColor;
    }

    /**
     * 设置选中的title的颜色 ColorStateList
     * @param color
     */
    public void setSelectTextColor(ColorStateList color){
        this.mSelectTextColor = color;
    }

    /**
     * 设置选中的title的颜色 int
     * @param color
     */
    public void setSelectTextColor(int color){
        this.mSelectTextColor = ColorStateList.valueOf(color);
    }

    /**
     * 获取选中的title的颜色
     * @return
     */
    public ColorStateList getSelectTextColor(){
        return mSelectTextColor;
    }

    /**
     * 设置FrameLayout的Id
     * @param frameLayoutId
     */
    public void setFrameLayoutId(int frameLayoutId){
        this.mMainContainerLayoutId = frameLayoutId;
    }

    /**
     * 获取FrameLayout的Id
     * @return
     */
    public int getFrameLayoutId(){
        return mMainContainerLayoutId;
    }

    /**
     * 设置item显示的个数
     * @param count
     */
    public void setTabVisibleCount(int count){
        this.mTabVisibleCount = count;
    }

    /**
     * 获取item显示的个数
     * @return
     */
    public int getTabVisibleCount(){
        return mTabVisibleCount;
    }


}
