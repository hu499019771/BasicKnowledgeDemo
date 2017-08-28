package com.chinabluedon.basicknowledgedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chinabluedon.basicknowledgedemo.annotation.base.ContentView;
import com.chinabluedon.basicknowledgedemo.annotation.base.Description;
import com.chinabluedon.basicknowledgedemo.annotation.base.ViewInject;
import com.chinabluedon.basicknowledgedemo.annotation.project.Filter;
import com.chinabluedon.basicknowledgedemo.annotation.project.Table;
import com.chinabluedon.basicknowledgedemo.reflect.FootStar;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    @ViewInject(R.id.textView)
    private TextView mTextView;

    @ViewInject(value = R.id.button, clickable = true)
    private Button mButton;


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //通过注解生成View；
        getAllAnnotationView();
        mTextView.setText("我是新文字嘿嘿");
        mButton.setText("我是新的按钮文字嘿嘿");
    }



    private void getAllAnnotationView () {

        //1.找到有该注解的字段
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(ViewInject.class)) {
                ViewInject viewInject = f.getAnnotation(ViewInject.class);
                f.setAccessible(true);
                //2.获取注解中的值
                int id = viewInject.value();
                //3.通过findviewbyid获取控件,并赋值给控件
                try {
                    f.set(this, findViewById(id));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @SuppressWarnings("Since15")
    private void testReflect () {
        try {
            Class<?> c = Class.forName("com.chinabluedon.basicknowledgedemo.reflect.FootStar");
            //1.获取类的全限定名
            String name = c.getName();
            //2.获取类名
            String simpleName = c.getSimpleName();
            //3.获取类的所有public字段名称
            Field[] fields = c.getFields();
            //4.获取类的某个public字段名字
            Field field = c.getField("club");
            //5.获取类的所有字段
            Field[] declaredFields = c.getDeclaredFields();
            //6.获取类的所有方法
            Method[] declaredMethods = c.getDeclaredMethods();
            //7.获取某个方法
            Method method = c.getDeclaredMethod("getName");
            //8.使用某个方法
            FootStar star = new FootStar("梅西", 30, "巴萨");
            String clubName = (String) field.get(star);
            //9.获取构造方法
            Constructor<?>[] constructors = c.getConstructors();
            for (Constructor constructor : constructors) {
            }
            //10.创建对象
            FootStar star1 = (FootStar) c.newInstance();
            //11.获取类上注解
            boolean b = c.isAnnotationPresent(ContentView.class);
            if (b) {
                ContentView annotation = c.getAnnotation(ContentView.class);

            }
            //12.获取方法上的注解
            boolean b1 = method.isAnnotationPresent(ContentView.class);
            if (b1) {
                ContentView annotation = method.getAnnotation(ContentView.class);
            }
            //13.获取字段上注解
            boolean b2 = field.isAnnotationPresent(ContentView.class);
            if (b2) {
                ContentView annotation = field.getAnnotation(ContentView.class);
                Log.e(TAG, annotation.value() + "");

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void testAnnotationSql () {

    }

    private String query (Filter filter) {
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
            if (fieldValue == null || (fieldValue instanceof Integer) && (Integer) fieldValue == 0) {
                continue;
            }
            sb.append(" and ").append(fildeName);
            if (fieldValue instanceof String) {
                if (((String) fieldValue).contains(",")) {
                    String[] split = ((String) fieldValue).split(",");
                    sb.append("in(");
                    for (String s : split) {
                        sb.append("'").append(s).append("'").append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(")");
                }
                sb.append("=").append("'").append(fieldValue).append("'");
            } else if (fieldValue instanceof Integer) {
                sb.append("=").append(fieldValue);
            }


        }
        return sb.toString();
    }

    @SuppressWarnings({"deprecation", "Since15"})
    private void testAnnotation () {
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


    @Override
    public void onClick (View v) {
        Toast.makeText(MainActivity.this, "我被点击了", Toast.LENGTH_SHORT).show();
    }
}
