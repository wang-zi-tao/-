package cn.yuheng.server.util;

import lombok.Data;

/**
 * @author 王子陶
 * @version 1.0
 * @date 2020/4/5 下午10:03
 */
@Data
public class Result<T> {
    @lombok.Data
    public static class Data<T> {
        public static final String SUCCESS_MESSAGE = "success";
        public static final String Fail_MESSAGE = "fail";
        public static final String NOT_LOGGED_IN_MESSAGE = "not logged in";
        public static final String UNAUTHORIZED_MESSAGE = "unauthorized";
        public static final String UNKNOWN_MESSAGE = "unknown";

        private String message;
        private T entity;

        public Data(String message, T entity) {
            this.message = message;
            this.entity = entity;
        }
    }

    public Result(int code, String message, T entity) {
        this.code = code;
        this.data = new Data(message, entity);
    }

    public Result(int code, T entity) {
        this.code = code;
        String message;
        switch (code) {
            case 200:
                message = Data.SUCCESS_MESSAGE;
                break;
            case 401:
                message = Data.NOT_LOGGED_IN_MESSAGE;
                break;
            case 406:
                message = Data.UNAUTHORIZED_MESSAGE;
                break;
            case 500:
                message = Data.Fail_MESSAGE;
            default:
                message = Data.UNKNOWN_MESSAGE;
        }
        this.data = new Data(message, entity);
    }

    public static <K> Result<K> success(K entity) {
        return new Result<>(200, Data.SUCCESS_MESSAGE, entity);
    }

    public static <K> Result<K> success() {
        return new Result<>(200, Data.SUCCESS_MESSAGE, null);
    }

    public static <K> Result<K> fail(K entity) {
        return new Result<>(500, Data.Fail_MESSAGE, entity);
    }

    public static <K> Result<K> fail(K entity, String message) {
        return new Result<>(500, message, entity);
    }

    public static <K> Result<K> fail() {
        return new Result<>(500, Data.Fail_MESSAGE, null);
    }

    public static Result<Object> successOrFail(boolean success) {
        return success ? success(null) : fail(null);
    }

    public static <E> Result<E> successOrFail(E entity) {
        return entity != null ? success(entity) : fail(null);
    }

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;
    public static final int NOT_LOGGED_IN_CODE = 401;
    public static final int UNAUTHORIZED_CODE = 406;

    private int code;
    private Data data;
}