import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentService service = new StudentService();

        while (true) {
            System.out.println("\n=====================================");
            System.out.println("        Student Result Management     ");
            System.out.println("=====================================");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by Roll");
            System.out.println("4. Exit");
            System.out.println("=====================================");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("\n  Invalid input! Please enter a number between 1–4.");
                sc.nextLine();
                continue;
            }

            int ch = sc.nextInt();
            sc.nextLine(); 

            switch (ch) {
                case 1 -> {
                    System.out.println("\n--- Add New Student ---");
                    System.out.print("Enter Roll No: ");
                    int roll = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Marks in 3 Subjects (space-separated): ");
                    int m1 = sc.nextInt();
                    int m2 = sc.nextInt();
                    int m3 = sc.nextInt();
                    sc.nextLine();

                    service.addStudent(new Student(roll, name, m1, m2, m3));
                    System.out.println(" Student added successfully!");
                }

                case 2 -> {
                    System.out.println("\n--- Student List ---");
                    service.displayAll();
                }

                case 3 -> {
                    System.out.println("\n--- Search Student ---");
                    System.out.print("Enter Roll No to Search: ");
                    int roll = sc.nextInt();
                    sc.nextLine();
                    service.searchByRoll(roll);
                }

                case 4 -> {
                    System.out.println("\n Exiting the program... Goodbye!");
                    sc.close();
                    return;
                }

                default -> System.out.println("\n  Invalid choice! Please enter a number between 1–4.");
            }
        }
    }
}
