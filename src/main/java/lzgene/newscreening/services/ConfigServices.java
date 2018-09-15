package lzgene.newscreening.services;

import lzgene.newscreening.dao.ConfigDao;
import lzgene.newscreening.model.Config;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConfigServices {

    @Resource
    private ConfigDao configDao;

    public List<Config> configTable(String authorizeName,String cf_code, int pageNo,int pageSize) {
        return configDao.configTable(authorizeName,cf_code, pageNo, pageSize);
    }

    public long getConfigCount(String authorizeName,String cf_code, int pageNo, int pageSize) {
        return configDao.getConfigCount(authorizeName,cf_code, pageNo, pageSize);
    }

    public int validateName(String cf_code, String authorizeName) {
        return configDao.validateName(cf_code,authorizeName);
    }

    public void createConfig(String cf_id, String authorizeName,String cf_code,Integer cf_flag,String cf_val, String cf_explain,String cf_order_no) {
        configDao.createConfig(cf_id,authorizeName,cf_code,cf_flag,cf_val,cf_explain,cf_order_no);
    }

    public void updateConfig(String cf_id, String authorizeName, String cf_code, Integer cf_flag,String cf_val, String cf_explain,String cf_order_no) {
        configDao.updateConfig(cf_id,authorizeName,cf_code,cf_flag,cf_val,cf_explain,cf_order_no);
    }

    public void deleteRow(String cf_id) {
        configDao.deleteRow(cf_id);
    }

    public Config getBarcodeLength(String authorizeName,String barcodeLength) {
        return configDao.getBarcodeLength(authorizeName,barcodeLength);
    }

}
