package lzgene.newscreening.dao;

import lzgene.newscreening.model.Config;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConfigDao {

    /*lz管理员*/
    @Select("<script> SELECT * FROM (SELECT *,ROW_NUMBER() OVER(ORDER BY cf_order_no) AS num FROM config " +
            " where cf_flag=0  " +
            " <if test='cf_code !=null &amp;&amp; cf_code !=\"\"'> and cf_code like '%${cf_code}%' </if>"+
            " <if test='authorizeName !=null &amp;&amp; authorizeName !=\"\"'> and authorizeName = #{authorizeName} </if>"+
            " )AS t WHERE  t.num BETWEEN #{pageNo} AND #{pageSize} </script>")
    List<Config> configTable(@Param(value="authorizeName")String authorizeName,
                             @Param(value="cf_code")String cf_code,
                             @Param(value="pageNo") int pageNo,
                             @Param(value="pageSize")int pageSize);
    @Select("<script> SELECT count(1) FROM  config where cf_flag=0  " +
            " <if test='cf_code !=null &amp;&amp; cf_code !=\"\"'> and cf_code like '%${cf_code}%' </if>"+
            " <if test='authorizeName !=null &amp;&amp; authorizeName !=\"\"'> and authorizeName = #{authorizeName} </if> " +
            " </script>")
    long getConfigCount(@Param(value="authorizeName")String authorizeName,
                        @Param(value="cf_code")String cf_code,
                        @Param(value="pageNo") int pageNo,
                        @Param(value="pageSize")int pageSize);


    @Select("select count(*) from config where cf_flag=0 and cf_code=#{cf_code} and authorizeName=#{authorizeName} ")
    int validateName(@Param(value="cf_code")String cf_code,@Param(value="authorizeName")String authorizeName);

    @Insert("insert into config (cf_id,authorizeName,cf_code,cf_flag,cf_val,cf_explain,cf_order_no) values" +
            " (#{cf_id},#{authorizeName},#{cf_code},#{cf_flag},#{cf_val},#{cf_explain},#{cf_order_no} )")
    void createConfig(@Param(value="cf_id")String cf_id,
                      @Param(value="authorizeName")String authorizeName,
                      @Param(value="cf_code")String cf_code,
                      @Param(value="cf_flag")Integer cf_flag,
                      @Param(value="cf_val")String cf_val,
                      @Param(value="cf_explain")String cf_explain,
                      @Param(value="cf_order_no")String cf_order_no);

    @Update("update config set cf_flag=#{cf_flag},cf_val=#{cf_val},cf_explain=#{cf_explain}," +
            " authorizeName=#{authorizeName},cf_code=#{cf_code},cf_order_no=#{cf_order_no}  where cf_id=#{cf_id} ")
    void updateConfig(@Param(value="cf_id")String cf_id,
                      @Param(value="authorizeName")String authorizeName,
                      @Param(value="cf_code")String cf_code,
                      @Param(value="cf_flag")Integer cf_flag,
                      @Param(value="cf_val")String cf_val,
                      @Param(value="cf_explain")String cf_explain,
                      @Param(value="cf_order_no")String cf_order_no);

    @Update("update config set cf_flag = 1 where cf_id = #{cf_id} ")
    void deleteRow(String cf_id);

    @Select("select * from config where cf_code=#{barcodeLength} and authorizeName=#{authorizeName} ORDER BY cf_order_no ")
    Config getBarcodeLength(@Param(value="authorizeName")String authorizeName,@Param(value="barcodeLength")String barcodeLength);


}
