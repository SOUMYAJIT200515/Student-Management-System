package util;

import model.Student;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class StudentFileHandler {

    // ✅ Validate file before reading
    private static boolean isFileValid(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("⚠ File does not exist: " + filePath);
            return false;
        }
        if (!file.canRead()) {
            System.out.println("⚠ Cannot read file: " + filePath);
            return false;
        }
        return true;
    }

    // ✅ Backup file before writing
    private static void backupFile(String filePath) {
        Path source = Paths.get(filePath);
        if (Files.exists(source)) {
            try {
                Path backup = Paths.get(filePath + ".bak");
                Files.copy(source, backup, StandardCopyOption.REPLACE_EXISTING);
                AppLogger.info("Backup created: " + backup);
            } catch (IOException e) {
                AppLogger.warning("Could not create backup: " + e.getMessage());
            }
        }
    }

    // ✅ Import students from file
    public static List<Student> importFromFile(String filePath) {
        List<Student> students = new ArrayList<>();
        FileReport report = new FileReport();

        if (!isFileValid(filePath)) return students;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // optional: skip header if CSV has one
            // br.readLine();

            while ((line = br.readLine()) != null) {
                try {
                    String[] data = line.split(",");
                    if (data.length != 4)
                        throw new StudentDataException("Invalid line format: " + line);

                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String dept = data[2].trim();
                    double cgpa = Double.parseDouble(data[3].trim());

                    if (cgpa < 0 || cgpa > 10)
                        throw new StudentDataException("Invalid CGPA for: " + line);

                    students.add(new Student(id, name, dept, cgpa));
                    report.addSuccess();

                } catch (StudentDataException | NumberFormatException e) {
                    AppLogger.warning(e.getMessage());
                    report.addFail();
                }
            }
        } catch (IOException e) {
            AppLogger.error("Error reading file", e);
        }

        report.printReport("Import");
        return students;
    }

    // ✅ Export students to file
    public static void exportToFile(String filePath, List<Student> students) {
        FileReport report = new FileReport();
        backupFile(filePath);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Student s : students) {
                bw.write(s.toString());
                bw.newLine();
                report.addSuccess();
            }
        } catch (IOException e) {
            AppLogger.error("Error writing to file", e);
            report.addFail();
        }

        report.printReport("Export");
    }
}
