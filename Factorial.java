import java.io.IOException;
import java.math.BigInteger;

public class Factorial {

    /**
     * Вычисляет факториал числа последовательным умножением.
     * Самый неэффективный метод в рамках доступной библиотеки java.math
     * Использование сторонних библиотек могло быть более эффективным
     */
    public static BigInteger calculateFactorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public static void main(String[] args) {
        while (true) {
            // Ограничим пользовательский ввод хранимым типом данных для хапрашиваемого числа
            System.out.println("Введите положительное целое число (макс. 2147483647):");

            try {
                long accumulatedNumber = 0; // Используем long для отслеживания переполнения int
                boolean hasDigits = false;
                boolean isNegative = false;
                boolean isOverflow = false;
                boolean isInvalidChar = false;

                // Читаем поток посимвольно напрямую через System.in.read()
                while (true) {
                    int code = System.in.read();

                    // Символы перевода строки (\n, \r) или конец потока (-1) означают ввод завершен
                    if (code == '\n' || code == '\r' || code == -1) {
                        break;
                    }

                    char ch = (char) code;

                    // Проверяем знак минуса, флаг hasDigits чтобы косвенно проверять только 1й символ
                    if (!hasDigits && ch == '-') {
                        isNegative = true;
                        break;
                    }

                    // Если это цифра, добавляем её к числу
                    if (ch >= '0' && ch <= '9') {
                        accumulatedNumber = accumulatedNumber * 10 + (ch - '0');
                        hasDigits = true;

                        // Защита от переполнения: если число превысило лимит int
                        if (accumulatedNumber > Integer.MAX_VALUE) {
                            isOverflow = true;
                            break;
                        }
                    } else {
                        isInvalidChar = true;
                        break;
                    }
                }

                /**
                 * Логика обработки флагов ошибок
                 * Можно было бы использовать throw new Exception()
                  */

                if (isInvalidChar) {
                    System.out.println("Ошибка: обнаружены недопустимые символы. Введите только цифры.\n");
                    continue;
                }

                if (!hasDigits) {
                    System.out.println("Ошибка: вы ничего не ввели.\n");
                    continue;
                }

                if (isNegative) {
                    System.out.println("Ошибка: число должно быть неотрицательным.\n");
                    continue;
                }

                if (isOverflow) {
                    System.out.println("Ошибка: введеное число слишком большое и выходит за рамки типа int.\n");
                    continue;
                }

                int number = (int) accumulatedNumber;

                // Вычисление и вывод
                BigInteger result = calculateFactorial(number);
                System.out.println("Факториал числа " + number + " равен: " + result);
                break;

            } catch (IOException e) {
                System.out.println("Ошибка ввода-вывода: " + e.getMessage());
                break;
            }
        }
    }
}
