package com.chinabluedon.basicknowledgedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chinabluedon.basicknowledgedemo.annotation.base.Description;
import com.chinabluedon.basicknowledgedemo.annotation.project.Filter;
import com.chinabluedon.basicknowledgedemo.annotation.project.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testAnnotationSql();
    }

    private void testAnnotationSql() {

    }

    private String query(Filter filter) {
        StringBuilder sb = new StringBuilder();
        //1.获取到class
        Class<? extends Filter> c = filter.getClass();
        //2.获取到table的名字
        boolean exist = c.isAnnotationPresent(Table.class);
        if (!exist) {
            return null;
        }
        Table t = c.getAnnotation(Table.class);
        String tableName = t.value();
        sb.append("select*from").append(tableName).append("where 1=1");
        //3.遍历所有字段
        Field[] declaredFields = c.getDeclaredFields();
        for (Field f : declaredFields) {
            //4.处理每个字段对应的sql
            //4.1拿到字段名
            boolean b = f.isAnnotationPresent(Table.class);
            if (!b) {
                continue;
            }
            Table table = f.getAnnotation(Table.class);
            String columnNane = table.value();
            //4.2拿到字段的值
            String fildeName = f.getName();
            String getMethodName = "get" + fildeName.substring(0, 1).toUpperCase() + fildeName.substring(1);
            Object fieldValue = null;
            try {
                Method getMethod = c.getMethod(getMethodName);
                fieldValue = getMethod.invoke(filter);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //4.3拼装sql
            if(fieldValue==null||(fieldValue instanceof Integer)&&(Integer)fieldValue==0) {
                continue;
            }
            sb.append(" and ").append(fildeName);
            if(fieldValue instanceof String) {
                if(((String) fieldValue).contains(",")) {
                    String[] split = ((String) fieldValue).split(",");
                    sb.append("in(");
                    for (String s : split) {
                        sb.append("'").append(s).append("'").append(",");
                    }
                    sb.deleteCharAt(sb.length()-1);
                    sb.append(")");
                }
                sb.append("=").append("'").append(fieldValue).append("'");
            }else if(fieldValue instanceof Integer) {
                sb.append("=").append(fieldValue);
            }


        }
        return sb.toString();
    }

    @SuppressWarnings({"deprecation", "Since15"})
    private void testAnnotation() {
        try {
            //1.使用类加载器
            Class<?> c = Class.forName("com.chinabluedon.basicknowledgedemo.annotation.base.Child");
            //2.找到类上面的注解
            boolean isExist = c.isAnnotationPresent(Description.class);
            if (isExist) {
                //3.拿到注解实例
                Description annotation = c.getAnnotation(Description.class);
                System.out.println(annotation.value());

                //4.找到方法上的注解
                Method[] methods = c.getMethods();
                for (Method method : methods) {
                    boolean annotationPresent = method.isAnnotationPresent(Description.class);
                    if (annotationPresent) {
                        Description annotation1 = method.getAnnotation(Description.class);
                        System.out.println(annotation1.value());
                    }
                }
                //另外一中解析方法
                for (Method method : methods) {
                    Annotation[] annotations = method.getAnnotations();
                    for (Annotation annotation1 : annotations) {
                        if (annotation1 instanceof Description) {
                            System.out.println(((Description) annotation1).value());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
