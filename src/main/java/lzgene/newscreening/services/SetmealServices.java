package lzgene.newscreening.services;

import lzgene.newscreening.dao.SetmealDao;
import lzgene.newscreening.model.Combine;
import lzgene.newscreening.model.Setmeal;
import lzgene.newscreening.model.Setmealcombine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class SetmealServices {


    @Resource
    private SetmealDao setmealDao;

    public List<Setmeal> getSetmeal(String authorizeName,String s_code,String s_name,int pageNo, int pageSize) {
        return setmealDao.getSetmeal(authorizeName,s_code,s_name,pageNo,pageSize);
    }

    public long getSetmealCount(String authorizeName,String s_code,String s_name,int pageNo, int pageSize) {
        return setmealDao.getSetmealCount(authorizeName,s_code,s_name,pageNo,pageSize);
    }

    public List<Setmeal> getSetmeal1(String authorizeName,String s_code,String s_name,int pageNo, int pageSize) {
        return setmealDao.getSetmeal1(authorizeName,s_code,s_name,pageNo,pageSize);
    }

    public long getSetmealCount1(String authorizeName,String s_code,String s_name,int pageNo, int pageSize) {
        return setmealDao.getSetmealCount1(authorizeName,s_code,s_name,pageNo,pageSize);
    }

    public int validateSetmeal(String s_code,String authorizeName) {
        return setmealDao.validateSetmeal(s_code,authorizeName);
    }

    public void createSetmeal(String setmeal_id,String authorizeName,String s_code, String s_name, BigDecimal s_price, String s_order_no) {
        setmealDao.createSetmeal(setmeal_id,authorizeName,s_code, s_name, s_price, s_order_no);
    }

    public void insertRela(String sc_id, String code_id, String setmeal_id) {
        setmealDao.insertRela(sc_id, code_id, setmeal_id);
    }

    public void delRela(String setmeal_id) {
        setmealDao.delRela(setmeal_id);
    }

    public void updateSetmeal(String setmeal_id,String s_code, String s_name, BigDecimal s_price, String s_order_no) {
        setmealDao.updateSetmeal(setmeal_id,s_code,s_name,s_price,s_order_no);
    }



    public void deleteSetmeal(String s_code) {
        setmealDao.deleteSetmeal(s_code);
    }

    public List<Setmeal> getListSetmea(String authorizeName) {
        return setmealDao.getListSetmea(authorizeName);
    }


    public List<Setmealcombine> getBySetmeal(String s_name) {
        return setmealDao.getBySetmeal(s_name);
    }

    public List<Setmeal> getGroupSetmeal() {
        return setmealDao.getGroupSetmeal();
    }

    public List<Setmeal> getAuthorizeNameSetmeal(String authorizeName) {
        return setmealDao.getAuthorizeNameSetmeal(authorizeName);
    }


}
