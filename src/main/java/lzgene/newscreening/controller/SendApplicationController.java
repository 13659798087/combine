package lzgene.newscreening.controller;

import lzgene.newscreening.entiry.PageResults;
import lzgene.newscreening.entiry.SendApplication;
import lzgene.newscreening.model.Applyinfo;
import lzgene.newscreening.model.Organization;
import lzgene.newscreening.services.OrganizationServices;
import lzgene.newscreening.services.SendApplicationServices;
import lzgene.newscreening.util.Paging;
import lzgene.newscreening.util.SSO;
import org.apache.axis.utils.StringUtils;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sendApplication")

public class SendApplicationController {

    @Resource
    private SendApplicationServices sendApplicationServices;

    @Resource
    private OrganizationServices organizationServices;

    private String view ="sendApplication/";

    @RequestMapping("/sendApplication")
    public String sendApplication(){
        return view + "sendApplication";

    }

    /***
     * 获取申请单录入信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/getApplication")
    public Map<String, Object> getApplyinfo(String pageNumber,String rowNumber,String sortName,String sortOrder,
                                            HttpServletRequest request,String p_name, String c_a_date1,String c_a_date2,
                                            String c_combine_code,String c_barcode,String c_hospital,String c_rpt_flag){

        List<SendApplication> sendApplication = new ArrayList<SendApplication>();
        long totalCount = 0;

        int pageNo = Integer.parseInt((pageNumber == null || pageNumber =="0") ? "1":pageNumber);
        int pageSize = Integer.parseInt((rowNumber == null || rowNumber =="0") ? "10":rowNumber);
        String orderBy = sortName == null  ? "updaterTime":sortName;
        String order = sortOrder == null  ? PageResults.DESC:sortOrder;

        PageResults<SendApplication> pageResults = new PageResults<SendApplication>();
        pageResults.setPageNo(pageNo);
        pageResults.setPageSize(pageSize);
        pageResults.setOrderBy(orderBy);
        pageResults.setOrder(order);

        Timestamp c_a_date_1 = null;
        Timestamp c_a_date_2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(!StringUtils.isEmpty(c_a_date1)){
            c_a_date_1 = Timestamp.valueOf(c_a_date1 +" 00:00:00");
        }
        if(!StringUtils.isEmpty(c_a_date2)){
            c_a_date_2 = Timestamp.valueOf(c_a_date2 +" 23:59:59");
        }

        HttpSession session = request.getSession();

        if(StringUtils.isEmpty(c_hospital)){//c_hospital是单位的id
            c_hospital = (String) session.getAttribute("organizationId");
        }

        sendApplication = sendApplicationServices.getApplicationList(c_hospital,p_name,(pageNo-1)*pageSize+1,pageNo*pageSize,c_a_date_1,c_a_date_2,c_combine_code,c_barcode,c_rpt_flag);
        totalCount = sendApplicationServices.getReportCount(c_hospital,p_name,(pageNo-1)*pageSize+1,pageNo*pageSize,c_a_date_1,c_a_date_2,c_combine_code,c_barcode,c_rpt_flag);

        pageResults.setResult(sendApplication);
        pageResults.setTotalCount(totalCount);

        return Paging.ajaxGrid(pageResults);
    }

    @RequestMapping("/cardEntry")
    public String cardEntry(){
        return view + "cardEntry";
    }



    @ResponseBody
    @RequestMapping("/get1")
    public void get1( ){

       List<SendApplication>  list = sendApplicationServices.get1();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        System.out.println(format.format(date1));

        for(SendApplication s : list){
          String i = "6";
          sendApplicationServices.updata(i,s.getC_p_id());
        }

        Date date2 = new Date();
        System.out.println(format.format(date2));

    }


}
