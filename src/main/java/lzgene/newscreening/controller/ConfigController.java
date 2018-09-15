package lzgene.newscreening.controller;

import lzgene.newscreening.entiry.PageResults;
import lzgene.newscreening.model.Config;
import lzgene.newscreening.services.ConfigServices;

import lzgene.newscreening.util.Guid;
import lzgene.newscreening.util.Paging;
import lzgene.newscreening.util.SSO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/config")
public class ConfigController {

    @Resource
    private ConfigServices configServices;

    @Value("${barcodeLength}")
    private String barcodeLength;

    @Value("${sampleDateValidation}")
    private String sampleDateValidation;

    private String view = "config/";

    @RequestMapping("/listConfig")
    public String templateList(Model model){
        model.addAttribute("admin",SSO.getLoginRole());
        return view+"listConfig";
    }

    @ResponseBody
    @RequestMapping("/configTable")
    public Map<String,Object> configTable(String pageNumber, String rowNumber, String sortName,String authorizeName,
                                          String sortOrder, String cf_code, HttpServletRequest request){

        List<Config> config = new ArrayList<Config>();
        long totalCount=0;

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<Config> pageResults = new PageResults<Config>();

        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        String authorizeHospital = "";

        if(  "0".equals( SSO.getLoginRole())  ){//超级管理员  //联兆公司超级管理员,对应用户的角色是超级管理员
            authorizeHospital = authorizeName;
            config = configServices.configTable(authorizeHospital,cf_code,(pageNo-1)*pageSize+1, pageNo*pageSize);
            totalCount = configServices.getConfigCount(authorizeHospital,cf_code,(pageNo-1)*pageSize+1, pageNo*pageSize);
        }
        else{//不是超级管理员
            authorizeHospital = (String)request.getSession().getAttribute("AuthorizeName");
            config = configServices.configTable(authorizeHospital,cf_code,(pageNo-1)*pageSize+1, pageNo*pageSize);
            totalCount = configServices.getConfigCount(authorizeHospital,cf_code,(pageNo-1)*pageSize+1, pageNo*pageSize);
        }

        pageResults.setResult(config);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);

    }

    /**
    验证
     */
    @ResponseBody
    @RequestMapping("/validateName")
    public Integer validateCombine(String cf_code,String authorizeName,String type){
        int i = 0;
        if("1".equals(type)){//新增
            i = configServices.validateName(cf_code,authorizeName);//查询组别信息
            if(i>0)
                i=1;
            else
                i=0;
        }
        if("2".equals(type)){ //编辑
            i = configServices.validateName(cf_code,authorizeName);//查询组别信息
            if(i>1)
                i=2;
            else
                i=0;
        }
        return i;
    }

    /**
     创建新模板
     */
    @RequestMapping("/createConfig")
    public String createConfig(String cf_id,String authorizeName,String cf_code,String cf_flag,
                               String cf_val,String cf_explain,String type,String cf_order_no){
        String sign = "";
        Integer cf_flag1 = null;
        if(!StringUtils.isEmpty(cf_flag)){
            cf_flag1 = Integer.parseInt(cf_flag);
        }

        //新增
        if(StringUtils.isEmpty(cf_id)){
            cf_id = Guid.GenerateGUID();
            configServices.createConfig(cf_id,authorizeName,cf_code,cf_flag1,cf_val,cf_explain,cf_order_no);
            sign = "add";
        }else{ //修改
            configServices.updateConfig(cf_id,authorizeName,cf_code,cf_flag1,cf_val,cf_explain,cf_order_no);
            sign = "edit";
        }

        return "redirect:addConfig?type=a&sign="+sign; //重定向
    }

    @RequestMapping("/addConfig")
    public String addConfig(Model model,String cf_id, String authorizeName, String cf_code, String cf_flag,
                            String cf_val,String cf_explain,String type,String sign,String  cf_order_no){

        //admin=0是超级管理员
        model.addAttribute("admin", SSO.getLoginRole());
        model.addAttribute("authorizeName",authorizeName);

        model.addAttribute("cf_id",cf_id);
        model.addAttribute("cf_code",cf_code);
        model.addAttribute("cf_flag",cf_flag);
        model.addAttribute("cf_val",cf_val);
        model.addAttribute("cf_explain",cf_explain);
        model.addAttribute("cf_order_no",cf_order_no);
        model.addAttribute("type",type);
        model.addAttribute("sign",sign);//标识是修改还是编辑
        return view+"addConfig";

    }

    /***
     * @param cf_id
     */
    @ResponseBody
    @RequestMapping("/deleteRow")
    public void deleteRow(String cf_id){
        configServices.deleteRow(cf_id);
    }

    //查询配置的条码号的长度
    @ResponseBody
    @RequestMapping("/barcodeLength")
    public Map barcodeLength(HttpServletRequest request){

        String authorizeName = (String)request.getSession().getAttribute("AuthorizeName");

        Map map = new HashMap();
        Config barConfig = configServices.getBarcodeLength(authorizeName,barcodeLength);//配置文件取的条码号长度
        Config dateConfig = configServices.getBarcodeLength(authorizeName,sampleDateValidation);//配置采样日期，校验日期输入
        if(barConfig!=null) map.put("barcodeLength",barConfig.getCf_val());

        if(dateConfig!=null) map.put("sampleDateValidation",dateConfig.getCf_val());

        return map;
    }



}
