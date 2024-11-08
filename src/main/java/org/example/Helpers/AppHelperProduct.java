package org.example.Helpers;

import org.example.interfaces.AppHelper;
import org.example.interfaces.Input;
import org.example.interfaces.FileRepository;
import org.example.model.Product;

import java.util.List;

public class AppHelperProduct implements AppHelper<Product> {
    private final FileRepository<Product> productRepository;
    private final Input inputProvider;

    public AppHelperProduct(FileRepository<Product> productRepository, Input inputProvider) {
        this.productRepository = productRepository;
        this.inputProvider = inputProvider;
    }

    @Override
    public Product create() {
        Product product = new Product();
        System.out.print("Введите название товара: ");
        product.setName(inputProvider.getInput());
        System.out.print("Введите описание товара: ");
        product.setDescription(inputProvider.getInput());

        double price;
        while (true) {
            try {
                System.out.print("Введите цену товара: ");
                price = Double.parseDouble(inputProvider.getInput());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное числовое значение для цены.");
            }
        }
        product.setPrice(price);

        return product;
    }

    @Override
    public void printList(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("Список товаров пуст.");
            return;
        }
        System.out.println("------ Список товаров ------");
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            System.out.printf("%d. %s%n", i + 1, product);
        }
    }

    @Override
    public FileRepository<Product> getRepository() {
        return productRepository;
    }
}
