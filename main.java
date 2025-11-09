import model.Student;
import util.StudentFileHandler;

import java.util.*;

public class main {
    private static final Scanner sc = new Scanner(System.in);
    private static final String INPUT_FILE = "students.csv";
    private static final String OUTPUT_FILE = "students_output.csv";
    private static List<Student> students = new ArrayList<>();
    private static boolean isSortedById = false; //  Track sorting state

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = getInt("Enter your choice: ");
            switch (choice) {
                case 1 -> loadFromFile();
                case 2 -> displayStudents();
                case 3 -> addStudent();
                case 4 -> searchStudent();
                case 5 -> exportToFile();
                case 6 -> sortById();
                case 7 -> sortByCgpa();
                case 8 -> categorizeByDept();
                case 0 -> System.out.println(" Exiting program...");
                default -> System.out.println(" Invalid choice, please try again.");
            }
        } while (choice != 0);
    }

    // ---------------- Menu ----------------
    private static void printMenu() {
        System.out.println("\n=====   Student Management System =====");
        System.out.println("1️:  Load students from file");
        System.out.println("2️:  Display all students");
        System.out.println("3️:  Add a new student");
        System.out.println("4️:  Search student");
        System.out.println("5️:  Export students to file");
        System.out.println("6️:  Sort by ID");
        System.out.println("7️:  Sort by CGPA");
        System.out.println("8️:  Categorize by Department");
        System.out.println("0️:  Exit");
        System.out.println("=======================================");
    }

    // ---------------- Functional Methods ----------------
    private static void loadFromFile() {
        students = StudentFileHandler.importFromFile(INPUT_FILE);
        isSortedById = false;
        System.out.println(" Loaded " + students.size() + " students from file.");
    }

    private static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println(" No student data available.");
            return;
        }

        printStudentTable(students, " STUDENT RECORDS");
    }

    private static void addStudent() {
        System.out.println("\n➕ Add New Student");

        int id = getInt("Enter ID: ");
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter Department: ");
        String dept = sc.nextLine().trim();
        double cgpa = getDouble("Enter CGPA (0.0 - 10.0): ");

        if (cgpa < 0 || cgpa > 10) {
            System.out.println("⚠ Invalid CGPA. Must be between 0 and 10.");
            return;
        }

        students.add(new Student(id, name, dept, cgpa));
        isSortedById = false;
        System.out.println(" Student added successfully!");
    }

    // ---------------- Binary Search & Regular Search ----------------
    private static void searchStudent() {
        if (students.isEmpty()) {
            System.out.println(" No student data to search.");
            return;
        }

        System.out.println("\n🔍 Search Options:");
        System.out.println("1️:  Search by ID (Binary Search)");
        System.out.println("2️:  Search by Name (Linear Search)");
        int option = getInt("Choose search type: ");

        if (option == 1) {
            if (!isSortedById) {
                System.out.println(" Data not sorted by ID. Sorting automatically...");
                sortById();
            }

            int id = getInt("Enter ID to search: ");
            int index = binarySearchById(students, id);

            if (index == -1) {
                System.out.println(" No student found with ID: " + id);
            } else {
                printStudentTable(List.of(students.get(index)), " SEARCH RESULT (BY ID)");
            }

        } else if (option == 2) {
            System.out.print("Enter name keyword: ");
            String keyword = sc.nextLine().trim().toLowerCase();
            List<Student> found = new ArrayList<>();

            for (Student s : students) {
                if (s.getName().toLowerCase().contains(keyword)) {
                    found.add(s);
                }
            }

            if (found.isEmpty()) {
                System.out.println(" No matching student found.");
            } else {
                printStudentTable(found, " SEARCH RESULTS (BY NAME)");
            }
        } else {
            System.out.println(" Invalid choice.");
        }
    }

    private static int binarySearchById(List<Student> list, int targetId) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int midId = list.get(mid).getId();

            if (midId == targetId)
                return mid;
            else if (midId < targetId)
                left = mid + 1;
            else
                right = mid - 1;
        }

        return -1;
    }

    private static void exportToFile() {
        if (students.isEmpty()) {
            System.out.println(" Nothing to export.");
            return;
        }
        StudentFileHandler.exportToFile(OUTPUT_FILE, students);
        System.out.println(" Students exported to " + OUTPUT_FILE);
    }

    // ---------------- Sorting ----------------
    private static void sortById() {
        if (students.isEmpty()) {
            System.out.println(" No data to sort.");
            return;
        }

        students.sort(Comparator.comparingInt(Student::getId));
        isSortedById = true;
        printStudentTable(students, " SORTED BY ID (ASCENDING)");
    }

    private static void sortByCgpa() {
        if (students.isEmpty()) {
            System.out.println(" No data to sort.");
            return;
        }

        students.sort(Comparator.comparingDouble(Student::getCgpa).reversed());
        isSortedById = false;
        printStudentTable(students, " SORTED BY CGPA (DESCENDING)");
    }

    // ---------------- Categorize ----------------
    private static void categorizeByDept() {
        if (students.isEmpty()) {
            System.out.println(" No data available.");
            return;
        }

        Set<String> departments = new TreeSet<>();
        for (Student s : students) {
            departments.add(s.getDepartment().toUpperCase());
        }

        System.out.println("\n Available Departments:");
        for (String d : departments) {
            System.out.println(" - " + d);
        }

        System.out.print("\nEnter Department Name: ");
        String dept = sc.nextLine().trim().toUpperCase();

        List<Student> filtered = new ArrayList<>();
        for (Student s : students) {
            if (s.getDepartment().equalsIgnoreCase(dept)) {
                filtered.add(s);
            }
        }

        if (filtered.isEmpty()) {
            System.out.println(" No students found in department: " + dept);
            return;
        }

        System.out.println("""
            Choose Sorting Option:
            1️:  Sort by ID
            2️:  Sort by CGPA
            0️:  No Sorting
            """);
        int choice = getInt("Enter your choice: ");
        switch (choice) {
            case 1 -> filtered.sort(Comparator.comparingInt(Student::getId));
            case 2 -> filtered.sort(Comparator.comparingDouble(Student::getCgpa).reversed());
        }

        printStudentTable(filtered, "🏛 STUDENTS IN DEPT: " + dept);
    }

    // ---------------- Table Display ----------------
    private static void printStudentTable(List<Student> list, String title) {
        System.out.println("\n=========== " + title + " ===========");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-15s %-10s%n", "ID", "Name", "Department", "CGPA");
        System.out.println("-------------------------------------------------------------");

        for (Student s : list) {
            System.out.printf("%-10d %-20s %-15s %-10.2f%n",
                    s.getId(),
                    s.getName(),
                    s.getDepartment(),
                    s.getCgpa());
        }

        System.out.println("-------------------------------------------------------------");
        System.out.println("Total Students: " + list.size());
    }

    // ---------------- Utility Input Methods ----------------
    private static int getInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Please enter a valid number.");
            }
        }
    }

    private static double getDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Please enter a valid number.");
            }
        }
    }
}
