import java.util.List;

public class Main {
    public static void main(String[] args) {

        ProductRepo products = new ProductRepo();
        OrderRepo orders = new OrderMapRepo();

        products.addProduct(new Product("p1","iPhone"));
        products.addProduct(new Product("p2","Galaxy Phone"));
        products.addProduct(new Product("p3","iPad"));
        products.addProduct(new Product("p4","Mac Book"));
        products.addProduct(new Product("p5","Xiaomi Phone"));

        ShopService service = new ShopService(products,orders,0);

        service.addOrder(List.of(
                products.getProducts().get(0).id(),
                products.getProducts().get(1).id(),
                products.getProducts().get(2).id())
        );

        service.addOrder(List.of(
                products.getProducts().get(0).id(),
                products.getProducts().get(1).id(),
                products.getProducts().get(2).id())
        );
        service.addOrder(List.of(
                products.getProducts().get(0).id(),
                products.getProducts().get(1).id(),
                products.getProducts().get(2).id())
        );

        service.addOrder(List.of("p2"));

        System.out.println(service.getAllOrdersByOrderStatus(OrderStatus.PROCESSING).size());
        service.updateOrder("3",OrderStatus.IN_DELIVERY);

        System.out.println(service.getProductRepo());
        System.out.println(service.getOrderRepo().getOrders());
    }
}