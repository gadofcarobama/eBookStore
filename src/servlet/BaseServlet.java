package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("utf-8");
            //获取当前正在运行的子类
            Class clazz=this.getClass();
            String method=req.getParameter("method");
            if(method==null){
                method="excute";
            }
            //获取表单提交过来的方法并通过反射来调用子类的对应的相同的方法来执行
            //该方法的第一个参数是方法的名字，后面的参数是方法的参数
            Method method1=clazz.getMethod(method,HttpServletRequest.class,HttpServletResponse.class);
            String url=(String) method1.invoke(clazz.newInstance(),req,resp);
           if(url!=null){
               //转发操作
            req.getRequestDispatcher(url).forward(req,resp);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
