package com.example.jhzyl.firstapp.ref;

import java.util.Date;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jhzyl.firstapp.R;


public class PullToRefreshViewBtp extends LinearLayout {
    private static final String TAG = "PullToRefreshView";
    // refresh states
    private static final int PULL_TO_REFRESH = 2;
    /**
     * 上啦
     */
    private static final int RELEASE_TO_REFRESH = 3;
    /**
     * 标志位
     */
    private static final int REFRESHING = 4;
    /**
     * 上啦状态
     */
    private static final int PULL_UP_STATE = 0;
    /**
     * 下拉状态
     */
    private static final int PULL_DOWN_STATE = 1;
    /**
     * 延时时间
     */
    private static final int DELAYEDTIME=1000;
    /**
     * last y
     */
    private int mLastMotionY;
    /**
     * lock
     */
    private boolean mLock;
    /**
     * header view
     */
    private View mHeaderView;
    /**
     * footer view
     */
    private View mFooterView;
    /**
     * list or grid
     */
    private AdapterView<?> mAdapterView;
    /**
     * scrollview
     */
    private ScrollView mScrollView;
    /**
     * header view height
     */
    private int mHeaderViewHeight;
    /**
     * footer view height
     */
    private int mFooterViewHeight;

    /**
     * header view image
     */
    private ImageView mHeaderImageView;
    /**
     * footer view image
     */
    private ImageView mFooterImageView;
    /**
     * header tip text
     */
    private TextView mHeaderTextView;
    /**
     * footer tip text
     */
    private TextView mFooterTextView;
    /**
     * header refresh time
     */
    private TextView mHeaderUpdateTextView;
    /**
     * footer refresh time
     */
    // private TextView mFooterUpdateTextView;
    /**
     * header progress bar
     */
    private ProgressBar mHeaderProgressBar;
    /**
     * footer progress bar
     */
    private ProgressBar mFooterProgressBar;
    /**
     * layout inflater
     */
    private LayoutInflater mInflater;
    /**
     * header view current state
     */
    private int mHeaderState;
    /**
     * footer view current state
     */
    private int mFooterState;
    /**
     * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
     */
    private int mPullState;
    /**
     * 变为向下的箭头,改变箭头方向
     */
    private RotateAnimation mFlipAnimation;
    /**
     * 变为逆向的箭头,旋转
     */
    private RotateAnimation mReverseFlipAnimation;
    /**
     * footer refresh listener
     */
    private OnFooterRefreshListener mOnFooterRefreshListener;
    /**
     * footer refresh listener
     */
    private OnHeaderRefreshListener mOnHeaderRefreshListener;

    /**
     * 禁止刷新
     */
    private boolean noRefresh;

    /**
     * 禁止加载更多
     */
    private boolean noAddMore;

    private boolean isAddMoreLoadding=false;
    /**
     * 用户自定义的加载更多的文字
     */
    private int footViewText = -1;
    private int headViewText = -1;
    private String  monthDay;
    private String  hourMinute;
    private ReFreshTimeUtils spu;
    private String where;
    /**
     * last update time
     */
    private int mytop=0;
    public PullToRefreshViewBtp(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshViewBtp(Context context) {
        super(context);
        init();
    }



    /**
     * init
     * 
     * @description
     *            hylin 2012-7-26上午10:08:33
     */
    private void init() {
    	spu = new ReFreshTimeUtils(this.getContext(),"refresh_time", this.getContext().MODE_PRIVATE);
        mFlipAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);
        mFlipAnimation.setFillAfter(true);
        mReverseFlipAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(250);
        mReverseFlipAnimation.setFillAfter(true);

        mInflater = LayoutInflater.from(getContext());
        // header view 在此添加,保证是第一个添加到linearlayout的最上端
        addHeaderView();
    }

    private void addHeaderView() {
        // header view
        mHeaderView = mInflater.inflate(R.layout.qgp_refresh_header, this, false);

        mHeaderImageView = (ImageView) mHeaderView
                .findViewById(R.id.pull_to_refresh_image);
        mHeaderTextView = (TextView) mHeaderView
                .findViewById(R.id.pull_to_refresh_text);
        mHeaderUpdateTextView = (TextView) mHeaderView
                .findViewById(R.id.pull_to_refresh_updated_at);
        mHeaderProgressBar = (ProgressBar) mHeaderView
                .findViewById(R.id.pull_to_refresh_progress);
        // header layout
        measureView(mHeaderView);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,mHeaderViewHeight);
        // 设置topMargin的值为负的header View高度,即将其隐藏在最上方
        params.topMargin = -(mHeaderViewHeight);
        // mHeaderView.setLayoutParams(params1);
        addView(mHeaderView, params);
    }
    private void addFooterView() {
        // footer view
        mFooterView = mInflater.inflate(R.layout.qgp_refresh_footer, this, false);
        mFooterImageView = (ImageView) mFooterView.findViewById(R.id.pull_to_load_image);
        mFooterTextView = (TextView) mFooterView.findViewById(R.id.pull_to_load_text);
        mFooterProgressBar = (ProgressBar) mFooterView.findViewById(R.id.pull_to_load_progress);
        mFooterProgressBar.setVisibility(View.VISIBLE);
        // footer layout
        measureView(mFooterView);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,mFooterViewHeight);
        // int top = getHeight();
        // params.topMargin
        // =getHeight();//在这里getHeight()==0,但在onInterceptTouchEvent()方法里getHeight()已经有值了,不再是0;
        // getHeight()什么时候会赋值,稍候再研究一下
        // 由于是线性布局可以直接添加,只要AdapterView的高度是MATCH_PARENT,那么footer view就会被添加到最后,并隐藏
        addView(mFooterView, params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // footer view 在此添加保证添加到linearlayout中的最后
        addFooterView();
        initContentAdapterView();
    }

    /**
     * init AdapterView like ListView,GridView and so on;or init ScrollView
     * 
     * @description hylin 2012-7-30下午8:48:12
     */
    private void initContentAdapterView() {
        int count = getChildCount();
        if (count < 3) {
            throw new IllegalArgumentException(
                    "this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
        }
        View view = null;
        for (int i = 0; i < count - 1; ++i) {
            view = getChildAt(i);
            if (view instanceof AdapterView<?>) {
                mAdapterView = (AdapterView<?>) view;
            }
            if (view instanceof ScrollView) {
                // finish later
                mScrollView = (ScrollView) view;
            }
        }
        if (mAdapterView == null && mScrollView == null) {
            throw new IllegalArgumentException(
                    "must contain a AdapterView or ScrollView in this layout!");
        }
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int y = (int) e.getRawY();
        switch (e.getAction()) {
        case MotionEvent.ACTION_DOWN:
            // 首先拦截down事件,记录y坐标
            mLastMotionY = y;
            break;
        case MotionEvent.ACTION_MOVE:
            // deltaY > 0 是向下运动,< 0是向上运动
            int deltaY = y - mLastMotionY;
            if (isRefreshViewScroll(deltaY)) {
                return true;
            }
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
            break;
        }
        return false;
    }

    /*
     * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
     * false)则由PullToRefreshView 的子View来处理;否则由下面的方法来处理(即由PullToRefreshView自己来处理)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mLock) {
            return true;
        }
        int y = (int) event.getRawY();
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            // onInterceptTouchEvent已经记录
            // mLastMotionY = y;
            break;
        case MotionEvent.ACTION_MOVE:
            int deltaY = y - mLastMotionY;
            if (mPullState == PULL_DOWN_STATE) {
                // PullToRefreshView执行下拉
                if (!noRefresh) {
                    headerPrepareToRefresh(deltaY);
                }
                // setHeaderPadding(-mHeaderViewHeight);
            } else if (mPullState == PULL_UP_STATE) {
                // PullToRefreshView执行上拉
                if (!noAddMore) {
                	footerPrepareToRefresh(deltaY);
				}
            }
            mLastMotionY = y;
            break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
            int topMargin = getHeaderTopMargin();
            all_mOffsetY=0;
            if (mPullState == PULL_DOWN_STATE) {
                if (topMargin >= mHeaderViewHeight) {
                    // 开始刷新
                    headerRefreshing();
                } else {
                    // 还没有执行刷新，重新隐藏
                    setHeaderTopMargin(-mHeaderViewHeight);
                }
            } else if (mPullState == PULL_UP_STATE) {
                if (Math.abs(topMargin) >= mHeaderViewHeight
                        + mFooterViewHeight) {
                    // 开始执行footer 刷新
                    footerRefreshing();
                } else {
                    // 还没有执行刷新，重新隐藏
                    setHeaderTopMargin(-mHeaderViewHeight);
                }
            }
            break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 是否应该到了父View,即PullToRefreshView滑动
     * 
     * @param deltaY
     *            , deltaY > 0 是向下运动,< 0是向上运动
     * @return
     */
    private boolean isRefreshViewScroll(int deltaY) {
        if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
            return false;
        }
        // 对于ListView和GridView
        if (mAdapterView != null) {
            // 子view(ListView or GridView)滑动到最顶端
            if (deltaY > 5) {
                View child = mAdapterView.getChildAt(0);
                if (child == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && child.getTop() == 0) {
                    mPullState = PULL_DOWN_STATE;
//                    Configure.isHeaderRefreshBoolean=true;
                    return true;
                }
                int top = child.getTop();
                if(top>mytop){
                	mytop=top;
                }
                int padding = mAdapterView.getPaddingTop();
                if (mAdapterView.getFirstVisiblePosition() == 0
                		&&Math.abs(top - padding) <= mytop) {// 这里之前用3可以判断,但现在不行,还没找到原因
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }

            } else if (deltaY < -5) {
                
                View lastChild = mAdapterView.getChildAt(mAdapterView
                        .getChildCount() - 1);
                if (lastChild == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                // 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
                // 等于父View的高度说明mAdapterView已经滑动到最后
                if (lastChild.getBottom() <= getHeight()
                        && mAdapterView.getLastVisiblePosition() == mAdapterView
                                .getCount() - 1) {
                    mPullState = PULL_UP_STATE;
                    return true;
                }
            }
        }
        // 对于ScrollView
        if (mScrollView != null) {
            // 子scroll view滑动到最顶端
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0 && mScrollView.getScrollY() == 0) {
                mPullState = PULL_DOWN_STATE;
                return true;
            } else if (deltaY < 0
                    && child.getMeasuredHeight() <= getHeight()
                            + mScrollView.getScrollY()) {
                mPullState = PULL_UP_STATE;
                return true;
            }
        }
        return false;
    }

    /**
     * header 准备刷新,手指移动过程,还没有释放
     * 
     * @param deltaY
     *            ,手指滑动的距离
     */
    private void headerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        // 当header view的topMargin>=0时，说明已经完全显示出来了,修改header view 的提示状态
        if (newTopMargin >= mHeaderViewHeight && mHeaderState != RELEASE_TO_REFRESH) {
            mHeaderTextView.setText(R.string.pull_to_refresh_release_label);
            mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderImageView.clearAnimation();
            mHeaderImageView.startAnimation(mFlipAnimation);
            mHeaderState = RELEASE_TO_REFRESH;
        } else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {// 拖动时没有释放
            mHeaderImageView.clearAnimation();
            mHeaderImageView.startAnimation(mFlipAnimation);
            // mHeaderImageView.
            mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
            mHeaderState = PULL_TO_REFRESH;
            
          //TODO
            monthDay = spu.loadStringKey(where+"monthDay", monthDay);
            hourMinute = spu.loadStringKey(where+"hourMinute", hourMinute);
            if(TextUtils.isEmpty(monthDay) || TextUtils.isEmpty(hourMinute)){
            	mHeaderUpdateTextView.setText("");
            }else{
            	mHeaderUpdateTextView.setText("更新于："+monthDay+" "+hourMinute);
            }
        }
    }

    /**
     * footer 准备刷新,手指移动过程,还没有释放 移动footer view高度同样和移动header view
     * 高度是一样，都是通过修改header view的topmargin的值来达到
     * 
     * @param deltaY
     *            ,手指滑动的距离
     */
    private void footerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        // 如果header view topMargin 的绝对值大于或等于header + footer 的高度
        // 说明footer view 完全显示出来了，修改footer view 的提示状态
        if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
                && mFooterState != RELEASE_TO_REFRESH) {
//            mFooterTextView
//                    .setText(R.string.pull_to_refresh_footer_release_label);
//            mFooterImageView.clearAnimation();
//            mFooterImageView.startAnimation(mFlipAnimation);
            mFooterState = RELEASE_TO_REFRESH;
        } else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
//            mFooterImageView.clearAnimation();
//            mFooterImageView.startAnimation(mFlipAnimation);
//            if (footViewText!=-1) {
//            	mFooterTextView.setText(footViewText);
//			}else {
//				mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
//			}
            mFooterState = PULL_TO_REFRESH;
        }
    }

    int all_mOffsetY=0;
    /**
     * 修改Header view top margin的值
     * 
     * @description
     * @param deltaY
     * @return hylin 2012-7-31下午1:14:31
     */
    private int changingHeaderViewTopMargin(int deltaY) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        float newTopMargin = params.topMargin + deltaY * 0.3f;
        // 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了,感谢网友yufengzungzhe的指出
        // 表示如果是在上拉后一段距离,然后直接下拉
        if (deltaY > 0 && mPullState == PULL_UP_STATE
                && Math.abs(params.topMargin) <= mHeaderViewHeight) {
            return params.topMargin;
        }
        // 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
        if (deltaY < 0 && mPullState == PULL_DOWN_STATE
                && Math.abs(params.topMargin) >= mHeaderViewHeight) {
            return params.topMargin;
        }
        params.topMargin = (int) newTopMargin;
//        mHeaderView.setLayoutParams(params);

        all_mOffsetY=all_mOffsetY+deltaY;
        mHeaderView.offsetTopAndBottom(deltaY);
        mAdapterView.offsetTopAndBottom(deltaY);

        ViewCompat.postInvalidateOnAnimation(this);
        return all_mOffsetY;
    }

    /**
     * header refreshing
     * 
     * @description hylin 2012-7-31上午9:10:12
     */
    public void headerRefreshing() {
        mHeaderState = REFRESHING;
        setHeaderTopMargin(0);
      /*  LayoutParams params = (LayoutParams)this.getLayoutParams();
        params.height =  params.height + mHeaderView.getLayoutParams().height;
        this.setLayoutParams(params);
        invalidate();*/
        mHeaderImageView.setVisibility(View.GONE);
        mHeaderImageView.clearAnimation();
        mHeaderImageView.setImageDrawable(null);
        mHeaderProgressBar.setVisibility(View.VISIBLE);
        mHeaderTextView.setText(R.string.pull_to_refresh_refreshing_label);
        if (mOnHeaderRefreshListener != null) {
            mOnHeaderRefreshListener.onHeaderRefresh(this);
        }
    }
    private int preListHeight;
    /**
     * footer refreshing
     * 
     * @description hylin 2012-7-31上午9:09:59
     */
    private void footerRefreshing() {
        mFooterState = REFRESHING;
     //   int top = mHeaderViewHeight + mFooterViewHeight;
     	 int top = mHeaderViewHeight;
        if(mAdapterView!=null){
             LayoutParams params = (LayoutParams)mAdapterView.getLayoutParams();
           //  mAdapterView.getMeasuredHeight();
             if(mAdapterView.getMeasuredHeight()>0){
             	preListHeight =  mAdapterView.getMeasuredHeight();
                 params.height = mAdapterView.getMeasuredHeight()-mFooterViewHeight;
             }
             
        }
        
        setHeaderTopMargin(-top);
        mFooterImageView.setVisibility(View.GONE);
        mFooterImageView.clearAnimation();
        mFooterImageView.setImageDrawable(null);
        mFooterProgressBar.setVisibility(View.VISIBLE);
        mFooterTextView
                .setText(R.string.pull_to_refresh_footer_refreshing_label);
        if (mOnFooterRefreshListener != null&&!isAddMoreLoadding) {
        	isAddMoreLoadding=true;
            mOnFooterRefreshListener.onFooterRefresh(this);
        }
    }

    /**
     * 设置header view 的topMargin的值
     *
     * @description
     * @param topMargin
     *            ，为0时，说明header view 刚好完全显示出来； 为-mHeaderViewHeight时，说明完全隐藏了
     *            hylin 2012-7-31上午11:24:06
     */
    private void setHeaderTopMargin(int topMargin) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        params.topMargin = topMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
    }

    /**
     * header view 完成更新后恢复初始状态
     * 
     * @description hylin 2012-7-31上午11:54:23
     */
    public void onHeaderRefreshComplete() {
        setHeaderTopMargin(-mHeaderViewHeight);
        mHeaderImageView.setVisibility(View.VISIBLE);
        mHeaderImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
        mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
        mHeaderProgressBar.setVisibility(View.GONE);
        
        Date date = new Date();
        monthDay = date.getMonth()+1+"-"+date.getDate();
        hourMinute= (date.getHours()<10?"0"+date.getHours():date.getHours())+":"+(date.getMinutes()<10?"0"+date.getMinutes():date.getMinutes());
        spu.saveStringKey(where+"monthDay", monthDay);
        spu.saveStringKey(where+"hourMinute", hourMinute);
        mHeaderState = PULL_TO_REFRESH;
        //延时发送
//        Configure.timer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                Configure.isHeaderRefreshBoolean=false;
//                Configure.hotSpotVisible=true;
//            }
//        }, DELAYEDTIME);

    }
    

    /**
     * Resets the list to a normal state after a refresh.
     * 
     * @param lastUpdated
     *            Last updated at.
     */
    public void onHeaderRefreshComplete(CharSequence lastUpdated) {
       
        setLastUpdated(lastUpdated);
        onHeaderRefreshComplete();
    }
//    private Date refreshDate;

    public void onHeaderRefreshComplete(Date refreshDate) {
//        this.refreshDate = refreshDate;
        String lastUpdated = null;
        if(refreshDate!=null)
        {
            lastUpdated = DataUtils
            .getTime(refreshDate,
                    this.getContext());
        }
        onHeaderRefreshComplete(lastUpdated);
    }

    /**
     * footer view 完成更新后恢复初始状态
     */
    public void onFooterRefreshComplete() {
        setHeaderTopMargin(-mHeaderViewHeight);
        if(mAdapterView!=null){
        	 LayoutParams params = (LayoutParams)mAdapterView.getLayoutParams();
             if(mAdapterView.getMeasuredHeight()>0&&preListHeight>0){
             	   params.height = preListHeight;
             }
//             if(mAdapterView instanceof GridView){
//             	GridView gridview=(GridView) mAdapterView;
//             	gridview.smoothScrollBy(100, 500);
//             }else if(mAdapterView instanceof ListView){
//             	ListView listview=(ListView) mAdapterView;
//             	listview.smoothScrollBy(100, 500);
//             }
        }
//        mFooterImageView.setVisibility(View.VISIBLE);
//        mFooterImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow_up);
//        mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
//        mFooterProgressBar.setVisibility(View.GONE);


        mFooterState = PULL_TO_REFRESH;
        isAddMoreLoadding=false;
    }

    /**
     * Set a text to represent when the list was last updated.
     *
     * @param lastUpdated
     *            Last updated at.
     */
    public void setLastUpdated(CharSequence lastUpdated) {
        if (lastUpdated != null) {
            mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderUpdateTextView.setText(lastUpdated);
        } else {
            mHeaderUpdateTextView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 获取当前header view 的topMargin
     * 
     * @description
     * @return hylin 2012-7-31上午11:22:50
     */
    private int getHeaderTopMargin() {
        return all_mOffsetY;
    }

    /**
     * set headerRefreshListener
     * 
     * @description
     * @param headerRefreshListener
     *            hylin 2012-7-31上午11:43:58
     */
    public void setOnHeaderRefreshListener(
            OnHeaderRefreshListener headerRefreshListener) {
        mOnHeaderRefreshListener = headerRefreshListener;
    }

    public void setOnFooterRefreshListener(
            OnFooterRefreshListener footerRefreshListener) {
        mOnFooterRefreshListener = footerRefreshListener;
    }

    /**
     * Interface definition for a callback to be invoked when list/grid footer
     * view should be refreshed.
     */
    public interface OnFooterRefreshListener {
        public void onFooterRefresh(PullToRefreshViewBtp view);
    }

    /**
     * Interface definition for a callback to be invoked when list/grid header
     * view should be refreshed.
     */
    public interface OnHeaderRefreshListener {
        public void onHeaderRefresh(PullToRefreshViewBtp view);
    }
    
    /**
     * 替换“加载更多”文字
     * @param text
     */
    public void setFootviewText(int text){
        this.footViewText = text;
    }
    
    public void setHeadviewText(int text){
    	this.headViewText = text;
    }

    /**
     * 是否禁止刷新(上面的)
     * @return true 禁止 false 允许
     */
    public boolean isNoRefresh() {
        return noRefresh;
    }

    /**
     * 设置是否禁止刷新(上面的)
     * @param noRefresh true 禁止 false 允许
     */
    public void setNoRefresh(boolean noRefresh) {
        this.noRefresh = noRefresh;
    }

    /** 是否禁止加载更多(下面的) 
     * @return true 禁止 false 允许
     */
	public boolean isNoAddMore() {
		return noAddMore;
	}

	/**
	 * 设置是否禁止加载更多(下面的)
	 * @param noAddMore true 禁止 false 允许 
	 */
	public void setNoAddMore(boolean noAddMore) {
		this.noAddMore = noAddMore;
	}
    public void setFootViewVisible(boolean b){
    	if(b){
        	mFooterView.setVisibility(View.VISIBLE);
    	}else{
        	mFooterView.setVisibility(View.GONE);
    	}

    }
    public void loadAddMore(){
    	if (mOnFooterRefreshListener != null&&!isAddMoreLoadding) {
        	isAddMoreLoadding=true;
            mOnFooterRefreshListener.onFooterRefresh(this);
        }
    }
    
    
}
