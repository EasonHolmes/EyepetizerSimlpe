package com.cui.video.helper;

import android.animation.Animator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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

    private final static String WEIBO_PACKAGENAME = "com.sina.weibo";
    private final static String WEIXIN_PACKAGENAME = "com.tencent.mm";
    private final static String WEIXIN_CLASSNAME_FRIEND = "com.tencent.mm.ui.tools.ShareImgUI";
    private final static String QQ_PACKAGENAME = "com.tencent.mobileqq";
    private final static String QQ_CLASSNAME = "com.tencent.mobileqq.activity.JumpActivity";


    /**
     * 弹出对话框点击事件，没有任何操作，仅关闭对话框
     */
    public final static DialogInterface.OnClickListener ALERT_DIALOG_OK_CLICK = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    };


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

    public final void errordialogMessageByMine(final String message) {
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

    public boolean isInstallApplication(String packageName) {
        try {
            PackageManager pm = mCurrentActivity.getPackageManager();
            pm.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void shareToWeChat(String playUrl) {
        if (isInstallApplication(WEIXIN_PACKAGENAME)) {
            share(WEIXIN_PACKAGENAME, WEIXIN_CLASSNAME_FRIEND, playUrl);
        } else {
            Toast.makeText(mCurrentActivity, "未安装微信", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareToWeibo(String playUrl) {
        if (isInstallApplication(WEIBO_PACKAGENAME)) {
            share(WEIBO_PACKAGENAME, "", playUrl);
        } else {
            Toast.makeText(mCurrentActivity, "未安装微博", Toast.LENGTH_SHORT).show();

        }
    }

    public void shareToQQ(String playUrl) {
        if (isInstallApplication(QQ_PACKAGENAME)) {
            share(QQ_PACKAGENAME, QQ_CLASSNAME, playUrl);
        } else {
            Toast.makeText(mCurrentActivity, "未安装QQ", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareMore(String playUrl) {
        share("", "", playUrl);
    }

    private void share(String packages, String className, String playUrl) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_SUBJECT, mCurrentActivity.getResources().getString(R.string.app_name));
        intent.putExtra(Intent.EXTRA_TEXT, playUrl);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!TextUtils.isEmpty(packages)) {
            intent.setPackage(packages);
        }
        if (!TextUtils.isEmpty(className)) {
            //指定打开的class，就不会出现让用户选择的界面了
            intent.setClassName(packages, className);
        }
        mCurrentActivity.startActivity(Intent.createChooser(intent, mCurrentActivity.getResources().getString(R.string.app_name)));
    }

}
