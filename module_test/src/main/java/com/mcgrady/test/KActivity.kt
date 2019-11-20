package com.mcgrady.test

import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

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


    fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT): Unit {
        Toast.makeText(this, message, duration).show()
    }
}