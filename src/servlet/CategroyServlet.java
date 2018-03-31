package servlet;

import bean.Book;
import bean.Category;
import service.BookService;
import service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "CategroyServlet")
public class CategroyServlet extends BaseServlet {
   public String findAllCategory(HttpServletRequest request,HttpServletResponse response){
       CategoryService categoryService= new CategoryService();
       List<Category> list=categoryService.findCate();
       request.setAttribute("list",list);
       return "/jsps/left.jsp";
   }
    //后台管理员查询所有的分类
    public String findAlladminCategory(HttpServletRequest req, HttpServletResponse resp) {
        //调用方法查询所有的分类
        CategoryService service = new CategoryService();
        List<Category> list = service.findCate();
        req.setAttribute("list", list);
        return "/adminjsps/admin/category/list.jsp";
    }
    //根据cid查询到当前的分类，再将分类显示到修改页面上进行修改
    public String dispatcherCategory(HttpServletRequest request,HttpServletResponse response){
       String cid=request.getParameter("cid");
       CategoryService categoryService=new CategoryService();
       Category category = categoryService.findCateByCid(cid);
       request.setAttribute("cate",category);
       return "/adminjsps/admin/category/mod.jsp";
    }
    //对查询到的用户进行修改
    public String updateCategory(HttpServletRequest request,HttpServletResponse response){
        String cname=request.getParameter("cname");
        String cid=request.getParameter("cid");
        //对category进行封装
        Category category=new Category();
        category.setCid(cid);
        category.setCname(cname);
        CategoryService categoryService=new CategoryService();
        categoryService.updateCategory(category);
        //对数据库再进行一次查询，将所有的结果返回到list页面中
        return "/category?method=findAlladminCategory";
    }
    public String deleteCategory(HttpServletRequest request,HttpServletResponse response){
        String cid=request.getParameter("cid");
        //首先把关联的图书表下面的cid设置为控才能进行删除操作
        BookService bookService=new BookService();
        List<Book> bookList= bookService.findBookByCid(cid);
        CategoryService categoryService=new CategoryService();
        //j将图书的cid置空
        categoryService.updateBookByCid(bookList);
        categoryService.deleteCategory(cid);
        return "/category?method=findAlladminCategory";
    }
    public String addCategory(HttpServletRequest request,HttpServletResponse response){
        //添加分类
        String cname=request.getParameter("cname");
        String cid= UUID.randomUUID().toString();
        //将cname和cid进行封装
        Category category=new Category();
        category.setCid(cid);
        category.setCname(cname);
        CategoryService categoryService=new CategoryService();
        categoryService.addCategory(category);
        return "/category?method=findAlladminCategory";
    }
}
