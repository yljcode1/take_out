package com.yao.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.common.Response;
import com.yao.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author yao
* @description 针对表【employee(员工信息)】的数据库操作Service
* @createDate 2023-01-29 20:57:47
*/
public interface EmployeeService extends IService<Employee> {
    /**
     * 登录接口
     *
     * @param request  请求
     * @param employee 员工信息
     * @return 员工id
     */
    Response<Employee> doLogin(HttpServletRequest request, Employee employee);
    /**
     * 退出接口
     *
     * @param request 请求
     * @return 退出信息
     */
    Response<String> doLogout(HttpServletRequest request);
    /**
     * 添加员工信息
     *
     * @param request  请求
     * @param employee 员工信息
     * @return 添加结果
     */
    Response<String> addEmployee(HttpServletRequest request, Employee employee);
    /**
     * 分页查询员工信息
     *
     * @param page     当前页
     * @param pageSize 页面大小
     * @param name     名称
     * @return 员工信息
     */
    Response<IPage<Employee>> page(Integer page, Integer pageSize, String name);
    /**
     * 禁用员工
     *
     * @param id     员工id
     * @param status 禁用状态
     * @return 提示
     */
    Response<String> updateStatus(Employee employee);
}
