package com.mcgrady.xskeleton.http.interf

/**
 * Created by mcgrady on 2019-08-22.
 */
interface IBaseResponse {
    val resultCode: Int
    val errMsg: String?
}