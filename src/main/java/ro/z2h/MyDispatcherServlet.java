package ro.z2h;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.controller.DepartmentController;
import ro.z2h.controller.EmployeeController;
import ro.z2h.fmk.AnnotationScanUtils;
import ro.z2h.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterAbortException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.rmi.activation.ActivationDesc;
import java.util.*;

/**
 * Created by George on 11/11/2014.
 */
public class MyDispatcherServlet extends HttpServlet {


    Map<String, MethodAttributes> myHashMap = new HashMap<String, MethodAttributes>();

    @Override
    public void init() throws ServletException {

        try {
            Iterable<Class> classes = AnnotationScanUtils.getClasses("ro.z2h.controller");

            for (Class aClass : classes) {
                //((Column)f.getAnnotation(Column.class)).nameColumn();
                if (aClass.isAnnotationPresent(MyController.class)) {
                    MyController controllerAnnotation = (MyController) aClass.getAnnotation(MyController.class);
                    String urlPath = controllerAnnotation.urlPath();
                    //System.out.println(urlPath);

                    Method[] methods = aClass.getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(MyRequestMethod.class)) {
                            MyRequestMethod methodAnnotation = (MyRequestMethod) (method.getAnnotation(MyRequestMethod.class));
                            System.out.println(urlPath + methodAnnotation.urlPath());

                            MethodAttributes methodAttributes = new MethodAttributes();
                            methodAttributes.setControllerClass(aClass.getName());
                            methodAttributes.setMethodName(method.getName());
                            methodAttributes.setMethodType(methodAnnotation.methodType());
                            methodAttributes.setMethodParameterTypes(method.getParameterTypes());

                            myHashMap.put(urlPath + methodAnnotation.urlPath(), methodAttributes);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        printMyMap();
    }

    private void printMyMap() {
        for (Map.Entry<String, MethodAttributes> entry : myHashMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("GET", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("POST", req, resp);
    }

    private void dispatchReply(String httpMethod, HttpServletRequest req, HttpServletResponse resp) {

        /* Dispatch */
        Object r = dispatch(httpMethod, req, resp);

        /* Replay */
        try {
            replay(r, req, resp);
        } catch (Exception e) {
            /* Catch errors */
            sendErrors(e, req, resp);
        }
    }


    /**
     * Where an application controller should be called.
     *
     * @param httpMethod name of method
     * @param req        request
     * @param resp       response
     * @return
     */
    private Object dispatch(String httpMethod, HttpServletRequest req, HttpServletResponse resp) {

        /*
        // pentru /test         - return "Hello world!"
        // pentru /employee     - return metoda getAllEmployees
        // pentru /department   - return getAllDepartments
        if (req.getPathInfo().startsWith("/employee"))
            return (new EmployeeController().getAllEmployees());

        if (req.getPathInfo().startsWith("/employee/one"))
            return (new EmployeeController().getOneEmployee());

        if (req.getPathInfo().startsWith("/department"))
            return (new DepartmentController().getAllDepartment());
        */

        MethodAttributes methodAttributes = myHashMap.get(req.getPathInfo());
        if (methodAttributes != null) {
            try {
                Class<?> controllerClass = Class.forName(methodAttributes.getControllerClass());
                Object controllerInstance = controllerClass.newInstance();


                Object responseProcesare = null;
                /*
                // my implementation - it's work!
                Enumeration<String> parameterNames = req.getParameterNames();
                if (parameterNames.hasMoreElements()){
                    Method methodWithParameters = controllerClass.getMethod(methodAttributes.getMethodName(),String.class);


                    while (parameterNames.hasMoreElements()) {
                        String paramName = parameterNames.nextElement();
                        String[] paramValues = req.getParameterValues(paramName);
                        String paramValue = paramValues[0];
                        responseProcesare = methodWithParameters.invoke(controllerInstance, paramValue);
                    }
                }
                else {
                    Method method = controllerClass.getMethod(methodAttributes.getMethodName());
                    responseProcesare = method.invoke(controllerInstance);
                }
                */
                Method method = controllerClass.getMethod(methodAttributes.getMethodName(), methodAttributes.getMethodParameterTypes());

                Parameter[] realMethodParameters = method.getParameters();
                List<String> methodParamsValues = new ArrayList<String>();

                for (Parameter realMethodParameter : realMethodParameters) {
                    String parameter = req.getParameter(realMethodParameter.getName());
                    methodParamsValues.add(parameter);
                }
                responseProcesare = method.invoke(controllerInstance, (String[]) methodParamsValues.toArray(new String[0]));
               // responseProcesare = method.invoke(controllerInstance, (String[])methodParamsValues.toArray(new String[0]),
               //         (String[])methodParamsValues.toArray(new String[1]));

                return responseProcesare;

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return "Hello World!";
    }

    /**
     * Use to send the view to the client
     *
     * @param r
     * @param req
     * @param resp
     */
    private void replay(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        /*
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            writer.printf(r.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        PrintWriter out = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(r);
        out.printf(s);


    }

    /**
     * Catch an error and print a message
     *
     * @param e
     * @param req
     * @param resp
     */
    private void sendErrors(Exception e, HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("There was an exception");
    }
}
