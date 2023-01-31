package com.yao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yao.common.Response;
import com.yao.entity.Employee;
import com.yao.service.EmployeeService;
import com.yao.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author yao
 * @description 针对表【employee(员工信息)】的数据库操作Service实现
 * @createDate 2023-01-29 20:57:47
 */
@Slf4j
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
        request.getSession().setAttribute("employee", oldEmployee.getId());
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

    /**
     * 添加员工信息
     *
     * @param request  请求
     * @param employee 员工信息
     * @return 添加结果
     */
    @Override
    public Response<String> addEmployee(HttpServletRequest request, Employee employee) {
        // 初始化默认值
        log.info("新增员工，员工信息:{}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
        employee.setCreateTime(new Date());
        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
        employee.setUpdateTime(new Date());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employee.setStatus(1);
        employeeMapper.insert(employee);
        return Response.success("新增员工成功");
    }

    /**
     * 分页查询员工信息
     *
     * @param page     当前页
     * @param pageSize 页面大小
     * @param name     名称
     * @return 员工信息
     */
    @Override
    public Response<IPage<Employee>> page(Integer page, Integer pageSize, String name) {
        IPage<Employee> employeePage = new Page<>(page, pageSize);
        IPage<Employee> employeeIPage = employeeMapper.selectPage(employeePage, new LambdaQueryWrapper<Employee>().like(StringUtils.isNotBlank(name), Employee::getUsername, name).orderByDesc(Employee::getUpdateTime));
        return Response.success(employeeIPage);
    }

    /**
     * 修改员工
     *
     * @param request  请求
     * @param employee 员工信息
     * @return 提示
     */
    @Override
    public Response<String> updateStatus(HttpServletRequest request, Employee employee) {
        log.info("修改员工信息");
        int update = employeeMapper.update(employee, new LambdaUpdateWrapper<Employee>()
                .set(Employee::getStatus, employee.getStatus())
                .set(Employee::getUpdateTime, new Date())
                .set(Employee::getUpdateUser, request.getSession().getAttribute("employee")).eq(Employee::getId, employee.getId()));
        if (update > 0) {
            return Response.success("修改成功");
        }
        return Response.error("修改失败");
    }
}




