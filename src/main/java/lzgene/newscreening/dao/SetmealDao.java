package lzgene.newscreening.dao;

import lzgene.newscreening.model.Combine;
import lzgene.newscreening.model.Setmeal;
import lzgene.newscreening.model.Setmealcombine;
import org.apache.ibatis.annotations.*;

import javax.management.ValueExp;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface SetmealDao {

    @Select("<script> SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY s_order_no asc) AS num FROM setmeal " +
            " where 1=1  and s_flag=0 and authorizeName=#{authorizeName} " +
            " <if test='s_name !=null &amp;&amp; s_name !=\"\"'> and s_name like '%${s_name}%' </if>"+
            " <if test='s_code !=null &amp;&amp; s_code !=\"\"'> and s_code like '%${s_code}%' </if>"+
            ") " +
            " AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script> ")
    List<Setmeal> getSetmeal( @Param(value="authorizeName")String authorizeName,
                             @Param(value="s_code") String s_code,
                             @Param(value="s_name") String s_name,
                             @Param(value="pageNo")int pageNo, @Param(value="pageSize")int pageSize);

    @Select("<script> SELECT count(1) from setmeal \n" +
            " where 1=1  and s_flag=0 and authorizeName=#{authorizeName}  \n" +
            " <if test='s_name !=null &amp;&amp; s_name !=\"\"'> and s_name like '%${s_name}%' </if>\n" +
            " <if test='s_code !=null &amp;&amp; s_code !=\"\"'> and s_code like '%${s_code}%' </if> </script>")
    long getSetmealCount( @Param(value="authorizeName")String authorizeName,
                         @Param(value="s_code") String s_code,
                         @Param(value="s_name") String s_name,
                         @Param(value="pageNo")int pageNo, @Param(value="pageSize")int pageSize);


    @Select("<script> SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY s_order_no asc) AS num FROM setmeal " +
            " where 1=1  and s_flag=0 " +
            " <if test='s_name !=null &amp;&amp; s_name !=\"\"'> and s_name like '%${s_name}%' </if>"+
            " <if test='s_code !=null &amp;&amp; s_code !=\"\"'> and s_code like '%${s_code}%' </if>"+
            " <if test='authorizeName !=null &amp;&amp; authorizeName !=\"\"'> and authorizeName = #{authorizeName} </if>"+
            ") " +
            " AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script> ")
    List<Setmeal> getSetmeal1(@Param(value="authorizeName") String authorizeName,
                              @Param(value="s_code") String s_code,
                              @Param(value="s_name") String s_name,
                              @Param(value="pageNo")int pageNo, @Param(value="pageSize")int pageSize);

    @Select("<script> SELECT count(1) from setmeal \n" +
            " where 1=1  and s_flag=0 \n" +
            " <if test='s_name !=null &amp;&amp; s_name !=\"\"'> and s_name like '%${s_name}%' </if> " +
            " <if test='authorizeName !=null &amp;&amp; authorizeName !=\"\"'> and authorizeName = #{authorizeName} </if> " +
            " <if test='s_code !=null &amp;&amp; s_code !=\"\"'> and s_code like '%${s_code}%' </if> </script>")
    long getSetmealCount1(@Param(value="authorizeName")String authorizeName,
                          @Param(value="s_code") String s_code,
                          @Param(value="s_name") String s_name,
                          @Param(value="pageNo")int pageNo, @Param(value="pageSize")int pageSize);




    @Select("select count(*) from setmeal where s_code=#{s_code} and authorizeName=#{authorizeName} and s_flag=0 ")
    int validateSetmeal(@Param(value="s_code")String s_code,@Param(value="authorizeName")String authorizeName);

    @Insert("insert into setmeal (setmeal_id,authorizeName,s_code,s_name,s_price,s_order_no,s_flag,create_time) values " +
            " (#{setmeal_id},#{authorizeName},#{s_code},#{s_name},#{s_price},#{s_order_no},0,getdate() ) ")
    void createSetmeal(@Param(value="setmeal_id")String setmeal_id,@Param(value="authorizeName")String authorizeName,
                       @Param(value="s_code") String s_code, @Param(value="s_name") String s_name,
                       @Param(value="s_price") BigDecimal s_price,@Param(value="s_order_no") String s_order_no);

    @Insert("insert into setmealcombine (sc_id,code_id,setmeal_id) values (#{sc_id},#{code_id},#{setmeal_id} )")
    void insertRela(@Param(value="sc_id")String sc_id,@Param(value="code_id")String code_id,@Param(value="setmeal_id")String setmeal_id);

    @Delete("delete from setmealcombine where setmeal_id=#{setmeal_id} ")
    void delRela(String setmeal_id);

    @Update("Update setmeal set s_code=#{s_code},s_name=#{s_name},s_price=#{s_price},s_order_no=#{s_order_no},update_time=getdate()" +
            " where setmeal_id=#{setmeal_id} ")
    void updateSetmeal(@Param(value="setmeal_id")String setmeal_id,
                       @Param(value="s_code") String s_code,
                       @Param(value="s_name") String s_name,
                       @Param(value="s_price") BigDecimal s_price,
                       @Param(value="s_order_no") String s_order_no);


    @Delete("update setmeal set s_flag = 1 where s_code = #{s_code} ")
    void deleteSetmeal(String s_code);

    @Select("select * from setmeal where s_flag=0 and authorizeName=#{authorizeName}")
    List<Setmeal> getListSetmea(String authorizeName);

    @Select("select sc.*,c.c_name,c.c_code from setmealcombine sc LEFT JOIN combine c on sc.code_id = c.code_id\n" +
            "left join setmeal s on sc.setmeal_id = s.setmeal_id where s.s_name = #{s_name} ")
    List<Setmealcombine> getBySetmeal(String s_name);

    @Select("SELECT s_code,s_name from setmeal where s_flag=0 GROUP BY  s_code,s_name ")
    List<Setmeal> getGroupSetmeal();

    @Select("SELECT s_code,s_name from setmeal where s_flag=0 and authorizeName= #{authorizeName} GROUP BY s_code,s_name ")
    List<Setmeal> getAuthorizeNameSetmeal(String authorizeName);

}
