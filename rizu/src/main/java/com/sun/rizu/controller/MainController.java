package com.sun.rizu.controller;

import com.sun.comm.base.AppConfig;
import com.sun.comm.base.BaseJsonController;
import com.sun.rizu.bean.cfg.CfgArea;
import com.sun.rizu.service.cfg.AreaService;
import com.sun.utils.JsonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunbin on 2015/11/10.
 */
@Controller
public class MainController extends BaseJsonController {

    private static HttpClient client= HttpClients.createDefault();
    String rizu_key= AppConfig.get("amap.rizu.key");//服务器
    String URL_CREATE_TABLE=AppConfig.get("amap.create.table.url");
    @Resource
    AreaService areaService=null;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "hello";
    }
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String newIndex() {
        return "index";
    }

    @RequestMapping(value = "/table", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addTable(){
        String tableName=getReq("tableName","");
        //创建httppost对象
        HttpPost post=new HttpPost(URL_CREATE_TABLE);
        post.addHeader("Content-Type","application/x-www-form-urluncoded");
        //创建参数列表
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key",rizu_key));
        params.add(new BasicNameValuePair("name",tableName));
        //创建httpEntity
        UrlEncodedFormEntity entity=null;
        HttpResponse resp=null;
        InputStream inputStream=null;
        BufferedReader bufferedReader=null;
        StringBuilder builder = new StringBuilder();
        try {
            entity=new UrlEncodedFormEntity(params,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (entity==null) return rt("创建失败，请重试！");
        //执行并返回解析结果
        post.setEntity(entity);
        try {
            resp=client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (resp==null) return rt("resp返回失败");
        try {
            inputStream=resp.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (inputStream==null) return rt("返回为空！");
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            for (String line = null; (line = bufferedReader.readLine()) != null;) {
                builder.append(line);
            }
            //Exception getting thrown in below line

            JSONObject json=JsonUtils.decode(builder.toString());
            System.out.println(builder.toString());
            Map<String, Object> mapResult=JsonUtils.jsonToMap(json);
            System.out.println(mapResult.get("status"));
            System.out.println(mapResult.get("info"));
            return rr(mapResult);
        }catch (Exception e){
            logger.error("解析失败");
            return rt("解析失败");
        }

    }

    @RequestMapping(value = "/getHistoryStatData", method = RequestMethod.GET)
    @ResponseBody
    public Object getHistoryStatData(){

        String getHistoryStatData= AppConfig.get("p2p.api.getHistoryStatData");//服务器
        String url="";
        String day=getReq("day");
        String secret=getReq("secret");
        String callback=getReq("callback");
        url=getHistoryStatData+"?day="+day+"&secret="+secret;

        //创建httpget对象
        HttpGet get=new HttpGet(url);
        get.addHeader("Content-Type","application/x-www-form-urlencoded");
        HttpResponse resp=null;
        InputStream inputStream=null;
        BufferedReader bufferedReader=null;
        StringBuilder builder = new StringBuilder();
        try {
            resp=client.execute(get);
            inputStream=resp.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
            rt("resp返回失败");
        }
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            for (String line = null; (line = bufferedReader.readLine()) != null;) {
                builder.append(line);
            }
            JSONObject json= JsonUtils.decode(builder.toString());
            System.out.println(builder.toString());
            String r=callback+"(["+builder.toString()+"])";

            Map<String, Object> mapResult=JsonUtils.jsonToMap(json);
            System.out.println(mapResult.get("r"));
            System.out.println(mapResult.get("lishi"));
            if(mapResult.get("r").equals(1)!=true) return builder.toString();
            return r;
        }catch (Exception e){
            logger.error("解析失败");
            return rt("解析失败");
        }

    }

    @RequestMapping(value = "/getBidInfoAndInvestorList", method = RequestMethod.GET)
    @ResponseBody
    public Object getBidInfoAndInvestorList(){

        String getBidInfoAndInvestorList= AppConfig.get("p2p.api.getBidInfoAndInvestorList");//服务器
        String url="";
        String bd=getReq("bd");
        String ed=getReq("ed");
        String is_now=getReq("is_now");
        String page=getReq("page");
        String perpage=getReq("perpage");

        String secret=getReq("secret");
        String callback=getReq("callback");
        url=getBidInfoAndInvestorList+"?bd="+bd+"&ed="+ed+"&is_now="+is_now+
                "&page="+page+"&perpage="+perpage+"&secret="+secret+"&callback="+callback;

        //创建httppost对象
        HttpGet get=new HttpGet(url);
        get.addHeader("Content-Type","application/x-www-form-urlencoded");
        HttpResponse resp=null;
        InputStream inputStream=null;
        BufferedReader bufferedReader=null;
        StringBuilder builder = new StringBuilder();
        try {
            resp=client.execute(get);
            inputStream=resp.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
            rt("resp返回失败");
        }
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            for (String line = null; (line = bufferedReader.readLine()) != null;) {
                builder.append(line);
            }
            JSONObject json= JsonUtils.decode(builder.toString());
            System.out.println(builder.toString());
            String r=callback+"(["+builder.toString()+"])";

            Map<String, Object> mapResult=JsonUtils.jsonToMap(json);
            System.out.println(mapResult.get("r"));
            System.out.println(mapResult.get("msg"));
//            return rr(mapResult);
            return r;
        }catch (Exception e){
            logger.error("解析失败");
            return rt("解析失败");
        }

    }

    @RequestMapping(value = "/getArea", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getArea(){
        int province = getReq("province",0);//是否获取省级列表
        int city = getReq("city",0);//是否获取城市列表
        int county =getReq("county",0);//是否获取县级列表
        int province_id = getReq("province_id",0); //取这个ID下的城市列表，此时必须city=1
        int city_id=getReq("city_id",0);//取这个ID下的县级列表，此时必须county=1
        Map<String,Object> m = new HashMap<String, Object>();
        if (province != 0) {
            List<CfgArea> provinceList = areaService.getProvinceList();
            m.put("province_list",provinceList);
        }
        if (city != 0 && province_id != 0) {
            List<CfgArea> cityList = areaService.getCityList(province_id);
            m.put("city_list",cityList);
        }
        if (county != 0 && city_id != 0) {
            List<CfgArea> countyList = areaService.getCityList(city_id);
            m.put("county_list",countyList);
        }
        return rr(m);
    }

    @RequestMapping(value = "/getHtml", method = RequestMethod.GET)
    @ResponseBody
    public String getHtml(){

        return "<ul><li>1</li><li>2</li></ul>";
    }
}