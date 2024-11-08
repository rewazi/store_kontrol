package org.example.Helpers;

import org.example.interfaces.AppHelper;
import org.example.interfaces.Input;
import org.example.interfaces.FileRepository;
import org.example.model.Customer;

import java.util.List;

public class AppHelperCustomer implements AppHelper<Customer> {
    private final FileRepository<Customer> customerRepository;
    private final Input inputProvider;

    public AppHelperCustomer(FileRepository<Customer> customerRepository, Input inputProvider) {
        this.customerRepository = customerRepository;
        this.inputProvider = inputProvider;
    }

    @Override
    public Customer create() {
        Customer customer = new Customer();
        System.out.print("Введите имя покупателя: ");
        customer.setName(inputProvider.getInput());
        System.out.print("Введите фамилию покупателя: ");
        customer.setSurname(inputProvider.getInput());
        System.out.print("Введите номер телефона покупателя: ");
        customer.setPhone(inputProvider.getInput());

        return customer;
    }

    @Override
    public void printList(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("Список покупателей пуст.");
            return;
        }
        System.out.println("------ Список покупателей ------");
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            System.out.printf("%d. Имя: %s, Фамилия: %s, Телефон: %s%n",
                    i + 1, customer.getName(), customer.getSurname(), customer.getPhone());
        }
    }

    @Override
    public FileRepository<Customer> getRepository() {
        return customerRepository;
    }
}
