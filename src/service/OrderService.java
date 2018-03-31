package service;

import bean.Book;
import bean.Order;
import bean.OrderItem;
import dao.OrderDao;
import dao.OrderDaoImpl;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class OrderService {
    public void save(Order order){
     OrderDao orderDao=new OrderDaoImpl();
     orderDao.save(order);
        List<OrderItem> list=order.getOrderItems();
        for(OrderItem orderItem:list){
            orderDao.saveOrderItem(orderItem);
        }
    }

    public List<Order> findOrdersByUid(String uid) {
        OrderDao orderDao=new OrderDaoImpl();
        List<Order> orders=orderDao.findOrdersByUid(uid);
        return orders;
    }

    public List<Map<String,Object>> findOrderItemByOid(String oid) {
        OrderDao orderDao=new OrderDaoImpl();
        List<Map<String,Object>> orderitems=orderDao.findOrderItemByOid(oid);
        return orderitems;
    }
    //根据id修改订单额状态
    public void updateOrderById(String r6_Order) {
        OrderDao dao = new OrderDaoImpl();
        dao.updateOrderById(r6_Order);
    }

    public List<Order> findOrdersByUidTest(String uid) {
        //根据uid将所有的订单查询出来
        OrderDao orderDao=new OrderDaoImpl();
        List<Order> listOrder = orderDao.findOrdersByUid(uid);
        for (Order order:listOrder){
            String oid = order.getOid();
            OrderDao orderDao1=new OrderDaoImpl();
           List<Map<String,Object>> orderItems = orderDao1.findOrderItemByOid(oid);
           for (Map<String,Object> map:orderItems){
           //用BeanUtils对orderItems进行遍历封装
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
               //将book放入orderItem中，再将orderItem放入order中
               orderItem.setBook(book);
               order.getOrderItems().add(orderItem);

           }
        }
        return listOrder;
    }

    public List<Order> findAllOrder() {
        OrderDao orderDao=new OrderDaoImpl();
       List<Order> list= orderDao.findAllOrder();
       return list;
    }

    public List<Order> findnotpay() {
        OrderDao orderDao=new OrderDaoImpl();
        List<Order> orders=orderDao.findnotpay();
        return orders;
    }

    public List<Order> findpay() {
        OrderDao orderDao=new OrderDaoImpl();
        List<Order> orders=orderDao.findpay();
        return orders;
    }

    public List<Order> findnotaddress() {
        OrderDao orderDao=new OrderDaoImpl();
        List<Order> orders=orderDao.findnotaddress();
        return orders;
    }

    public List<Order> adminfindaddress() {
        OrderDao orderDao=new OrderDaoImpl();
        List<Order> orders=orderDao.findaddress();
        return orders;
    }
}
