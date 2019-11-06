package ru.cft.focusstart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultiplicationTablePrinter {

    public static void main(String[] args) throws IOException {
        printGreeting(System.out);
        int size = readSize(new BufferedReader(new InputStreamReader(System.in)));

        int width = Integer.toString(size * size).length();
        String cellFormat = "%" + width + "d";
        String separator = String.join("", Collections.nCopies(width, "-"));

        for (int i = 0; i < size; i++) {
            StringBuilder numbersLine = new StringBuilder();
            StringBuilder separatorLine = new StringBuilder();
            for (int j = 0; j < size; j++) {
                numbersLine.append(String.format(cellFormat, i * j));
                separatorLine.append(separator);
                if (j < size - 1) {
                    numbersLine.append("|");
                    separatorLine.append("+");
                }
            }
            System.out.println(numbersLine);
            if (i < size - 1) {
                System.out.println(separatorLine);
            }
        }
    }

    public static int readSize(BufferedReader reader) throws IOException {
        String inputString = reader.readLine();
        int size = Integer.valueOf(inputString);
        if (size < 1 || size > 32) {
            throw new IllegalArgumentException("Size should be a number between 1 and 32");
        }
        return size;
    }

    public static Tuple readSizes(BufferedReader reader) throws IOException {
        String width = reader.readLine();
        String height = reader.readLine();
        return new Tuple(Integer.valueOf(width), Integer.valueOf(height));
    }

    public static void printGreeting(PrintStream printStream) {
        try {
            printStream.print("Input the table size: ");
        } catch (Exception ignore) {
        }
    }

    public static void printGreetingWithRandomUserName(PrintStream printStream) {
        List<String> userNames = Stream.of("Buzz", "Rex", "Bo", "Hamm", "Slink").collect(Collectors.toList());
        String userName = userNames.get(new Random().nextInt(userNames.size()));
        printStream.printf("Hello, %s! Input the table size: ", userName);
    }
}
