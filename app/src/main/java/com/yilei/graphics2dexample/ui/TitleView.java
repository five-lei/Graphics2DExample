package com.yilei.graphics2dexample.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilei.graphics2dexample.R;

/**
 * Created by lei on 2018/3/1.
 */

public class TitleView extends RelativeLayout{
    private int textColor = 0xffffff;
    private int drawablePadding = 0;//text默认的padding
    private int minViewWidth = 35;
    private int defaultSmallTextSize = 16;
    private int defaultTextSize = 20;
    private TextView mLeftBackText;//左边text
    private ImageView mLeftBackImage;//左边返回image
    private TextView mTitleText;
    private TextView mRightText;//右边第一个text
    private TextView mRightTextTwo;//右边第二个text
    private ImageView mRightImage;//右边第一个image
    private ImageView mRightImageTwo;//右边第二个image


    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * 初始化属性，创建组建并添加到容器中
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView);

        initLeftImage(context, typedArray);//创建左边的image
        initLeftText(context, typedArray);//创建左边的text
        initTitleText(context, typedArray);//创建标题
        initRightText(context, typedArray);//创建右边第一个text
        initRightTextTwo(context, typedArray);//创建右边第二个text
        initRightImage(context, typedArray);//创建右边第一个image
        initRightImageTwo(context, typedArray);//创建右边第二个image

        typedArray.recycle();
    }

    /**
     * 根据需求创建左边得ImageView
     * @param context
     * @param typedArray
     */
    private void initLeftImage(Context context, TypedArray typedArray){
        int leftImageDrawable = typedArray.getResourceId(R.styleable.TitleView_title_left_image, 0);
        if(leftImageDrawable != 0){
            LayoutParams params = initLayoutParams();
            mLeftBackImage = createImageView(context, R.id.tv_left_image, leftImageDrawable, params);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            addView(mLeftBackImage);
        }
    }

    /**
     * 创建左边的text
     * @param context
     * @param typedArray
     */
    private void initLeftText(Context context, TypedArray typedArray){
        int leftText = typedArray.getResourceId(R.styleable.TitleView_title_left_text, 0);
        //首先判断引入的自定义leftText资源id是否 > 0，大于0则是从value中取得，否则就是直接赋值得String
        CharSequence charSequence = leftText > 0 ? typedArray.getResources().getText(leftText)
                : typedArray.getString(R.styleable.TitleView_title_left_text);
        LayoutParams params = initLayoutParams();
        mLeftBackText = createTextView(context, R.id.tv_left_text, charSequence, params);
        setTextViewDrawable(typedArray, R.styleable.TitleView_left_text_drawable_left,
                R.styleable.TitleView_left_text_drawable_right, mLeftBackText);
        if(mLeftBackImage != null){
            params.addRule(RelativeLayout.RIGHT_OF, mLeftBackImage.getId());
        }else{
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }

        float textSize = getDimensionPixelSize(typedArray, R.styleable.TitleView_title_small_textSize, defaultSmallTextSize);
        mLeftBackText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mLeftBackText.setTextColor(getTextColorFromAttr(typedArray));
        addView(mLeftBackText);
    }

    /**
     * 添加Title标题TextView
     * @param context
     * @param typedArray
     */
    private void initTitleText(Context context, TypedArray typedArray){
        int titleText = typedArray.getResourceId(R.styleable.TitleView_title_name, 0);
        CharSequence charSequence = titleText > 0 ? typedArray.getResources().getText(titleText)
                : typedArray.getString(R.styleable.TitleView_title_name);
        LayoutParams params = initLayoutParams();
        mTitleText = createTextView(context, R.id.tv_title_name, charSequence, params);
        setTextViewDrawable(typedArray, R.styleable.TitleView_title_drawable_left,
                R.styleable.TitleView_title_drawable_right, mTitleText);

        float textSize = getDimensionPixelSize(typedArray, R.styleable.TitleView_title_textSize, defaultTextSize);
        mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mTitleText.setTextColor(getTextColorFromAttr(typedArray));

        int gravity = typedArray.getResourceId(R.styleable.TitleView_title_gravity, 0);
        if(gravity > 0){
            if(gravity == Gravity.LEFT){
                //根据LeftImage是否存在判断添加左边得控件 存在就添加LeftImage的右边，否则添加到LeftTextView的右边
                params.addRule(RelativeLayout.RIGHT_OF, mLeftBackImage == null ? mLeftBackText.getId() : mLeftBackImage.getId());
            }
        }else{
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
        }
        addView(mTitleText);
    }

    /**
     * 根据需求添加右边TextView
     * @param context
     * @param typedArray
     */
    private void initRightText(Context context, TypedArray typedArray){
        int rightText = typedArray.getResourceId(R.styleable.TitleView_title_right_text, 0);
        CharSequence charSequence = rightText > 0 ? typedArray.getResources().getText(rightText)
                : typedArray.getString(R.styleable.TitleView_title_right_text);

        LayoutParams params = initLayoutParams();
        mRightText = createTextView(context, R.id.tv_right_text, charSequence, params);
        setTextViewDrawable(typedArray, R.styleable.TitleView_right_text_drawable_left,
                R.styleable.TitleView_right_text_drawable_right, mRightText);

        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        float textSize = getDimensionPixelSize(typedArray, R.styleable.TitleView_title_small_textSize, defaultSmallTextSize);
        mRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mRightText.setTextColor(getTextColorFromAttr(typedArray));

        addView(mRightText);
    }

    private void initRightTextTwo(Context context, TypedArray typedArray){
        int rightTextTwo = typedArray.getResourceId(R.styleable.TitleView_title_right_text_two, 0);
        CharSequence charSequence = rightTextTwo > 0 ? typedArray.getResources().getText(rightTextTwo)
                : typedArray.getString(R.styleable.TitleView_title_right_text_two);

        LayoutParams params = initLayoutParams();
        mRightTextTwo = createTextView(context, R.id.tv_right_text_two, charSequence, params);
        setTextViewDrawable(typedArray, R.styleable.TitleView_right_text_two_drawable_left,
                R.styleable.TitleView_right_text_two_drawable_right, mRightTextTwo);

        if(mRightImage != null){
            params.addRule(RelativeLayout.LEFT_OF, mRightImage.getId());
        }else if(mRightImageTwo != null){
            params.addRule(RelativeLayout.LEFT_OF, mRightImageTwo.getId());
        }else if(mRightText != null){
            params.addRule(RelativeLayout.LEFT_OF, mRightText.getId());
        }else{
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }

        float textSize = getDimensionPixelSize(typedArray, R.styleable.TitleView_title_small_textSize, defaultSmallTextSize);
        mRightTextTwo.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mRightTextTwo.setTextColor(getTextColorFromAttr(typedArray));

        addView(mRightTextTwo);
    }

    /**
     * 如果title有右边显示图片的需求，创建第一个
     * @param context
     * @param typedArray
     */
    private void initRightImage(Context context, TypedArray typedArray){
        int rightImage = typedArray.getResourceId(R.styleable.TitleView_title_right_image, 0);
        if(rightImage != 0){
            LayoutParams params = initLayoutParams();
            mRightImage = createImageView(context, R.id.tv_right_image, rightImage, params);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            addView(mRightImage);
        }
    }

    private void initRightImageTwo(Context context, TypedArray typedArray){
        int rightImageTwo = typedArray.getResourceId(R.styleable.TitleView_title_right_text_two, 0);
        if(rightImageTwo != 0){
            LayoutParams params = initLayoutParams();
            mRightImageTwo = createImageView(context, R.id.tv_right_image_two, rightImageTwo, params);
            if(mRightImage != null){
                params.addRule(RelativeLayout.LEFT_OF, mRightImage.getId());
            }else{
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
            addView(mRightImageTwo);
        }

    }

    /**
     * 初始化宽高参数
     * @return
     */
    private LayoutParams initLayoutParams(){
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * 创建TextView
     * @param context
     * @param id
     * @param charSequence
     * @return
     */
    private TextView createTextView(Context context, int id, CharSequence charSequence, LayoutParams params){
        TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setId(id);
        textView.setText(charSequence);
        textView.setGravity(Gravity.CENTER);
        textView.setMinWidth((int) getPixelSizeByDp(minViewWidth));
        return textView;
    }

    /**
     * 创建ImageVIew
     * @param context
     * @param id
     * @param drawableId
     * @param params
     * @return
     */
    private ImageView createImageView(Context context, int id, int drawableId, LayoutParams params){
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setId(id);
//        imageView.setMinimumWidth((int) getPixelSizeByDp(minViewWidth));
        imageView.setImageResource(drawableId);
        return imageView;
    }

    /**
     * 设置TextView左右两边drawable
     * @param typedArray
     * @param leftDrawableStyleable
     * @param rightDrawableStyleable
     * @param textView
     */
    private void setTextViewDrawable(TypedArray typedArray, int leftDrawableStyleable, int rightDrawableStyleable, TextView textView){
        int leftDrawable = typedArray.getResourceId(leftDrawableStyleable, 0);
        int rightDrawable = typedArray.getResourceId(rightDrawableStyleable, 0);
        textView.setCompoundDrawablePadding((int) getPixelSizeByDp(drawablePadding));
        textView.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, 0, rightDrawable, 0);
    }

    /**
     * 判断textSize是否是直接赋值sp还是从values中引入
     * @param typedArray
     * @param styleable
     * @param defaultSize
     * @return
     */
    private float getDimensionPixelSize(TypedArray typedArray, int styleable, int defaultSize){
        int sizeStyleable = typedArray.getResourceId(styleable, 0);
        return sizeStyleable > 0 ? typedArray.getResources().getDimensionPixelSize(sizeStyleable)
                : typedArray.getDimensionPixelSize(styleable, (int) getPixelSizeBySp(defaultSize));
    }

    /**
     * 判断textColor是否是直接赋值还是从value中引入
     * @param typedArray
     * @return
     */
    private int getTextColorFromAttr(TypedArray typedArray){
        int textColorStyleable = typedArray.getResourceId(R.styleable.TitleView_title_textColor, 0);
        if(textColorStyleable > 0){
            return typedArray.getResources().getColor(textColorStyleable);
        }else{
            return typedArray.getColor(R.styleable.TitleView_title_textColor, textColor);
        }
    }

    /**
     * 根据手机得分辨率从dp单位转换成px像素
     * @param dp
     * @return
     */
    private float getPixelSizeByDp(int dp){
        Resources resources = this.getResources();
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    private float getPixelSizeBySp(int sp){
        Resources resources = this.getResources();
        final  float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;

    }
}
