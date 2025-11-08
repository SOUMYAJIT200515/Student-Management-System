public class Student {
    private int rollNo;
    private String name;
    private int marks1, marks2, marks3;
    private double average;
    private char grade;

    // Constructor initializes and calculates average + grade
    public Student(int rollNo, String name, int marks1, int marks2, int marks3) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks1 = marks1;
        this.marks2 = marks2;
        this.marks3 = marks3;
        calculateAverageAndGrade();
    }

    //  Private helper method
    private void calculateAverageAndGrade() {
        this.average = (marks1 + marks2 + marks3) / 3.0;
        if (average >= 90) grade = 'A';
        else if (average >= 75) grade = 'B';
        else if (average >= 60) grade = 'C';
        else if (average >= 40) grade = 'D';
        else grade = 'F';
    }

    //  Getters
    public int getRollNo() { return rollNo; }
    public String getName() { return name; }
    public int getMarks1() { return marks1; }
    public int getMarks2() { return marks2; }
    public int getMarks3() { return marks3; }
    public double getAverage() { return average; }
    public char getGrade() { return grade; }

    // string for quick display
    @Override
    public String toString() {
        return String.format("%-8d %-15s %-7d %-7d %-7d %-8.2f %-6c",
                rollNo, name, marks1, marks2, marks3, average, grade);
    }
}
