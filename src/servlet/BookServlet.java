package servlet;

import bean.Book;
import bean.Category;
import org.apache.commons.beanutils.BeanUtils;
import service.BookService;
import service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@WebServlet(name = "BookServlet")
public class BookServlet extends BaseServlet {
    public String findAllBook(HttpServletRequest request,HttpServletResponse response){
        BookService bookService=new BookService();
        List<Book> bookList=bookService.findAll();
        request.setAttribute("list",bookList);
        return "/jsps/book/list.jsp";
    }
    public String findBookByCid(HttpServletRequest request,HttpServletResponse response){
        BookService bookService=new BookService();
        String cid=request.getParameter("cid");
        List<Book> bookList=bookService.findBookByCid(cid);
        request.setAttribute("list",bookList);
        return "jsps/book/list.jsp";
    }
    //一本书的详情页面
   public String findBook(HttpServletRequest request,HttpServletResponse response){
        String bid=request.getParameter("bid");
//        System.out.println("bid is"+bid);
        BookService bookService=new BookService();
        Book book=bookService.findBook(bid);
        request.setAttribute("book",book);
        return "/jsps/book/desc.jsp";
   }
   public String adminfindAllBook(HttpServletRequest request,HttpServletResponse response){
       BookService bookService=new BookService();
       List<Book> bookList=bookService.findAll();
       request.setAttribute("list",bookList);
       return "/adminjsps/admin/book/list.jsp";
   }
   public String findBookAdmin(HttpServletRequest request,HttpServletResponse response){
       String bid=request.getParameter("bid");
       BookService bookService=new BookService();
       Book book=bookService.findBook(bid);
       //查询所有分类
       CategoryService categoryService=new CategoryService();
       List<Category> list=categoryService.findCate();
       request.setAttribute("book",book);
       request.setAttribute("category",list);
       return "/adminjsps/admin/book/desc.jsp";
   }
   //修改图书
    public String mod(HttpServletRequest request,HttpServletResponse response){
        //使用BeanUtils进行封装
        try {
            Book book=new Book();
            BeanUtils.populate(book,request.getParameterMap());
            //book对象中的price是double类型，需要转换类型，然后手动将book中的价钱设置进去
            double price=Double.parseDouble(request.getParameter("price"));
            book.setPrice(price);
            //对book进行修gai
            BookService bookService=new BookService();
            bookService.mod(book);
            return "/book?method=adminfindAllBook";
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String disAddBook(HttpServletRequest request,HttpServletResponse response){
        //查询所有图书的分类
        CategoryService categoryService=new CategoryService();
        List<Category> list=categoryService.findCate();
        request.setAttribute("list",list);
        return "/adminjsps/admin/book/add.jsp";
    }
    //通过bid删除书信息
    public String del(HttpServletRequest request,HttpServletResponse response){
        //获取图书的bid
        String bid=request.getParameter("bid");
        System.out.println("bid is :"+bid);
        //调用方法实现删除
        BookService service = new BookService();
        service.delBook(bid);
        //到所有图书的页面
        return "/book?method=adminfindAllBook";
    }
}
