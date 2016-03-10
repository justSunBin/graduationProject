package com.sun.rizu.service.cfg;

import com.sun.rizu.bean.cfg.CfgArea;
import com.sun.rizu.dao.cfg.CfgAreaDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by qxh on 2015/11/23.
 */
@Service
public class AreaService {

    @Resource
    private CfgAreaDao cfgAreaDao = null;

    /**
     * 获得省份列表
     * @return
     */
    public List<CfgArea> getProvinceList()
    {
        return cfgAreaDao.getProvinceList();
    }


    /**
     * 获取省份
     * @param province_id
     * @return
     */
    public CfgArea getProvince(int province_id)
    {
        return cfgAreaDao.get(province_id);
    }

    /**
     * 获取城市
     * @param city_id
     * @return
     */
    public CfgArea getCity(int city_id)
    {
        return cfgAreaDao.get(city_id);
    }


    /**
     * 获取省份/城市的名称
     * @param id  城市ID或者省份ID
     * @return
     */
    public String getName(int id)
    {
        return cfgAreaDao.getName(id);
    }

    /**
     * 获得省份下属的城市列表
     * @return List
     */
    public List<CfgArea> getCityList(int province_id)
    {
        return cfgAreaDao.getCityList(province_id);
    }
}
