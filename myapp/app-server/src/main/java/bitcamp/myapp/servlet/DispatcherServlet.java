package bitcamp.myapp.servlet;

import bitcamp.myapp.annotation.RequestMapping;
import bitcamp.myapp.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;

@MultipartConfig(
        maxFileSize = 1024 * 1024 * 60,
        maxRequestSize = 1024 * 1024 * 100)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {

    private List<Object> controllers;

    @Override
    public void init() throws ServletException {
        controllers = (List<Object>) this.getServletContext().getAttribute("controllers");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            // 클라이언트가 요청한 URL을 가지고 페이지 컨트롤러를 찾는다.
            String controllerPath = req.getPathInfo();

            Object pageController = null;
            Method requestHandler = null;

            loop:
            for (Object controller : controllers) {
                Method[] methods = controller.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    if (requestMapping == null || !requestMapping.value().equals(controllerPath)) {
                        continue;
                    }
                    requestHandler = method;
                    pageController = controller;
                    break loop;
                }
            }

            if (pageController == null) {
                throw new Exception("해당 URL을 처리할 수 없습니다.");
            }

            Map<String, Object> map = new HashMap<>();
            // requestHandler에게 전달할 아규먼트 준비
            Object[] arguments = prepareRequestHandlerArguments(requestHandler, req, res, map);

            if (requestHandler.getReturnType() == void.class) {
                requestHandler.invoke(pageController, arguments);
                return;
            }

            String viewName = (String) requestHandler.invoke(pageController, arguments); // 배열에 담아 통째로 넘겨주는 것도 가능하다.

            copyMapToServletRequest(map, req);

            // 페이지 컨트롤러가 정상적으로 실행했으면, viewName을 가져와서 포워딩 한다.
            if (viewName == null) {
                return;
            }

            if (viewName.startsWith("redirect:")) {
                res.sendRedirect(viewName.substring(9));
            } else {
                req.getRequestDispatcher(viewName).forward(req, res);
            }

        } catch (Exception e) {
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }

    private Object[] prepareRequestHandlerArguments(Method requestHandler,
                                                    HttpServletRequest req,
                                                    HttpServletResponse res,
                                                    Map<String, Object> requestAttributesMap) throws Exception {
        // 메서드의 파라미터 분석
        Parameter[] params = requestHandler.getParameters();
        ArrayList<Object> args = new ArrayList<>();

        for (Parameter param : params) {
            Class<?> paramType = param.getType();
            if (paramType == ServletRequest.class || paramType == HttpServletRequest.class) {
                args.add(req);
            } else if (paramType == ServletResponse.class || paramType == HttpServletResponse.class) {
                args.add(res);
            } else if (paramType == HttpSession.class) {
                args.add(req.getSession());
            } else if (paramType == Map.class) {
                args.add(requestAttributesMap);
            } else if (paramType.isPrimitive() ||
                    paramType == String.class ||
                    paramType == java.util.Date.class ||
                    paramType == java.sql.Date.class ||
                    paramType == int[].class) {
                RequestParam paramAnno = param.getAnnotation(RequestParam.class);
                args.add(getDefaultTypeValueFromRequestParameter(req, param.getType(), paramAnno.value()));

            } else if (paramType == Part.class) {
                RequestParam paramAnno = param.getAnnotation(RequestParam.class);
                args.add(req.getPart(paramAnno.value()));
            } else if (paramType == Part[].class) {
                RequestParam paramAnno = param.getAnnotation(RequestParam.class);
                args.add(getPartArray(req, paramAnno.value()));
            } else {
                args.add(createDomainObject(req, paramType));
            }
        }

        return args.toArray();
    }

    // 페이지 컨트롤러가 requestAttributesMap에 값을 저장해둔 것을 꺼내 ServletRequest에 담는다.

    private void copyMapToServletRequest(Map<String, Object> map, ServletRequest req) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            req.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    private Object getDefaultTypeValueFromRequestParameter(HttpServletRequest req, Class<?> paramType, String paramName) {
        // 클라이언트가 보낸 값들 중에서 paramName에 해당하는 값을 꺼낸다.
        String paramValue = req.getParameter(paramName);


        if (paramType != boolean.class && paramType.getComponentType() == null && paramValue == null) {
            return null;
        }

        if (paramType == byte.class) {
            return Byte.parseByte(paramValue);
        } else if (paramType == short.class) {
            return Short.parseShort(paramValue);
        } else if (paramType == int.class) {
            return Integer.parseInt(paramValue);
        } else if (paramType == int[].class) {
            String[] paramValues = req.getParameterValues(paramName);
            if (paramValues == null) {
                return new int[0];
            }

            int[] values = new int[paramValues.length];
            for (int i = 0; i < paramValues.length; i++) {
                values[i] = Integer.parseInt(paramValues[i]);
            }
            return values;
        } else if (paramType == long.class) {
            return Long.parseLong(paramValue);
        } else if (paramType == float.class) {
            return Float.parseFloat(paramValue);
        } else if (paramType == double.class) {
            return Double.parseDouble(paramValue);
        } else if (paramType == char.class) {
            return paramValue.charAt(0);
        } else if (paramType == boolean.class) {
            if (paramValue == null ||
                    paramValue.equals("0") ||
                    paramValue.equals("false") ||
                    paramValue.equals("off") ||
                    paramValue.equals("no")) {
                return false;
            }
            return true;
        } else if (paramType == java.util.Date.class || paramType == java.sql.Date.class) {
            return java.sql.Date.valueOf(paramValue);
        } else {
            return paramValue;
        }
    }

    private Object createDomainObject(HttpServletRequest req, Class<?> paramType) throws Exception {
        // 요청 핸들러가 원하는 파라미터 객체 생성
        Object domainObject = paramType.getConstructor().newInstance();
        Method[] methods = paramType.getDeclaredMethods();

        // 도메인 객체의 셋터 메서드를 호출하여 클라이언트가 보낸 값을 보관한다.
        for (Method m : methods) {
            if (!Modifier.isPublic(m.getModifiers()) || !m.getName().startsWith("set")) {
                continue;
            }

            Class<?> propertyType = m.getParameterTypes()[0]; // setter 메서드에 파라미터가 1개 있다고 가정
            String propertyName = Character.toLowerCase(m.getName().charAt(3)) + m.getName().substring(4);

            // 세터 메서드의 이름(프로퍼티 명)과
            Object value = getDefaultTypeValueFromRequestParameter(req, propertyType, propertyName);
            if (value == null) {
                continue;
            }

            // 세터 메서드에 넣을 값이 클라이언트가 보낸 파라미터에 있으면 객체에 보관한다.
            m.invoke(domainObject, value);
        }

        return domainObject;
    }

    private Part[] getPartArray(HttpServletRequest req, String paramName) throws Exception {
        Collection<Part> parts = req.getParts(); // title 값, content 값, file 첨부 모두 포함
        ArrayList<Part> list = new ArrayList<>();

        for (Part part : parts) {
            if (!part.getName().equals(paramName)) {
                continue;
            }
            list.add(part);
        }
        return list.toArray(new Part[0]);
    }
}
