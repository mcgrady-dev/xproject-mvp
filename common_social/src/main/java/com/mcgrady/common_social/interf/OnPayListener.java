package com.mcgrady.common_social.interf;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/1/9
 */

public interface OnPayListener {

    void onPaySuccess(String result);

    void onPayFailure(String result);

    void onPayConfirm(String result);

    void onPayCheck(String status);
}
