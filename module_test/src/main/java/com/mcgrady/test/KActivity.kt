package com.mcgrady.test

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 *
 * Created by mcgrady on 2019-11-19.
 */
class KActivity : AppCompatActivity() {

    private var str: String? = "Unknow"

    init {
        str = "Hello World"
    }

    override fun onResume() {
        super.onResume()

        toast("Hello World", Toast.LENGTH_LONG)
    }


    fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }
}