package org.common.enums;

public enum ResponseCodeEnum {

    CODE_200(200,"成功"),
    CODE_400(400,"请求错误"),
    CODE_401(401,"未授权"),
    CODE_403(403,"禁止访问"),
    CODE_404(404,"找不到资源"),
    CODE_405(405,"请求方法不支持"),
    CODE_406(406,"请求头信息错误"),
    CODE_408(408,"请求超时"),
    CODE_409(409,"请求冲突"),
    CODE_410(410,"请求过期"),
    CODE_411(411,"请求长度错误"),
    CODE_412(412,"请求错误"),
    CODE_413(413,"请求数据过大"),
    CODE_414(414,"请求地址过长"),
    CODE_415(415,"请求格式错误"),
    CODE_416(416,"请求范围错误"),
    CODE_500(500,"服务器内部错误"),
    CODE_600(600,"参数类型错误"),
    CODE_601(601,"主键冲突");

    private Integer code;
    private String desc;

    ResponseCodeEnum (Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }

}
