package com.ongoshop.utils.others;


import android.app.Activity;

import androidx.annotation.IntDef;
import androidx.annotation.StringRes;

import com.sdsmdg.tastytoast.TastyToast;
import com.ongoshop.utils.others.MyApplication;

/**
 * {@link TastyToastUtils}
 */

public class TastyToastUtils {

    public static void shortToast(@StringRes int text) {
        shortToast(MyApplication.Companion.getInstance().getString(text));
    }

    public static void shortToast(String text) {
        show(text, TastyToast.LENGTH_SHORT);
    }

    public static void longToast(@StringRes int text) {
        longToast(MyApplication.Companion.getInstance().getString(text));
    }

    public static void longToast(String text) {
        show(text, TastyToast.LENGTH_LONG);
    }

    public static void makeToast(Activity activity, String text, int toastType) {
       TastyToast.makeText(activity, text,  TastyToast.LENGTH_SHORT, toastType).show();
    }

    private static void show(String text, @ToastLength int length) {
      //  makeToast(text, length).show();
    }

    @IntDef({TastyToast.LENGTH_LONG, TastyToast.LENGTH_SHORT})
    private @interface ToastLength {

    }
}
