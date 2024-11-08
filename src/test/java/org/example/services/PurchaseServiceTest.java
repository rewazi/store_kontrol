package org.example.services;

import org.example.interfaces.AppHelper;
import org.example.interfaces.FileRepository;
import org.example.interfaces.Input;
import org.example.model.Customer;
import org.example.model.Product;
import org.example.model.Purchase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class PurchaseServiceTest {
    private PurchaseService purchaseService;
    private AppHelper<Purchase> appHelperPurchaseMock;
    private AppHelper<Customer> appHelperCustomerMock;
    private AppHelper<Product> appHelperProductMock;
    private Input inputProviderMock;
    private FileRepository<Purchase> purchaseRepositoryMock;
    private FileRepository<Customer> customerRepositoryMock;
    private FileRepository<Product> productRepositoryMock;

    @Before
    public void setUp() {
        appHelperPurchaseMock = mock(AppHelper.class);
        appHelperCustomerMock = mock(AppHelper.class);
        appHelperProductMock = mock(AppHelper.class);
        inputProviderMock = mock(Input.class);

        purchaseRepositoryMock = mock(FileRepository.class);
        customerRepositoryMock = mock(FileRepository.class);
        productRepositoryMock = mock(FileRepository.class);

        when(appHelperPurchaseMock.getRepository()).thenReturn(purchaseRepositoryMock);
        when(appHelperCustomerMock.getRepository()).thenReturn(customerRepositoryMock);
        when(appHelperProductMock.getRepository()).thenReturn(productRepositoryMock);

        purchaseService = new PurchaseService(
                appHelperPurchaseMock,
                appHelperCustomerMock,
                appHelperProductMock,
                inputProviderMock
        );
    }

    // Test for the add() method
    @Test
    public void testAddSuccess() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customers.add(customer);

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        products.add(product);

        when(customerRepositoryMock.load()).thenReturn(customers);
        when(productRepositoryMock.load()).thenReturn(products);

        when(inputProviderMock.getInput()).thenReturn("1", "1"); // First input for customer, second for product

        // Act
        boolean result = purchaseService.add();

        // Assert
        assertTrue(result);
        verify(purchaseRepositoryMock).save(any(Purchase.class));
    }

    @Test
    public void testAddNoCustomers() {
        // Arrange
        when(customerRepositoryMock.load()).thenReturn(new ArrayList<>());
        when(inputProviderMock.getInput()).thenReturn("1"); // Mock input to prevent NumberFormatException

        // Act
        boolean result = purchaseService.add();

        // Assert
        assertFalse(result);
        verify(purchaseRepositoryMock, never()).save(any(Purchase.class));
    }

    @Test
    public void testAddNoProducts() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customers.add(customer);

        when(customerRepositoryMock.load()).thenReturn(customers);
        when(productRepositoryMock.load()).thenReturn(new ArrayList<>());

        when(inputProviderMock.getInput()).thenReturn("1");

        // Act
        boolean result = purchaseService.add();

        // Assert
        assertFalse(result);
        verify(purchaseRepositoryMock, never()).save(any(Purchase.class));
    }

    @Test
    public void testAddInvalidCustomerIndex() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());

        when(customerRepositoryMock.load()).thenReturn(customers);

        when(inputProviderMock.getInput()).thenReturn("5"); // Invalid index

        // Act
        boolean result = purchaseService.add();

        // Assert
        assertFalse(result);
        verify(purchaseRepositoryMock, never()).save(any(Purchase.class));
    }

    @Test
    public void testAddInvalidProductIndex() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customers.add(customer);

        List<Product> products = new ArrayList<>();
        products.add(new Product());

        when(customerRepositoryMock.load()).thenReturn(customers);
        when(productRepositoryMock.load()).thenReturn(products);

        when(inputProviderMock.getInput()).thenReturn("1", "5"); // Invalid product index

        // Act
        boolean result = purchaseService.add();

        // Assert
        assertFalse(result);
        verify(purchaseRepositoryMock, never()).save(any(Purchase.class));
    }

    // Test for the print() method
    @Test
    public void testPrintWithPurchases() {
        // Arrange
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(new Purchase(new Customer(), new Product()));

        when(purchaseRepositoryMock.load()).thenReturn(purchases);

        // Act
        purchaseService.print();

        // Assert
        // You can verify that appropriate methods were called if necessary
    }

    @Test
    public void testPrintEmptyPurchases() {
        // Arrange
        when(purchaseRepositoryMock.load()).thenReturn(new ArrayList<>());

        // Act
        purchaseService.print();

        // Assert
        // Verify that the correct message is displayed
    }

    // Test for the remove() method
    @Test
    public void testRemoveSuccess() {
        // Arrange
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(new Purchase(new Customer(), new Product()));
        when(purchaseRepositoryMock.load()).thenReturn(purchases);
        when(inputProviderMock.getInput()).thenReturn("1");

        // Act
        boolean result = purchaseService.remove(null);

        // Assert
        assertTrue(result);
        verify(purchaseRepositoryMock).save(purchases);
    }

    @Test
    public void testRemoveEmptyList() {
        // Arrange
        when(purchaseRepositoryMock.load()).thenReturn(new ArrayList<>());

        // Act
        boolean result = purchaseService.remove(null);

        // Assert
        assertFalse(result);
        verify(purchaseRepositoryMock, never()).save(anyList());
    }

    @Test
    public void testRemoveInvalidIndex() {
        // Arrange
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(new Purchase(new Customer(), new Product()));
        when(purchaseRepositoryMock.load()).thenReturn(purchases);
        when(inputProviderMock.getInput()).thenReturn("5"); // Invalid index

        // Act
        boolean result = purchaseService.remove(null);

        // Assert
        assertFalse(result);
        verify(purchaseRepositoryMock, never()).save(anyList());
    }

    // Test for the edit() method
    @Test
    public void testEditNotAvailable() {
        // Act
        boolean result = purchaseService.edit(null);

        // Assert
        assertFalse(result);
    }
}
