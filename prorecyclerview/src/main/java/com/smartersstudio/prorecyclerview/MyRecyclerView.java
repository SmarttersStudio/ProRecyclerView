package com.smartersstudio.prorecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.Objects;

public class MyRecyclerView extends RelativeLayout {
    protected int ITEM_LEFT_TO_LOAD_MORE = 10;
    private Context context;
    private String emptyMessage, errorMessage, emptyAsset, errorAsset;
    private TypedArray typedArray;
    private TextView emptyText, errorText;
    private LottieAnimationView emptyLottie, errorLottie;
    private Button errorRetry, emptyReload;
    private RecyclerView mRecycler;
    private RelativeLayout moreProgressLayout, emptyLayout, errorLayout, loaderLayout;
    private SwipeRefreshLayout refresh;
    private ShimmerFrameLayout shimmerLayout;
    private LinearLayout loaderContainer;

    public MyRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
        initView(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
        initView(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttributes(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.context=context;
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.my_recycler_view,this);
        errorText = v.findViewById(R.id.error_text);
        emptyText = v.findViewById(R.id.empty_text);

        emptyLottie = v.findViewById(R.id.empty_lottie);
        errorLottie = v.findViewById(R.id.error_lottie);

        errorRetry = v.findViewById(R.id.error_reload);
        emptyReload = v.findViewById(R.id.empty_reload);

        mRecycler = v.findViewById(R.id.recycler);
        moreProgressLayout = v.findViewById(R.id.more_progress);
        errorLayout = v.findViewById(R.id.error);
        emptyLayout = v.findViewById(R.id.empty);
        loaderLayout = v.findViewById(R.id.loader);
        loaderContainer=v.findViewById(R.id.loader_container);
        refresh=v.findViewById(R.id.refresh);
        shimmerLayout = v.findViewById(R.id.shimmer_view_container);
        showRecycler();
        hideEmpty();
        hideError();
        hideLoader();

        if (!TextUtils.isEmpty(errorMessage)) errorText.setText(errorMessage);
        if (!TextUtils.isEmpty(emptyMessage)) emptyText.setText(emptyMessage);
        if (!TextUtils.isEmpty(emptyAsset)) emptyLottie.setAnimation(emptyAsset);
        if (!TextUtils.isEmpty(errorAsset)) errorLottie.setAnimation(errorAsset);

    }

    private void initAttributes(Context context, AttributeSet attrs) {
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.myRecyclerVIew);

        try {
            emptyAsset = typedArray.getString(R.styleable.myRecyclerVIew_empty_asset);
            errorAsset = typedArray.getString(R.styleable.myRecyclerVIew_error_asset);
            emptyMessage = typedArray.getString(R.styleable.myRecyclerVIew_empty_text);
            errorMessage = typedArray.getString(R.styleable.myRecyclerVIew_error_text);
        }finally {
            typedArray.recycle();
        }
    }

    public void setEmptyAsset(String assetName){
        emptyLottie.setAnimation(assetName);
    }
    public void setErrorAsset(String assetName){
        errorLottie.setAnimation(assetName);
    }
    public Animation getEmptyAsset(){
        return emptyLottie.getAnimation();
    }
    public Animation getErrorAsset(){
        return errorLottie.getAnimation();
    }

    public RecyclerView getRecyclerView() {
        return mRecycler;
    }

    public void setErrorMessage(String message) {
        errorText.setText(message);
    }

    public void setEmptyMessage(String message) {
        emptyText.setText(message);
    }

    public void setErrorMessage(int resId) {
        errorText.setText(resId);
    }

    public void setEmptyMessage(int resId) {
        emptyText.setText(resId);
    }
    public  void showRecycler(){
        refresh.setVisibility(VISIBLE);
    }
    public void hideRecycler(){
        refresh.setVisibility(GONE);
    }
    public  void showEmpty(){
        emptyLayout.setVisibility(VISIBLE);
    }
    public void hideEmpty(){
        emptyLayout.setVisibility(GONE);
    }
    public  void showError(){
        errorLayout.setVisibility(VISIBLE);
    }
    public void hideError(){
        errorLayout.setVisibility(GONE);
    }
    public  void showLoader(){
        loaderLayout.setVisibility(VISIBLE);
        shimmerLayout.startShimmer();
    }
    public void hideLoader(){
        loaderLayout.setVisibility(GONE);
        shimmerLayout.stopShimmer();
    }

    public void onRetry(OnClickListener onClickListener) {
        errorRetry.setOnClickListener(onClickListener);
    }

    public void onReload(OnClickListener onClickListener) {
        emptyReload.setOnClickListener(onClickListener);
    }
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecycler.setLayoutManager(manager);
    }
    public void setAdapter(RecyclerView.Adapter adapter){
        mRecycler.setAdapter(adapter);
    }
    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener listener){
        refresh.setOnRefreshListener(listener);
    }
    public void setOnMoreListener(final OnMoreListener listener){
        mRecycler
                .addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        int totalItemCount = Objects.requireNonNull(recyclerView.getLayoutManager()).getItemCount();
                        int lastVisibleItem = ((LinearLayoutManager) Objects.requireNonNull(mRecycler.getLayoutManager()))
                                .findLastVisibleItemPosition();
                        if (!isMoreLoading()
                                && totalItemCount <= (lastVisibleItem + 5)) {
                        listener.onMoreAsked(totalItemCount,lastVisibleItem);

                        }
                    }
                });
    }
    public  boolean isRefreshing(){
        return  refresh.isRefreshing();
    }
    public  boolean isMoreLoading(){
        return  moreProgressLayout.getVisibility()==VISIBLE;
    }
    public void setRefreshing(boolean b){
        refresh.setRefreshing(b);
    }
    public  void setMoreLoading(boolean b){
        moreProgressLayout.setVisibility(b?VISIBLE:GONE);
    }
    public void addLoaderLayout(int resId){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(resId,loaderContainer);

    }
}
