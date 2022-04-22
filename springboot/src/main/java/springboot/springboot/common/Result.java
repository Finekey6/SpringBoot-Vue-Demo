package springboot.springboot.common;

/**
 * json包装类
 * @param <T>
 */

/**
 * 用于设置返回体中的信息
 * 在浏览器F12界面的netwoek的Response中可见方法设置的信息
 * @param <T>
 */
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    /**
     * 用于设置前端返回体中的信息
     * 在前端F12界面的netwoek的Response中可见方法设置的信息
     * @return 返回前端Response中的信息
     */
    public static Result success() {
        Result result = new Result<>();
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}