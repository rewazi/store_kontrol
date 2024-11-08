package org.example.interfaces;

import java.util.List;
import java.util.Scanner;

public interface AppHelper<T> {
    T create();
    void printList(List<T> elements);
    FileRepository<T> getRepository();

    class ConsoleInput implements Input {
        private final Scanner scanner = new Scanner(System.in);

        @Override
        public String getInput() {
            return scanner.nextLine();  // Возвращает строку, введенную пользователем
        }
    }
}
