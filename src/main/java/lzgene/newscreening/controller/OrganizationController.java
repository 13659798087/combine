package lzgene.newscreening.controller;

import lzgene.newscreening.model.Organization;
import lzgene.newscreening.services.OrganizationServices;
import lzgene.newscreening.util.Guid;
import lzgene.newscreening.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @Resource
    private OrganizationServices organizationServices;


    private String view = "organization/";

    //编辑节点名字
    @RequestMapping("/getOrganization")
    public String getOrganization(){
        return view+"getOrganization";
    }

   // 得到树形结构的数据
    @ResponseBody
    @RequestMapping("/getTree")
    public List<Map<String, Object>> getTree(Model model, HttpServletRequest request){
        String organizationId = (String)request.getSession().getAttribute("organizationId");

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        resultList = organizationServices.getOrganizationById(organizationId);

        //resultList = organizationServices.getAllOrganization();
        //第一级数据（根节点）
       /* Map<String, Object> orgMap = new HashMap<String, Object>();
        orgMap.put("open", true); //根节点展开
        orgMap.put("id", "0");//根节点的ID
        orgMap.put("name", "机构"); //根节点的名字
        orgMap.put("pId", "0"); //根节点的名字
        resultList.add(0, orgMap);*/

        return resultList;
    }

    // 得到树形结构的数据
    @ResponseBody
    @RequestMapping("/getByOwn")
    public List<Map<String, Object>> getByOwn(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String organizationId = (String) session.getAttribute("organizationId");
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        resultList = organizationServices.getByOwn(organizationId);
        return resultList;
    }

     // 创建树的节点
    @ResponseBody
    @RequestMapping("/createSon")
    public Organization createSon(Organization or){
        or.setId(Guid.GenerateGUID());
        organizationServices.createSon(or);
        return or;
    }

    //编辑节点名字
    @ResponseBody
    @RequestMapping("/editName")
    public void editName(String id,String name){
        organizationServices.editName(id,name);
    }

    //编辑节点名字
    @ResponseBody
    @RequestMapping("/removeNode")
    public void removeNode(String id){
        organizationServices.removeNode(id);
    }

    //编辑节点名字
    @ResponseBody
    @RequestMapping("/getOrg")
    public Organization getOrg(String organizationId){
        Organization organization = organizationServices.getOrg(organizationId);
        return organization;
    }

    //编辑节点名字
    @ResponseBody
    @RequestMapping("/getDeleted")
    public List<Organization> getDeleted(){
        List<Organization> organization = organizationServices.getDeleted();
        return organization;
    }


    //编辑节点名字
    @ResponseBody
    @RequestMapping("/reduction")
    public void reduction(String id){
        organizationServices.reduction(id);
    }



    /*如果是lz超级管理员登录，就查询出筛查中心级别的单位*/
    @ResponseBody
    @RequestMapping("/getOrganizationByLevel")
    public List<Organization> getOrganizationByLevel(HttpServletRequest request){
        List<Organization> organization = null;
        organization = organizationServices.getOrganizationByLevel();
        return organization;
    }


    /*如果是lz超级管理员登录，就查询出筛查中心级别的单位*/
    @ResponseBody
    @RequestMapping("/getByOrganizationId")
    public List<Organization> getByOrganizationId(HttpServletRequest request){
        String organizationId = (String)request.getSession().getAttribute("organizationId");
        List<Organization> organization = organizationServices.getByOrganizationId(organizationId);
        return organization;
    }


    /**
     *登录成功获取用户菜单权限
     */
    @ResponseBody
    @RequestMapping("/getOrganizationTree")
    public Map<String,Object> getLoginMessage(HttpServletRequest request){
        // List<Map<String,Object>> listResult = new ArrayList<HashMap<String,Object>() ;
        Map<String,Object> result = new HashMap<String,Object>();
        String organizationId = (String)request.getSession().getAttribute("organizationId");

        List<Map<String, Object>> resultList = organizationServices.getOrganizationById1(organizationId);
        resultList = Util.DataToTree(resultList, "parentId", null, "id",
                "children");
        result.put("resultList",resultList);
        return result;
    }


    // 得到树形结构的数据
   /* @ResponseBody
    @RequestMapping("/getTree")
    public List<Map<String, Object>> getTree(Model model, HttpServletRequest request){
        String organizationId = (String)request.getSession().getAttribute("organizationId");

        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        resultList = organizationServices.getOrganizationById(organizationId);

        return resultList;
    }*/


}
