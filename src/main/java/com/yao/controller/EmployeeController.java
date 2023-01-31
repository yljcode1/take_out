package com.yao.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yao.common.Response;
import com.yao.entity.Employee;
import com.yao.service.EmployeeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 分页查询员工信息
     *
     * @param page     当前页
     * @param pageSize 页面大小
     * @param name     名称
     * @return 员工信息
     */
    @GetMapping("/page")
    public Response<IPage<Employee>> page(@RequestParam Integer page, @RequestParam Integer pageSize, String name) {
        return employeeService.page(page, pageSize, name);
    }

    /**
     * 修改员工
     *
     * @param request  请求
     * @param employee 员工信息
     * @return 提示
     */
    @PutMapping
    public Response<String> updateStatus(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.updateStatus(request, employee);
    }

    /**
     * 根据员工id获取到员工信息
     *
     * @param id 唯一标识
     * @return 员工信息
     */
    @GetMapping("/{id}")
    public Response<Employee> detailById(@PathVariable Long id) {
        return employeeService.detailById(id);
    }
}
