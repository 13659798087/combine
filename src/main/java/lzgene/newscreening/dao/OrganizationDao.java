package lzgene.newscreening.dao;

import lzgene.newscreening.model.Organization;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrganizationDao {

    @Select("SELECT id,parentId pId,name from organization where isDelete=0 ")
    List<Map<String,Object>> getAllOrganization();

    @Insert("INSERT INTO organization (id,name,parentId,level,isDelete) VALUES (#{id},#{name},#{parentId},#{level},0)")
    void createSon(Organization or);

    @Update("update organization set name= #{name} where id = #{id} ")
    void editName(@Param(value="id")String id, @Param(value="name")String name);

    @Update("update organization set isDelete= 1 where id = #{id} ")
    void removeNode(String id);

    @Select("SELECT * from organization where id=#{id} ")
    Organization getOrg(String id);

    @Select("SELECT * from organization where isDelete=0 ")
    List<Organization> getOrganization();


   /* @Select("with temp ( id,name,parentId)\n" +
            "as\n" +
            "(\n" +
            "select id,name,parentId\n" +
            "from organization\n" +
            "where id = '10'\n" +
            "union all\n" +
            "select a.id,a.name,a.parentId\n" +
            "from organization a\n" +
            "inner join temp on a.parentId = temp.id\n" +
            ")\n" +
            "select * from temp ")*/



    @Select("SELECT * from organization where isDelete=1 ")
    List<Organization> getDeleted();

    @Update("update organization set isDelete= 0 where id = #{id} ")
    void reduction(String id);

    @Select("with temp (id,name,parentId,level,isDelete)\n" +
            "            as\n" +
            "            (\n" +
            "            select id,name,parentId,level,isDelete\n" +
            "            from organization\n" +
            "            where id = #{organizationId} \n" +
            "            union all\n" +
            "            select a.id,a.name,a.parentId,a.level,a.isDelete\n" +
            "            from organization a\n" +
            "            inner join temp on a.parentId = temp.id\n" +
            "            )\n" +
            "            SELECT id,name,parentId pId,level from temp where isDelete=0 ")
    List<Map<String,Object>> getOrganizationById(String organizationId);

    @Select("with temp (id,name,parentId,level,isDelete)\n" +
            "            as\n" +
            "            (\n" +
            "            select id,name,parentId,level,isDelete\n" +
            "            from organization\n" +
            "            where id = #{organizationId} \n" +
            "            union all\n" +
            "            select a.id,a.name,a.parentId,a.level,a.isDelete\n" +
            "            from organization a\n" +
            "            inner join temp on a.parentId = temp.id\n" +
            "            )\n" +
            "            SELECT id,name,parentId,level from temp where isDelete=0 ")
    List<Map<String,Object>> getOrganizationById1(String organizationId);



    /*如果是lz超级管理员登录，就查询出筛查中心级别的单位*/
    @Select("SELECT * from organization where level = '1' and isDelete=0 ")
    List<Organization> getOrganizationByLevel();

    //递归
    @Select("with temp ( id,name,parentId,isDelete)\n" +
            "as\n" +
            "(\n" +
            "select id,name,parentId,isDelete\n" +
            "from organization\n" +
            "where id = #{organizationId}\n" +
            "union all\n" +
            "select a.id,a.name,a.parentId,a.isDelete\n" +
            "from organization a\n" +
            "inner join temp on a.parentId = temp.id and a.isDelete = 0\n" +
            ")\n" +
            "select * from temp ")
    List<Organization> getByOrganizationId(String organizationId);

    @Select("SELECT * from organization where id = #{organizationId}  ")
    Organization getHospitalLevel(String organizationId);


    /*根据子id查询父级id*/
    @Select("with cte as\n" +
            "(\n" +
            " select id,parentId,level,name from organization o where id= #{organizationId} \n" +
            " union all\n" +
            " select a.id,a.parentId,a.[level],a.name from organization a join cte b on a.ID=b.parentId where a.parentId is not null  \n" +
            ")\n" +
            " select * from cte where 1=1 and level = '1' ")
    Organization getHospitalIdByName(String organizationId);


    @Select("with temp ( id,name,parentId)\n" +
            "as\n" +
            "(\n" +
            "select id,name,parentId\n" +
            "from organization\n" +
            "where id = #{organizationId} \n" +
            "union all\n" +
            "select a.id,a.name,a.parentId\n" +
            "from organization a\n" +
            "inner join temp on a.parentId = temp.id\n" +
            ")\n" +
            "select id,parentId pId,name from temp ")
    List<Map<String,Object>> getByOwn(String organizationId);


    /*根据子id查询父级id*/
    @Select("with cte as\n" +
            "(\n" +
            " select id,parentId,level,name from organization o where name= #{hospital} \n" +
            " union all\n" +
            " select a.id,a.parentId,a.[level],a.name from organization a join cte b on a.ID=b.parentId where a.parentId is not null  \n" +
            ")\n" +
            " select top(1) * from cte where level = '1' ")
    Organization getByHospitalName(String hospital);

}