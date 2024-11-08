package org.example;

import org.example.Helpers.AppHelperPurchase;
import org.example.interfaces.AppHelper;
import org.example.interfaces.FileRepository;
import org.example.interfaces.Input;
import org.example.model.Customer;
import org.example.model.Product;
import org.example.model.Purchase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AppHelperPurchaseTest {
    private AppHelperPurchase appHelperPurchase;
    private FileRepository<Purchase> purchaseRepositoryMock;
    private Input inputProviderMock;
    private AppHelper<Customer> customerHelperMock;
    private AppHelper<Product> productHelperMock;

    private FileRepository<Customer> customerRepositoryMock;
    private FileRepository<Product> productRepositoryMock;

    @Before
    public void setUp() {
        purchaseRepositoryMock = mock(FileRepository.class);
        inputProviderMock = mock(Input.class);
        customerHelperMock = mock(AppHelper.class);
        productHelperMock = mock(AppHelper.class);

        customerRepositoryMock = mock(FileRepository.class);
        productRepositoryMock = mock(FileRepository.class);

        when(customerHelperMock.getRepository()).thenReturn(customerRepositoryMock);
        when(productHelperMock.getRepository()).thenReturn(productRepositoryMock);

        appHelperPurchase = new AppHelperPurchase(
                purchaseRepositoryMock,
                inputProviderMock,
                customerHelperMock,
                productHelperMock
        );
    }

    @Test
    public void testCreateSuccess() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customers.add(customer);

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        products.add(product);

        when(customerRepositoryMock.load()).thenReturn(customers);
        when(productRepositoryMock.load()).thenReturn(products);

        when(inputProviderMock.getInput()).thenReturn("1", "1"); // Ввод для выбора покупателя и товара

        // Act
        Purchase result = appHelperPurchase.create();

        // Assert
        assertNotNull(result);
        assertEquals(customer, result.getCustomer());
        assertEquals(product, result.getProduct());
    }

    @Test
    public void testCreateNoCustomers() {
        // Arrange
        when(customerRepositoryMock.load()).thenReturn(new ArrayList<>());
        when(inputProviderMock.getInput()).thenReturn("1");

        // Act
        Purchase result = appHelperPurchase.create();

        // Assert
        assertNull(result);
    }

    @Test
    public void testCreateNoProducts() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customers.add(customer);

        when(customerRepositoryMock.load()).thenReturn(customers);
        when(productRepositoryMock.load()).thenReturn(new ArrayList<>());

        when(inputProviderMock.getInput()).thenReturn("1");

        // Act
        Purchase result = appHelperPurchase.create();

        // Assert
        assertNull(result);
    }

    @Test
    public void testCreateInvalidCustomerIndex() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());

        when(customerRepositoryMock.load()).thenReturn(customers);

        when(inputProviderMock.getInput()).thenReturn("5"); // Некорректный индекс

        // Act
        Purchase result = appHelperPurchase.create();

        // Assert
        assertNull(result);
    }

    @Test
    public void testCreateInvalidProductIndex() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customers.add(customer);

        List<Product> products = new ArrayList<>();
        products.add(new Product());

        when(customerRepositoryMock.load()).thenReturn(customers);
        when(productRepositoryMock.load()).thenReturn(products);

        when(inputProviderMock.getInput()).thenReturn("1", "5"); // Некорректный индекс товара

        // Act
        Purchase result = appHelperPurchase.create();

        // Assert
        assertNull(result);
    }

    @Test
    public void testPrintListWithPurchases() {
        // Arrange
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(new Purchase(new Customer(), new Product()));

        // Act
        appHelperPurchase.printList(purchases);

        // Assert
        // Здесь можно проверить вывод, если перенаправите System.out
    }

    @Test
    public void testPrintListEmpty() {
        // Arrange
        List<Purchase> purchases = new ArrayList<>();

        // Act
        appHelperPurchase.printList(purchases);

        // Assert
        // Проверка корректного сообщения о пустом списке
    }

    @Test
    public void testGetRepository() {
        // Act
        FileRepository<Purchase> repository = appHelperPurchase.getRepository();

        // Assert
        assertEquals(purchaseRepositoryMock, repository);
    }
}
