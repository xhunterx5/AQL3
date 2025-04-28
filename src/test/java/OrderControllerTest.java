import exercice2.Order;
import exercice2.OrderController;
import exercice2.OrderDao;
import exercice2.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class OrderControllerTest {

    @Test
    void testCreateOrder() {
        // Mock OrderDao
        OrderDao orderDao = mock(OrderDao.class);

        // Créer un vrai OrderService mais en injectant un mock OrderDao
        OrderService orderService = new OrderService(orderDao);

        // Mock OrderService
        OrderService orderServiceMock = mock(OrderService.class);

        // OrderController utilisant le mock du Service
        OrderController orderController = new OrderController(orderServiceMock);

        // Créer une commande fictive
        Order order = new Order(1L, "Laptop", 2);

        // Appeler createOrder sur le controller
        orderController.createOrder(order);

        // Vérifier que OrderService.createOrder a été appelé
        verify(orderServiceMock).createOrder(order);

        // Vérifier aussi que OrderDao.saveOrder est appelé via OrderService directement
        OrderService realServiceWithDaoMock = new OrderService(orderDao);
        realServiceWithDaoMock.createOrder(order);
        verify(orderDao).saveOrder(order);
    }
}
