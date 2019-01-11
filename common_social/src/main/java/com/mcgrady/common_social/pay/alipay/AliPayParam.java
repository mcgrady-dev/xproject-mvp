package com.mcgrady.common_social.pay.alipay;

/**
 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 *
 * orderInfo 的获取必须来自服务端；
 *
 * @author: mcgrady
 * @date: 2019/1/9
 */
public class AliPayParam {

    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public String appId;

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public String pId;

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public String targetId = "";

    /**
     *  pkcs8 格式的商户私钥。
     *
     * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * 	RSA2_PRIVATE。
     *
     * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public String rsa_private;
    public String rsa2_private;

    /**
     * 支付订单参数
     */

    public String biz_content;
    public String charset;
    public String method;
    public String sign_type;
    public String timestamp;
    public String version;

    private AliPayParam(Builder builder) {
        appId = builder.appId;
        pId = builder.pId;
        targetId = builder.targetId;
        rsa_private = builder.rsa_private;
        rsa2_private = builder.rsa2_private;
        biz_content = builder.biz_content;
        charset = builder.charset;
        method = builder.method;
        sign_type = builder.sign_type;
        timestamp = builder.timestamp;
        version = builder.version;
    }


    public static final class Builder {
        private String appId;
        private String pId;
        private String targetId;
        private String rsa_private;
        private String rsa2_private;
        private String biz_content;
        private String charset;
        private String method;
        private String sign_type;
        private String timestamp;
        private String version;

        public Builder() {
        }

        public Builder appId(String val) {
            appId = val;
            return this;
        }

        public Builder pId(String val) {
            pId = val;
            return this;
        }

        public Builder targetId(String val) {
            targetId = val;
            return this;
        }

        public Builder rsa_private(String val) {
            rsa_private = val;
            return this;
        }

        public Builder rsa2_private(String val) {
            rsa2_private = val;
            return this;
        }

        public Builder biz_content(String val) {
            biz_content = val;
            return this;
        }

        public Builder charset(String val) {
            charset = val;
            return this;
        }

        public Builder method(String val) {
            method = val;
            return this;
        }

        public Builder sign_type(String val) {
            sign_type = val;
            return this;
        }

        public Builder timestamp(String val) {
            timestamp = val;
            return this;
        }

        public Builder version(String val) {
            version = val;
            return this;
        }

        public AliPayParam create() {
            return new AliPayParam(this);
        }
    }
}
