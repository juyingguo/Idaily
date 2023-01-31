package com.liuguangqiang.idaily.feature.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.liuguangqiang.idaily.R;
import com.liuguangqiang.idaily.databinding.ActivityStoryBinding;
import com.liuguangqiang.idaily.entity.Story;
import com.liuguangqiang.idaily.app.BaseActivity;
import com.liuguangqiang.idaily.adapter.WebViewAdapter;
import com.liuguangqiang.idaily.utils.HtmlUtil;

import java.util.ArrayList;

import timber.log.Timber;

public class StoryActivity extends BaseActivity {

    public static final String ARG_STORY = "ARG_STORY";

    private StoryViewModel viewModel;

    private ActivityStoryBinding binding;
    private WebViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new StoryViewModel();
        binding = ActivityStoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel.pushArguments(getIntent().getExtras());
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateBinding() {
    }

    private void initViews() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsingToolbarTitle);
        binding.collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.gray_dark));
        binding.collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.gray_dark));
        binding.collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsingToolbarTitle);
        binding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float maxOffset = (appBarLayout.getHeight() - binding.toolbar.getHeight());
                float ratio = (verticalOffset + maxOffset) / maxOffset;
                int color = ColorUtils.blendARGB(Color.WHITE, Color.BLACK, 1 - ratio);
                binding.toolbar.getNavigationIcon().setTint(color);
            }
        });

        adapter = new WebViewAdapter(new ArrayList<>());
        binding.rvWebView.setLayoutManager(new LinearLayoutManager(this));
        binding.rvWebView.setAdapter(adapter);

        viewModel.storyLiveData.observe(this, new Observer<Story>() {
            @Override
            public void onChanged(Story story) {
                binding.collapsingToolbarLayout.setTitle(story.title);
                Glide.with(StoryActivity.this).load(story.getImage()).into(binding.ivPic);
                adapter.addData(story);
                //webView页面数据加载
//                String htmlData = HtmlUtil.createHtmlData(story, false);
//                binding.wvNews.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
            }
        });
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) binding.toolbar.getLayoutParams();
        layoutParams.topMargin = getStatusBarHeight();
        Timber.d("status bar height:" + getStatusBarHeight());
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
