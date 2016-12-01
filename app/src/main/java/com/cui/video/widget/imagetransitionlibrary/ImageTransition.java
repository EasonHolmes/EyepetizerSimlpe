/*
 * Copyright 2016 Vikram Kakkar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cui.video.widget.imagetransitionlibrary;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * A {@link android.transition.Transition} based on {@link ChangeBounds}
 * that provides animation support between a circular
 * and rectangular ImageView (implemented as {@link TransitionImageView})
 * residing in two different activities.
 * Functionality is provided by {@link ImageTransitionCompatHelper}.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class ImageTransition extends ChangeBounds {

    //private static final String PROPNAME_ROUNDING_PROGRESS = "itl:changeBounds:roundingProgress";

    public ImageTransition() {
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public String[] getTransitionProperties() {
        // pass parent's properties...
        return ImageTransitionCompatHelper.getTransitionProperties(super.getTransitionProperties());
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        ImageTransitionCompatHelper.captureValues(transitionValues.view, transitionValues.values);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        ImageTransitionCompatHelper.captureValues(transitionValues.view, transitionValues.values);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        // pass parent's Animator
        return ImageTransitionCompatHelper.createAnimator(super.createAnimator(sceneRoot, startValues, endValues),
                sceneRoot, endValues.view, startValues.values, endValues.values);
    }
}
