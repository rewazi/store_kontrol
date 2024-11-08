package org.example;

import org.example.Helpers.AppHelperProduct;
import org.example.interfaces.FileRepository;
import org.example.interfaces.Input;
import org.example.model.Product;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class AppHelperProductTest {

    private AppHelperProduct appHelperProduct;
    private FileRepository<Product> productRepositoryMock;
    private Input inputProviderMock;

    @Before
    public void setUp() {
        productRepositoryMock = mock(FileRepository.class);
        inputProviderMock = mock(Input.class);

        appHelperProduct = new AppHelperProduct(productRepositoryMock, inputProviderMock);
    }

    @Test
    public void testCreateSuccess() {
        // Arrange
        when(inputProviderMock.getInput()).thenReturn("Product Name", "Product Description", "99.99");

        // Act
        Product product = appHelperProduct.create();

        // Assert
        assertNotNull(product);
        assertEquals("Product Name", product.getName());
        assertEquals("Product Description", product.getDescription());
        assertEquals(99.99, product.getPrice(), 0.001);
    }

    @Test
    public void testCreateInvalidPriceInput() {
        // Arrange
        when(inputProviderMock.getInput()).thenReturn(
                "Product Name",
                "Product Description",
                "invalid price", // First invalid input
                "still invalid", // Second invalid input
                "50.0"           // Correct input
        );

        // Act
        Product product = appHelperProduct.create();

        // Assert
        assertNotNull(product);
        assertEquals("Product Name", product.getName());
        assertEquals("Product Description", product.getDescription());
        assertEquals(50.0, product.getPrice(), 0.001);
    }

    @Test
    public void testPrintListWithProducts() {
        // Arrange
        List<Product> products = new ArrayList<>();
        Product product1 = new Product("Product1", "Description1", 10.0);
        Product product2 = new Product("Product2", "Description2", 20.0);
        products.add(product1);
        products.add(product2);

        // Act
        appHelperProduct.printList(products);

        // Assert
        // You can add assertions if you capture the console output
    }

    @Test
    public void testPrintListEmpty() {
        // Arrange
        List<Product> products = new ArrayList<>();

        // Act
        appHelperProduct.printList(products);

        // Assert
        // You can add assertions if you capture the console output
    }

    @Test
    public void testGetRepository() {
        // Act
        FileRepository<Product> repository = appHelperProduct.getRepository();

        // Assert
        assertEquals(productRepositoryMock, repository);
    }
}
