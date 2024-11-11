import java.io.BufferedWriter;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.time.LocalDate;  
import java.time.format.DateTimeFormatter;  
import java.time.format.DateTimeParseException;  
import java.util.Scanner;  

public class UserDataApp {  
    
    public static void main(String[] args) {  
        Scanner scanner = new Scanner(System.in);  
        System.out.println("Введите данные (Фамилия Имя Отчество ДатаРождения НомерТелефона Пол):");  
        
        String input = scanner.nextLine();  
        try {  
            String[] parts = input.split(" ");  
            
            if (parts.length != 6) {  
                throw new IllegalArgumentException("Количество введенных элементов должно быть равно 6. Вы вводили: " + parts.length);  
            }  

            String surname = parts[0];  
            String firstName = parts[1];  
            String patronymic = parts[2];  
            LocalDate birthDate = parseDate(parts[3]);  
            long phoneNumber = parsePhoneNumber(parts[4]);  
            char gender = parseGender(parts[5]);  

            String dataToWrite = String.format("%s %s %s %s %d %c", surname, firstName, patronymic, birthDate, phoneNumber, gender);  
            writeToFile(surname, dataToWrite);  
            System.out.println("Данные успешно записаны в файл.");  

        } catch (IllegalArgumentException | DateTimeParseException e) {  
            System.out.println("Ошибка: " + e.getMessage());  
        } catch (IOException e) {  
            System.out.println("Ошибка записи в файл: " + e.getMessage());  
            e.printStackTrace();  // Вывод стека ошибок  
        } finally {  
            scanner.close();  
        }  
    }  

    private static LocalDate parseDate(String dateString) {  
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");  
        return LocalDate.parse(dateString, formatter);  
    }  

    private static long parsePhoneNumber(String phoneString) {  
        try {  
            return Long.parseLong(phoneString);  
        } catch (NumberFormatException e) {  
            throw new IllegalArgumentException("Номер телефона должен быть целым числом: " + phoneString);  
        }  
    }  
    
    private static char parseGender(String genderString) {  
        if (genderString.length() != 1 || !(genderString.equalsIgnoreCase("f") || genderString.equalsIgnoreCase("m"))) {  
            throw new IllegalArgumentException("Пол должен быть 'f' или 'm': " + genderString);  
        }  
        return genderString.charAt(0);  
    }  

    private static void writeToFile(String fileName, String data) throws IOException {  
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {  
            writer.write(data);  
            writer.newLine();  
        }  
    }  
}