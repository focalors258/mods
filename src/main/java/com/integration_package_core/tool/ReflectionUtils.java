package com.integration_package_core.tool;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


import java.util.logging.Logger;

public class ReflectionUtils {
    private static final Logger LOGGER = Logger.getLogger(ReflectionUtils.class.getName());


    public static <T, M> void set(String wayName, T value, M target) throws NoSuchFieldException, IllegalAccessException {


        Field wayController = target.getClass().getDeclaredField(wayName);

        wayController.setAccessible(true);

        wayController.set(target, value);

    }

    public static <T, M> Object get(String wayName, M target) throws IllegalAccessException {

        // Class<M> clazz= (Class<M>) target.getClass();

        List<Field> cache=new ArrayList<>();;

         findFieldsByTypeRecursive(target.getClass(), wayName,cache);

        if(!cache.isEmpty()){
            Field wayController = cache.get(0);

            if (wayController != null) {
                wayController.setAccessible(true);

                return wayController.get(target);

            }
        }

        return null;

    }

    public static <M> void findFieldsByTypeRecursive(Class<M> clazz, String wayName,List<Field> cache) {

        if (clazz.equals(Object.class)) {
            return;
        }

        try {

            //System.out.println(clazz);



            Field wayController = clazz.getDeclaredField(wayName);

           // System.out.println(wayController);

            wayController.setAccessible(true);

            cache.add(wayController);



        } catch (NoSuchFieldException e) {



            findFieldsByTypeRecursive(clazz.getSuperclass(), wayName,cache);

            // 处理未找到字段的情况（例如记录日志或返回null）
            //  System.err.println("字段 '" + wayName + "' 不存在于类 " + target.getClass());
            //return null;
        }


       // return null;


    }


    /**
     * 获取对象中特定类型成员变量的值
     */
    public static <M> M get(Object targetObject, Class<M> fieldType)
            throws ReflectiveOperationException {
        if (targetObject == null) {
            return null;
        }

        Field field = findSingleFieldByType(targetObject.getClass(), fieldType);
        field.setAccessible(true);
        return (M) field.get(targetObject);
    }

    /**
     * 设置对象中特定类型成员变量的值
     */
    public static <M> void set(Object targetObject, Class<M> fieldType, M value)
            throws ReflectiveOperationException {
        if (targetObject == null) {
            return;
        }

        Field field = findSingleFieldByType(targetObject.getClass(), fieldType);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    /**
     * 获取类中唯一的特定类型成员变量（包括父类）
     */
    private static Field findSingleFieldByType(Class<?> clazz, Class<?> fieldType) throws NoSuchFieldException {
        List<Field> matches = new ArrayList<>();
        findFieldsByTypeRecursive(clazz, fieldType, matches);

        if (matches.isEmpty()) {
            throw new NoSuchFieldException("No field of type " + fieldType.getName() + " found in " + clazz.getName());
        }
        if (matches.size() > 1) {
            //LOGGER.warning("Multiple fields of type {} found in {}, using first match",
            // fieldType.getName(), clazz.getName());
            return matches.get(0); // 或抛出异常，根据需求决定
        }

        return matches.get(0);
    }

    /**
     * 递归查找类及其父类中所有匹配类型的字段
     */
    private static void findFieldsByTypeRecursive(Class<?> clazz, Class<?> fieldType, List<Field> matches) {
        if (clazz == null || clazz.equals(Object.class)) {
            return;
        }

        // LOGGER.fine("Searching for field of type {} in class {}", fieldType.getName(), clazz.getName());
        for (Field field : clazz.getDeclaredFields()) {
            if (fieldType.isAssignableFrom(field.getType())) {
                //LOGGER.fine("Found matching field: {}", field.getName());
                matches.add(field);
            }
        }

        findFieldsByTypeRecursive(clazz.getSuperclass(), fieldType, matches);
    }
}