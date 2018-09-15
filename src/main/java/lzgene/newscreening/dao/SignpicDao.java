package lzgene.newscreening.dao;


import lzgene.newscreening.model.Signpic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SignpicDao {

    @Insert(" insert into signpic (id,sp_name,sp_pic,authorizeName,create_time) values (#{id},#{sp_name},#{sp_pic},#{authorizeName},getDATE() ) ")
    void uploadPicture(Signpic signpic);

    @Select("select * from signpic where id=#{id}")
    Signpic Pictureshows(String id);


    @Select(" <script> SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY create_time DESC) AS num FROM signpic where 1=1 " +
            " <if test='sp_name !=null &amp;&amp; sp_name !=\"\"'> and sp_name like #{sp_name} </if>"+
            " <if test='authorizeName !=null &amp;&amp; authorizeName !=\"\"'> and authorizeName = #{authorizeName} </if>"+
            " ) AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script> ")
    List<Signpic> getSignpic(@Param(value = "authorizeName")String authorizeName,
                              @Param(value = "sp_name")String sp_name,
                              @Param(value = "pageNo") int pageNo,
                              @Param(value = "pageSize") int pageSize);

    @Select("<script> select count(*) from signpic where 1=1 " +
            " <if test='sp_name !=null &amp;&amp; sp_name !=\"\"'> and sp_name like #{sp_name} </if>"+
            " <if test='authorizeName !=null &amp;&amp; authorizeName !=\"\"'> and authorizeName = #{authorizeName} </if> " +
            " </script>")
    long getSignCount(@Param(value = "authorizeName")String authorizeName,
                       @Param(value = "sp_name")String sp_name,
                       @Param(value = "pageNo") int pageNo,
                       @Param(value = "pageSize") int pageSize);


    @Select("select count(*) from signpic where sp_name=#{sp_name} and authorizeName = #{authorizeName} ")
    int validateSign(@Param(value = "sp_name")String sp_name,@Param(value = "authorizeName")String authorizeName);

    @Update("update signpic set sp_name=#{sp_name},sp_pic=#{sp_pic},authorizeName=#{authorizeName} where id = #{id} ")
    void updateSignpic(@Param(value = "id")String id,
                       @Param(value = "sp_name")String sp_name,
                       @Param(value = "authorizeName")String authorizeName,
                       @Param(value = "sp_pic")byte[] sp_pic);

    @Delete("delete from signpic where id = #{id} ")
    void deleteSignpic(String id);


}
