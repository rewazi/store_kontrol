package org.example.services;

import org.example.interfaces.AppHelper;
import org.example.interfaces.Service;
import org.example.interfaces.Input;
import org.example.model.Product;

import java.util.List;

public class ProductService implements Service<Product> {
    private final AppHelper<Product> appHelperProduct;
    private final Input inputProvider;

    public ProductService(AppHelper<Product> appHelperProduct, Input inputProvider) {
        this.appHelperProduct = appHelperProduct;
        this.inputProvider = inputProvider;
    }

    @Override
    public boolean add() {
        Product product = appHelperProduct.create();
        if (product != null) {
            appHelperProduct.getRepository().save(product);
            System.out.println("Товар успешно добавлен.");
            return true;
        }
        System.out.println("Ошибка при добавлении товара.");
        return false;
    }

    @Override
    public void print() {
        appHelperProduct.printList(appHelperProduct.getRepository().load());
    }

    @Override
    public List<Product> list() {
        return List.of();
    }

    @Override
    public boolean edit(Product product) {
        var products = appHelperProduct.getRepository().load();
        if (products.isEmpty()) {
            System.out.println("Список товаров пуст. Нечего редактировать.");
            return false;
        }

        print();
        System.out.print("Введите номер товара для редактирования: ");
        int indexToEdit = Integer.parseInt(inputProvider.getInput()) - 1;

        if (indexToEdit >= 0 && indexToEdit < products.size()) {
            Product updatedProduct = appHelperProduct.create();
            products.set(indexToEdit, updatedProduct);
            appHelperProduct.getRepository().save(products);
            System.out.println("Товар успешно отредактирован.");
            return true;
        } else {
            System.out.println("Некорректный выбор товара.");
            return false;
        }
    }

    @Override
    public boolean remove(Product product) {
        var products = appHelperProduct.getRepository().load();
        if (products.isEmpty()) {
            System.out.println("Список товаров пуст. Нечего удалять.");
            return false;
        }

        print();
        System.out.print("Введите номер товара для удаления: ");
        int indexToRemove = Integer.parseInt(inputProvider.getInput()) - 1;

        if (indexToRemove >= 0 && indexToRemove < products.size()) {
            products.remove(indexToRemove);
            appHelperProduct.getRepository().save(products);
            System.out.println("Товар успешно удален.");
            return true;
        } else {
            System.out.println("Некорректный выбор товара.");
            return false;
        }
    }
}
