package com.nyw.lune.app.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.nyw.lune.R;


public class EntranceView extends LinearLayout{

    private Context context;
    private Boolean isTop;
    private String textContent;
    private float padding;
    private int backgroundColor;
    private int drawableResourceId;
    private TextView textView;
    private ImageView imageView;
    private LinearLayout topLayout;
    private LinearLayout rightLayout;
    private int height = 0;
    private int width = 0;


    public EntranceView(Context context) {
        this(context, null);
    }

    public EntranceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EntranceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVar(context);
        initTypedArray(attrs, defStyleAttr);
        initLayout();
    }

    private void initVar(Context context) {
        this.context = context;
    }

    private void initTypedArray(@Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EntranceView, 0, defStyleAttr);
        textContent = array.getString(R.styleable.EntranceView_lune_text_content);
        isTop = array.getBoolean(R.styleable.EntranceView_lune_is_top, false);
        padding = array.getDimensionPixelSize(R.styleable.EntranceView_lune_padding, 0);
        width = array.getDimensionPixelSize(R.styleable.EntranceView_lune_drawable_width, 0);
        height = array.getDimensionPixelSize(R.styleable.EntranceView_lune_drawable_height, 0);
        backgroundColor = array.getColor(R.styleable.EntranceView_lune_bg, 0);
        drawableResourceId = array.getResourceId(R.styleable.EntranceView_lune_drawable, 0);
        array.recycle();
    }

    private void initLayout() {
        LayoutInflater.from(context).inflate(R.layout.layout_entrance_view, this);
        setBackgroundColor(backgroundColor);
        topLayout = findViewById(R.id.ll_top);
        rightLayout = findViewById(R.id.ll_right);
        if (isTop) {
            topLayout.setVisibility(VISIBLE);
            rightLayout.setVisibility(GONE);
            textView = findViewById(R.id.tv_top);
            imageView = findViewById(R.id.iv_top);
            textView.setPadding(0, (int) padding, 0, 0);
            LayoutParams para = (LayoutParams) imageView.getLayoutParams();
            para.height = width;
            para.width = height;
            imageView.setLayoutParams(para);
        } else {
            rightLayout.setVisibility(VISIBLE);
            topLayout.setVisibility(GONE);
            textView = findViewById(R.id.tv_right);
            imageView = findViewById(R.id.iv_right);
            textView.setPadding(0, 0, (int) padding, 0);
            LayoutParams para = (LayoutParams) imageView.getLayoutParams();
            para.height = width;
            para.width = height;
            imageView.setLayoutParams(para);
        }
        textView.setText(textContent);
        imageView.setBackgroundResource(drawableResourceId);
    }
}
