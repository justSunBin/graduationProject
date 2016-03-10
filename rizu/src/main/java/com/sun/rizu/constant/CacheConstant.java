package com.sun.rizu.constant;

/**
 * Created by qxh on 2015/11/9.
 */
public class CacheConstant {

    /*模拟SESSION产生的数据key缓存前缀，可以在redis中查询*/
    final public static String SID_KEY_PREFIX = "SID_KEY";

    /**
     * 短信：注册使用的验证码key缓存前缀，可以在redis中查询
     */
    final public static String KEY_SMS_MRCODE = "SMS_MRCODE";


    /**
     * 短信：登录后使用的验证码key缓存前缀，可以在redis中查询
     */
    final public static String KEY_SMS_VERYCODE = "SMS_VERYCODE";




    /**
     * 所有car_model表配置项缓存3天，只有清空之后才能重新加载
     */
    final public static String KEY_CAR_MODEL_ALL = "CAR_MODEL_ALL_"; //缓存所有car_model表的数据的KEY的前缀，如：CAR_MODEL_ALL_xxx
    final public static String KEY_CAR_MODEL_TIME = "CAR_MODEL_TIME"; /*car_model表缓存时间的KEY*/
    final public static int VAL_CAR_MODEL_TIME = 3600*3*24; /*缓存3天，3天后重新加载*/

    final public static String KEY_CAR_MODEL = "CAR_MODEL_"; /*车系item缓存时间的KEY的前缀，如：CAR_MODEL_xxx*/
    final public static String KEY_CAR_MODEL_LIST = "CAR_MODEL_LIST_"; /*某个品牌下属车系列表缓存的KEY,如：CAR_MODEL_LIST_[品牌ID]*/

    final public static String KEY_CAR_BRAND = "CAR_BRAND_"; /*汽车品牌item缓存缓存时间的KEY前缀*/
    final public static String KEY_CAR_BRAND_LIST = "CAR_BRAND_LIST";/*品牌列表缓存的KEY*/


    //cfg_开头数据表的缓存时间
    final public static int VAL_CFG_TIME = 3600*30*24; /*缓存30天，30天后重新加载*/

    /**
     * 所有cfg_setting表配置项缓存30天，清空之后才能重新加载
     *
     */
    final public static String KEY_CFG_SETTING_TIME = "CFG_SETTING_TIME"; /*cfg_setting表缓存时间的KEY*/
    final public static String KEY_CFG_SETTING = "CFG_SETTING_"; /*cfg_setting表每一项缓存的KEY前缀，比如：CFG_SETTING_[cfg_key]*/


    /**
     * 所有cfg_bank表配置项缓存30天，清空之后才能重新加载
     *
     */
    final public static String KEY_CFG_BANK_TIME = "CFG_BANK_TIME"; /*cfg_setting表缓存时间的KEY*/
    final public static String KEY_CFG_BANK = "CFG_BANK_"; /*cfg_bank表每一项缓存的KEY前缀，比如：CFG_BANK_[bank_id]*/
    final public static String KEY_CFG_BANK_ALLLIST = "CFG_BANK_ALLLIST"; //银行所有列表

    //所有cfg_area标的缓存项
    final public static String KEY_CFG_AREA_TIME = "CFG_AREA_TIME"; /*cfg_setting表缓存时间的KEY*/
    final public static String KEY_CFG_AREA = "CFG_AREA_"; /*cfg_bank表每一项缓存的KEY前缀，比如：CFG_AREA_[id]*/

    final public static String KEY_CFG_AREA_PROVINCELIST = "CFG_AREA_PROVINCELIST"; /*cfg_bank表所有省份*/
    final public static String KEY_CFG_AREA_CITYLIST = "CFG_AREA_CITYLIST_"; /*cfg_bank表所有省份下的城市列表*/

    //所有cfg_payment的缓存项
    final public static String KEY_CFG_PAYMENT_TIME = "CFG_PAYMENT_TIME"; /*cfg_payment表缓存时间的KEY*/
    final public static String KEY_CFG_PAYMENT = "CFG_PAYMENT_"; /*cfg_payment表每一项缓存的KEY前缀，比如：CFG_PAYMENT_[id]*/

    //所有cfg_smsplatform表配置项缓存30天，清空之后才能重新加载
    final public static String KEY_CFG_SMSPLATFORM_TIME = "CFG_SMSPLATFORM_TIME"; /*cfg_smsplatform表缓存时间的KEY*/
    final public static String KEY_CFG_SMSPLATFORM = "CFG_SMSPLATFORM_"; /*cfg_smsplatform表每一项缓存的KEY前缀，比如：CFG_SMSPLATFORM_[id]*/
    final public static String KEY_CFG_SMSPLATFORM_ALLLIST = "CFG_SMSPLATFORM_ALLLIST"; //短信支付平台所有列表

    //API接口锁操作的缓存
    final public static String KEY_OPEN_LOCCK = "OPEN_LOCK"; //锁操作的KEY
    final public static int KEY_OPEN_LOCCK_TIME = 3*60; /*锁操作缓存时间*/

    //后台锁操作的缓存
    final public static String KEY_RUN_LOCCK = "RUN_LOCK"; //锁操作的KEY
    final public static int KEY_RUN_LOCCK_TIME = 3*60; /*锁操作缓存时间*/
}
