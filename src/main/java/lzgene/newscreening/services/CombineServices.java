package lzgene.newscreening.services;

import lzgene.newscreening.dao.CombineDao;
import lzgene.newscreening.model.Combine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CombineServices {

    @Resource
    private CombineDao combineDao;

    public List<Combine> getCombineList(String authorizeName, String c_name, String c_rpt, int pageNo, int pageSize) {
        return combineDao.getCombineList(authorizeName,c_name,c_rpt,pageNo,pageSize);
    }

    public long getCombineCount(String authorizeName, String c_name, String c_rpt, int pageNo, int pageSize) {
        return combineDao.getCombineCount(authorizeName,c_name,c_rpt,pageNo,pageSize);
    }

    public List<Combine> getCombineById(String setmeal_id) {
        return combineDao.getCombineById(setmeal_id);
    }

    public int validateCombine(String c_code,String authorizeName) {
        return combineDao.validateCombine(c_code,authorizeName);
    }

    public void createCombine(String code_id,String c_code, String c_name, BigDecimal c_price, String c_order_no, String c_rpt, String c_rpt_title,
                              String c_rpt_bz1, String c_rpt_bz2,String paper_size,String authorizeName) {
        combineDao.createCombine(code_id,c_code,c_name,c_price,c_order_no,c_rpt,c_rpt_title,c_rpt_bz1,c_rpt_bz2,paper_size,authorizeName);
    }

    public void updateCombine(String code_id,String c_code, String c_name, BigDecimal c_price, String c_order_no, String c_rpt, String c_rpt_title,
                              String c_rpt_bz1, String c_rpt_bz2,String paper_size,String authorizeName) {
        combineDao.updateCombine(code_id,c_code,c_name,c_price,c_order_no,c_rpt,c_rpt_title,c_rpt_bz1,c_rpt_bz2,paper_size,authorizeName);
    }

    public List<Combine> getCombine() {
        return combineDao.getCombine();
    }

    public void deleteCombine(String code_id) {
        combineDao.deleteCombine(code_id);
    }

    public Combine getCombineByCode(String c_code) {
        return combineDao.getCombineByCode(c_code);
    }

    public List<Combine> getCombineGroup() {
        return combineDao.getCombineGroup();
    }


    public List<Combine> getCombineByHospital(String authorizeName) {
        return combineDao.getCombineByHospital(authorizeName);
    }

    public List<Combine> getGroupCombine() {
        return combineDao.getGroupCombine();
    }

    public List<Combine> getAuthorizeNameCombine(String authorizeName) {
        return combineDao.getAuthorizeNameCombine(authorizeName);
    }


    public List<Combine> getGroupCombine1() {
        return combineDao.getGroupCombine1();
    }

    public List<Combine> getAuthorizeNameCombine1(String authorizeName) {
        return combineDao.getAuthorizeNameCombine1(authorizeName);
    }

}
