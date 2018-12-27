package com.mcgrady.common_social.interf;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2018/12/27
 */

public interface OnWebViewImageListener {

    /**
     * 点击webview上的图片，传入该缩略图的大图Url
     *
     * @param bigImageUrl
     */
    void showImagePreview(String bigImageUrl);
}
