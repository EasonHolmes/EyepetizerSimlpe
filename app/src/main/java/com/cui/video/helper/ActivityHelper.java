package com.cui.video.helper;

import android.animation.Animator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.cui.video.AbstractBaseActivity;
import com.cui.video.BuildConfig;
import com.cui.video.R;
import com.cui.video.utils.LogUtils;
import com.cui.video.utils.StringUtils;
import com.cui.video.widget.SimpleProgressDialog;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;

import static android.widget.Toast.makeText;

/**
 * 人脉通 Activity 帮助类 onclik事件 dialog帮助
 *
 * @since 2014-6-17
 */
public final class ActivityHelper {

    /**
     * 弹出对话框点击事件，没有任何操作，仅关闭对话框
     */
    public final static DialogInterface.OnClickListener ALERT_DIALOG_OK_CLICK = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    /**
     * 弹出对话框点击事件，没有任何操作，仅取消对话框的显示
     */
    public final static DialogInterface.OnClickListener ALERT_DIALOG_CANCEL_CLICK = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    };
    /**
     * 弹出对话框点击事件，没有任何操作，仅取消对话框的显示
     */
    public final static DialogInterface.OnClickListener ALERT_DIALOG_CANCEL_CLICK2 = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };

    /**
     * startActivityForResult 使用的 resultCode
     */
    public final static class ResultCodeConstant {

    }

    /**
     * 从修改入口进入 修改采购和分类中有用
     */
    public static boolean COME_FROM_UPDATE = false;


    /**
     * 图片文件的 Type 格式
     */
    public final static String IMAGE_UNSPECIFIED = "image/*";

    protected final Handler mHandler;

    /**
     * 当前Activity
     */
    private AbstractBaseActivity mCurrentActivity;

    /**
     * 如果当前Activity是被内嵌的，返回父的Activity
     */
    private AbstractBaseActivity mMainActivity;

    public ActivityHelper(AbstractBaseActivity currentActivity) {
        mCurrentActivity = currentActivity;
        mHandler = currentActivity.mHandler;
        if (mMainActivity == null) {
            mMainActivity = mCurrentActivity;
        }
    }

    public AbstractBaseActivity getMainActivity() {
        return mMainActivity;
    }


    /**
     * 弹出进度对话框 <br/>
     */
    private SimpleProgressDialog simple_loading_dialog;

    /**
     * 显示简短的文字弹出框，正在加载中...
     */
    public final synchronized void showSimpleLoadDialog() {
        showSimpleLoadDialog(R.string.text_loading);
    }

    /**
     * 显示简短的文字弹出框，正在加载中...
     *
     * @param resId
     */
    public final synchronized void showSimpleLoadDialog(int resId) {
        showSimpleLoadDialog(getMainActivity().getString(resId));
    }

    /**
     * 显示简短的文字弹出框，正在加载中...
     *
     * @param msg
     */
    public final synchronized void showSimpleLoadDialog(final String msg) {
        mHandler.post(() -> {
            if (simple_loading_dialog != null) {
                simple_loading_dialog.dismiss();
                simple_loading_dialog = null;
            }
            if (getMainActivity() != null && !getMainActivity().isFinishing()) {
                simple_loading_dialog = SimpleProgressDialog.createDialog(getMainActivity());
                if (StringUtils.isNotEmpty(msg)) {
                    simple_loading_dialog.setMessage(msg);
                }
                simple_loading_dialog.setCancelable(true);
                simple_loading_dialog.setCanceledOnTouchOutside(false);
                if (mCurrentActivity != null && mCurrentActivity instanceof AbstractBaseActivity) {
                    simple_loading_dialog.setCancelMessage(((AbstractBaseActivity) mCurrentActivity).getSimpleLoadDialogCancelMessage());
                }
                simple_loading_dialog.show();
            }
        });
    }
    /**
     * 取消加载中弹出框
     */
    public final synchronized void dismissSimpleLoadDialog() {
        mHandler.post(() -> {
            if (simple_loading_dialog != null && simple_loading_dialog.isShowing()) {
                simple_loading_dialog.dismiss();
            }
            simple_loading_dialog = null;
        });
    }

    public final void toast(int resId) {
        toast(mCurrentActivity.getString(resId));
    }

    public final void toast(final String text) {
        if (StringUtils.isEmpty(text))
            return;
        mCurrentActivity.runOnUiThread(() -> {
            dismissSimpleLoadDialog();
            makeText(mCurrentActivity, text, Toast.LENGTH_SHORT).show();
        });
    }

    public final void dialogMessage(int messageId) {
        dialogMessage(mCurrentActivity.getString(messageId));
    }

    public final void dialogMessage(final String message) {
        if (StringUtils.isEmpty(message)) return;

        mHandler.post(() -> {
            AlertDialog.Builder b = new AlertDialog.Builder(mCurrentActivity);
            b.setMessage(message);
            b.setCancelable(false);
            b.setPositiveButton(R.string.text_know, ActivityHelper.ALERT_DIALOG_OK_CLICK);
            if (mCurrentActivity != null && !mCurrentActivity.isFinishing()) {
                b.show().setCanceledOnTouchOutside(false);
            }
        });
    }

    public final void dialogMessageErrorByService(final String error) {
        if (error == null || StringUtils.isEmpty(error)) return;

        mHandler.post(() -> {
            AlertDialog.Builder b = new AlertDialog.Builder(mCurrentActivity);
            b.setMessage(error);
            b.setCancelable(false);
            b.setPositiveButton(R.string.text_know, ActivityHelper.ALERT_DIALOG_OK_CLICK);
            if (mCurrentActivity != null && !mCurrentActivity.isFinishing()) {
                b.show().setCanceledOnTouchOutside(false);
            }
        });
    }

    public final void ErrordialogMessageByMine(final String message) {
        dismissSimpleLoadDialog();
        if (StringUtils.isEmpty(message) || !BuildConfig.DEBUG) return;
        mHandler.post(() -> {
            AlertDialog.Builder b = new AlertDialog.Builder(mCurrentActivity);
            b.setMessage(message);
            b.setCancelable(false);
            b.setPositiveButton(R.string.text_know, ActivityHelper.ALERT_DIALOG_OK_CLICK);
            if (mCurrentActivity != null && !mCurrentActivity.isFinishing()) {
                b.show().setCanceledOnTouchOutside(false);
            }
        });
    }

    public final void dialogMessageAndTitle(final String title, final String message) {
        if (StringUtils.isEmpty(message) || StringUtils.isEmpty(title)) return;

        mHandler.post(() -> {
            AlertDialog.Builder b = new AlertDialog.Builder(mCurrentActivity);
            b.setMessage(message);
            b.setCancelable(false);
            b.setTitle(title);
            b.setPositiveButton(R.string.text_know, ActivityHelper.ALERT_DIALOG_OK_CLICK);
            if (mCurrentActivity != null && !mCurrentActivity.isFinishing()) {
                b.show().setCanceledOnTouchOutside(false);
            }
        });
    }

    public final Animator startCirCulerAnimation(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = ViewAnimationUtils.createCircularReveal(v, v.getWidth() / 2, v.getHeight() / 2,
                    (float) Math.hypot(v.getWidth(), v.getHeight()), 0);
            animator.setDuration(650);
            animator.setInterpolator(new DecelerateInterpolator(2f));
            animator.start();
            return animator;
        }
        return null;
    }
}
