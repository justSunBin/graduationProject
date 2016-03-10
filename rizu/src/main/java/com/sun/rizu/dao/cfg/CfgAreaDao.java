package com.sun.rizu.dao.cfg;

import com.sun.comm.base.BaseDao;
import com.sun.utils.TimeUtils;
import com.sun.rizu.bean.cfg.CfgArea;
import com.sun.rizu.constant.CacheConstant;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by qxh on 2015/11/19.
 */
@Component
public class CfgAreaDao extends BaseDao {

    /**
     * 把所有数据缓存到REDIS中
     */
    public void allToCache()
    {
        long now = TimeUtils.time();
        int cachTime = cache.getInt(CacheConstant.KEY_CFG_AREA_TIME);
        if ( cachTime > now) {
            return ;
        }


        List<CfgArea> list = getAllFromDb();
        if (list == null || list.size() ==0) {
            return ;
        }
        for(int i =0; i<list.size(); i++ ) {
            CfgArea item = list.get(i);
            String key = CacheConstant.KEY_CFG_AREA + item.getId();
            cache.set(key,item);
        }


        //缓存截止时间 ：当前时间+超时时间
        cache.set(CacheConstant.KEY_CFG_AREA_TIME,CacheConstant.VAL_CFG_TIME);
    }

    /**
     * 从数据库中缓存
     * @return
     */
    public List<CfgArea> getAllFromDb()
    {

        try {
            return getSqlMapClientTemplate().queryForList("cfgArea.allList");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



    public CfgArea get(int id)
    {

        //allToCache(); //缓存cfg_setting表所有数据

        if (id == 0) return null;
        String key = CacheConstant.KEY_CFG_AREA + id;
        CfgArea item = (CfgArea)cache.getFromJson(key,CfgArea.class);
        if (item == null) {
            try {
                item = (CfgArea)getSqlMapClientTemplate().queryForObject("cfgArea.getById",id);

                cache.set(key,item,CacheConstant.VAL_CFG_TIME);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        return item;
    }

    /**
     * 获得配置项的配置项
     * @param id bank_id
     * @param
     * @return
     */
    public String getName(int id)
    {
        logger.warn("id="+id);
        if (id == 0) {
            return "";
        }
        CfgArea item = get(id);
        if (item == null) {
            return "";
        }

        return item.name;
    }

    public List<CfgArea> getProvinceList()
    {
        String key = CacheConstant.KEY_CFG_AREA_PROVINCELIST;
        List<CfgArea> list =  cache.listFromJson(key,CfgArea.class);
        if (list == null) {
            try {
                list = getSqlMapClientTemplate().queryForList("cfgArea.getProvinceList");

                cache.set(key,list,CacheConstant.VAL_CFG_TIME);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        return list;
    }

    public List<CfgArea> getCityList(int province_id)
    {
        String key = CacheConstant.KEY_CFG_AREA_CITYLIST + province_id;
        List<CfgArea> list =  cache.listFromJson(key,CfgArea.class);
        if (list == null) {
            try {
                list = getSqlMapClientTemplate().queryForList("cfgArea.getCityList",province_id);

                cache.set(key,list,CacheConstant.VAL_CFG_TIME);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        return list;
    }
}
