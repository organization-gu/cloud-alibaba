package com.lanswon.management.aop.log.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @Description: 使用方法 #{xxx.xxx}
 * @Author GU-YW
 * @Date 2019/12/4 13:36
 */
public class AnnotationResolver {

    private static AnnotationResolver resolver ;


    public static AnnotationResolver newInstance(){

        if (resolver == null) {
            return resolver = new AnnotationResolver();
        }else{
            return resolver;
        }

    }


    /**
     * 解析注解上的值
     * @param joinPoint
     * @param str1
     * @return
     */
    public String resolver(JoinPoint joinPoint, String str1){

        int i = str1.indexOf("#{");
        int i1 = str1.indexOf("}", i);


        boolean b = i > -1 && i1> -1;
        if(b){

            String  stra = str1.substring(i,i1+1);//截取#{}xx.xx
            String strb = stra.replaceAll("#\\{", "").replaceAll("\\}", "");

            String value = "";
            //处理业务 返回 结果
            if (strb.contains(".")) { // 复杂类型
                try {
                    value = (String)complexResolver(joinPoint, strb);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                value = (String)simpleResolver(joinPoint, strb);
            }


            str1  = str1.replace(stra, value); //然后替换 值
            str1 = resolver(joinPoint,str1);

        }

        return str1;
    }


    /**
     *
     * @param joinPoint
     * @param str  class.property
     * @return
     * @throws Exception
     */
    private Object complexResolver(JoinPoint joinPoint, String str) throws Exception {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        String[] strs = str.split("\\.");

        for (int i = 0; i < names.length; i++) {
            if (strs[0].equals(names[i])) {
                Object obj = args[i];
                Method dmethod = obj.getClass().getDeclaredMethod(getMethodName(strs[1]), null);
                Object value = dmethod.invoke(args[i]);
                return getValue(value, 1, strs);
            }
        }

        return null;

    }

    private Object getValue(Object obj, int index, String[] strs) {

        try {
            if (obj != null && index < strs.length - 1) {
                Method method = obj.getClass().getDeclaredMethod(getMethodName(strs[index + 1]), null);
                obj = method.invoke(obj);
                getValue(obj, index + 1, strs);
            }

            return obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getMethodName(String name) {
        return "get" + name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
    }


    private Object simpleResolver(JoinPoint joinPoint, String str) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] names = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < names.length; i++) {
            if (str.equals(names[i])) {
                return args[i];
            }
        }
        return null;
    }
}
