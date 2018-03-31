package servlet;

import bean.Book;
import bean.Cart;
import bean.CartItem;
import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CartServlet")
public class CartServlet extends BaseServlet {
    //查看session中是否存在购物车
     public Cart getCart(HttpServletRequest request) {
         Cart cart = (Cart) request.getSession().getAttribute("cart");
         if (cart == null) {
             cart = new Cart();
             request.getSession().setAttribute("cart", cart);
         }
       return cart;
     }
    //获取传来的id和数量
    public String addCart(HttpServletRequest request,HttpServletResponse response){
        //获取传来的id
        String bid=request.getParameter("bid");
        //获取传来的数量
        String count=request.getParameter("count");
        int con=0;
        if (count!=null){
            con=Integer.parseInt(count);
        }
        //将数量写进购物项中
        CartItem cartItem=new CartItem();
        cartItem.setCount(con);
        //根据id查询书记
        BookService bookService=new BookService();
        Book book=bookService.findBook(bid);
        if(book == null) {
            return "/jsps/book/desc.jsp";
        }else {
            //将图书封装进购物项中
            cartItem.setBook(book);
            //将购物项封装进购物车中
            Cart cart=getCart(request);
            cart.addCart(cartItem);
        }
        return "/jsps/cart/list.jsp";
    }
        //根据图书的id删除购物项
    public String removeCartItem(HttpServletRequest request,HttpServletResponse response){
        String bid=request.getParameter("bid");
        Cart cart=getCart(request);
        cart.removeCart(bid);
        return "/jsps/cart/list.jsp";
    }
    //实现清空购物车的功能
    public String clear(HttpServletRequest request,HttpServletResponse response){
        Cart cart=getCart(request);
        cart.clear();
        return "/jsps/cart/list.jsp";
    }
}
