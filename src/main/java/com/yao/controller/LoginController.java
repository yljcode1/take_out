package com.yao.controller;

import com.yao.common.Response;
import com.yao.entity.Employee;
import com.yao.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录接口
 *
 * @date: 2023-01-29
 * @author: yao
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class LoginController {

    private final EmployeeService employeeService;

    /**
     * 登录接口
     *
     * @param request  请求
     * @param employee 员工信息
     * @return 员工id
     */
    @PostMapping("/login")
    public Response<Employee> doLogin(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.doLogin(request, employee);
    }

    /**
     * 退出接口
     *
     * @param request 请求
     * @return 退出信息
     */
    @PostMapping("/logout")
    public Response<String> doLogout(HttpServletRequest request) {
        return employeeService.doLogout(request);
    }

}
