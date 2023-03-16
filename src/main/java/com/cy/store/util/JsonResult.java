package com.cy.store.util;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于接收前端的返回值
 * 将会以JSON的形式进行返回，而Json的设置通常需要三个属性，状态码state，描述信息message，响应数据data
 * @param <E>
 */
@Data
public class JsonResult<E> implements Serializable {
    /** 状态码 */
    private Integer state;
    /** 状态描述信息 */
    private String message;
    /** 响应数据 */
    private E data;


    /*super()表示执行父类的无参数构造器*/
    public JsonResult() {
        super();
    }
    public JsonResult(Integer state) {
        super();
        this.state = state;
    }
    /** 出现异常时调用 */
    public JsonResult(Throwable e) {
        super();
// 获取异常对象中的异常信息
        this.message = e.getMessage();
    }
    public JsonResult(Integer state, E data) {
        super();
        this.state = state;
        this.data = data;
    }
}


