package ro.z2h;

import ro.z2h.annotation.MyController;
import ro.z2h.controller.DepartmentController;
import ro.z2h.controller.EmployeeController;
import ro.z2h.fmk.AnnotationScanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.rmi.activation.ActivationDesc;
import java.util.Iterator;

/**
 * Created by George on 11/11/2014.
 */
public class MyDispatcherServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("asdlksajdlksahfjkhdfjkldhds");

        try {
            Iterable<Class> classes = AnnotationScanUtils.getClasses("ro.z2h.controller");
            System.out.println("kkk: " + classes);

            for (Class c : classes){
                System.out.println("jjj");
                //((Column)f.getAnnotation(Column.class)).nameColumn();
                if (c.isAnnotationPresent(MyController.class)){
                    Annotation annotation = c.getAnnotation(MyController.class);
                    String url = ((MyController)annotation).urlPath();
                    System.out.println("bdsfkl: " + url);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

    private void dispatchReply(String httpMethod, HttpServletRequest req, HttpServletResponse resp){

        /* Dispatch */
        Object r = dispatch(httpMethod, req, resp);

        /* Replay */
        replay(r, req, resp);

        /* Catch errors */
        Exception e = null;
        sendErrors(e, req, resp);
    }


    /**
     * Where an application controller should be called.
     *
     * @param httpMethod name of method
     * @param req request
     * @param resp response
     * @return
     */
    private Object dispatch(String httpMethod, HttpServletRequest req, HttpServletResponse resp) {

        // pentru /test         - return "Hello world!"
        // pentru /employee     - return metoda getAllEmployees
        // pentru /department   - return getAllDepartments
        if (req.getPathInfo().startsWith("/employee"))
            return (new EmployeeController().getAllEmployees());

        if (req.getPathInfo().startsWith("/employee/one"))
            return (new EmployeeController().getOneEmployee());

        if (req.getPathInfo().startsWith("/department"))
            return (new DepartmentController().getAllDepartment());


        return "Hello World!";
    }

    /**
     * Use to send the view to the client
     * @param r
     * @param req
     * @param resp
     */
    private void replay(Object r, HttpServletRequest req, HttpServletResponse resp) {
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            writer.printf(r.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Catch an error and print a message
     * @param e
     * @param req
     * @param resp
     */
    private void sendErrors(Exception e, HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("There was an exception");
    }
}
