package bitcamp.myapp.dao;

import org.apache.ibatis.session.SqlSession;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoFactory {
    private SqlSession sqlSession;

    public DaoFactory(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public <T> T createObject(Class<T> daoType) throws Exception {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{daoType},
//              0. 기존 코드
//              new InvocationHandler() {
//                  @Override
//                  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                      System.out.println("----> " + method.getName());
//                      return null;
//                  }
//              }

//              1. 람다
//              (Object proxy, Method method, Object[] args) -> {
//                  System.out.println("----> " + method.getName());
//                  return null;
//              }

//              2. 내부 메서드로 뺌
//              (Object proxy, Method method, Object[] args) -> {
//                  return this.invoke(proxy, method, args)
//              }

//              3. 메서드 레퍼런스 사용
                // InvocationHandler의 invoke가 호출되면 해당 메서드를 실행하게 자바가 알아서 짜줌
                this::invoke
        );
    }

    // 메서드의 이름(invoke -> aaa)은 달라도 리턴 타입과 파라미터만 동일하다면 내부 메서드로 사용 가능
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        String namespace = proxy.getClass().getInterfaces()[0].getSimpleName();
        String sqlId = method.getName();
        String statement = String.format("%s.%s", namespace, sqlId);

        Object paramValue = null;
        if (args != null) {
            if (args.length == 1) {
                paramValue = args[0];
            } else {
                Parameter[] params = method.getParameters();
                Map<String, Object> map = new HashMap<>();
                for (int i = 0; i < args.length; i++) {
                    Param anno = params[i].getAnnotation(Param.class);
                    map.put(anno.value(), args[i]);
                }
                paramValue = map;
            }
        }

        Class<?> returnType = method.getReturnType();

        if (returnType == List.class) {
            return sqlSession.selectList(statement, paramValue);
        } else if (returnType == int.class || returnType == void.class || returnType == boolean.class) {
            int count = sqlSession.insert(statement, paramValue);
            if(returnType == boolean.class){
                return (int) count > 0;
            }else if(returnType == void.class){
                return null;
            }else {
                return count;
            }
        }else {
            return sqlSession.selectOne(statement, paramValue);
        }
    }
}
