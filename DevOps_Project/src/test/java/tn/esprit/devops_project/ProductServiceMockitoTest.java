package tn.esprit.devops_project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class ProductServiceMockitoTest {
    @Mock
    private StockRepository stockRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        // Initialiser les mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() {

        Stock stock = Stock.builder()
                .idStock(1L)
                .title("test")
                .build();

        when(stockRepository.findById(1L)).thenReturn(Optional.ofNullable(stock));

        Product productEln = Product.builder()
                .idProduct(1L)
                .title("Laptop")
                .price(1500.99f)
                .quantity(10)
                .warrantyPeriod(24)  // Pour la catégorie ELECTRONICS
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(productEln);

        Product savedProduct = productService.addProduct(productEln,1L);

        Assertions.assertNotNull(savedProduct.getIdProduct());
        Assertions.assertEquals(ProductCategory.ELECTRONICS, savedProduct.getCategory());
        Assertions.assertEquals(1L, savedProduct.getStock().getIdStock());
        Assertions.assertEquals(1500.99f, savedProduct.getPrice());
        Assertions.assertEquals(10, savedProduct.getQuantity());

        verify(stockRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }
}