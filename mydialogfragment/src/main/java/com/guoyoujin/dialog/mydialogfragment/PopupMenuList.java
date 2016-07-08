package com.guoyoujin.dialog.mydialogfragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * 在此写用途
 *
 * @author: guoyoujin
 * @mail: guoyoujin123@gmail.com
 * @date: 2016-07-08 16:22
 * @version: V1.0 <描述当前版本功能>
 */

public class PopupMenuList extends PopupWindow{
    private static final String TAG = "PopupMenuList";
    private LinearLayout popupMenuListLinerLayout;
    private ListView popupMenuListListView;
    private View popView;
    //datas
    private Wrapper wrapper;
    private Context context;

    public PopupMenuList(Context context) {
        super(context);
        initView();
    }

    private void setWrapper(Wrapper wrapper){
        this.wrapper = wrapper;
    }
    
    private void initView(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = inflater.inflate(R.layout.layout_popup_menu_list, null);// 加载菜单布局文件
        popupMenuListLinerLayout = (LinearLayout) popView.findViewById(R.id.popupMenuListLinerLayout);
        popupMenuListListView = (ListView) popView.findViewById(R.id.popupMenuListListView);
        if(wrapper.contentView!=null){
            this.setContentView(wrapper.contentView);
        }else{
            this.setContentView(popView);// 把布局文件添加到popupwindow中
        }
        this.setFocusable(wrapper.focusable);// 获取焦点
        this.setTouchable(wrapper.touchable); // 设置PopupWindow可触摸
        this.setOutsideTouchable(wrapper.outsideTouchable); // 设置非PopupWindow区域可触摸
        this.setBackgroundDrawable(wrapper.backgroundDrawable);
    }

    /**
     * 设置显示的位置
     *
     * @param resourId
     * 这里的x,y值自己调整可以
     */
    public void showLocation(int resourId) {
        showAsDropDown(((Activity)context).findViewById(resourId), dip2px((Activity)context, 0), dip2px((Activity)context, -8));
    }

    /**
     * // dip转换为px
     * @param context
     * @param dipValue
     * @return
     */
    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }



    private static class Wrapper{
        public Context context;
        
        public View contentView;//content view
        
        public boolean cancelable = true;
        
        public boolean focusable = true;
        
        public boolean touchable = true;
        
        public boolean outsideTouchable = true;
        
        public Drawable backgroundDrawable = new ColorDrawable(0x00000000);

    }

    public static class Builder {

        private final Wrapper wrapper;

        public Builder(Context context) {
            wrapper = new Wrapper();
            wrapper.context = context;
        }

        /**
         * 
         * @return
         */
        public Context getContext() {
            return wrapper.context;
        }

      
        /**
         * 
         * @param contentView
         * @return
         */
        public Builder setContentView(View contentView){
            wrapper.contentView = contentView;
            return this;
        }

        /**
         * 
         * @param cancelable
         * @return
         */
        public Builder setCancelable(boolean cancelable){
            wrapper.cancelable= cancelable;
            return this;
        }

        /**
         * // 获取焦点
         * @param focusable
         * @return
         */
        public Builder setFocusable(boolean focusable){
            wrapper.focusable = focusable;
            return this;
        }

        /**
         * // 设置PopupWindow可触摸
         * @param touchable
         * @return
         */
        public Builder setTouchable(boolean touchable){
            wrapper.touchable = touchable;
            return this;
        }

        /**
         * // 设置非PopupWindow区域可触摸
         * @param outsideTouchable
         * @return
         */
        public Builder setoutsideTouchable(boolean outsideTouchable){
            wrapper.outsideTouchable = outsideTouchable;
            return this;
        }


        /**
         * 
         * @param backgroundDrawable
         * @return
         */
        public Builder setbackgroundDrawable(Drawable backgroundDrawable){
            wrapper.backgroundDrawable = backgroundDrawable;
            return this;
        }

        /**
         * 创建
         * @return
         */
        public PopupWindow create() {
            final PopupMenuList popupMenuList = new PopupMenuList(wrapper.context);
            popupMenuList.setWrapper(wrapper);
            return popupMenuList;
        }
    }
}