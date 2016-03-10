package com.sun.comm.base;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sun.comm.cache.CacheHandler;
import com.sun.utils.JsonUtils;
import com.sun.rizu.bean.cfg.CfgSetting;
import com.sun.rizu.constant.CacheConstant;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * BaseDao,Dao需继承此Dao
 *
 * @author qxh since 2015-11-4 下午10:52:36
 */
public class BaseDao extends SqlMapClientDaoSupport {

    protected Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    protected CacheHandler cache = new CacheHandler(); //缓存操作

    @PostConstruct
    public void initSqlMapClient() {
        super.setSqlMapClient(sqlMapClient);
    }

    /**
     * 获得表刚插入的ID
     * @return
     */
    public long getLastInsertId()
    {
        try {
            Long i = (Long)sqlMapClient.queryForObject("comm.getLastInsertId");
            return i.longValue();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 事务支持:开始事务
     * @return
     */
    public void begin()
    {
        try {
            sqlMapClient.queryForObject("comm.begin");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 事务支持：回滚
     * @return
     */
    public void rollback()
    {
        try {
            sqlMapClient.queryForObject("comm.rollback");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 事务支持：提交
     * @return
     */
    public void commit()
    {
        try {
            sqlMapClient.queryForObject("comm.commit");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得公共配置表cfg_setting的配置项
     * @param cfg_key
     * @return
     */
    public CfgSetting getCfgSetting(String cfg_key)
    {

        if (cfg_key.equals("")) return null;
        String key = CacheConstant.KEY_CFG_SETTING + cfg_key;
        CfgSetting item = (CfgSetting)cache.getFromJson(key,CfgSetting.class);
        if (item == null) {
            try {
                item = (CfgSetting)getSqlMapClientTemplate().queryForObject("cfgSetting.getByCfgKey",cfg_key);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }

        return item;
    }

    /**
     * 获得配置项[cfg_setting]的子项名称
     * @param cfg_key 表cfg_setting中的cfg_key字段
     * @param key 表cfg_setting中的val字段，是个k/v键值对的JSON
     * @return
     */
    public String getCfgSettingName(String cfg_key,String key)
    {
        logger.warn("key="+key);
        if (key == null || key.equals("")) {
            return "";
        }
        CfgSetting item = getCfgSetting(cfg_key);
        if (item == null) {
            return "";
        }
        String val = item.getVal(); //这是一个JSON字符串，数据库中保存的
        if (val == null || val.equals("")) {
            return "";
        }
        JSONObject jsonObj = JsonUtils.decode(val);

        if (jsonObj.has(key) == false) {
            return "";
        }
        return jsonObj.get(key).toString();
    }
}
