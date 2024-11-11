package org.example;

import org.example.Helpers.AppHelperCustomer;
import org.example.Helpers.AppHelperProduct;
import org.example.Helpers.AppHelperPurchase;
import org.example.interfaces.AppHelper;
import org.example.interfaces.FileRepository;
import org.example.interfaces.Input;
import org.example.model.Customer;
import org.example.model.Product;
import org.example.model.Purchase;
import org.example.repository.FileStorage;
import org.example.services.CustomerService;
import org.example.services.ProductService;
import org.example.services.PurchaseService;

public class NPTV23ElectronicsStore {
    public static void main(String[] args) {
        Input inputProvider = new AppHelper.ConsoleInput();

        // Используем FileStorage вместо InMemoryRepository
        FileRepository<Customer> customerRepository = new FileStorage<>("customers.dat");
        FileRepository<Product> productRepository = new FileStorage<>("products.dat");
        FileRepository<Purchase> purchaseRepository = new FileStorage<>("purchases.dat");

        // Настраиваем помощников приложения с репозиториями
        AppHelper<Customer> appHelperCustomer = new AppHelperCustomer(customerRepository, inputProvider);
        AppHelper<Product> appHelperProduct = new AppHelperProduct(productRepository, inputProvider);
        AppHelper<Purchase> appHelperPurchase = new AppHelperPurchase(purchaseRepository, inputProvider, appHelperCustomer, appHelperProduct);

        // Инициализируем сервисы с соответствующими помощниками и провайдером ввода
        CustomerService customerService = new CustomerService(appHelperCustomer, inputProvider);
        ProductService productService = new ProductService(appHelperProduct, inputProvider);
        PurchaseService purchaseService = new PurchaseService(appHelperPurchase, appHelperCustomer, appHelperProduct, inputProvider);

        // Запускаем приложение
        App app = new App(customerService, productService, purchaseService, inputProvider);
        app.run();
    }
}