import java.util.*;

public class StudentService {
    private List<Student> students = new ArrayList<>();

    // Add new student
    public void addStudent(Student s) {
        students.add(s);
        System.out.println("Student added successfully!");
    }

    // Sort students by roll number
    private void sortByRoll() {
        Collections.sort(students, Comparator.comparingInt(Student::getRollNo));
    }

    // Display all students (sorted)
    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("No records found!");
            return;
        }

        sortByRoll();

        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-8s %-15s %-7s %-7s %-7s %-8s %-6s%n",
                "Roll", "Name", "Mark1", "Mark2", "Mark3", "Avg", "Grade");
        System.out.println("-------------------------------------------------------------");

        for (Student s : students) {
            System.out.printf("%-8d %-15s %-7d %-7d %-7d %-8.2f %-6c%n",
                    s.getRollNo(), s.getName(),
                    s.getMarks1(), s.getMarks2(), s.getMarks3(),
                    s.getAverage(), s.getGrade());
        }
    }

    // Search student by roll number using binary search
    public void searchByRoll(int roll) {
        if (students.isEmpty()) {
            System.out.println("No student records available!");
            return;
        }

        sortByRoll();

        int low = 0, high = students.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            Student midStudent = students.get(mid);

            if (midStudent.getRollNo() == roll) {
                System.out.println("\n✅ Student Found:");
                System.out.println("-------------------------------------------------------------");
                System.out.printf("%-8s %-15s %-7s %-7s %-7s %-8s %-6s%n",
                        "Roll", "Name", "Mark1", "Mark2", "Mark3", "Avg", "Grade");
                System.out.println("-------------------------------------------------------------");
                System.out.printf("%-8d %-15s %-7d %-7d %-7d %-8.2f %-6c%n",
                        midStudent.getRollNo(), midStudent.getName(),
                        midStudent.getMarks1(), midStudent.getMarks2(), midStudent.getMarks3(),
                        midStudent.getAverage(), midStudent.getGrade());
                return;
            } else if (midStudent.getRollNo() < roll) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("No student found with roll: " + roll);
    }
}
