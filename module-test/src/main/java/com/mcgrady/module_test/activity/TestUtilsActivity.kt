package com.mcgrady.module_test.activity

import android.content.Intent
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ResourceUtils
import com.mcgrady.module_test.R
import java.io.File

class TestUtilsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_utils)


        //动态获取Drawable资源
        ResourceUtils.getDrawableIdByName("ic_launcher")

        val btnTest = findViewById<Button>(R.id.btn_test)
        btnTest.performClick()
        btnTest.performLongClick()

        // 获取 Throwable 错误信息
        Log.getStackTraceString(Throwable("xxxx", null))

        //禁止系统截屏
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            obtainThumbnail()
        }

        // 从源Bitmap中根据 alpha 获取新的 bitmap 对象
        val bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888)
        bitmap.extractAlpha()

        

    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun obtainThumbnail() {
        //根据视频文件源获取缩略图
        ThumbnailUtils.createVideoThumbnail(File("file_path"), Size(10, 10), null)

        //根据Bitmap
        ThumbnailUtils.extractThumbnail(Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888), 10, 10)
    }

    override fun onBackPressed() {
//        super.onBackPressed()

        //拦截 Back 键，使 App 进入后台而不是关闭
        Intent(Intent.ACTION_MAIN).apply {
            this.addCategory(Intent.CATEGORY_HOME)
            startActivity(this)
        }
    }
}