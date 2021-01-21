package com.ongoshop.utils.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.ongoshop.R


import java.util.*

class ProgressHUD : Dialog {
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, theme: Int) : super(context, theme) {}

    companion object {
        fun create(context: Context
        ): ProgressHUD {
            val dialog = ProgressHUD(context, R.style.AppTheme)
            dialog.setTitle("fdfdf")
            dialog.setContentView(R.layout.progressview)

         /*   if (message == null || message.isEmpty()) {
                dialog.findViewById<View>(R.id.message).visibility = View.GONE
            } else {
                val txt = dialog.findViewById<View>(R.id.message) as TextView
                txt.text = message
            }
*/
            dialog.setCancelable(true)
            Objects.requireNonNull<Window>(dialog.getWindow())
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.attributes.gravity = Gravity.CENTER
            dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            val lp = dialog.window!!.attributes
            lp.dimAmount = 0.2f
            dialog.window!!.attributes = lp
            dialog.getWindow ()!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
               dialog.show()
            return dialog
        }
    }
}