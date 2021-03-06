package com.enumaelish.controller;

import com.enumaelish.entity.House;
import com.enumaelish.service.HouseService;
import com.enumaelish.utils.ControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 链家信息抓取查询
 */
@Controller
public class LianjiaHouseController {

    @Autowired
    HouseService houseService;


    @RequestMapping(value = "/lianjia", method = RequestMethod.GET)
    public String lianjiaindex() {
        return "lianjiahousesearch";
    }

    @RequestMapping(value = "/lianjiasearch", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> search(HttpServletRequest request) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        String city = request.getParameter("city");
        double min = 0;
        double max = Double.MAX_VALUE;
        try{
            min = Double.valueOf(request.getParameter("min"));
            max = Double.valueOf(request.getParameter("max"));
        }catch (Exception e){
        }
        // 若什么都不输入，则表示搜索全部商品
        try {
            PageRequest pageable = ControllerUtil.getPageRequest(request);
            Page<House> query = houseService.query(city, min, max, pageable);
            List list = query.getContent();
            stringObjectHashMap.put("results", list);
            stringObjectHashMap.put("totalElements", query.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringObjectHashMap;
    }


}
