package com.sun.comm.err;

/**
 * Created by qxh on 2015/11/26.
 */
public class ErrException extends Exception {

    String message; //定义String类型变量
    public ErrException(String ErrorMessagr) {  //父类方法
        message = ErrorMessagr;
    }
    public String getMessage(){   //覆盖getMessage()方法
        return message;
    }

}
