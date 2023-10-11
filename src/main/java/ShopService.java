import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ShopService {
    private ProductRepo productRepo;
    private OrderRepo orderRepo;
    private int totalOrders;

    public Order addOrder(List<String> productIds) {
        List<Product> products = new ArrayList<>();
        for (String productId : productIds) {
            Optional<Product> productToOrder = productRepo.getProductById(productId);
            try{
                products.add(productToOrder.get());
            } catch (RuntimeException e) {
                throw new RuntimeException("Product mit der Id: " + productId + " konnte nicht bestellt werden!");
            }
        }
        totalOrders++;
        Order newOrder = new Order(String.valueOf(totalOrders), products,OrderStatus.PROCESSING, LocalDateTime.now());
        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getAllOrdersByOrderStatus(OrderStatus orderStatus){
         return orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus().equals(orderStatus))
                .collect(Collectors.toList());
    }

    public void updateOrder(String orderId, OrderStatus orderStatus) {
        if(orderRepo.getOrderById(orderId).orderStatus() == orderStatus) {
            return;
        } else {
            Order newOrder = orderRepo.getOrderById(orderId).withOrderStatus(orderStatus);
            orderRepo.removeOrder(orderId);
            orderRepo.addOrder(newOrder);
        }

    }
}