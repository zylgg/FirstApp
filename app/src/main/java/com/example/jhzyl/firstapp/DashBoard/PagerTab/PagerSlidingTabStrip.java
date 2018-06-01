package com.example.jhzyl.firstapp.DashBoard.PagerTab;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jhzyl.firstapp.R;


public class PagerSlidingTabStrip extends HorizontalScrollView {

    public static final int DEF_VALUE_TAB_TEXT_ALPHA = 150;
    private static final String TAG = "PagerSlidingTabStrip";
    private static final int[] ANDROID_ATTRS = new int[]{
            android.R.attr.textColorPrimary,
            android.R.attr.padding,
            android.R.attr.paddingLeft,
            android.R.attr.paddingRight,
    };

    ///这些索引必须与上面的ATTR数组相关
    private static final int TEXT_COLOR_PRIMARY = 0;
    private static final int PADDING_INDEX = 1;
    private static final int PADDING_LEFT_INDEX = 2;
    private static final int PADDING_RIGHT_INDEX = 3;

    private LinearLayout mTabsContainer;
    private LinearLayout.LayoutParams mTabLayoutParams;

    private final PagerAdapterObserver mAdapterObserver = new PagerAdapterObserver();
    private final PageListener mPageListener = new PageListener();
    private OnTabReselectedListener mTabReselectedListener = null;
    public OnPageChangeListener mDelegatePageListener;
    private ViewPager mPager;

    private int mTabCount;

    private int mCurrentPosition = 0;
    /**
     * 滚动的百分比
     */
    private float mCurrentPositionOffset = 0f;

    private Paint mRectPaint;
    private Paint mDividerPaint;

    private int mIndicatorColor;
    private int mIndicatorHeight = 2;

    private int mUnderlineHeight = 0;
    private int mUnderlineColor;

    private int mDividerWidth = 0;
    private int mDividerPadding = 0;
    private int mDividerColor;

    private int mTabPadding = 12;
    private int mTabTextSize = 14;
    private ColorStateList mTabTextColor = null;

    private int mPaddingLeft = 0;
    private int mPaddingRight = 0;

    private boolean isExpandTabs = false;
    private boolean isCustomTabs;
    private boolean isPaddingMiddle = false;
    private boolean isTabTextAllCaps = true;

    private Typeface mTabTextTypeface = null;
    private int mTabTextTypefaceStyle = Typeface.BOLD;

    private int mScrollOffset;
    private int mLastScrollX = 0;

    private int mTabBackgroundResId = R.drawable.psts_background_tab;

    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFillViewport(true);
        setWillNotDraw(false);
        mTabsContainer = new LinearLayout(context);
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        addView(mTabsContainer);

        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setStyle(Style.FILL);
        //初始化默认属性
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mScrollOffset, dm);
        mIndicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mIndicatorHeight, dm);
        mUnderlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mUnderlineHeight, dm);
        mDividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mDividerPadding, dm);
        mTabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mTabPadding, dm);
        mDividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mDividerWidth, dm);
        mTabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTabTextSize, dm);

        mDividerPaint = new Paint();
        mDividerPaint.setAntiAlias(true);
        mDividerPaint.setStrokeWidth(mDividerWidth);

        // 获取来自于系统属性设置的值
        TypedArray a = context.obtainStyledAttributes(attrs, ANDROID_ATTRS);
        int textPrimaryColor = a.getColor(TEXT_COLOR_PRIMARY, ContextCompat.getColor(context, android.R.color.black));
        mUnderlineColor = textPrimaryColor;
        mDividerColor = textPrimaryColor;
        mIndicatorColor = textPrimaryColor;

        int padding = a.getDimensionPixelSize(PADDING_INDEX, 0);
        mPaddingLeft = padding > 0 ? padding : a.getDimensionPixelSize(PADDING_LEFT_INDEX, 0);
        mPaddingRight = padding > 0 ? padding : a.getDimensionPixelSize(PADDING_RIGHT_INDEX, 0);
        a.recycle();

        String tabTextTypefaceName = "sans-serif";
        //从API 21开始使用Roboto媒体作为默认字体。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabTextTypefaceName = "sans-serif-medium";
            mTabTextTypefaceStyle = Typeface.NORMAL;
        }

        // 获取自定义属性
        a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);
        mIndicatorColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, mIndicatorColor);
        mIndicatorHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight, mIndicatorHeight);
        mUnderlineColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsUnderlineColor, mUnderlineColor);
        mUnderlineHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight, mUnderlineHeight);
        mDividerColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor, mDividerColor);
        mDividerWidth = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsDividerWidth, mDividerWidth);
        mDividerPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsDividerPadding, mDividerPadding);
        isExpandTabs = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand, isExpandTabs);
        mScrollOffset = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsScrollOffset, mScrollOffset);
        isPaddingMiddle = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsPaddingMiddle, isPaddingMiddle);
        mTabPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight, mTabPadding);
        mTabBackgroundResId = a.getResourceId(R.styleable.PagerSlidingTabStrip_pstsTabBackground, mTabBackgroundResId);
        mTabTextSize = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsTabTextSize, mTabTextSize);
        mTabTextColor = a.hasValue(R.styleable.PagerSlidingTabStrip_pstsTabTextColor) ? a.getColorStateList(R.styleable.PagerSlidingTabStrip_pstsTabTextColor) : null;
        mTabTextTypefaceStyle = a.getInt(R.styleable.PagerSlidingTabStrip_pstsTabTextStyle, mTabTextTypefaceStyle);
        isTabTextAllCaps = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsTabTextAllCaps, isTabTextAllCaps);
        int tabTextAlpha = a.getInt(R.styleable.PagerSlidingTabStrip_pstsTabTextAlpha, DEF_VALUE_TAB_TEXT_ALPHA);
        String fontFamily = a.getString(R.styleable.PagerSlidingTabStrip_pstsTabTextFontFamily);
        a.recycle();

        //颜色选择器
        if (mTabTextColor == null) {
            mTabTextColor = createColorStateList(textPrimaryColor, textPrimaryColor, Color.argb(
                    tabTextAlpha,
                    Color.red(textPrimaryColor),
                    Color.green(textPrimaryColor),
                    Color.blue(textPrimaryColor)));
        }

        //文本样式
        if (fontFamily != null) {
            tabTextTypefaceName = fontFamily;
        }
        mTabTextTypeface = Typeface.create(tabTextTypefaceName, mTabTextTypefaceStyle);

        //底部内边距，显示指示器和下划线
        setTabsContainerParentViewPaddings();

        //配置选项卡的容器LayoutParams，可以用于相同的分隔空间，也可以仅用于包装选项卡。
        mTabLayoutParams = isExpandTabs ?
                new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f) :
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    }

    private void setTabsContainerParentViewPaddings() {
        int bottomMargin = mIndicatorHeight >= mUnderlineHeight ? mIndicatorHeight : mUnderlineHeight;
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), bottomMargin);
    }

    public void setViewPager(ViewPager pager) {
        this.mPager = pager;
        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        isCustomTabs = pager.getAdapter() instanceof CustomTabProvider;
        pager.addOnPageChangeListener(mPageListener);
        pager.getAdapter().registerDataSetObserver(mAdapterObserver);
        mAdapterObserver.setAttached(true);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();
        mTabCount = mPager.getAdapter().getCount();
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            if (isCustomTabs) {//是否是自定义tab布局
                tabView = ((CustomTabProvider) mPager.getAdapter()).getCustomTabView(this, i);
            } else {
                tabView = LayoutInflater.from(getContext()).inflate(R.layout.psts_tab, this, false);
            }

            CharSequence title = mPager.getAdapter().getPageTitle(i);
            addTab(i, title, tabView);
        }
        //测量宽度，
        mTabsContainer.measure(0,0);
        Log.i("mTabsContainer_width",""+mTabsContainer.getMeasuredWidth());
        if (mTabsContainer.getWidth()>getContext().getResources().getDisplayMetrics().widthPixels){
            setShouldExpand(false);
        }else{
            setShouldExpand(true);
        }

        updateTabStyles();
    }

    private void addTab(final int position, CharSequence title, View tabView) {
        TextView textView = tabView.findViewById(R.id.psts_tab_title);
        if (textView != null) {
            if (title != null) textView.setText(title);
        }

        tabView.setFocusable(true);
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPager.getCurrentItem() != position) {
                    View tab = mTabsContainer.getChildAt(mPager.getCurrentItem());
                    unSelect(tab);
                    mPager.setCurrentItem(position);
                } else if (mTabReselectedListener != null) {
                    mTabReselectedListener.onTabReselected(position);
                }
            }
        });
//        mTabLayoutParams.leftMargin
        mTabsContainer.addView(tabView, position, mTabLayoutParams);
    }

    /**
     * 更改tab样式
     */
    private void updateTabStyles() {
        for (int i = 0; i < mTabCount; i++) {
            View v = mTabsContainer.getChildAt(i);
            v.setBackgroundResource(mTabBackgroundResId);
            v.setPadding(mTabPadding, v.getPaddingTop(), mTabPadding, v.getPaddingBottom());
            TextView tab_title = (TextView) v.findViewById(R.id.psts_tab_title);
            if (tab_title != null) {
                tab_title.setTextColor(mTabTextColor);
                tab_title.setTypeface(mTabTextTypeface, mTabTextTypefaceStyle);
                tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
                // 设置是否显示为大写形式
                if (isTabTextAllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab_title.setAllCaps(true);
                    } else {
                        tab_title.setText(tab_title.getText().toString().toUpperCase());
                    }
                }
            }
        }
    }

    private void scrollToChild(int position, int offset) {
        if (mTabCount == 0) {
            return;
        }

        int newScrollX = mTabsContainer.getChildAt(position).getLeft() + offset;
        if (position > 0 || offset > 0) {
            //光标滚动超过屏幕一半时，tab的父容器产生滚动
            Pair<Float, Float> lines = getIndicatorCoordinates();
            newScrollX += ((lines.second - lines.first) / 2);

            newScrollX -= mScrollOffset;
        }

        if (newScrollX != mLastScrollX) {
            mLastScrollX = newScrollX;
            Log.i(TAG, "scrollToChild: "+newScrollX);
            scrollTo(newScrollX, 0);
        }
    }

    public Pair<Float, Float> getIndicatorCoordinates() {
        //默认值:在当前标签下的行。
        View currentTab = mTabsContainer.getChildAt(mCurrentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();
        // 如果存在偏移，在当前和下一个选项卡之间开始插入左和右坐标。
        if (mCurrentPositionOffset > 0f && mCurrentPosition < mTabCount - 1) {
            View nextTab = mTabsContainer.getChildAt(mCurrentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();
//            lineLeft = (mCurrentPositionOffset * nextTabLeft + (1f - mCurrentPositionOffset) * lineLeft);
            lineLeft = lineLeft + mCurrentPositionOffset * (nextTabLeft - lineLeft);//同上（left即为当前tab宽度变化）
//            lineRight = (mCurrentPositionOffset * nextTabRight + (1f - mCurrentPositionOffset) * lineRight);
            lineRight = lineRight + mCurrentPositionOffset * (nextTabRight - lineRight);//同上（right即为下一个tab宽度变化）
        }

        return new Pair<>(lineLeft, lineRight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (isPaddingMiddle && mTabsContainer.getChildCount() > 0) {
            View view = mTabsContainer.getChildAt(0);
            int halfWidthFirstTab = view.getMeasuredWidth() / 2;
            mPaddingLeft = mPaddingRight = getWidth() / 2 - halfWidthFirstTab;
        }

        if (isPaddingMiddle || mPaddingLeft > 0 || mPaddingRight > 0) {
            int width;
            if (isPaddingMiddle) {
                width = getWidth();
            } else {
                // 为抵消选项卡开始和结束位置手动设置填充。
                width = getWidth() - mPaddingLeft - mPaddingRight;
            }

            //确保tabContainer比HorizontalScrollView更大，以便能够滚动。
//            mTabsContainer.setMinimumWidth(width);
            //当我们通过滑动时，将边距设置为false来查看选项卡。控件的绘制区域是否在padding里面的
            setClipToPadding(false);
        }

        setPadding(mPaddingLeft, getPaddingTop(), mPaddingRight, getPaddingBottom());
        if (mScrollOffset == 0) {
            mScrollOffset = getWidth() / 2 - mPaddingLeft;
        }

        if (mPager != null) {
            mCurrentPosition = mPager.getCurrentItem();
        }

        mCurrentPositionOffset = 0f;
        scrollToChild(mCurrentPosition, 0);
        updateSelection(mCurrentPosition);
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || mTabCount == 0) {
            return;
        }

        final int height = getHeight();
        // draw divider
        if (mDividerWidth > 0) {
            mDividerPaint.setStrokeWidth(mDividerWidth);
            mDividerPaint.setColor(mDividerColor);
            for (int i = 0; i < mTabCount - 1; i++) {
                View tab = mTabsContainer.getChildAt(i);
                canvas.drawLine(tab.getRight(), mDividerPadding, tab.getRight(), height - mDividerPadding, mDividerPaint);
            }
        }

        // draw underline
        if (mUnderlineHeight > 0) {
            mRectPaint.setColor(mUnderlineColor);
            canvas.drawRect(mPaddingLeft, height - mUnderlineHeight, mTabsContainer.getWidth() + mPaddingRight, height, mRectPaint);
        }

        // draw indicator line
        if (mIndicatorHeight > 0) {
            mRectPaint.setColor(mIndicatorColor);
            Pair<Float, Float> lines = getIndicatorCoordinates();
            canvas.drawRect(lines.first + mPaddingLeft, height - mIndicatorHeight, lines.second + mPaddingLeft, height, mRectPaint);
        }
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.i(TAG, "position: " + position);
            Log.i(TAG, "positionOffset: " + positionOffset);
            Log.i(TAG, "positionOffsetPixels: " + positionOffsetPixels);

            mCurrentPosition = position;
            mCurrentPositionOffset = positionOffset;
            int offset = mTabCount > 0 ? (int) (positionOffset * mTabsContainer.getChildAt(position).getWidth()) : 0;
            scrollToChild(position, offset);
            invalidate();
            if (mDelegatePageListener != null) {
                mDelegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(mPager.getCurrentItem(), 0);
            }
            if (mDelegatePageListener != null) {
                mDelegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            updateSelection(position);

            //Select current item
            View currentTab = mTabsContainer.getChildAt(position);
            select(currentTab);
            //Unselect prev item
            if (position > 0) {
                View prevTab = mTabsContainer.getChildAt(position - 1);
                unSelect(prevTab);
            }
            //Unselect next item
            if (position < mPager.getAdapter().getCount() - 1) {
                View nextTab = mTabsContainer.getChildAt(position + 1);
                unSelect(nextTab);
            }

            if (mDelegatePageListener != null) {
                mDelegatePageListener.onPageSelected(position);
            }
        }

    }

    private void updateSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            View tv = mTabsContainer.getChildAt(i);
            final boolean selected = i == position;
            if (selected) {
                select(tv);
            } else {
                unSelect(tv);
            }
        }
    }

    private void unSelect(View tab) {
        if (tab != null) {
            TextView tab_title = (TextView) tab.findViewById(R.id.psts_tab_title);
            if (tab_title != null) {
                tab_title.setSelected(false);
            }
            if (isCustomTabs) ((CustomTabProvider) mPager.getAdapter()).tabUnselected(tab);
        }
    }

    private void select(View tab) {
        if (tab != null) {
            TextView tab_title = (TextView) tab.findViewById(R.id.psts_tab_title);
            if (tab_title != null) {
                tab_title.setSelected(true);
            }
            if (isCustomTabs) ((CustomTabProvider) mPager.getAdapter()).tabSelected(tab);
        }
    }

    private class PagerAdapterObserver extends DataSetObserver {

        private boolean attached = false;

        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        void setAttached(boolean attached) {
            this.attached = attached;
        }

        boolean isAttached() {
            return attached;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mPager != null) {
            if (!mAdapterObserver.isAttached()) {
                mPager.getAdapter().registerDataSetObserver(mAdapterObserver);
                mAdapterObserver.setAttached(true);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPager != null) {
            if (mAdapterObserver.isAttached()) {
                mPager.getAdapter().unregisterDataSetObserver(mAdapterObserver);
                mAdapterObserver.setAttached(false);
            }
        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentPosition = savedState.currentPosition;
        if (mCurrentPosition != 0 && mTabsContainer.getChildCount() > 0) {
            unSelect(mTabsContainer.getChildAt(0));
            select(mTabsContainer.getChildAt(mCurrentPosition));
        }
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = mCurrentPosition;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }


    public void setOnTabReselectedListener(OnTabReselectedListener tabReselectedListener) {
        this.mTabReselectedListener = tabReselectedListener;
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mDelegatePageListener = listener;
    }


    public int getIndicatorColor() {
        return this.mIndicatorColor;
    }

    public int getIndicatorHeight() {
        return mIndicatorHeight;
    }

    public int getUnderlineColor() {
        return mUnderlineColor;
    }

    public int getDividerColor() {
        return mDividerColor;
    }

    public int getDividerWidth() {
        return mDividerWidth;
    }

    public int getUnderlineHeight() {
        return mUnderlineHeight;
    }

    public int getDividerPadding() {
        return mDividerPadding;
    }

    public int getScrollOffset() {
        return mScrollOffset;
    }

    public boolean getShouldExpand() {
        return isExpandTabs;
    }

    public int getTextSize() {
        return mTabTextSize;
    }

    public boolean isTextAllCaps() {
        return isTabTextAllCaps;
    }

    public ColorStateList getTextColor() {
        return mTabTextColor;
    }

    public int getTabBackground() {
        return mTabBackgroundResId;
    }

    public int getTabPaddingLeftRight() {
        return mTabPadding;
    }

    public LinearLayout getTabsContainer() {
        return mTabsContainer;
    }

    public int getTabCount() {
        return mTabCount;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    public float getCurrentPositionOffset() {
        return mCurrentPositionOffset;
    }

    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.mIndicatorColor = ContextCompat.getColor(getContext(), resId);
        invalidate();
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.mIndicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public void setUnderlineColor(int underlineColor) {
        this.mUnderlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.mUnderlineColor = ContextCompat.getColor(getContext(), resId);
        invalidate();
    }

    public void setDividerColor(int dividerColor) {
        this.mDividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.mDividerColor = ContextCompat.getColor(getContext(), resId);
        invalidate();
    }

    public void setDividerWidth(int dividerWidthPx) {
        this.mDividerWidth = dividerWidthPx;
        invalidate();
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.mUnderlineHeight = underlineHeightPx;
        invalidate();
    }

    public void setDividerPadding(int dividerPaddingPx) {
        this.mDividerPadding = dividerPaddingPx;
        invalidate();
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.mScrollOffset = scrollOffsetPx;
        invalidate();
    }

    public void setShouldExpand(boolean shouldExpand) {
        if (this.isExpandTabs==shouldExpand)return;
        this.isExpandTabs = shouldExpand;
        //配置选项卡的容器LayoutParams，可以用于相同的分隔空间，也可以仅用于包装选项卡。
        mTabLayoutParams = isExpandTabs ?
                new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f) :
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        if (mPager != null) {
            notifyDataSetChanged();
        }
    }

    public void setAllCaps(boolean textAllCaps) {
        this.isTabTextAllCaps = textAllCaps;
    }

    public void setTextSize(int textSizePx) {
        this.mTabTextSize = textSizePx;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        setTextColor(ContextCompat.getColor(getContext(), resId));
    }

    public void setTextColor(int textColor) {
        setTextColor(createColorStateList(textColor));
    }

    public void setTextColorStateListResource(int resId) {
        setTextColor(ContextCompat.getColorStateList(getContext(), resId));
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.mTabTextColor = colorStateList;
        updateTabStyles();
    }

    private ColorStateList createColorStateList(int color_state_default) {
        return new ColorStateList(
                new int[][]{new int[]{}},//default
                new int[]{color_state_default}//default
        );
    }

    private ColorStateList createColorStateList(int color_state_pressed, int color_state_selected, int color_state_default) {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed}, //pressed
                        new int[]{android.R.attr.state_selected}, // enabled
                        new int[]{} //default
                },
                new int[]{
                        color_state_pressed,
                        color_state_selected,
                        color_state_default
                }
        );
    }

    public void setTypeface(Typeface typeface, int style) {
        this.mTabTextTypeface = typeface;
        this.mTabTextTypefaceStyle = style;
        updateTabStyles();
    }

    public void setTabBackground(int resId) {
        this.mTabBackgroundResId = resId;
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.mTabPadding = paddingPx;
        updateTabStyles();
    }

    public interface CustomTabProvider {
        View getCustomTabView(ViewGroup parent, int position);

        void tabSelected(View tab);

        void tabUnselected(View tab);
    }

    public interface OnTabReselectedListener {
        void onTabReselected(int position);
    }
}
