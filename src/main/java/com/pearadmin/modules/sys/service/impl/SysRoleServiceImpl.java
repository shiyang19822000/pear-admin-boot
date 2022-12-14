package com.pearadmin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.tools.SequenceUtil;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.*;
import com.pearadmin.modules.sys.mapper.*;
import com.pearadmin.modules.sys.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Describe: 角色服务实现类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    /**
     * 注入角色服务
     */
    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 注入权限服务
     */
    @Resource
    private SysPowerMapper sysPowerMapper;

    /**
     * 注入部门服务
     * */
    @Resource
    private SysDeptMapper sysDeptMapper;

    /**
     * 注入角色权限服务
     */
    @Resource
    private SysRolePowerMapper sysRolePowerMapper;

    /**
     * 注入角色部门服务
     * */
    @Resource
    private SysRoleDeptMapper sysRoleDeptMapper;

    /**
     * 引入用户角色服务
     */
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;


    /**
     * Describe: 查询角色数据
     * Param: QueryRoleParam
     * Return: 操作结果
     */
    @Override
    public List<SysRole> list(SysRole param) {
        return sysRoleMapper.selectRole(param);
    }

    /**
     * Describe: 查询角色数据 分页
     * Param: QueryRoleParam
     * Return: 操作结果
     */
    @Override
    public PageInfo<SysRole> page(SysRole param, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<SysRole> list = sysRoleMapper.selectRole(param);
        return new PageInfo<>(list);
    }

    /**
     * Describe: 保存角色数据
     * Param: SysRole
     * Return: 操作结果
     */
    @Override
    public boolean save(SysRole sysRole) {
        int result = sysRoleMapper.insert(sysRole);
        return result > 0;
    }

    /**
     * Describe: 根据 ID 查询角色
     * Param: id
     * Return: 返回角色信息
     */
    @Override
    public SysRole getById(String id) {
        return sysRoleMapper.selectById(id);
    }

    /**
     * Describe: 修改用户数据
     * Param: SysUser
     * Return: 操作结果
     */
    @Override
    public boolean update(SysRole sysRole) {
        Integer result = sysRoleMapper.updateById(sysRole);
        return result > 0;
    }

    /**
     * Describe: 查询角色权限信息
     * Param: id
     * Return: 返回角色信息
     */
    @Override
    public List<SysPower> getRolePower(String roleId) {
        List<SysPower> allPower = sysPowerMapper.selectList(null);
        List<SysRolePower> myPower = sysRolePowerMapper.selectByRoleId(roleId);
        allPower.forEach(sysPower -> myPower.forEach(sysRolePower -> {
            if (sysRolePower.getPowerId().equals(sysPower.getPowerId())) {
                sysPower.setCheckArr("1");
            }
        }));
        return allPower;
    }

    @Override
    public List<SysDept> getRoleDept(String roleId) {
        List<SysDept> allDept = sysDeptMapper.selectList(null);
        LambdaQueryWrapper<SysRoleDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleDept::getRoleId,roleId);
        List<SysRoleDept> myDept = sysRoleDeptMapper.selectList(wrapper);
        allDept.forEach(sysDept -> {
            myDept.forEach(sysRoleDept -> {
                if(sysRoleDept.getDeptId().equals(sysDept.getDeptId())){
                    sysDept.setCheckArr("1");
                }
            });
        });
        return allDept;
    }

    @Override
    @Transactional
    public Boolean saveRoleDept(String roleId, List<String> deptIds) {
        sysRoleDeptMapper.deleteByRoleId(roleId);
        List<SysRoleDept> roleDepts = new ArrayList<>();
        deptIds.forEach(deptId -> {
            SysRoleDept sysRoleDept = new SysRoleDept();
            sysRoleDept.setId(SequenceUtil.makeStringId());
            sysRoleDept.setRoleId(roleId);
            sysRoleDept.setDeptId(deptId);
            roleDepts.add(sysRoleDept);
        });
        if(roleDepts.size() > 0) {
            sysRoleDeptMapper.batchInsert(roleDepts);
        }
        return true;
    }

    /**
     * Describe: 保存角色权限数据
     * Param: roleId powerIds
     * Return: 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveRolePower(String roleId, List<String> powerIds) {
        sysRolePowerMapper.deleteByRoleId(roleId);
        List<SysRolePower> rolePowers = new ArrayList<>();
        powerIds.forEach(powerId -> {
            SysRolePower sysRolePower = new SysRolePower();
            sysRolePower.setId(SequenceUtil.makeStringId());
            sysRolePower.setRoleId(roleId);
            sysRolePower.setPowerId(powerId);
            rolePowers.add(sysRolePower);
        });
        int result = sysRolePowerMapper.batchInsert(rolePowers);
        return result > 0;
    }

    /**
     * Describe: 根据 id 删除角色数据
     * Param: id
     * Return: Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean remove(String id) {
        sysRoleMapper.deleteById(id);
        sysUserRoleMapper.deleteByRoleId(id);
        sysRolePowerMapper.deleteByRoleId(id);
        return true;
    }

    /**
     * Describe: 根据 id 批量删除角色数据
     * Param: ids
     * Return: Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchRemove(String[] ids) {
        sysRoleMapper.deleteBatchIds(Arrays.asList(ids));
        sysUserRoleMapper.deleteByRoleIds(ids);
        sysRolePowerMapper.deleteByRoleIds(ids);
        return true;
    }

}
