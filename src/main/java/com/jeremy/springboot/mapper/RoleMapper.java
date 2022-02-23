package com.jeremy.springboot.mapper;

import com.jeremy.springboot.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jeremy
 * @since 2022-02-21
 */
public interface RoleMapper extends BaseMapper<Role> {


    @Select("select id from sys_role where role_key = #{roleKey} ")
    Integer selectByRoleKey(@Param("roleKey") String roleKey);
}
