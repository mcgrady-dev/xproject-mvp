package com.mcgrady.xskeleton.http;

/**
 * Created by mcgrady on 2019-08-18.
 */
public interface Api {
    String APP_DOMAIN = "http://192.168.0.201:9095/PLVSApp";

    String REQUEST_SUCCESS = "200";

    // 错误码
    String ERROR_USER_INCORRECT = "1001";
    String ERROR_PHONE_EXIST = "1019";
    String VALIDATION_OVERTIME = "1020";
    String NOT_PERMISSIONS = "403";
    String API_EXCPTION = "500";
    String REQUEST_PARAM_INCORRECT = "1000";
    String VADATION_INCORRECT = "1002";
    String FIRST_STOP_AFTER_DELETE = "1003";
    String UPLOADING_FILE_OVERSIZE = "1004";
    String EDIT_COMMO_LEVEL = "1005";
    String COMMO_CLASS_RE = "1006";
    String COMMO_TRADEMARK_RE = "1007";
    String LOGISTICS_TEMPLATE_RE = "1008";
    String COMMO_ATTRIBUTE_RE = "1009";
    String USER_NOT_REGISTER = "1010";
    String USER_ALREADY_ADD = "1011";
    String COMMO_CLASS_DELETE = "1012";
    String COMMO_TRANDMARK_DELETE = "1013";
    String COMMO_STORE_OFF = "1014";
    String USER_FORBIDDEN = "1015";
    String ONLY_FIVE_OPERATION = "1024";
    String NAME_ONE_MODIFICATION = "1017";
    String NAME_EXIST = "1018";
    String FIVE_ERR_PWDD = "1016";
    String PWD_NOT_LIKE = "1053";
    String BINDPHONELIKE = "1025";
    String MODIFICOUNTTHREE = "1026";
    String MODIFIPHONEOVERTHREE = "1027";
    String NOTLOGININ = "1052";
    String PASSOWRDINCOREECT = "1055";
    String NOTMATCHIMGBANKCARD = "1041";
    String IDCARDEXIT = "1038";
    String STORENAMEEXIT = "1028";
    String COMPANYNAMEEXIT = "1030";
    String CODEEXIT = "1029";
    String BANKREPETION = "1074";
    String REPETITION = "1032";
    String SERIVENULL ="1044";
    String LIMITMESSAGE = "1082";
    String NO_DEAL = "1081";
}
