package com.ongoshop.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ongoshop.R
import java.util.*

class MainActivity : AppCompatActivity() {
    var mContaxt: Context? = null
    var ivLogo: ImageView? = null
    var timer: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContaxt = this
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                val i = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }, 3000)
        ivLogo = findViewById(R.id.ivLogo)
    }
}