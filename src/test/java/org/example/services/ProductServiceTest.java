package org.example.services;

import org.example.interfaces.AppHelper;
import org.example.interfaces.FileRepository;
import org.example.interfaces.Input;
import org.example.model.Product;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceTest {
    private ProductService productService;
    private AppHelper<Product> appHelperProductMock;
    private Input inputProviderMock;
    private FileRepository<Product> repositoryMock;

    @Before
    public void setUp() {
        appHelperProductMock = mock(AppHelper.class);
        inputProviderMock = mock(Input.class);
        repositoryMock = mock(FileRepository.class);

        when(appHelperProductMock.getRepository()).thenReturn(repositoryMock);

        productService = new ProductService(appHelperProductMock, inputProviderMock);
    }

    // Тесты для метода add()

    @Test
    public void testAddSuccess() {
        // Arrange
        Product product = new Product();
        when(appHelperProductMock.create()).thenReturn(product);

        // Act
        boolean result = productService.add();

        // Assert
        assertTrue(result);
        verify(repositoryMock).save(product);
    }

    @Test
    public void testAddFailure() {
        // Arrange
        when(appHelperProductMock.create()).thenReturn(null);

        // Act
        boolean result = productService.add();

        // Assert
        assertFalse(result);
        verify(repositoryMock, never()).save(any(Product.class));
    }

    // Тест для метода print()

    @Test
    public void testPrint() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        when(repositoryMock.load()).thenReturn(products);

        // Act
        productService.print();

        // Assert
        verify(appHelperProductMock).printList(products);
    }

    // Тест для метода list()

    @Test
    public void testList() {
        // Act
        List<Product> products = productService.list();

        // Assert
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    // Тесты для метода edit()

    @Test
    public void testEditSuccess() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        when(repositoryMock.load()).thenReturn(products);
        when(inputProviderMock.getInput()).thenReturn("1");
        Product updatedProduct = new Product();
        when(appHelperProductMock.create()).thenReturn(updatedProduct);

        // Act
        boolean result = productService.edit(null);

        // Assert
        assertTrue(result);
        assertEquals(updatedProduct, products.get(0));
        verify(repositoryMock).save(products);
    }

    @Test
    public void testEditEmptyList() {
        // Arrange
        when(repositoryMock.load()).thenReturn(new ArrayList<>());

        // Act
        boolean result = productService.edit(null);

        // Assert
        assertFalse(result);
        verify(repositoryMock, never()).save(anyList());
    }

    @Test
    public void testEditInvalidIndex() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        when(repositoryMock.load()).thenReturn(products);
        when(inputProviderMock.getInput()).thenReturn("5");

        // Act
        boolean result = productService.edit(null);

        // Assert
        assertFalse(result);
        verify(repositoryMock, never()).save(anyList());
    }

    // Тесты для метода remove()

    @Test
    public void testRemoveSuccess() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(repositoryMock.load()).thenReturn(products);
        when(inputProviderMock.getInput()).thenReturn("1");

        // Act
        boolean result = productService.remove(null);

        // Assert
        assertTrue(result);
        assertEquals(1, products.size());
        verify(repositoryMock).save(products);
    }

    @Test
    public void testRemoveEmptyList() {
        // Arrange
        when(repositoryMock.load()).thenReturn(new ArrayList<>());

        // Act
        boolean result = productService.remove(null);

        // Assert
        assertFalse(result);
        verify(repositoryMock, never()).save(anyList());
    }

    @Test
    public void testRemoveInvalidIndex() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        when(repositoryMock.load()).thenReturn(products);
        when(inputProviderMock.getInput()).thenReturn("3");

        // Act
        boolean result = productService.remove(null);

        // Assert
        assertFalse(result);
        verify(repositoryMock, never()).save(anyList());
    }
}
