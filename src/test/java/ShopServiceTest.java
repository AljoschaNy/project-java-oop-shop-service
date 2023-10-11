import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShopServiceTest {

    @Test
    void addOrderTest() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        Order expected = new Order("-1", List.of(new Product("1", "Apfel")),OrderStatus.PROCESSING);
        assertEquals(expected.products(), actual.products());
        assertNotNull(expected.id());
    }

    @Test
    void addOrderTest_whenInvalidProductId_expectNull() {
        //GIVEN
        ShopService shopService = new ShopService();
        List<String> productsIds = List.of("1", "2");

        //WHEN
        Order actual = shopService.addOrder(productsIds);

        //THEN
        assertNull(actual);
    }

    @Test
    void updateOrder_ifStatusIsEqualToOrderStatus_orderStatusIsTheSame() {
        //GIVEN
        ShopService service = new ShopService();
        service.getOrderRepo().addOrder(new Order("1",List.of(new Product("p1","iPhone")),OrderStatus.PROCESSING));

        Order actual = new Order("1",List.of(new Product("p1","iPhone")),OrderStatus.PROCESSING);
        service.updateOrder("1", OrderStatus.PROCESSING);
        Order expect = new Order("1",List.of(new Product("p1","iPhone")),OrderStatus.PROCESSING);
        assertEquals(actual,expect);
    }

    @Test
    void updateOrder_ifStatusIsNotEqualToOrderStatus_changeOrderStatusToNewStatus() {
        //GIVEN
        ShopService service = new ShopService();
        service.getOrderRepo().addOrder(new Order("1",List.of(new Product("p1","iPhone")),OrderStatus.PROCESSING));

        Order actual = new Order("1",List.of(new Product("p1","iPhone")),OrderStatus.PROCESSING);
        service.updateOrder("1", OrderStatus.IN_DELIVERY);
        Order expect = new Order("1",List.of(new Product("p1","iPhone")),OrderStatus.IN_DELIVERY);
        assertEquals(actual,expect);
    }
}
