package com.cui.video.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cui.video.R;
import com.cui.video.utils.StringUtils;

/**
 * Created by cuiyang on 16/6/2.
 */
public class SimpleProgressDialog extends Dialog {

    private static SimpleProgressDialog simpleProgressDialog = null;
    private static TextView progressTxt;

    public SimpleProgressDialog(Context context) {
        super(context);
    }

    public SimpleProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static SimpleProgressDialog createDialog(Context context) {
        simpleProgressDialog = new SimpleProgressDialog(context, R.style.SimpleProgressDialog);
        simpleProgressDialog.setContentView(R.layout.simple_progress_dialog);
        simpleProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        progressTxt = (TextView) simpleProgressDialog.findViewById(R.id.text1);
        return simpleProgressDialog;
    }

    public SimpleProgressDialog setMessage(String strMessage) {
        if (!StringUtils.isEmpty(strMessage)) {
            progressTxt.setVisibility(View.VISIBLE);
            progressTxt.setText(strMessage);
        } else {
            progressTxt.setVisibility(View.GONE);
        }
        return simpleProgressDialog;
    }
}
