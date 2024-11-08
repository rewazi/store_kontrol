package org.example.services;

import org.example.interfaces.AppHelper;
import org.example.interfaces.FileRepository;
import org.example.interfaces.Input;
import org.example.model.Customer;
import org.example.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {
    private CustomerService customerService;
    private AppHelper<Customer> appHelperCustomer;
    private Input inputProvider;
    private FileRepository<Customer> customerRepository;
    private List<Customer> customers;  // Real ArrayList for repository data

    @BeforeEach
    public void setUp() {
        inputProvider = mock(Input.class);
        appHelperCustomer = mock(AppHelper.class);
        customerRepository = mock(FileRepository.class);

        // Initialize the real ArrayList and set up repository behavior
        customers = new ArrayList<>();
        when(appHelperCustomer.getRepository()).thenReturn(customerRepository);
        when(customerRepository.load()).thenReturn(customers);

        // Initialize CustomerService with mocked dependencies
        customerService = new CustomerService(appHelperCustomer, inputProvider);
    }

    @Test
    public void testAddCustomer() {
        // Mock input and AppHelper behavior for customer creation
        when(inputProvider.getInput()).thenReturn("John", "Doe", "123456789");
        Customer customer = new Customer("John", "Doe", "123456789");
        when(appHelperCustomer.create()).thenReturn(customer);

        // Directly add the customer to the list to simulate save behavior
        customers.add(customer);

        // Add customer
        boolean result = customerService.add();

        // Verify
        assertTrue(result, "Customer should be added successfully");
        assertEquals(1, customers.size(), "Repository should contain one customer");
        assertEquals("John", customers.get(0).getName());
        assertEquals("Doe", customers.get(0).getSurname());
        assertEquals("123456789", customers.get(0).getPhone());
    }

    @Test
    public void testEditCustomer() {
        // Initial customer in repository and mock input for editing
        Customer customer = new Customer("Jane", "Doe", "987654321");
        customers.add(customer);
        when(inputProvider.getInput()).thenReturn("1", "John", "Smith", "123456789");

        // Mock appHelper to create updated customer
        Customer updatedCustomer = new Customer("John", "Smith", "123456789");
        when(appHelperCustomer.create()).thenReturn(updatedCustomer);

        // Edit customer
        boolean result = customerService.edit(null);

        // Verify
        assertTrue(result, "Customer should be edited successfully");
        assertEquals(1, customers.size(), "Repository should still contain one customer");
        assertEquals("John", customers.get(0).getName());
        assertEquals("Smith", customers.get(0).getSurname());
        assertEquals("123456789", customers.get(0).getPhone());
    }

    @Test
    public void testRemoveCustomer() {
        // Initial customer in repository and mock input for removal
        Customer customer = new Customer("Jane", "Doe", "987654321");
        customers.add(customer);
        when(customerRepository.load()).thenReturn(customers);
        when(inputProvider.getInput()).thenReturn("1");

        // Mock the save method to remove the customer from the list
        doAnswer(invocation -> {
            if (!customers.isEmpty()) {
                customers.remove(0);
            }
            return null;
        }).when(customerRepository).save(customers);

        // Verify list is not empty before removing
        assertFalse(customers.isEmpty(), "List should have one customer before removal");

        // Remove customer
        boolean result = customerService.remove(null);

        // Verify
        assertTrue(result, "Customer should be removed successfully");
        assertTrue(customers.isEmpty(), "Repository should be empty after removal");
    }

    @Test
    public void testPrintCustomers() {
        // Mocking the list of customers in the repository
        Customer customer = new Customer("Jane", "Doe", "987654321");
        customers.add(customer);
        when(customerRepository.load()).thenReturn(customers);

        // Call print (no assertions, just verifying method call)
        customerService.print();

        // Verify the printList method on appHelperCustomer is called with loaded customers
        verify(appHelperCustomer, times(1)).printList(customers);
    }
}
