package com.mcgrady.module_test.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.BaseMmsColumns.CONTENT_CLASS
import com.mcgrady.module_test.R
import com.mcgrady.module_test.activity.ui.login.LoginActivity
import com.mcgrady.module_test.hencoder.PracticeDrawActivity
import org.simpleframework.xml.transform.Transform

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, CustomViewActivity::class.java))
        finish()
    }
}
