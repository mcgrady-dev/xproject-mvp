package com.mcgrady.xskeleton.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import androidx.annotation.StyleRes
import com.mcgrady.xskeleton.R
import java.util.*

/**
 * Created by mcgrady on 2019-08-23.
 */
class LoadingDialog @JvmOverloads constructor(context: Context, @StyleRes themeResId: Int = R.style.LoadingDialog) : Dialog(context, themeResId) {
    init {
        Objects.requireNonNull(window).setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.view_loading_dialog)
        val params = window.attributes
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = params
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }
}