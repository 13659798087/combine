package lzgene.newscreening.services;

import lzgene.newscreening.dao.OrganizationDao;
import lzgene.newscreening.model.Organization;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationServices {

    @Resource
    private OrganizationDao organizationDao;

    public List<Map<String,Object>> getAllOrganization() {
        return organizationDao.getAllOrganization();
    }

    public void createSon(Organization or) {
        organizationDao.createSon(or);
    }

    public void editName(String id, String name) {
        organizationDao.editName(id,name);
    }

    public void removeNode(String id) {
        organizationDao.removeNode(id);
    }

    public Organization getOrg(String id) {
        return organizationDao.getOrg(id);
    }

    public List<Organization> getOrganization() {
        return organizationDao.getOrganization();
    }

    public List<Organization> getDeleted() {
        return organizationDao.getDeleted();
    }

    public void reduction(String id) {
        organizationDao.reduction(id);
    }


    public List<Map<String,Object>> getOrganizationById(String organizationId) {
        return organizationDao.getOrganizationById(organizationId);
    }

    public List<Map<String,Object>> getOrganizationById1(String organizationId) {
        return organizationDao.getOrganizationById1(organizationId);
    }

    public List<Organization> getOrganizationByLevel() {
        return organizationDao.getOrganizationByLevel();
    }

    public List<Organization> getByOrganizationId(String organizationId) {
        return organizationDao.getByOrganizationId(organizationId);
    }

    public Organization getHospitalLevel(String hospital) {
        return organizationDao.getHospitalLevel(hospital);
    }

    public Organization getHospitalIdByName(String organizationId) {
        return organizationDao.getHospitalIdByName(organizationId);
    }

    public List<Map<String,Object>> getByOwn(String organizationId) {
        return organizationDao.getByOwn(organizationId);
    }

    public Organization getByHospitalName(String hospital) {
        return organizationDao.getByHospitalName(hospital);
    }


}
