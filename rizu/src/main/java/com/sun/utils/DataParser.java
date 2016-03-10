package com.sun.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;


public class DataParser {

	 /**
     * 取得所有域变量（包括父类）
     * 
     * @param cls
     *            类
     * @param end
     *            最终父类
     */
    public static List<Field> getFields(Class<?> cls, Class<?> end) {

        List<Field> list = new ArrayList<Field>();

        if (!cls.equals(end)) {

            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                list.add(field);
            }

            Class<?> superClass = (Class<?>) cls.getGenericSuperclass();
            list.addAll(getFields(superClass, end));
        }

        return list;
    }

    /**
     * 解析JSON对象
     * 
     * @param jsonObject
     *            JSON对象
     * @param bean
     *            Bean
     */
    public static void parseJSONObject(JSONObject jsonObject, Object bean) throws Exception {

        List<Field> fieldList = getFields(bean.getClass(), Object.class);

        for (Field field : fieldList) {

            field.setAccessible(true);
            String fieldName = field.getName();

            if (jsonObject.has(fieldName)
                    && !"null".equals(String.valueOf(jsonObject.get(fieldName)))
                    && String.valueOf(jsonObject.get(fieldName)).length() > 0) {

                String typeName = field.getType().getSimpleName();

                if ("List".equals(typeName)) {

                    ParameterizedType pType = (ParameterizedType) field.getGenericType();
                    Class<?> clz = (Class<?>) pType.getActualTypeArguments()[0];

                    JSONArray jsonArray = jsonObject.getJSONArray(fieldName);
                    ArrayList<Object> list = new ArrayList<Object>();

                    boolean isInnerClass = clz.getName().contains("$");

                    for (int j = 0; j < jsonArray.size(); j++) {
                        Object childBean = null;
                        if (isInnerClass) {
                            childBean = clz.getDeclaredConstructors()[0].newInstance(bean);
                        } else {
                            childBean = clz.newInstance();
                        }

                        JSONObject subJsonObject = jsonArray.optJSONObject(j);
                        if (subJsonObject == null) {
//                            childBean = jsonArray.get(j);
                        } else {
                            parseJSONObject(subJsonObject, childBean);
                            //subJsonObject 不为空list add
                            list.add(childBean);
                        }
                    }
                    field.set(bean, list);

                } else if ("String[]".equals(typeName)) {
                	JSONArray contentArray = jsonObject.getJSONArray(fieldName);
                	String[] strArray = new String[contentArray.size()];
                	for (int j = 0; j < contentArray.size(); j++) {
                		strArray[j] = contentArray.getString(j);
                	}
                    field.set(bean, strArray);

                } else if ("String".equals(typeName)) {
                    field.set(bean, String.valueOf(jsonObject.get(fieldName)));

                } else if ("int".equals(typeName) || "Integer".equals(typeName)) {
                    field.set(bean, Integer.parseInt(String.valueOf(jsonObject.get(fieldName))));

                } else if ("float".equals(typeName) || "Float".equals(typeName)) {
                    field.set(bean, Float.parseFloat(String.valueOf(jsonObject.get(fieldName))));

                } else if ("double".equals(typeName) || "Double".equals(typeName)) {
                    field.set(bean, Double.parseDouble(String.valueOf(jsonObject.get(fieldName))));

                } else if ("boolean".equals(typeName) || "Boolean".equals(typeName)) {
                    field.set(bean, Boolean.parseBoolean(String.valueOf(jsonObject.get(fieldName))));

                } else if ("long".equals(typeName) || "Long".equals(typeName)) {
                    field.set(bean, Long.parseLong(String.valueOf(jsonObject.get(fieldName))));

                } else {
                    Class<?> clz = field.getType();
                    JSONObject childObject = jsonObject.getJSONObject(fieldName);

                    boolean isInnerClass = clz.getName().contains("$");

                    Object childBean = null;
                    if (isInnerClass) {
                        childBean = clz.getDeclaredConstructors()[0].newInstance(bean);
                    } else {
                        childBean = clz.newInstance();
                    }

                    parseJSONObject(childObject, childBean);
                    field.set(bean, childBean);
                }
            }else{
            	//给相应对象赋值
/*				String typeName = field.getType().getSimpleName();
				if ("String".equals(typeName)) {
					field.set(bean, "");
				}*/
            }
        }
    }
    
}
