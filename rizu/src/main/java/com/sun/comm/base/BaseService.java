package com.sun.comm.base;

import com.sun.comm.cache.CacheHandler;
//import com.sun.wache.bean.file.FilePhoto;
//import com.sun.wache.dao.file.FilePhotoDao;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.Map;

/**
 * BaseDao,Dao需继承此Dao
 *
 * @author qxh since 2015-11-4 下午10:52:36
 */
public class BaseService  {

    protected Logger logger = Logger.getLogger(this.getClass().getName());

    protected int errorCode = 0; /*错误代码*/
    protected String errorMsg = ""; /*错误描述*/

    protected CacheHandler cache = new CacheHandler(); //缓存操作


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public CacheHandler getCache() {
        return cache;
    }


//    @Resource
//    protected FilePhotoDao filePhotoDao ;

    protected String photourl = AppConfig.getPhotoUrl();

    /**
     * 获得获得图片URL
     * @param id
     * @param size 图片类型 real,small,middle,big
     * @return
     */
//    public String getPhotoUrl(long id,String size)
//    {
//        String path = "";
//        if (id == 0) {
//            return "";
//        }
//        FilePhoto pitem = filePhotoDao.getById(id);
//        if (pitem == null) return "";
//
//        if (size == null || size.equals("") || size.equals("real")) {
//            path = photourl + "/" + pitem.getPath();
//        }else if (size.equals("small")){
//            path = photourl + "/" + pitem.getSmall_path();
//        }else if (size.equals("middle")){
//            path = photourl + "/" + pitem.getMiddle_path();
//        }else if (size.equals("big")){
//            path = photourl + "/" + pitem.getBig_path();
//        }
//
//        return path;
//    }

    /**
     * 组合一下搜索条件，增加页码和每页条数
     * @param m  搜索条件的map，SQL语句可以通过这个MAP组合搜索条件WHERE语句
     * @param page 页码
     * @param perpage 每页条数
     * @return
     */
    public Map<String,Object> pageMap(Map<String,Object> m,int page,int perpage)
    {
        page = Math.max(page,1);
        perpage = Math.max(perpage,1);
        Map<String,Object> map = m;//条件

        m.put("perpage",perpage);
        m.put("start",(page-1)*perpage);
        return m;
    }
}
