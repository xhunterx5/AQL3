import exercice3.Product;
import exercice3.ProductApiClient;
import exercice3.ProductService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Test
    void testGetProductSuccess() throws Exception {
        // Mock de ProductApiClient
        ProductApiClient productApiClient = mock(ProductApiClient.class);

        Product expectedProduct = new Product("p1", "Smartphone", 699.99);
        when(productApiClient.getProduct("p1")).thenReturn(expectedProduct);

        ProductService productService = new ProductService(productApiClient);

        Product actualProduct = productService.getProduct("p1");

        verify(productApiClient).getProduct("p1");
        assertNotNull(actualProduct);
        assertEquals("Smartphone", actualProduct.getName());
        assertEquals(699.99, actualProduct.getPrice());
    }

    @Test
    void testGetProductDataFormatError() throws Exception {
        // Mock de ProductApiClient qui retourne un produit avec données erronées
        ProductApiClient productApiClient = mock(ProductApiClient.class);

        when(productApiClient.getProduct("p2")).thenReturn(new Product("p2", null, -1.0));

        ProductService productService = new ProductService(productApiClient);

        Product actualProduct = productService.getProduct("p2");

        verify(productApiClient).getProduct("p2");
        assertNotNull(actualProduct);
        assertNull(actualProduct.getName());
        assertTrue(actualProduct.getPrice() < 0);
    }

    @Test
    void testGetProductApiFailure() throws Exception {
        // Mock de ProductApiClient qui lève une exception
        ProductApiClient productApiClient = mock(ProductApiClient.class);

        when(productApiClient.getProduct("p3")).thenThrow(new RuntimeException("API unreachable"));

        ProductService productService = new ProductService(productApiClient);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.getProduct("p3");
        });

        verify(productApiClient).getProduct("p3");
        assertEquals("API unreachable", exception.getMessage());
    }
}
