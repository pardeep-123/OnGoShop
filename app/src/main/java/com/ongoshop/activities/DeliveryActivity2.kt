package com.ongoshop.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ongoshop.R

class DeliveryActivity2 : AppCompatActivity() {
    var ivBack: ImageView? = null
    var mContext: Context? = null
    var btnDelivery: Button? = null
    var btnDelivery2: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery2)
        mContext = this
        ivBack = findViewById(R.id.ivBack)
        ivBack!!.setOnClickListener(View.OnClickListener { finish() })
        btnDelivery = findViewById(R.id.btnDelivery)
        /* btnDelivery2=findViewById(R.id.btnDelivery2);
        btnDelivery2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });*/

        btnDelivery!!.setTag(1)
        btnDelivery!!.text = getString(R.string.board_for_delivery)
        btnDelivery!!.setOnClickListener(View.OnClickListener { v ->
            if (btnDelivery!!.text.toString() == getString(R.string.complete_delivery)) {
                btnDelivery!!.text = getString(R.string.complete_delivery)
                val intent = Intent(mContext, HomeActivity::class.java)
                startActivity(intent)
                //openNewActivity();
                v.tag = 1 //pause
            } else {
                val status = v.tag as Int
                if (status == 1) {
                    btnDelivery!!.text = getString(R.string.board_for_delivery)
                    v.tag = 0 //pause
                } else {
                    btnDelivery!!.text = getString(R.string.complete_delivery)

                    //openNewActivity();
                    v.tag = 1 //pause
                }
            }
        })
    }

    fun openNewActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}