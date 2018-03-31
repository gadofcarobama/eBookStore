package servlet;

import bean.User;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import utils.MyJDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Service;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;


public class UserServlet extends BaseServlet {
    //对用户进行操作
    public String updateUser(HttpServletRequest request,HttpServletResponse response){
        //接受传来的用户id
        String uid=request.getParameter("uid");
        UserService userService=new UserService();
        userService.updateUser(uid);
        return "/user?method=findAllUser";
    }
    //后台管理员登录
     public String adminLogin(HttpServletRequest request,HttpServletResponse response){
         String adminname=request.getParameter("adminname");
         String password=request.getParameter("password");
         if ("admin".equals(adminname)&&"123456".equals(password)){
             request.getSession().setAttribute("admin",adminname+"#huyi#+"+password);
             //重定向时得加项目名称
             try {
                 response.sendRedirect(request.getContextPath()+" /adminjsps/admin/index.jsp");
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }else {
             request.setAttribute("msg","您输入的用户名或密码错误");
             return "adminjsps/login.jsp";
         }
         return null;
     }
     //注册的方法
    public String regist(HttpServletRequest request,HttpServletResponse response) {
        //判断数据库中是否存在该用户名
        try {
            //处理中文乱码
            request.setCharacterEncoding("utf-8");
            String userName=request.getParameter("username");
            UserService service=new UserService();
            User user=service.findByUserName(userName);
           if(user!=null){
               //该用户已经存在
               request.getSession().setAttribute("msg","该用户已经存在");
               request.getRequestDispatcher("/jsps/user/regist.jsp").forward(request,response);
           }else {
               //完成用户的添加
               //进行用户的封装
               User user1=new User();
               BeanUtils.populate(user1,request.getParameterMap());
               //手动进行登录和uid的封装
               String uid= UUID.randomUUID().toString();
               String status="1";
               user1.setUid(uid);
               user1.setStatus(status);
               UserService userService=new UserService();
               boolean flag=userService.add(user1);
               if(flag){
                   request.getSession().setAttribute("msg","添加成功");
                   return "/jsps/msg.jsp";
               }else{
                   request.getSession().setAttribute("msg","添加失败");
                   return "/jsps/user/regist.jsp";
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String login(HttpServletRequest request,HttpServletResponse response){
        //得到输入的验证
        String codeInput = request.getParameter("code");
        //得到session里面的验证码
        String codeSession = (String) request.getSession().getAttribute("code");
        //比较
        if(!codeInput.equals(codeSession)) {//不相同
            request.setAttribute("msg", "验证码输入有误");
            return "/jsps/user/login.jsp";
        }
        try{
            //处理中文乱码
            request.setCharacterEncoding("utf-8");
            //获取用户名密码
            String username=request.getParameter("username");
            String password=request.getParameter("password");
            //调用方法进行查询
            UserService service=new UserService();
            User user=service.find(username,password);
            //判断是否存在user
            if(user==null){
                request.setAttribute("msg","用户名或密码错误");
                return "/jsps/user/login.jsp";
            }else {
                //将User放入Session对象中
                request.getSession().setAttribute("user",user);
                response.sendRedirect("/jsps/main.jsp");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //退出功能
    public String exit(HttpServletRequest request,HttpServletResponse response){
        HttpSession session=request.getSession();
        session.invalidate();
        //重定向到主页面
        try {
            response.sendRedirect("jsps/main.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //查询所有用户
    public String findAllUser(HttpServletRequest request,HttpServletResponse response){
        UserService userService=new UserService();
        List<User> list = userService.findAllUser();
        request.setAttribute("list",list);
        return "/adminjsps/admin/user/list.jsp";
    }
}
