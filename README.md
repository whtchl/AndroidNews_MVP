# AndroidNews
 
 用泛型的方式构建MVP模式
 
获取泛型中的类的类型


public class TUtil {

    public static <T> T getT(Object o, int i) {
    
        try {
            Log.d("TUtil","Class "+i+":type is "+  (Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i]);

            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}



<img src="https://raw.githubusercontent.com/whtchl/AndroidNews/master/art/1.jpg"/>
<img src="https://raw.githubusercontent.com/whtchl/AndroidNews/master/art/2.jpg"/>
<img src="https://raw.githubusercontent.com/whtchl/AndroidNews/master/art/3.jpg"/>
<img src="https://raw.githubusercontent.com/whtchl/AndroidNews/master/art/4.jpg"/>
<img src="https://raw.githubusercontent.com/whtchl/AndroidNews/master/art/5.jpg"/>
