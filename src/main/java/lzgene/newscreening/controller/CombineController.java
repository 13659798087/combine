package lzgene.newscreening.controller;

import lzgene.newscreening.entiry.PageResults;
import lzgene.newscreening.model.Combine;
import lzgene.newscreening.model.Organization;
import lzgene.newscreening.services.CombineServices;
import lzgene.newscreening.services.OrganizationServices;
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

@Controller
@RequestMapping("/combine")
public class CombineController {

    @Resource
    private CombineServices combineServices;

    @Resource
    private OrganizationServices organizationServices;

    private String view = "combine/";

    @RequestMapping("/listCombine")
    public String listSetmeal(Model model){
        model.addAttribute("admin",SSO.getLoginRole());
        return view+"listCombine";
    }


    @ResponseBody
    @RequestMapping("/getTableCombine")
    public Map<String, Object> getTableCombine(String pageNumber,String rowNumber,String sortName,String authorizeName,
                                               String sortOrder,String c_name,String c_rpt,HttpServletRequest request){

        List<Combine> listCombine = new ArrayList<Combine>();
        long totalCount=0;

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<Combine> pageResults = new PageResults<Combine>();
        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        String authorizeHospital = "";

        if(  "0".equals( SSO.getLoginRole())  ){//超级管理员  //联兆公司超级管理员,对应用户的角色是超级管理员
            authorizeHospital = authorizeName;
            listCombine = combineServices.getCombineList(authorizeHospital,c_name,c_rpt,(pageNo-1)*pageSize+1, pageNo*pageSize);//查询组别信息
            totalCount = combineServices.getCombineCount(authorizeHospital,c_name,c_rpt,(pageNo-1)*pageSize+1, pageNo*pageSize);
        }
        else{//不是超级管理员
            authorizeHospital = (String)request.getSession().getAttribute("AuthorizeName");
            listCombine = combineServices.getCombineList(authorizeHospital,c_name,c_rpt,(pageNo-1)*pageSize+1, pageNo*pageSize);//查询组别信息
            totalCount = combineServices.getCombineCount(authorizeHospital,c_name,c_rpt,(pageNo-1)*pageSize+1, pageNo*pageSize);
        }

        pageResults.setResult(listCombine);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);
    }

    @RequestMapping("/createCombine")
    public String createCombine(String code_id,String c_code,String c_name,String c_price,String c_order_no,String c_rpt,String c_rpt_title,
                                String c_rpt_bz1,String c_rpt_bz2,String paper_size,String authorizeName){
        String sign = "";
        BigDecimal price = null;
        if(!StringUtils.isEmpty(c_price)){
            price = new BigDecimal(c_price);
        }

        //新增
        if(StringUtils.isEmpty(code_id)){
            code_id = Guid.GenerateGUID();
            combineServices.createCombine(code_id,c_code,c_name,price,c_order_no,c_rpt,c_rpt_title,c_rpt_bz1,c_rpt_bz2,paper_size,authorizeName);
            sign = "add";
        }else{ //修改
            combineServices.updateCombine(code_id,c_code,c_name,price,c_order_no,c_rpt,c_rpt_title,c_rpt_bz1,c_rpt_bz2,paper_size,authorizeName);
            sign = "edit";
        }

        return "redirect:addCombine?type=a&sign="+sign; //重定向
    }

    @RequestMapping("/addCombine")
    public String addCombine(HttpServletRequest request,Model model,String code_id,String authorizeName,String c_code,String c_name, String c_price, String c_order_no, String c_rpt,
                             String c_rpt_title, String c_rpt_bz1, String c_rpt_bz2, String paper_size, String type, String sign){

        String authorizeId = (String) request.getSession().getAttribute("AuthorizeId");

        //admin=0是超级管理员
        model.addAttribute("admin",SSO.getLoginRole());
        model.addAttribute("authorizeName",authorizeName);

        model.addAttribute("code_id",code_id);
        model.addAttribute("c_code",c_code);
        model.addAttribute("c_name",c_name);
        model.addAttribute("c_price",c_price);
        model.addAttribute("c_order_no",c_order_no);
        model.addAttribute("c_rpt",c_rpt);
        model.addAttribute("c_rpt_title",c_rpt_title);
        model.addAttribute("c_rpt_bz1",c_rpt_bz1);
        model.addAttribute("c_rpt_bz2",c_rpt_bz2);
        model.addAttribute("paper_size",paper_size);

        model.addAttribute("type",type);
        model.addAttribute("sign",sign);//标识是修改还是编辑


        model.addAttribute("authorizeId",authorizeId);

        return view+"addCombine";
    }


    @ResponseBody
    @RequestMapping("/getCombineByAuthorize")
    public List<Combine> getCombineByAuthorize(HttpServletRequest request){
        List<Combine> listCombine = null;
        String admin = (String) request.getSession().getAttribute("admin");
        if("0".equals(admin)){ //超级管理员
            listCombine = combineServices.getGroupCombine();//查询所有组别项目
        }else{
            String AuthorizeName = (String) request.getSession().getAttribute("AuthorizeName");
            listCombine = combineServices.getAuthorizeNameCombine(AuthorizeName);//查询筛查中心下组别项目*/
        }
        return listCombine;
    }

    @ResponseBody
    @RequestMapping("/getCombineByAuthorize1")
    public List<Combine> getCombineByAuthorize1(HttpServletRequest request){
        List<Combine> listCombine = null;
        String admin = (String) request.getSession().getAttribute("admin");
        if("0".equals(admin)){ //超级管理员
            listCombine = combineServices.getGroupCombine1();//查询所有组别项目
        }else{
            String AuthorizeName = (String) request.getSession().getAttribute("AuthorizeName");
            listCombine = combineServices.getAuthorizeNameCombine1(AuthorizeName);//查询筛查中心下组别项目*/
        }
        return listCombine;
    }


    @ResponseBody
    @RequestMapping("/getCombine")
    public List<Combine> getCombine(){
        List<Combine> listCombine = combineServices.getCombine();
        return listCombine;
    }


    @ResponseBody
    @RequestMapping("/getCombineByCode")
    public Combine getCombineByCode(String c_code){
        Combine combine = combineServices.getCombineByCode(c_code);
        return combine;
    }

    @ResponseBody
    @RequestMapping("/getCombineById")
    public List<Combine> getCombineById(String setmeal_id){
        List<Combine> list = combineServices.getCombineById(setmeal_id);
        return list;
    }

    @ResponseBody
    @RequestMapping("/validateCombine")
    public Integer validateCombine(String c_code,String authorizeName,String type){
        int i = 0;
        if("1".equals(type)){
            i = combineServices.validateCombine(c_code,authorizeName);//查询组别信息
            if(i>0)
                i=1;
            else
                i=0;
        }
        if("2".equals(type)){
            i = combineServices.validateCombine(c_code,authorizeName);//查询组别信息
            if(i>1)
                i=2;
            else
                i=0;
        }
        return i;
    }

    /***
     *  根据id删除行
     * @param code_id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteCombine")
    public void deleteCombine(String code_id){
        combineServices.deleteCombine(code_id);
    }


    @ResponseBody
    @RequestMapping("/getCombineGroup")
    public List<Combine> getCombineGroup1(String authorizeName){
        List<Combine> listCombine = combineServices.getCombineByHospital(authorizeName);
        return listCombine;
    }



}
