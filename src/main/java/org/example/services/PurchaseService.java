package org.example.services;

import org.example.interfaces.AppHelper;
import org.example.interfaces.Service;
import org.example.interfaces.Input;
import org.example.model.Customer;
import org.example.model.Product;
import org.example.model.Purchase;

import java.util.List;

public class PurchaseService implements Service<Purchase> {
    private final AppHelper<Purchase> appHelperPurchase;
    private final AppHelper<Customer> appHelperCustomer;
    private final AppHelper<Product> appHelperProduct;
    private final Input inputProvider;

    public PurchaseService(AppHelper<Purchase> appHelperPurchase,
                           AppHelper<Customer> appHelperCustomer, AppHelper<Product> appHelperProduct,
                           Input inputProvider) {
        this.appHelperPurchase = appHelperPurchase;
        this.appHelperCustomer = appHelperCustomer;
        this.appHelperProduct = appHelperProduct;
        this.inputProvider = inputProvider;
    }

    @Override
    public boolean add() {
        Customer customer = selectCustomer();
        Product product = selectProduct();

        if (customer != null && product != null) {
            Purchase purchase = new Purchase(customer, product);
            appHelperPurchase.getRepository().save(purchase);
            System.out.println("Покупка успешно добавлена.");
            return true;
        }

        System.out.println("Ошибка при добавлении покупки.");
        return false;
    }

    @Override
    public void print() {
        var purchases = appHelperPurchase.getRepository().load();
        if (purchases.isEmpty()) {
            System.out.println("Список покупок пуст.");
        } else {
            System.out.println("------ Список покупок ------");
            for (int i = 0; i < purchases.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, purchases.get(i));
            }
        }
    }

    @Override
    public List<Purchase> list() {
        return List.of();
    }

    @Override
    public boolean edit(Purchase purchase) {
        System.out.println("Редактирование покупки недоступно.");
        return false;
    }

    @Override
    public boolean remove(Purchase purchase) {
        var purchases = appHelperPurchase.getRepository().load();
        if (purchases.isEmpty()) {
            System.out.println("Список покупок пуст.");
            return false;
        }

        print();
        System.out.print("Введите номер покупки для удаления: ");
        int indexToRemove = Integer.parseInt(inputProvider.getInput()) - 1;

        if (indexToRemove >= 0 && indexToRemove < purchases.size()) {
            purchases.remove(indexToRemove);
            appHelperPurchase.getRepository().save(purchases);
            System.out.println("Покупка успешно удалена.");
            return true;
        } else {
            System.out.println("Покупка не найдена.");
            return false;
        }
    }

    private Customer selectCustomer() {
        appHelperCustomer.printList(appHelperCustomer.getRepository().load());
        System.out.print("Введите номер покупателя для покупки: ");
        int customerIndex = Integer.parseInt(inputProvider.getInput()) - 1;

        if (customerIndex >= 0 && customerIndex < appHelperCustomer.getRepository().load().size()) {
            return appHelperCustomer.getRepository().load().get(customerIndex);
        }
        System.out.println("Некорректный выбор покупателя.");
        return null;
    }

    private Product selectProduct() {
        appHelperProduct.printList(appHelperProduct.getRepository().load());
        System.out.print("Введите номер товара для покупки: ");
        int productIndex = Integer.parseInt(inputProvider.getInput()) - 1;

        if (productIndex >= 0 && productIndex < appHelperProduct.getRepository().load().size()) {
            return appHelperProduct.getRepository().load().get(productIndex);
        }
        System.out.println("Некорректный выбор товара.");
        return null;
    }
}
