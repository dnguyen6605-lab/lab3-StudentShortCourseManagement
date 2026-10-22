package utils;

import java.time.LocalDate;
import java.util.Scanner;

public class Inputter {
    public final Scanner scanner;
    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final String stuIdRegex = "[STUstu]{3}[0-9]{4}";
    public static final String courseIdRegex = "[A-Za-z]{3}[0-9]{3}";
    public static final String NAME_PATTERN = "^\\S+(\\s+\\S+)+$";

    public Inputter(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getAnInteger(String inpMsg, String errMsg) {
        System.out.print(inpMsg);
        while (true) {
            try {
                int number = Integer.parseInt(scanner.nextLine().trim());
                return number;
            } catch (Exception e) {
                System.out.print(errMsg);
            }
        }
    }

    public int getAnInteger(String inpMsg, String errMsg,
            int lowerBound, int upperBound) {
        System.out.print(inpMsg);
        while (true) {
            try {
                int number = Integer.parseInt(scanner.nextLine().trim());
                if (number < lowerBound || number > upperBound)
                    throw new Exception();
                return number;
            } catch (Exception e) {
                System.out.print(errMsg);
            }
        }
    }

    public double getADouble(String inpMsg, String errMsg) {
        System.out.print(inpMsg);
        while (true) {
            try {
                double number = Double.parseDouble(scanner.nextLine().trim());
                return number;
            } catch (Exception e) {
                System.out.print(errMsg);
            }
        }
    }

    public double getADouble(String inpMsg, String errMsg,
            double lowerBound, double upperBound) {
        System.out.print(inpMsg);
        while (true) {
            try {
                double number = Double.parseDouble(scanner.nextLine().trim());
                if (number < lowerBound || number > upperBound)
                    throw new Exception();
                return number;
            } catch (Exception e) {
                System.out.print(errMsg);
            }
        }
    }

    public String getAString(String inpMsg, String errMsg, String regex) {
        System.out.print(inpMsg);
        while (true) {
            try {
                String str = scanner.nextLine().trim();
                if (str.isEmpty() || !str.matches(regex))
                    throw new Exception();
                return str;
            } catch (Exception e) {
                System.out.print(errMsg);
            }
        }
    }

    public String getAString(String inpMsg, String errMsg) {
        System.out.print(inpMsg);
        while (true) {
            try {
                String str = scanner.nextLine().trim();
                if (str.isEmpty())
                    throw new Exception();
                return str;
            } catch (Exception e) {
                System.out.print(errMsg);
            }
        }
    }

    public boolean getYesNo(String inpMsg) {
        while (true) {
            System.out.print(inpMsg + " (Y/N): ");
            String value = scanner.nextLine().trim();
            if ("Y".equalsIgnoreCase(value) || "YES".equalsIgnoreCase(value)) {
                return true;
            }
            if ("N".equalsIgnoreCase(value) || "NO".equalsIgnoreCase(value)) {
                return false;
            }
            System.out.println("Enter Y or N.");
        }
    }

    public LocalDate getDate(String inpMsg, String errMsg) {
        System.out.print(inpMsg);
        java.time.format.DateTimeFormatter formatter =
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            try {
                String str = scanner.nextLine().trim();
                LocalDate date = LocalDate.parse(str, formatter);
                if (!date.isAfter(LocalDate.now()))
                    throw new Exception();
                return date;
            } catch (Exception e) {
                System.out.print(errMsg);
            }
        }
    }
}
