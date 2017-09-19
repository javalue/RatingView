package com.trans.transitionsdemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/19.
 */

public class RatingView extends LinearLayout {

    private int RATE_COUNT = 5;
    private int ANIM_DURATION = 400;
    private int ITEM_MARGIN = 30;
    private AnimatorSet mAnimatorSet = new AnimatorSet();
    private ArrayList<ImageView> mRatingImages = new ArrayList<>();
    private ArrayList<ObjectAnimator> objectAnimators = new ArrayList<>();

    public RatingView(Context context) {
        super(context);
        init(context);
    }

    public RatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        for (int i = 0; i < RATE_COUNT; i++) {
            RelativeLayout parent = new RelativeLayout(context);
            parent.setBackgroundResource(R.drawable.icon_star_n);
            ImageView img = createImage(i, context);
            mRatingImages.add(img);
            parent.addView(img);

            LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMarginEnd(ITEM_MARGIN);
            addView(parent, lp);

            ObjectAnimator objectAnimator = createAlphaAnim(img);
            objectAnimators.add(objectAnimator);
            if (i == 0) {
                mAnimatorSet.play(objectAnimator);
            } else if (i > 0) {
                mAnimatorSet.play(objectAnimator).after(objectAnimators.get(i - 1));
            }
        }
        mAnimatorSet.addListener(animatorListener);
        mAnimatorSet.start();
    }

    private ImageView createImage(int i, Context context) {
        ImageView img = new ImageView(context);
        img.setImageResource(R.drawable.icon_star_m);
        img.setAlpha(0f);
        img.setOnClickListener(mOnClickListener);
        img.setTag(i);
        return img;
    }

    private ObjectAnimator createAlphaAnim(View obj) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(obj, "alpha", 0f, 1f);
        objectAnimator.setDuration(ANIM_DURATION);
        objectAnimator.setInterpolator(new LinearInterpolator());
        return objectAnimator;
    }

    private Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        long start = 0;

        @Override
        public void onAnimationStart(Animator animator) {
            start = System.currentTimeMillis();
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            Log.i("time", System.currentTimeMillis() - start + "");
            for (ImageView rate : mRatingImages) {
                rate.setAlpha(0f);
            }
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            for (int i = 0; i <= pos; i++) {
                mRatingImages.get(i).setAlpha(1f);
            }
            Toast.makeText(getContext(), pos + 1 + "", Toast.LENGTH_SHORT).show();
        }
    };
}
