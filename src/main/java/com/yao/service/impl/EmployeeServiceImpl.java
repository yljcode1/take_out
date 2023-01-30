package com.yao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.regexp.internal.RE;
import com.yao.common.Response;
import com.yao.entity.Employee;
import com.yao.service.EmployeeService;
import com.yao.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author yao
 * @description 针对表【employee(员工信息)】的数据库操作Service实现
 * @createDate 2023-01-29 20:57:47
 */
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    private final EmployeeMapper employeeMapper;

    /**
     * 登录接口
     *
     * @param request  请求
     * @param employee 员工信息
     * @return 员工id
     */
    @Override
    public Response<Employee> doLogin(HttpServletRequest request, Employee employee) {
        // 1、将前端密码md5加密
        String passwordMD = "";
        if (StringUtils.isNotBlank(employee.getPassword())) {
            passwordMD = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes(StandardCharsets.UTF_8));
        }
        // 2、页面提交用户名查询数据库
        Employee oldEmployee = employeeMapper.selectOne(new LambdaQueryWrapper<Employee>().eq(StringUtils.isNotBlank(employee.getUsername()), Employee::getUsername, employee.getUsername()));
        // 3、没有查询返回登录失败结果
        if (null == oldEmployee) {
            return Response.error("用户不存在");
        }
        // 4、密码比对，如果不一致返回登录失败结果
        if (!Objects.equals(oldEmployee.getPassword(), passwordMD)) {
            return Response.error("密码错误");
        }
        // 5、查看员工状态，如果已禁用，则返回已禁用结果
        if (oldEmployee.getStatus() != 1) {
            return Response.error("用户已被禁用");
        }
        // 6、登录成功，将员工id存入Session
        return Response.success(oldEmployee);
    }
    /**
     * 退出接口
     *
     * @param request 请求
     * @return 退出信息
     */
    @Override
    public Response<String> doLogout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return Response.success("退出成功");
    }
}




