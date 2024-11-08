package org.example.Helpers;

import org.example.interfaces.AppHelper;
import org.example.interfaces.Input;
import org.example.interfaces.FileRepository;
import org.example.model.Purchase;
import org.example.model.Customer;
import org.example.model.Product;

import java.util.List;

public class AppHelperPurchase implements AppHelper<Purchase> {
    private final FileRepository<Purchase> purchaseRepository;
    private final Input inputProvider;
    private final AppHelper<Customer> customerHelper;
    private final AppHelper<Product> productHelper;

    public AppHelperPurchase(FileRepository<Purchase> purchaseRepository, Input inputProvider,
                             AppHelper<Customer> customerHelper, AppHelper<Product> productHelper) {
        this.purchaseRepository = purchaseRepository;
        this.inputProvider = inputProvider;
        this.customerHelper = customerHelper;
        this.productHelper = productHelper;
    }

    @Override
    public Purchase create() {
        System.out.println("------ Создание новой покупки ------");

        Customer customer = selectCustomer();
        if (customer == null) {
            System.out.println("Некорректный выбор покупателя. Операция отменена.");
            return null;
        }

        Product product = selectProduct();
        if (product == null) {
            System.out.println("Некорректный выбор товара. Операция отменена.");
            return null;
        }

        return new Purchase(customer, product);
    }

    private Customer selectCustomer() {
        List<Customer> customers = customerHelper.getRepository().load();
        customerHelper.printList(customers);
        System.out.print("Выберите номер покупателя: ");
        int customerIndex = Integer.parseInt(inputProvider.getInput()) - 1;

        if (customerIndex >= 0 && customerIndex < customers.size()) {
            return customers.get(customerIndex);
        }
        System.out.println("Некорректный выбор покупателя.");
        return null;
    }

    private Product selectProduct() {
        List<Product> products = productHelper.getRepository().load();
        productHelper.printList(products);
        System.out.print("Выберите номер товара: ");
        int productIndex = Integer.parseInt(inputProvider.getInput()) - 1;

        if (productIndex >= 0 && productIndex < products.size()) {
            return products.get(productIndex);
        }
        System.out.println("Некорректный выбор товара.");
        return null;
    }

    @Override
    public void printList(List<Purchase> purchases) {
        if (purchases.isEmpty()) {
            System.out.println("Список покупок пуст.");
            return;
        }
        System.out.println("------ Список покупок ------");
        for (int i = 0; i < purchases.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, purchases.get(i));
        }
    }

    @Override
    public FileRepository<Purchase> getRepository() {
        return purchaseRepository;
    }
}
