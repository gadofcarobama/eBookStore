package bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 购物车实体
* */
public class Cart {
 private double total;
 private List< CartItem> CartItems;
 private Map<String,CartItem> map=new HashMap<String,CartItem>();
 public double getTotal(){
     return total;
 }
    //直接向页面返回一个集合，方便在页面中遍历
    public Collection<CartItem> getCartItems() {
        //返回map里面的values的值
        return map.values();
    }
 //判断当前购物车是否存在该商品，存在则在基础上加一，并且修改小计
    public void addCart(CartItem cartItems){
       //判断当前是否存在该id
        String bid=cartItems.getBook().getBid();
        if(map.containsKey(bid)){
            //存在该商品
            CartItem _item=map.get(bid);
            _item.setCount(_item.getCount()+cartItems.getCount());
        }else{
            //没有该商品，直接添加进map集合
            map.put(bid,cartItems);
        }
        //添加总计
        total+=cartItems.getSubtotal();
    }
     //删除购物项
    public void removeCart(String bid){
        CartItem cartItem=map.remove(bid);
        //总价重新计算
        total-=cartItem.getSubtotal();
    }
    //清空购物车
    public void clear(){
        map.clear();
        total=0;
    }
}
