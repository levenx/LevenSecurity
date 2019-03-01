package com.leven.domain.common;

/**
 * Created by 孙乐进 on 2019/2/25.
 */
public enum  Status {

    /**返回码*/
    SUCCESS(200, "OK"),
    NO_USER(101,"NO_USER"),
    PASSWORD_ERROR(102,"PASSWORD_ERROR"),
    AUTHENTICATION_ERROR(103,"AUTHENTICATION_ERROR"),

    PARAM_NULL(302, "PARAM_NULL"),
    DATA_NULL(303,"DATA_NULL"),


    BAD_REQUEST(400, "Bad Request"),

    SYSTEM_ERROR(500,"SYSTEM_ERROR");

    private int code;
    private String describe;

    Status(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":")
                .append(code);
        sb.append(",\"describe\":\"")
                .append(describe).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
