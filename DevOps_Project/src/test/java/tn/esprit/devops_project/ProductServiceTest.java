package tn.esprit.devops_project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;
import tn.esprit.devops_project.services.StockServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevOps_ProjectSpringBootApplication.class)
 class ProductServiceTest {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private StockServiceImpl stockService;
//test
    @Test
     void testAddProduct() {
        Stock stock = Stock.builder()
                .title("test")
                .build();
        stock = stockRepository.save(stock);

        Product product = Product.builder()
                .title("Laptop")
                .price(1500.99f)
                .quantity(10)
                .warrantyPeriod(24)
                .build();

        Product savedProduct = productService.addProduct(product, stock.getIdStock());

        Assertions.assertNotNull(savedProduct.getIdProduct());
        Assertions.assertEquals(ProductCategory.ELECTRONICS, savedProduct.getCategory());
        Assertions.assertEquals(stock.getIdStock(), savedProduct.getStock().getIdStock());
        Assertions.assertEquals(1500.99f, savedProduct.getPrice());
        Assertions.assertEquals(10, savedProduct.getQuantity());

        productService.deleteProduct(savedProduct.getIdProduct());
        stockService.deleteStock(stock.getIdStock());
    }
}
