package util;

public class FileReport {
    private int total = 0;
    private int success = 0;
    private int failed = 0;

    public void addSuccess() { total++; success++; }
    public void addFail() { total++; failed++; }

    public void printReport(String type) {
        System.out.println("\n " + type + " Summary:");
        System.out.println("Total records: " + total);
        System.out.println(" Successful: " + success);
        System.out.println(" Failed: " + failed);
    }
}
