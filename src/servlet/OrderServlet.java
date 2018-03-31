package servlet;

import bean.*;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.commons.beanutils.BeanUtils;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "OrderServlet")
public class OrderServlet extends BaseServlet {
    //前台订单功能的完善
    public String findAllOrdertest(HttpServletRequest request,HttpServletResponse response){
         //先查询所有订单，然后将遍历出订单的cid，根据cid查询出订单项，然后将所有订单项封装进order中，然后进行所有的遍历
        //获取当前用户的id
        try {
			/*
			 * 1、得到当前用户的id 从session里面获取当前用户的id
			 * 2、调用方法查询，返回list集合
			 * */
            User user = (User) request.getSession().getAttribute("user");
            if(user == null) {
                request.setAttribute("msg", "请登录！");
                return "/jsps/msg.jsp";
            }
            //得到用户的id
            String uid = user.getUid();
            OrderService service = new OrderService();
            List<Order> list = service.findOrdersByUidTest(uid);
            request.setAttribute("order", list);
            return "/jsps/order/list1.jsp";
        }catch(Exception e) {
            return null;
        }
    }
    //支付成功之后执行这个方法
    public String callBack(HttpServletRequest request, HttpServletResponse response) {
        String r6_Order = request.getParameter("r6_Order");//订单的id
        String r3_Amt = request.getParameter("r3_Amt");
        String r9_BType = request.getParameter("r9_BType");
        //如果r9_BType是1，修改订单状态
        if("1".equals(r9_BType)) {
            OrderService service = new OrderService();
            service.updateOrderById(r6_Order);
            request.setAttribute("msg", "订单号："+r6_Order+",支付金额： "+r3_Amt );
            return "/jsps/msg.jsp";
        }
        return "/jsps/msg.jsp";
    }

    //在线支付
    public String payOrder(HttpServletRequest request, HttpServletResponse response) {
        //得到订单id和银行编码
        String oid = request.getParameter("oid");
        String pd_FrpId = request.getParameter("pd_FrpId");

//		String p0_Cmd  = "Buy";
//		StringBuffer sb = new StringBuffer();
//		sb.append("https://www.yeepay.com/app-merchant-proxy/node?");
//		sb.append("p0_Cmd").append("=").append(p0_Cmd).append("&");

        String url = PaymentUtil.getUrl(oid, pd_FrpId);
        //重定向到支付公司
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
   //对orders进行封装
//   private String oid;
//    private String ordertime;
//    private double total;
//    private int status;//订单状态
//    private User user;
//    private List<OrderItem> orderItems=new ArrayList<OrderItem>();
    public String addOrder(HttpServletRequest request,HttpServletResponse response){
        String oid = UUID.randomUUID().toString();
        Order order=new Order();
        order.setOid(oid);
        Date datetime=new Date();
        String ordertime=datetime.toLocaleString();
        order.setOrdertime(ordertime);
        Cart cart=(Cart) request.getSession().getAttribute("cart");
        order.setTotal(cart.getTotal());
        order.setState(1);
        order.setAddress(null);
        User user=(User) request.getSession().getAttribute("user");
        if(user==null){
            request.setAttribute("msg","请先登录");
            return "/jsps/msg.jsp";
        }else {
            order.setUser(user);
        }
        //设置List<OrderItem>
        for (CartItem cartItem:cart.getCartItems()){
             OrderItem orderItem=new OrderItem();
//            private String iid;
//            private int count;
//            private double subtotal;
//            private Order order;
//            private Book book;
            orderItem.setIid(UUID.randomUUID().toString());
            orderItem.setCount(cartItem.getCount());
            orderItem.setSubtotal(cartItem.getSubtotal());
            orderItem.setOrder(order);
            orderItem.setBook(cartItem.getBook());
            //得到订单里的list集合,并将其封装到order中的orderItem中
            order.getOrderItems().add(orderItem);
        }

        //将订单的信息传到数据库中
        OrderService orderService=new OrderService();
        orderService.save(order);
        request.setAttribute("order",order);
        return "/jsps/order/desc.jsp";
    }
    public String findAllOrder(HttpServletRequest request,HttpServletResponse response){
        //获取当前用户的id
       User user=(User) request.getSession().getAttribute("user");
       if (user==null){
           request.setAttribute("msg","请先登录");
           return "jsps/msg.jsp";
       }
       else {
        String uid=user.getUid();
        //通过当前的uid调用service方法进行查询
        OrderService orderService=new OrderService();
         List<Order> list=orderService.findOrdersByUid(uid);
         //转发
        request.setAttribute("order",list);
        return "/jsps/order/list.jsp";
       }
    }
    public String findOrderItemByOid(HttpServletRequest request,HttpServletResponse response){
        User user=(User) request.getSession().getAttribute("user");
        if (user==null){
            request.setAttribute("msg","请先登录");
            return "/jsps/msg.jsp";
        }else {
        String oid=request.getParameter("oid");
        OrderService orderService=new OrderService();
        //返回Lis<Map<String,Object>>list中存放的是一个个由字段名和值组成的map对象
        List<Map<String,Object>> orderitems = orderService.findOrderItemByOid(oid);
        request.setAttribute("orderitem",orderitems);
        return "/jsps/order/list.jsp";
        }
    }
    public String adminfindAllOrder(HttpServletRequest request,HttpServletResponse response){
        //查询所有订单
        OrderService orderService=new OrderService();
        List<Order> list=orderService.findAllOrder();
        //遍历list集合并调用方法查询对应的订单项，然后进行封装
        for (Order order:list){
            String oid=order.getOid();
            //调用方法进行订单项查询和封装
         List<Map<String,Object>> list1=orderService.findOrderItemByOid(oid);
        //进行遍历封装
           for (Map<String,Object> map:list1){
               OrderItem orderItem=new OrderItem();
               Book book=new Book();
               try {
                   BeanUtils.populate(orderItem,map);
                   BeanUtils.populate(book,map);
                   //将book封装进orderItem中
                   orderItem.setBook(book);
                   //将orderItem放入order中的orderItem集合中
                   order.getOrderItems().add(orderItem);

               } catch (IllegalAccessException e) {
                   e.printStackTrace();
               } catch (InvocationTargetException e) {
                   e.printStackTrace();
               }
           }
        }
        request.setAttribute("order",list);
        return "adminjsps/admin/order/list.jsp";
    }
    public String adminfindnotpay(HttpServletRequest request,HttpServletResponse response){
        //查询未付款的订单
        OrderService orderService=new OrderService();
        List<Order> orders=orderService.findnotpay();
        //将order集合遍历，然后进行封装
        for (Order order:orders){
            String oid=order.getOid();
            List<Map<String,Object>> map=orderService.findOrderItemByOid(oid);
            for (Map<String,Object> map1:map){
                OrderItem orderItem=new OrderItem();
                Book book=new Book();
                try {
                    BeanUtils.populate(orderItem,map1);
                    BeanUtils.populate(book,map1);
                    orderItem.setBook(book);
                    order.getOrderItems().add(orderItem);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        request.setAttribute("order",orders);
        return "adminjsps/admin/order/list.jsp";
    }
    public String adminfindpay(HttpServletRequest request,HttpServletResponse response){
        //获取已经付款的订单
        OrderService orderService=new OrderService();
       List<Order> orders = orderService.findpay();
       for (Order order:orders){
           String oid=order.getOid();
           List<Map<String,Object>> map=orderService.findOrderItemByOid(oid);
           for (Map<String,Object> map1:map){
               OrderItem orderItem=new OrderItem();
               Book book=new Book();
               try {
                   BeanUtils.populate(orderItem,map1);
                   BeanUtils.populate(book,map1);
                   orderItem.setBook(book);
                   order.getOrderItems().add(orderItem);
               } catch (IllegalAccessException e) {
                   e.printStackTrace();
               } catch (InvocationTargetException e) {
                   e.printStackTrace();
               }


           }
       }
        request.setAttribute("order",orders);
        return "adminjsps/admin/order/list.jsp";
    }
    public String adminfindnotaddress(HttpServletRequest request,HttpServletResponse response){
        OrderService orderService=new OrderService();
      List<Order> orders = orderService.findnotaddress();
      for (Order order:orders){
          String oid = order.getOid();
          List<Map<String,Object>> maps= orderService.findOrderItemByOid(oid);
          for (Map<String,Object> map:maps){
              OrderItem orderItem=new OrderItem();
              Book book=new Book();
              try {
                  BeanUtils.populate(orderItem,map);
                  BeanUtils.populate(book,map);
                  orderItem.setBook(book);
                  order.getOrderItems().add(orderItem);
              } catch (IllegalAccessException e) {
                  e.printStackTrace();
              } catch (InvocationTargetException e) {
                  e.printStackTrace();
              }
          }

      }
        request.setAttribute("order",orders);
        return "adminjsps/admin/order/list.jsp";
    }
    public String adminfindaddress(HttpServletRequest request,HttpServletResponse response){
        OrderService orderService=new OrderService();
       List<Order> orders = orderService.adminfindaddress();
       for (Order order:orders){
           String oid=order.getOid();
           List<Map<String,Object>> maps = orderService.findOrderItemByOid(oid);
           for (Map<String,Object> map:maps){
               OrderItem orderItem=new OrderItem();
               Book book=new Book();
               try {
                   BeanUtils.populate(orderItem,map);
                   BeanUtils.populate(book,map);
               } catch (IllegalAccessException e) {
                   e.printStackTrace();
               } catch (InvocationTargetException e) {
                   e.printStackTrace();
               }
               //把book封装进orderitem中
               orderItem.setBook(book);
               //把orderitem封装进order中
               order.getOrderItems().add(orderItem);
           }
       }
       request.setAttribute("order",orders);
        return "adminjsps/admin/order/list.jsp";
    }
}
