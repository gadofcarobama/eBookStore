package dao;

import bean.Order;
import bean.OrderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import utils.MyJDBCUtils;

import javax.management.Query;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void save(Order order) {
        DataSource ds= MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        Object[] os={order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getUser().getUid(),order.getAddress()};
        try {
            queryRunner.update("insert into orders values(?,?,?,?,?,?)",os);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveOrderItem(OrderItem orderItem) {
    //添加orderItem
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        Object[] os={orderItem.getIid(),orderItem.getCount(),orderItem.getSubtotal(),orderItem.getOrder().getOid(),orderItem.getBook().getBid()};
        try {
            queryRunner.update("insert into orderitem values(?,?,?,?,?)",os);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> findOrdersByUid(String uid) {
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
            List<Order> orders=queryRunner.query("select * from orders where uid=?",
                    new BeanListHandler<Order>(Order.class),uid);
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> findOrderItemByOid(String oid) {
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        //根据oid查询到订单项表的数据，然后在进行多表查询，根据已经查询到的订单项查询book表中和其订单列表相同的bid，
        // 然后将结果封装到map中，最后封装到lisy中
        try {
            List<Map<String,Object>> orderitems =
                    queryRunner.query("select * from orderitem o , book b where o.bid=b.bid and oid=?",
                    new MapListHandler(),oid);
            return orderitems;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateOrderById(String r6_Order) {
        try {
            QueryRunner runner = new QueryRunner(MyJDBCUtils.getDataSource());
            runner.update("update orders set state=? where oid=?", 2,r6_Order);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> findAllOrder() {
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
            List<Order> list=queryRunner.query("select * from orders",new BeanListHandler<Order>(Order.class));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> findnotpay() {
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
           List<Order> list= queryRunner.query("select * from orders where state=?",new BeanListHandler<Order>(Order.class),1);
           return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> findpay() {
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
            List<Order> list= queryRunner.query("select * from orders where state=?",new BeanListHandler<Order>(Order.class),2);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> findnotaddress() {
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
            List<Order> list =
                    queryRunner.query
                            ("select * from orders where state=?", new BeanListHandler<Order>(Order.class),3);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> findaddress() {
        DataSource ds=MyJDBCUtils.getDataSource();
        QueryRunner queryRunner=new QueryRunner(ds);
        try {
            List<Order> orders = queryRunner.query("select * from orders where state=?",new BeanListHandler<Order>(Order.class),4);
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
