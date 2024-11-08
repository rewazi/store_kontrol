package org.example;

import org.example.interfaces.Input;
import org.example.services.CustomerService;
import org.example.services.ProductService;
import org.example.services.PurchaseService;

public class App {
    private final CustomerService customerService;
    private final ProductService productService;
    private final PurchaseService purchaseService;
    private final Input inputProvider;

    public App(CustomerService customerService, ProductService productService, PurchaseService purchaseService, Input inputProvider) {
        this.customerService = customerService;
        this.productService = productService;
        this.purchaseService = purchaseService;
        this.inputProvider = inputProvider;
    }

    public void run() {
        System.out.println("------ Магазин электроники ------");
        boolean repeat = true;
        do {
            System.out.println("Меню:");
            System.out.println("0. Выйти из программы");
            System.out.println("1. Добавить товар");
            System.out.println("2. Список товаров");
            System.out.println("3. Редактировать товар");
            System.out.println("4. Удалить товар");
            System.out.println("5. Добавить покупателя");
            System.out.println("6. Список покупателей");
            System.out.println("7. Редактировать покупателя");
            System.out.println("8. Удалить покупателя");
            System.out.println("9. Купить товар");
            System.out.println("10. История покупок");
            System.out.print("Введите номер задачи: ");

            int task = Integer.parseInt(inputProvider.getInput());

            switch (task) {
                case 0:
                    repeat = false;
                    break;
                case 1:
                    productService.add();
                    break;
                case 2:
                    productService.print();
                    break;
                case 3:
                    productService.edit(null);
                    break;
                case 4:
                    productService.remove(null);
                    break;
                case 5:
                    customerService.add();
                    break;
                case 6:
                    customerService.print();
                    break;
                case 7:
                    customerService.edit(null);
                    break;
                case 8:
                    customerService.remove(null);
                    break;
                case 9:
                    purchaseService.add();
                    break;
                case 10:
                    purchaseService.print();
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        } while (repeat);

        System.out.println("До свидания!");
    }
}
