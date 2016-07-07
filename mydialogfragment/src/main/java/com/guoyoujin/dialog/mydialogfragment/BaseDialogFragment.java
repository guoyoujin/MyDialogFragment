package com.guoyoujin.dialog.mydialogfragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;

/**
 * * created by trycatch 2016/05/17
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BaseDialogFragment extends AppCompatDialogFragment implements View.OnClickListener{
    private static final String TAG = BaseDialogFragment.class.getSimpleName();
    private View view;
    public String DIALOG_FRAGMENT_STYLE_TAG="com.gyj.dialog.fragment.style";
    private Wrapper wrapper;

    //views
    private RelativeLayout rl_title;
    private RelativeLayout rl_buttons;
    private ImageView iv_title;
    private TextView tv_title;
    private LinearLayout ll_content;
    private Button btn_yes;
    private Button btn_no;
    private LinearLayout ll_container;

    private void setWrapper(Wrapper wrapper){
        this.wrapper = wrapper;
    }
    private static class Wrapper implements Serializable{
        public CharSequence title;
        public Context context;
        public CharSequence[] messages;
        public Drawable icon;
        public CharSequence btTextYes;
        public CharSequence btTextNo;
        public View.OnClickListener btListenerYes;
        public View.OnClickListener btListenerNo;
        public OnMultiClickListener btMultiListenerYes;
        public OnMultiClickListener btMultiListenerNo;
        public OnItemClickListener onItemClickListener;
        public View contentView;//content view
        public ContentViewOperator contentViewOperator;
        public int contentViewLayoutResId;
        public boolean cancelable = true;
        public boolean showTitle = true;
        public boolean showButtons = true;
        public boolean showNegativeButton = true;
        public boolean showPositiveButton = true;
        public boolean contentViewClickable = true;
        public float widthRatio = 0.9f;
        public int widthMaxDp = 0;
        public int cornerRadiusDp = 3;
        public int dividerMarginHorizontalDp = 12;
        public int primaryTextColor = 0xff2ca99f;
        public int contentTextColor = 0xaa111111;
        public int contentDividerColor = 0x44999999;
        public int contentPaddingDp = 16;
        public int contentItemHeightDp = 56;
        public int contentTextSizeDp = 18;
    }

    public void saveData(Bundle outState){
        outState.putSerializable("wrapper",wrapper);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            wrapper = (Wrapper) savedInstanceState.get("wrapper");
        }
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    @Override
    public void onStart() {
        super.onStart();
        initWindow();
    }
   
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveData(outState);
    }
    
    
    public void initWindow(){
        Dialog dialog = getDialog();
        Window win = dialog.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = (int)(wrapper.widthRatio * getScreenWidth(getContext()));
        if(wrapper.widthMaxDp != 0 && lp.width > dp2px(getContext(), wrapper.widthMaxDp)){
            lp.width = dp2px(getContext(), wrapper.widthMaxDp);
        }
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        win.setAttributes(lp);
    }
    
    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_mddialog, container);
        initView();
        return view;
    }
    
    private void initView() {
        ll_container = (LinearLayout)view.findViewById(R.id.ll_container);
        rl_title = (RelativeLayout)view.findViewById(R.id.rl_title);
        iv_title = (ImageView)view.findViewById(R.id.iv_title);
        tv_title = (TextView)view.findViewById(R.id.tv_title);
        ll_content = (LinearLayout)view.findViewById(R.id.ll_content);
        rl_buttons = (RelativeLayout)view.findViewById(R.id.rl_buttons);
        btn_yes = (Button)view.findViewById(R.id.btn_yes);
        btn_no = (Button)view.findViewById(R.id.btn_no);
        if(wrapper == null){
            wrapper = new Wrapper();
        }
        if(!wrapper.showTitle){
            rl_title.setVisibility(View.GONE);
        }
        if(!wrapper.showButtons){
            rl_buttons.setVisibility(View.GONE);
        }
        if(!wrapper.showNegativeButton){
            btn_no.setVisibility(View.GONE);
        }
        if(!wrapper.showPositiveButton){
            btn_yes.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)btn_no.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }

        if(wrapper.icon != null){
            iv_title.setImageDrawable(wrapper.icon);
            iv_title.setVisibility(View.VISIBLE);
            tv_title.setPadding(0, 0, 0, 0);
        }
        tv_title.setText(wrapper.title);
        tv_title.setTextColor(wrapper.primaryTextColor);
        btn_yes.setTag(BUTTON_POSITIVE_INDEX);
        btn_yes.setOnClickListener(this);
        btn_yes.setTextColor(wrapper.primaryTextColor);
        if(!TextUtils.isEmpty(wrapper.btTextYes)){
            btn_yes.setText(wrapper.btTextYes);
        }
        btn_no.setTag(BUTTON_NEGATIVE_INDEX);
        btn_no.setOnClickListener(this);
        if(!TextUtils.isEmpty(wrapper.btTextNo)){
            btn_no.setText(wrapper.btTextNo);
        }
        ll_container.setBackgroundDrawable(getRoundRectShapeDrawable(getActivity(), wrapper.cornerRadiusDp, Color.WHITE));
        btn_yes.setBackgroundDrawable(getStateListDrawable(getActivity(), 0, 0xdddddddd, 0x00000000));
        btn_no.setBackgroundDrawable(getStateListDrawable(getActivity(), 0, 0xdddddddd, 0x00000000));

        analyseContent();
    }
    private static final int BUTTON_POSITIVE_INDEX = -1;
    private static final int BUTTON_NEGATIVE_INDEX = -2;

    private void analyseContent(){
        if(wrapper.messages == null || wrapper.messages.length == 0){
            if(wrapper.contentView != null){
                ll_content.addView(wrapper.contentView);
            }else if(wrapper.contentViewLayoutResId != 0) {
                LayoutInflater.from(getActivity()).inflate(wrapper.contentViewLayoutResId, ll_content);
            }
            if(wrapper.contentViewOperator != null && ll_content.getChildCount() != 0){
                wrapper.contentViewOperator.operate(ll_content.getChildAt(0));
            }
            return;
        }

        int itemPadding = dp2px(getActivity(), wrapper.contentPaddingDp);
        int itemHeight = dp2px(getActivity(), wrapper.contentItemHeightDp);
        for(int i = 0; i < wrapper.messages.length; i++){
            TextView tv = new TextView(getActivity());
            tv.setText(wrapper.messages[i]);
            tv.setTextSize(wrapper.contentTextSizeDp);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            tv.setOnClickListener(this);
            tv.setClickable(wrapper.contentViewClickable);
            tv.setTextColor(wrapper.contentTextColor);
            tv.setTag(i);
            tv.setMinHeight(itemHeight);
            if(wrapper.messages.length == 1){
                if(wrapper.showTitle && wrapper.showButtons){
                    tv.setBackgroundDrawable(getStateListDrawable(getActivity(), 0, 0xdddddddd, 0x00000000));
                }else if(wrapper.showTitle && !wrapper.showButtons){
                    tv.setBackgroundDrawable(getStateListDrawableForBottomItem(getActivity(), wrapper.cornerRadiusDp, 0xdddddddd, 0x00000000));
                }else if(!wrapper.showTitle && wrapper.showButtons){
                    tv.setBackgroundDrawable(getStateListDrawableForTopItem(getActivity(), wrapper.cornerRadiusDp, 0xdddddddd, 0x00000000));
                }else{
                    tv.setBackgroundDrawable(getStateListDrawable(getActivity(), wrapper.cornerRadiusDp, 0xdddddddd, 0x00000000));
                }
            }else{
                if(i == 0){
                    if(wrapper.showTitle){
                        tv.setBackgroundDrawable(getStateListDrawable(getActivity(), 0, 0xdddddddd, 0x00000000));
                    }else{
                        tv.setBackgroundDrawable(getStateListDrawableForTopItem(getActivity(), wrapper.cornerRadiusDp, 0xdddddddd, 0x00000000));
                    }
                }else if(i == wrapper.messages.length - 1){
                    if(wrapper.showButtons){
                        tv.setBackgroundDrawable(getStateListDrawable(getActivity(), 0, 0xdddddddd, 0x00000000));
                    }else{
                        tv.setBackgroundDrawable(getStateListDrawableForBottomItem(getActivity(), wrapper.cornerRadiusDp, 0xdddddddd, 0x00000000));
                    }
                }else{
                    tv.setBackgroundDrawable(getStateListDrawable(getActivity(), 0, 0xdddddddd, 0x00000000));
                }
            }
            tv.setPadding(itemPadding, 0, itemPadding, 0);
            ll_content.addView(tv);
            if(i != wrapper.messages.length-1){
                View divider = new View(getActivity());
                divider.setBackgroundColor(wrapper.contentDividerColor);
                LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
                dividerParams.setMargins(dp2px(getActivity(), wrapper.dividerMarginHorizontalDp), 0,
                        dp2px(getActivity(), wrapper.dividerMarginHorizontalDp), 0);
                divider.setLayoutParams(dividerParams);
                ll_content.addView(divider);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Object o = v.getTag();
        if(o != null && o instanceof Integer){
            if(((Integer)o) == BUTTON_POSITIVE_INDEX){
                if(wrapper.btListenerYes != null){
                    wrapper.btListenerYes.onClick(v);
                }
                if(wrapper.btMultiListenerYes != null && (wrapper.contentView != null || wrapper.contentViewLayoutResId != 0)){
                    if(ll_content.getChildCount() > 0){
                        wrapper.btMultiListenerYes.onClick(v, ll_content.getChildAt(0));
                    }
                }
            }else if(((Integer)o) == BUTTON_NEGATIVE_INDEX){
                if(wrapper.btListenerNo != null){
                    wrapper.btListenerNo.onClick(v);
                }
                if(wrapper.btMultiListenerNo != null && (wrapper.contentView != null || wrapper.contentViewLayoutResId != 0)){
                    if(ll_content.getChildCount() > 0){
                        wrapper.btMultiListenerNo.onClick(v, ll_content.getChildAt(0));
                    }
                }
            }else if(((Integer)o) >= 0){
                if(wrapper.onItemClickListener != null){
                    wrapper.onItemClickListener.onItemClicked((Integer)o);
                }
            }
        }
        dismiss();
    }

    public interface OnItemClickListener{
        void onItemClicked(int index);
    }

    public interface ContentViewOperator{
        void operate(View contentView);
    }

    public interface OnMultiClickListener{
        void onClick(View clickedView, View contentView);
    }

    public static class Builder {

        private final Wrapper wrapper;

        public Builder(Context context) {
            wrapper = new Wrapper();
            wrapper.context = context;
        }

        public Context getContext() {
            return wrapper.context;
        }


        /**
         * dialog title
         * @param titleId
         * @return
         */
        public Builder setTitle(int titleId) {
            wrapper.title = wrapper.context.getText(titleId);
            return this;
        }
        /**
         * dialog title
         * @param title
         * @return
         */
        public Builder setTitle(CharSequence title) {
            wrapper.title = title.toString();
            return this;
        }

        /**
         * dialog message
         * @param messagesId
         * @return
         */
        public Builder setMessages(int messagesId) {
            wrapper.context.getResources().getTextArray(messagesId);
            return this;
        }

        /**
         * dialog message
         * @param messages
         * @return
         */
        public Builder setMessages(CharSequence[] messages) {
            wrapper.messages = messages;
            return this;
        }

        /**
         * left iconId
         * @param iconId
         * @return
         */
        public Builder setIcon(int iconId) {
            wrapper.icon = wrapper.context.getResources().getDrawable(iconId);
            return this;
        }

        /**
         * left icon Drawable
         * @param icon
         * @return
         */
        public Builder setIcon(Drawable icon) {
            wrapper.icon = icon;
            return this;
        }

        /**
         * set PositiveButton View.OnClickListener callback
         * @param listener
         * @return
         */
        public Builder setPositiveButton(final View.OnClickListener listener) {
            wrapper.btListenerYes = listener;
            return this;
        }

        /**
         * set PositiveButton View.OnClickListener callback and button text
         * @param textId
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int textId, final View.OnClickListener listener) {
            wrapper.btTextYes = wrapper.context.getText(textId);
            wrapper.btListenerYes = listener;
            return this;
        }

        /**
         * set PositiveButton View.OnClickListener callback and button text
         * @param text
         * @param listener
         * @return
         */
        public Builder setPositiveButton(CharSequence text, final View.OnClickListener listener) {
            wrapper.btTextYes = text;
            wrapper.btListenerYes = listener;
            return this;
        }

        public Builder setPositiveButtonMultiListener(final OnMultiClickListener btMultiListenerYes) {
            wrapper.btMultiListenerYes = btMultiListenerYes;
            return this;
        }

        public Builder setNegativeButton(final View.OnClickListener listener) {
            wrapper.btListenerNo = listener;
            return this;
        }

        public Builder setNegativeButton(int textId, final View.OnClickListener listener) {
            wrapper.btTextNo = wrapper.context.getText(textId);
            wrapper.btListenerNo = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text, final View.OnClickListener listener) {
            wrapper.btTextNo = text;
            wrapper.btListenerNo = listener;
            return this;
        }

        public Builder setNegativeButtonMultiListener(final OnMultiClickListener btMultiListenerNo) {
            wrapper.btMultiListenerNo = btMultiListenerNo;
            return this;
        }

        public Builder setShowNegativeButton(boolean showNegativeButton){
            wrapper.showNegativeButton = showNegativeButton;
            return this;
        }

        public Builder setShowPositiveButton(boolean showPositiveButton){
            wrapper.showPositiveButton = showPositiveButton;
            return this;
        }
        public Builder setCancelable(boolean cancelable) {
            wrapper.cancelable = cancelable;
            return this;
        }

        /**
         * is show tittle
         * @param showTitle
         * @return
         */
        public Builder setShowTitle(boolean showTitle) {
            wrapper.showTitle = showTitle;
            return this;
        }

        /**
         * is show buttons
         * @param showButtons
         * @return
         */
        public Builder setShowButtons(boolean showButtons) {
            wrapper.showButtons = showButtons;
            return this;
        }

        public Builder setOnItemClickListener(final OnItemClickListener listener) {
            wrapper.onItemClickListener = listener;
            return this;
        }

        /**
         * custom view id 
         * @param layoutResId
         * @return
         */
        public Builder setContentView(int layoutResId) {
            wrapper.contentView = null;
            wrapper.contentViewLayoutResId = layoutResId;
            return this;
        }

        public Builder setContentViewClickable(boolean clickable) {
            wrapper.contentViewClickable = clickable;
            return this;
        }

        /**
         * custom view
         * @param contentView
         * @return
         */
        public Builder setContentView(View contentView) {
            wrapper.contentView = contentView;
            wrapper.contentViewLayoutResId = 0;
            return this;
        }

        /**
         *dialog  Ratio width
         * @param widthRatio
         * @return
         */
        public Builder setWidthRatio(float widthRatio){
            wrapper.widthRatio = widthRatio;
            return this;
        }

        /**
         * dialog width max
         * @param widthMaxDp
         * @return
         */
        public Builder setWidthMaxDp(int widthMaxDp){
            wrapper.widthMaxDp = widthMaxDp;
            return this;
        }

        public Builder setBackgroundCornerRadius(int cornerRadiusDp){
            wrapper.cornerRadiusDp = cornerRadiusDp;
            return this;
        }

        public Builder setDividerMarginHorizontalDp(int marginHoriDp){
            wrapper.dividerMarginHorizontalDp = marginHoriDp;
            return this;
        }

        public Builder setPrimaryTextColor(int primaryTextColor){
            wrapper.primaryTextColor = primaryTextColor;
            return this;
        }

        /**
         * dialog content text color 
         * @param contentTextColor
         * @return
         */
        public Builder setContentTextColor(int contentTextColor){
            wrapper.contentTextColor = contentTextColor;
            return this;
        }

        /**
         * dialog content divider color
         * @param contentDividerColor
         * @return
         */
        public Builder setContentDividerColor(int contentDividerColor){
            wrapper.contentDividerColor = contentDividerColor;
            return this;
        }

        public Builder setContentPaddingDp(int contentPaddingDp){
            wrapper.contentPaddingDp = contentPaddingDp;
            return this;
        }

        public Builder setContentItemHeightDp(int contentItemHeightDp){
            wrapper.contentItemHeightDp = contentItemHeightDp;
            return this;
        }

        public Builder setContentTextSizeDp(int contentTextSizeDp){
            wrapper.contentTextSizeDp = contentTextSizeDp;
            return this;
        }

        public Builder setContentViewOperator(ContentViewOperator operator){
            wrapper.contentViewOperator = operator;
            return this;
        }

        /**
         * create BaseDialogFragment
         * @return BaseDialogFragment
         */
        public BaseDialogFragment create() {
            BaseDialogFragment dialog = new BaseDialogFragment();
            dialog.setCancelable(wrapper.cancelable);
            dialog.setWrapper(wrapper);
            return dialog;
        }

        /**
         * build method init BaseDialogFragment
         * @param tag
         * @return
         */
        public Builder showDialog(String tag) {
            BaseDialogFragment dialog = create();
            FragmentTransaction fragmentTransaction = ((AppCompatActivity)(getContext())).getSupportFragmentManager().beginTransaction();
            dialog.show(fragmentTransaction,tag);
            return this;
        }
    }

    private Drawable getRoundRectShapeDrawable(float[] cornerRadiusPx, int color){
        RoundRectShape rr = new RoundRectShape(cornerRadiusPx, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rr);
        drawable.getPaint().setColor(color);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        return drawable;
    }

    private Drawable getRoundRectShapeDrawable(Context context, int cornerRadiusDp, int color){
        int cornerRadiusPx = dp2px(context, cornerRadiusDp);
        float[] outerR = new float[] { cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx,
                cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx};
        return getRoundRectShapeDrawable(outerR, color);
    }

    private Drawable getRoundRectShapeDrawableForTopItem(Context context, int cornerRadiusDp, int color){
        int cornerRadiusPx = dp2px(context, cornerRadiusDp);
        float[] outerR = new float[] { cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, 0, 0, 0, 0};
        return getRoundRectShapeDrawable(outerR, color);
    }

    private Drawable getRoundRectShapeDrawableForBottomItem(Context context, int cornerRadiusDp, int color){
        int cornerRadiusPx = dp2px(context, cornerRadiusDp);
        float[] outerR = new float[] { 0, 0, 0, 0, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx, cornerRadiusPx};
        return getRoundRectShapeDrawable(outerR, color);
    }

    private int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    private Drawable getStateListDrawable(Context context, int cornerRadiusDp, int colorPressed, int colorNormal){
        StateListDrawable stateListDrawable = new StateListDrawable();

        int[] stateHighlighted = new int[]{android.R.attr.state_pressed};
        Drawable highlightedDrawable = getRoundRectShapeDrawable(context, cornerRadiusDp, colorPressed);
        stateListDrawable.addState(stateHighlighted, highlightedDrawable);

        int[] stateNormal = new int[]{};
        Drawable normalDrawable = getRoundRectShapeDrawable(context, cornerRadiusDp, colorNormal);
        stateListDrawable.addState(stateNormal, normalDrawable);
        return stateListDrawable;
    }

    private Drawable getStateListDrawableForTopItem(Context context, int cornerRadiusDp, int colorPressed, int colorNormal){
        StateListDrawable  stateListDrawable = new StateListDrawable();

        int[] stateHighlighted = new int[]{android.R.attr.state_pressed};
        Drawable highlightedDrawable = getRoundRectShapeDrawableForTopItem(context, cornerRadiusDp, colorPressed);
        stateListDrawable.addState(stateHighlighted, highlightedDrawable);

        int[] stateNormal = new int[]{};
        Drawable normalDrawable = getRoundRectShapeDrawableForTopItem(context, cornerRadiusDp, colorNormal);
        stateListDrawable.addState(stateNormal, normalDrawable);
        return stateListDrawable;
    }

    private Drawable getStateListDrawableForBottomItem(Context context, int cornerRadiusDp, int colorPressed, int colorNormal){
        StateListDrawable  stateListDrawable = new StateListDrawable();

        int[] stateHighlighted = new int[]{android.R.attr.state_pressed};
        Drawable highlightedDrawable = getRoundRectShapeDrawableForBottomItem(context, cornerRadiusDp, colorPressed);
        stateListDrawable.addState(stateHighlighted, highlightedDrawable);

        int[] stateNormal = new int[]{};
        Drawable normalDrawable = getRoundRectShapeDrawableForBottomItem(context, cornerRadiusDp, colorNormal);
        stateListDrawable.addState(stateNormal, normalDrawable);
        return stateListDrawable;
    }
}