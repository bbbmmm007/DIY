package com.zhny.computer.controller;

import com.zhny.computer.service.ex.*;
import com.zhny.computer.service.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;


/**
 * 控制层类的基类
 */
public class BaseController {
    public static final int SUCCESS = 200;

    @ExceptionHandler(RuntimeException.class)
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>();
        if (e instanceof DataSelectException) {
            result.setState(4001);
            result.setMessage("数据查询异常");
        }else if (e instanceof DataMatchException) {
            result.setState(4002);
            result.setMessage("数据匹配错误");
        }else if (e instanceof DataInsertException) {
            result.setState(4003);
            result.setMessage("数据插入异常");
        }else if (e instanceof DataUpdateException) {
            result.setState(4004);
            result.setMessage("数据更新异常");
        }else if (e instanceof DataDeleteException) {
            result.setState(4005);
            result.setMessage("数据删除异常");
        }else if (e instanceof DataAccessException) {
            result.setState(4006);
            result.setMessage("数据访问异常");
        }
        return result;
    }


    // 从 session 获取 uid，增加空值处理
    protected final Integer getUidFromSession(HttpSession session) {
        Object uid = session.getAttribute("uid");
        return (uid != null) ? Integer.valueOf(uid.toString()) : null; // 返回 null 如果 uid 不存在
    }
    // 从 session 获取 username，增加空值处理
    protected final String getUsernameFromSession(HttpSession session) {
        Object username = session.getAttribute("username");
        return (username != null) ? username.toString() : null; // 返回 null 如果 username 不存在
    }


    protected final Integer getAmidFromSession(HttpSession session) {
        Object amid = session.getAttribute("amid");
        return (amid != null) ? Integer.valueOf(amid.toString()) : null;

    }
    protected final String getAdminNameFromSession(HttpSession session) {

        Object adminName = session.getAttribute("adminName");
        return (adminName != null) ? adminName.toString() : null;
    }
}
