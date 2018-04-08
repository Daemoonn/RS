package com.example.rs.vo;

public class ServerResponse<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> ServerResponse<T> createSuccessResponse(T data) {
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setCode(200);
        serverResponse.setMsg("success");
        serverResponse.setData(data);
        return serverResponse;
    }

    public static <T> ServerResponse<T> createErrorResponse(Integer code, String msg) {
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setCode(code);
        serverResponse.setMsg(msg);
        return serverResponse;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
