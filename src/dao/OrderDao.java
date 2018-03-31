package dao;

import bean.Order;
import bean.OrderItem;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    public void save(Order order);
    public void saveOrderItem(OrderItem orderItem);

    List<Order> findOrdersByUid(String uid);

    List<Map<String,Object>> findOrderItemByOid(String oid);
    //根据id进行修改
    void updateOrderById(String r6_Order);

    List<Order> findAllOrder();

    List<Order> findnotpay();

    List<Order> findpay();

    List<Order> findnotaddress();

    List<Order> findaddress();
}
