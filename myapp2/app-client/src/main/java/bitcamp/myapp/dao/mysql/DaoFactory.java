package bitcamp.myapp.dao.mysql;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DaoFactory {
    public <T> T createObject(Class<T> daoType){
        Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{daoType}, this::invoke);
        return null;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
