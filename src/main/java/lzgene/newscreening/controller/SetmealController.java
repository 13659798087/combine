package lzgene.newscreening.controller;

import lzgene.newscreening.entiry.PageResults;
import lzgene.newscreening.model.*;
import lzgene.newscreening.services.OrganizationServices;
import lzgene.newscreening.services.SetmealServices;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//套餐
@Controller
@RequestMapping("/setmeal")
public class SetmealController {

    @Resource
    private SetmealServices setmealServices;

    @Resource
    private OrganizationServices organizationServices;

    private String view = "setmeal/";

    @RequestMapping("/listSetmeal")
    public String listSetmeal(Model model){
        model.addAttribute("admin",SSO.getLoginRole());
        return view+"listSetmeal";
    }

    @ResponseBody
    @RequestMapping("/getSetmeal")
    public Map<String, Object> getSetmeal(String pageNumber,String rowNumber,String sortName,String authorizeName,
                                          String sortOrder,String s_code,String s_name,HttpServletRequest request){

        List<Setmeal> listSetmeal = new ArrayList<Setmeal>();
        long totalCount=0;

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<Setmeal> pageResults = new PageResults<Setmeal>();
        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        String authorizeHospital = "";

        if( "0".equals( SSO.getLoginRole()) ){//超级管理员  //联兆公司超级管理员,对应用户的角色是超级管理员
            authorizeHospital = authorizeName;
            listSetmeal = setmealServices.getSetmeal1(authorizeHospital,s_code,s_name,(pageNo-1)*pageSize+1, pageNo*pageSize);//查询套餐信息
            totalCount = setmealServices.getSetmealCount1(authorizeHospital,s_code,s_name,(pageNo-1)*pageSize+1, pageNo*pageSize);
        }
        else{//不是超级管理员，查询自己单位下的数据
            authorizeHospital = (String)request.getSession().getAttribute("AuthorizeName");
            listSetmeal = setmealServices.getSetmeal(authorizeHospital,s_code,s_name,(pageNo-1)*pageSize+1, pageNo*pageSize);//查询套餐信息
            totalCount = setmealServices.getSetmealCount(authorizeHospital,s_code,s_name,(pageNo-1)*pageSize+1, pageNo*pageSize);
        }

        pageResults.setResult(listSetmeal);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);
    }

    @RequestMapping("/createSetmeal")
    public String createSetmeal(HttpServletRequest request,String setmeal_id,String authorizeName,String s_code,String s_name, String s_price, String s_order_no,String roles){
        String sign = "";

        BigDecimal price = null;
        if(!StringUtils.isEmpty(s_price)){
            price=new BigDecimal(s_price);
        }

        //新增
        if(StringUtils.isEmpty(setmeal_id)){
            setmeal_id = Guid.GenerateGUID();
            setmealServices.createSetmeal(setmeal_id,authorizeName,s_code,s_name,price,s_order_no);
            sign = "add";
        }else{//修改
            setmealServices.updateSetmeal(setmeal_id,s_code,s_name,price,s_order_no);
            //删除关系
            setmealServices.delRela(setmeal_id);
            sign = "edit";
        }

        //建立关系表
        String[] a = roles.split(",");
        for(int i=0;i<a.length;i++){
            String sc_id = Guid.GenerateGUID();
            String code_id = a[i];
            setmealServices.insertRela(sc_id,code_id,setmeal_id);
        }

        return "redirect:addSetmeal?type=a&sign="+sign; //重定向
    }

    @RequestMapping("/addSetmeal")
    public String addSetmeal(HttpServletRequest request,Model model,String setmeal_id,String authorizeName,String s_code,String s_name,String s_price,String s_order_no,String type,String sign){
        String s_code1 = s_code;
        String s_name1 = s_name;

        if(!StringUtils.isEmpty(s_code) && s_code.contains(" ")){
            s_code1 = s_code.replaceAll(" ","+");
        }
        if(!StringUtils.isEmpty(s_name) && s_name.contains(" ")){
            s_name1 = s_name.replaceAll(" ","+");
        }

        //admin=0是超级管理员
        model.addAttribute("admin",SSO.getLoginRole());
        model.addAttribute("authorizeName",authorizeName);

        model.addAttribute("setmeal_id",setmeal_id);
        model.addAttribute("s_code",s_code1);
        model.addAttribute("s_name",s_name1);
        model.addAttribute("s_price",s_price);
        model.addAttribute("s_order_no",s_order_no);
        model.addAttribute("type",type);
        model.addAttribute("sign",sign);//标识是修改还是编辑
        return view+"addSetmeal";
    }

    @ResponseBody
    @RequestMapping("/validateSetmeal")
    public Integer validateSetmeal(String s_code,String authorizeName,String type){
        int i=0;
        if("1".equals(type)){
            i = setmealServices.validateSetmeal(s_code,authorizeName);//新增时
            if(i>0)
                i=1;
            else
                i=0;
        }
        if("2".equals(type)){
            i = setmealServices.validateSetmeal(s_code,authorizeName);//编辑不修改信息
            if(i>1)
                i=2;
            else
                i=0;
        }
        return i;
    }

    /***
     *  根据id删除行
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteSetmeal")
    public void deleteSetmeal(String s_code){
        setmealServices.deleteSetmeal(s_code);
    }

    /***
     *  根据s_code查询中间表所包含的组别的id
     */
    @ResponseBody
    @RequestMapping("/getBySetmeal")
    public List<Setmealcombine> getBySetmeal(String s_code){
        List<Setmealcombine> setmealcombine = new ArrayList<Setmealcombine>();
        setmealcombine = setmealServices.getBySetmeal(s_code);
        return setmealcombine;
    }









}
