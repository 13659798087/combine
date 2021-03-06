package lzgene.newscreening.services;

import lzgene.newscreening.dao.RoleDao;
import lzgene.newscreening.model.Role;
import lzgene.newscreening.model.UserRoleMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServices {

    @Resource
    private RoleDao roleDao;

    public void insertRela(String id, String userId, String roleId) {
        roleDao.insertRela(id,userId,roleId);
    }

    public List<UserRoleMenu> getRoleByUserId(String userId) {
        return roleDao.getRoleByUserId(userId);
    }

    public void deleteRela(String userId) {
        roleDao.deleteRela(userId);
    }

    public void creatRole(String roleId, String roleName, String roleLevel) {
        roleDao.creatRole(roleId,roleName,roleLevel);
    }

    public void updateRole(String roleId, String roleName, String roleLevel) {
        roleDao.updateRole(roleId,roleName,roleLevel);
    }

    public void deleteRoleMune(String roleId) {
        roleDao.deleteRoleMune(roleId);
    }

    public List<Role> getRoleByNum_1(String roleName, int pageNo, int pageSize) {
        return roleDao.getRoleByNum_1(roleName,pageNo,pageSize);
    }

    public long getRoleCount_1(String roleName, int pageNo, int pageSize) {
        return roleDao.getRoleCount_1(roleName,pageNo,pageSize);
    }


    public List<Role> getRoleByNum(String roleName, int pageNo, int pageSize,String superRole) {
        return roleDao.getRoleByNum(roleName,pageNo,pageSize,superRole);
    }

    public long getRoleCount(String roleName, int pageNo, int pageSize,String superRole) {
        return roleDao.getRoleCount(roleName,pageNo,pageSize,superRole);
    }

    public void deleteRole(String roleId) {
        roleDao.deleteRole(roleId);
    }

    public void deleteUserRole(String roleId) {
        roleDao.deleteUserRole(roleId);
    }

    public void deleteRoleMenu(String roleId) {
        roleDao.deleteRoleMenu(roleId);
    }

    public List<Role> getAllRole() {
        return roleDao.getAllRole();
    }

    public List<Role> getAllRole1(String superRole) {
        return roleDao.getAllRole1(superRole);
    }
}
