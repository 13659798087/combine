package lzgene.newscreening.dao;

import lzgene.newscreening.model.Combine;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CombineDao {


    @Select("SELECT * from combine where c_flag=0 ORDER BY c_order_no ")
    List<Combine> getCombine();

    /*lz超级管理员查询到的项目*/
    @Select("<script> SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY c_order_no asc) AS num FROM combine " +
            "where 1=1 and c_flag=0" +
            " <if test='c_name !=null &amp;&amp; c_name !=\"\"'> and c_name like '%${c_name}%' </if>"+
            " <if test='c_rpt !=null &amp;&amp; c_rpt !=\"\"'> and c_rpt like '%${c_rpt}%' </if>"+
            " <if test='authorizeName !=null &amp;&amp; authorizeName !=\"\"'> and authorizeName = #{authorizeName} </if>"+
            ") " +
            " AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script> ")
    List<Combine> getCombineList(@Param(value = "authorizeName")String authorizeName,
                                  @Param(value = "c_name") String c_name,
                                  @Param(value = "c_rpt") String c_rpt,@Param(value = "pageNo") int pageNo,
                                  @Param(value = "pageSize") int pageSize);

    /*lz超级管理员查询到的项目的条数*/
    @Select("<script> SELECT count(*) from combine where 1=1 and c_flag=0 " +
            " <if test='c_name !=null &amp;&amp; c_name !=\"\"'> and c_name like '%${c_name}%' </if>"+
            " <if test='authorizeName !=null &amp;&amp; authorizeName !=\"\"'> and authorizeName = #{authorizeName} </if>"+
            " <if test='c_rpt !=null &amp;&amp; c_rpt !=\"\"'> and c_rpt like '%${c_rpt}%' </if> </script>" )
    long getCombineCount(@Param(value = "authorizeName")String authorizeName,
                          @Param(value = "c_name") String c_name,
                          @Param(value = "c_rpt") String c_rpt,@Param(value = "pageNo") int pageNo,
                          @Param(value = "pageSize") int pageSize);


    @Select("SELECT * from setmeal s inner join setmealcombine sc on s.setmeal_id=sc.setmeal_id\n" +
            "inner join combine c on c.code_id=sc.code_id where s.setmeal_id= #{setmeal_id} ")
    List<Combine> getCombineById(String setmeal_id);

    @Select("select count(*) from combine where c_code = #{c_code} and authorizeName=#{authorizeName} and c_flag=0 ")
    int validateCombine(@Param(value="c_code")String c_code,@Param(value="authorizeName")String authorizeName);

    @Insert("INSERT into combine (code_id,c_code,c_name,c_price,c_order_no,c_rpt,c_rpt_title,c_rpt_bz1,c_rpt_bz2,create_time,paper_size,authorizeName) " +
            "VALUES (#{code_id},#{c_code},#{c_name},#{c_price},#{c_order_no},#{c_rpt},#{c_rpt_title},#{c_rpt_bz1},#{c_rpt_bz1},getdate(),#{paper_size},#{authorizeName} )")
    void createCombine(@Param(value="code_id")String code_id, @Param(value="c_code") String c_code,@Param(value="c_name") String c_name,
                       @Param(value="c_price")BigDecimal c_price,@Param(value="c_order_no")String c_order_no,
                       @Param(value="c_rpt")String c_rpt,@Param(value="c_rpt_title") String c_rpt_title,
                       @Param(value="c_rpt_bz1")String c_rpt_bz1,@Param(value="c_rpt_bz2")String c_rpt_bz2,
                       @Param(value="paper_size")String paper_size,@Param(value="authorizeName")String authorizeName);

    @Update("update combine set c_code=#{c_code},c_name=#{c_name},c_price=#{c_price},c_order_no=#{c_order_no},c_rpt=#{c_rpt}," +
            "c_rpt_title=#{c_rpt_title},c_rpt_bz1=#{c_rpt_bz1},c_rpt_bz2=#{c_rpt_bz2},paper_size=#{paper_size},authorizeName=#{authorizeName} where code_id=#{code_id} ")
    void updateCombine( @Param(value="code_id") String code_id,
                        @Param(value="c_code") String c_code,@Param(value="c_name") String c_name,
                        @Param(value="c_price")BigDecimal c_price,@Param(value="c_order_no")String c_order_no,
                        @Param(value="c_rpt")String c_rpt,@Param(value="c_rpt_title") String c_rpt_title,
                        @Param(value="c_rpt_bz1")String c_rpt_bz1,@Param(value="c_rpt_bz2")String c_rpt_bz2,
                        @Param(value="paper_size")String paper_size,@Param(value="authorizeName")String authorizeName);

    @Delete("update combine set c_flag = 1 where code_id = #{code_id} ")
    void deleteCombine(String code_id);

    @Select("SELECT * from combine where c_code = #{c_code} ")
    Combine getCombineByCode(String c_code);

    @Select("SELECT * from combine where c_flag = 0 ")
    List<Combine> getCombineGroup();

    @Select("SELECT * from combine where c_flag = 0 and authorizeName=#{authorizeName} ")
    List<Combine> getCombineByHospital(String authorizeName);

    @Select("SELECT c_code,c_name from combine where c_flag=0 GROUP BY  c_code,c_name ")
    List<Combine> getGroupCombine();

    @Select("SELECT c_code,c_name from combine where c_flag=0 and authorizeName= #{authorizeName} GROUP BY c_code,c_name ")
    List<Combine> getAuthorizeNameCombine(String authorizeName);

    @Select("SELECT * from combine where c_flag=0 order by c_order_no ")
    List<Combine> getGroupCombine1();

    @Select("SELECT * from combine where c_flag=0 and authorizeName= #{authorizeName} order by c_order_no ")
    List<Combine> getAuthorizeNameCombine1(String authorizeName);



}
