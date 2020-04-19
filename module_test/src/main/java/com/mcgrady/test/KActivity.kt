package com.mcgrady.test

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.test_activity_sliding_icontab.*

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

        val rootView: View = find(android.R.id.content)

        android.R.id.content.onClick {
            toast("content")
        }

        test_tv_label.setOnClickListener{
            println("click label")
        }

        test_tv_label.text = "Label"

        toast("Hello World", Toast.LENGTH_LONG)

        println(this.isMobileConnect())
    }

    fun Int.onClick(click: ()->Unit) {
        val tmp = find<View>(this).apply {
            setOnClickListener{
                click()
            }
        }
    }

    fun Activity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    fun Context.isMobileConnect(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null) {
            return activeNetworkInfo.isAvailable
        }

        return false
    }

    fun <T: View> Activity.find(@IdRes id: Int): T {
        return findViewById<T>(id)
    }
}

