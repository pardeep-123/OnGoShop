package com.ongoshop.utils.others;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ongoshop.R;


public class CustomProgressBar {
    private Dialog mDialog;

    public CustomProgressBar(Context context) {

        if (mDialog == null) {
            mDialog = new Dialog(context);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.custom_progress_dialog);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Window window = mDialog.getWindow();
            if (window == null)
                return;
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
            mDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mDialog.setCancelable(false);

        }
    }

    public void hideProgressBar() {
        showHideProgressBar(false/*, null*/);
    }

    public void showHideProgressBar(boolean showHide/*, String label*/) {

        if (mDialog == null)
            return;
        if (showHide) {
            mDialog.show();
        } else {
            if (mDialog.isShowing()) {
                try {
                    mDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}