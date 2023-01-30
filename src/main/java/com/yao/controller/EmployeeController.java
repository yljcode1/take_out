package com.yao.controller;

import com.yao.common.Response;
import com.yao.entity.Employee;
import com.yao.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 员工管理controller
 *
 * @date: 2023-01-30
 * @author: yao
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    /**
     * 添加员工信息
     *
     * @param request  请求
     * @param employee 员工信息
     * @return 添加结果
     */
    @PostMapping
    public Response<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.addEmployee(request, employee);
    }
}
