package com.xzc.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ObjectTool {

    public static Object getObject(Class<?> clazz, Map<String, String[]> data)
            throws Exception {
        Object obj = clazz.newInstance();
        Set<Entry<String, String[]>> entrySet = data.entrySet();
        for (Entry<String, String[]> m : entrySet) {
            String name = m.getKey();
            String[] value = m.getValue();
            try {
                PropertyDescriptor pd = new PropertyDescriptor(name, clazz);
                Method setMethod = pd.getWriteMethod();
                if (pd.getPropertyType().isArray()) {
                    setMethod.invoke(obj, (Object) value);
                } else {
                    setMethod.invoke(obj, value[0]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return obj;
    }

    /**
     * 将父类所有的属性COPY到子类中。
     * 类定义中child一定要extends father；
     * 而且child和father一定为严格javabean写法，属性为username，方法为getUsername
     *
     * @param father
     * @param child
     */
    public static void fatherToChild(Object father, Object child) {
        if (!(child.getClass().getSuperclass() == father.getClass())) {
            System.err.println("child不是father的子类");
        }
        Class fatherClass = father.getClass();
        Field ff[] = fatherClass.getDeclaredFields();
        for (int i = 0; i < ff.length; i++) {
            Field f = ff[i];//取出每一个属性
            try {
                Method m = fatherClass.getMethod("get" + upperHeadChar(f.getName()));//方法getDeleteDate
                Object obj = m.invoke(father);//取出属性值
                f.set(child, obj);
                System.out.println(child.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    private static String upperHeadChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
    }

}
