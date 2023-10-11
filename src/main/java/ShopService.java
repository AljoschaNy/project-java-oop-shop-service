import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class ShopService {
    private ProductRepo productRepo = new ProductRepo();
    private OrderRepo orderRepo = new OrderMapRepo();

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
        Order newOrder = new Order(UUID.randomUUID().toString(), products,OrderStatus.PROCESSING);
        return orderRepo.addOrder(newOrder);
    }

    public List<Order> getAllOrdersByOrderStatus(OrderStatus orderStatus){
         return orderRepo.getOrders().stream()
                .filter(order -> order.orderStatus().equals(orderStatus))
                .collect(Collectors.toList());
    }
}
