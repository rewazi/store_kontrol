package org.example.services;

import org.example.interfaces.AppHelper;
import org.example.interfaces.Service;
import org.example.interfaces.Input;
import org.example.model.Customer;

import java.util.List;

public class CustomerService implements Service<Customer> {
    private final AppHelper<Customer> appHelperCustomer;
    private final Input inputProvider;

    public CustomerService(AppHelper<Customer> appHelperCustomer, Input inputProvider) {
        this.appHelperCustomer = appHelperCustomer;
        this.inputProvider = inputProvider;
    }

    @Override
    public boolean add() {
        Customer customer = appHelperCustomer.create();
        if (customer != null) {
            appHelperCustomer.getRepository().save(customer);
            System.out.println("Покупатель успешно добавлен.");
            return true;
        }
        System.out.println("Ошибка при добавлении покупателя.");
        return false;
    }

    @Override
    public void print() {
        appHelperCustomer.printList(appHelperCustomer.getRepository().load());
    }

    @Override
    public List<Customer> list() {
        return List.of();
    }

    @Override
    public boolean edit(Customer customer) {
        var customers = appHelperCustomer.getRepository().load();
        if (customers.isEmpty()) {
            System.out.println("Список покупателей пуст. Нечего редактировать.");
            return false;
        }

        print();
        System.out.print("Введите номер покупателя для редактирования: ");
        int indexToEdit = Integer.parseInt(inputProvider.getInput()) - 1;

        if (indexToEdit >= 0 && indexToEdit < customers.size()) {
            Customer updatedCustomer = appHelperCustomer.create();
            customers.set(indexToEdit, updatedCustomer);
            appHelperCustomer.getRepository().save(customers);
            System.out.println("Покупатель успешно отредактирован.");
            return true;
        } else {
            System.out.println("Некорректный выбор покупателя.");
            return false;
        }
    }

    @Override
    public boolean remove(Customer customer) {
        var customers = appHelperCustomer.getRepository().load();
        if (customers.isEmpty()) {
            System.out.println("Список покупателей пуст. Нечего удалять.");
            return false;
        }

        print();
        System.out.print("Введите номер покупателя для удаления: ");
        int indexToRemove = Integer.parseInt(inputProvider.getInput()) - 1;

        if (indexToRemove >= 0 && indexToRemove < customers.size()) {
            customers.remove(indexToRemove);
            appHelperCustomer.getRepository().save(customers);
            System.out.println("Покупатель успешно удален.");
            return true;
        } else {
            System.out.println("Некорректный выбор покупателя.");
            return false;
        }
    }
}
