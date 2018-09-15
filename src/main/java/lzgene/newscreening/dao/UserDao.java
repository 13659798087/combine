package lzgene.newscreening.dao;

import lzgene.newscreening.model.Organization;
import lzgene.newscreening.model.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select count(userId) from  userinfo where userName=#{userName} and password=#{password} ")
    int loginValidate(@Param(value = "userName")String userName,@Param(value = "password")String password);

    @Insert("INSERT INTO userinfo (userId,userName,password,dayLoginError,createTime,organizationId,parentId) " +
            " VALUES (#{userId},#{userName},#{password},#{dayLoginError},getdate(),#{organizationId},#{parentId} )")
    void creatUser(@Param(value = "userId")String userId,
                   @Param(value = "userName")String userName,
                   @Param(value = "password")String password,
                   @Param(value = "dayLoginError")Integer dayLoginError,
                   @Param(value = "organizationId")String organizationId,
                   @Param(value = "parentId")String parentId);

    @Select("select count(userId) from  userinfo where userName=#{userName} and organizationId=#{organizationId} ")
    int validateUser(@Param(value = "userName")String userName,@Param(value = "organizationId")String organizationId);


    @Update("update userinfo set  userName=#{userName},password=#{password},dayLoginError=#{dayLoginError},organizationId=#{organizationId} where userId=#{userId} ")
    void updateUser(@Param(value = "userId")String userId,
                    @Param(value = "userName")String userName,
                    @Param(value = "password")String password,
                    @Param(value = "dayLoginError")String dayLoginError,
                    @Param(value = "organizationId")String organizationId);

    @Select("select * from  userinfo where userName=#{userName} and password=#{password} ")
    UserInfo getUserInfo(@Param(value = "userName")String userName,@Param(value = "password")String password);

    @Select("select * from userinfo where parentId=#{userId} ")
    List<UserInfo> getUserByParentId(String userId);

   /* @Select("<script>  SELECT * FROM ( " +
            "SELECT ROW_NUMBER() OVER(ORDER BY u.createTime DESC) AS num, u.*,o.name, (SELECT '['+r.roleName+']' FROM userRole ur " +
            "left join  role r  on ur.roleId=r.roleId " +
            "and ur.userId=u.userId  FOR xml path ('') ) listRole " +
            "from userinfo u left join organization o on u.organizationId=o.id where 1=1 " +
            "<if test='userName !=null &amp;&amp; userName !=\"\"'> and userName like '%${userName}%' </if>"+
            "<if test='organizationName !=null &amp;&amp; organizationName !=\"\"'> and o.name = #{organizationName} </if>"+
            ")AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script> ")
    List<UserInfo> getUserMessage(@Param(value = "pageNo") int pageNo,
                                  @Param(value = "pageSize") int pageSize,
                                  @Param(value = "userName")String userName,
                                  @Param(value = "organizationName")String organizationName);*/

    @Select("<script> with temp ( id,name,parentId)\n" +
            "            as\n" +
            "            (\n" +
            "            select id,name,parentId\n" +
            "            from organization\n" +
            "            where id = #{organizationId}\n" +
            "            union all\n" +
            "            select a.id,a.name,a.parentId\n" +
            "            from organization a\n" +
            "            inner join temp on a.parentId = temp.id\n" +
            "            )\n" +
            " SELECT * FROM ( \n" +
            "            SELECT ROW_NUMBER() OVER(ORDER BY u.createTime DESC) AS num, u.*,o.name, (SELECT '['+r.roleName+']' FROM userRole ur \n" +
            "            left join  role r  on ur.roleId=r.roleId\n" +
            "            and ur.userId=u.userId  FOR xml path ('') ) listRole\n" +
            "            from userinfo u inner join temp o on u.organizationId=o.id \n" +
            "            where 1=1\n" +
            "            <if test='userName !=null &amp;&amp; userName !=\"\"'> and userName like '%${userName}%' </if>"+
            "            <if test='organizationName !=null &amp;&amp; organizationName !=\"\"'> and o.name = #{organizationName} </if>"+
            "            )AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script> ")
    List<UserInfo> getUserMessage(@Param(value = "pageNo") int pageNo,
                                  @Param(value = "pageSize") int pageSize,
                                  @Param(value = "userName")String userName,
                                  @Param(value = "organizationName")String organizationName,
                                  @Param(value = "organizationId")String organizationId);


    @Select("<script> with temp ( id,name,parentId)\n" +
            "            as\n" +
            "            (\n" +
            "            select id,name,parentId\n" +
            "            from organization\n" +
            "            where id = #{organizationId}\n" +
            "            union all\n" +
            "            select a.id,a.name,a.parentId\n" +
            "            from organization a\n" +
            "            inner join temp on a.parentId = temp.id\n" +
            "            )" +
            " SELECT count(1) FROM ( " +
            " SELECT ROW_NUMBER() OVER(ORDER BY u.createTime DESC) AS num, u.*,o.name, (SELECT '['+r.roleName+']' FROM userRole ur " +
            " left join  role r  on ur.roleId=r.roleId " +
            " and ur.userId=u.userId  FOR xml path ('') ) listRole " +
            " from userinfo u inner join temp o on u.organizationId=o.id \n" +
            " where 1=1 " +
            " <if test='userName !=null &amp;&amp; userName !=\"\"'> and userName like '%${userName}%' </if>"+
            " <if test='organizationName !=null &amp;&amp; organizationName !=\"\"'> and o.name like '%${organizationName}%' </if>"+
            " )AS t </script> ")
    long getUserCount(@Param(value = "pageNo") int pageNo,
                      @Param(value = "pageSize") int pageSize,
                      @Param(value = "userName")String userName,
                      @Param(value = "organizationName")String organizationName,
                      @Param(value = "organizationId")String organizationId);


    @Delete("delete from userinfo where userId = #{userId}")
    void deleteUser(String userId);

    @Select("select * from userinfo where userName=#{userName} ")
    UserInfo userloginMessage(String userName);

    //每天登录的错误次数加1
    @Update("update userinfo set dayLoginError = dayLoginError + 1 where userName = #{userName} ")
    void addOneCount(String userName);

    @Update("UPDATE userinfo set dayLoginError=0 where dayLoginError <= #{dayLimitLoginError} ")
    void resetDayLoginError(Integer dayLimitLoginError);

    @Update("UPDATE userinfo set password =#{newPassword},updateTime= getDate() where userName = #{userName} and password = #{oldPassword} ")
    int changePsw(@Param(value = "userName") String userName,
                  @Param(value = "newPassword") String newPassword,
                  @Param(value = "oldPassword") String oldPassword);

    @Select("select * from organization where id = #{organizationId} ")
    Organization getOrganizationMessage(String organizationId);

    @Select(" with cte as\n" +
            "(\n" +
            "select ID,ParentID,name,o.[level],o.name\n" +
            "from organization o\n" +
            "where id = #{organizationId} \n" +
            "union all\n" +
            "select h.ID,h.ParentID,h.name,h.[level],h.name\n" +
            "from organization h\n" +
            "inner join cte c on h.id=c.ParentID\n" +
            ")\n" +
            "select ID,name \n" +
            "from cte where cte.[level]='1'\n" +
            "order by ParentID; ")
    Organization getAuthorizeName(String organizationId);

    @Select(" with cte as\n" +
            "(\n" +
            "select ID,ParentID,name,o.[level],o.name\n" +
            "from organization o\n" +
            "where id = #{organizationId} \n" +
            "union all\n" +
            "select h.ID,h.ParentID,h.name,h.[level],h.name\n" +
            "from organization h\n" +
            "inner join cte c on h.id=c.ParentID\n" +
            ")\n" +
            " select ID,name from cte order by ParentID; ")
    Organization getCompanyName(String organizationId);

    @Select("select r.roleName from userinfo u \n" +
            "LEFT JOIN userRole ur on u.userId = ur.userId\n" +
            "LEFT JOIN role r on ur.roleId = r.roleId\n" +
            "where u.userId = #{userId} ")
    List<String> getListRoleName(String userId);


}
