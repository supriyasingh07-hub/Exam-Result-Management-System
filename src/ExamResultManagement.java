import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Exam Result Management System  (PS No. 48)
 * Name   : Supriya Kumari
 * ERP ID : RU-25-11495
 *
 * Console application to add, display, search and sort student exam results.
 * Concepts: ArrayList, Sorting (manual), Searching (manual), Methods, File Handling.
 */
public class ExamResultManagement {

    // ---------------------------------------------------------------
    // Result class : one object per student record
    // ---------------------------------------------------------------
    static class Result {
        private final String id;
        private final String name;
        private final int m1, m2, m3;
        private final int total;
        private final double percentage;
        private final String status;

        Result(String id, String name, int m1, int m2, int m3) {
            this.id = id;
            this.name = name;
            this.m1 = m1;
            this.m2 = m2;
            this.m3 = m3;
            this.total = m1 + m2 + m3;
            this.percentage = (total * 100.0) / 300.0;   // each subject out of 100
            this.status = (percentage >= PASS_PERCENTAGE) ? "Pass" : "Fail";
        }

        String getId()          { return id; }
        String getName()        { return name; }
        double getPercentage()  { return percentage; }

        /** Line stored in results.txt */
        String toFileLine() {
            return id + "|" + name + "|" + m1 + "|" + m2 + "|" + m3;
        }

        /** Full detail line (used for add / display / search) */
        String fullDetails() {
            return id + " | " + name + " | Total: " + total + "/300 | Percentage: "
                    + String.format("%.2f", percentage) + " | Result: " + status;
        }

        /** Short line (used for sorted list) */
        String shortDetails() {
            return id + " | " + name + " | " + String.format("%.2f", percentage) + " | " + status;
        }
    }

    // ---------------------------------------------------------------
    // Constants and data
    // ---------------------------------------------------------------
    private static final String FILE_NAME = "results.txt";
    private static final double PASS_PERCENTAGE = 40.0;
    private static final int FIRST_ID_NUMBER = 101;

    private static final ArrayList<Result> results = new ArrayList<>();
    private static int nextIdNumber = FIRST_ID_NUMBER;
    private static final Scanner sc = new Scanner(System.in);

    // ---------------------------------------------------------------
    // main : load -> menu loop -> save on exit
    // ---------------------------------------------------------------
    public static void main(String[] args) {
        loadFromFile();
        int choice;
        do {
            printMenu();
            choice = readMenuChoice();
            switch (choice) {
                case 1: addResult();            break;
                case 2: displayAll();           break;
                case 3: searchById();           break;
                case 4: sortByPercentage();     break;
                case 5:
                    saveToFile();
                    System.out.println("Data saved to " + FILE_NAME + ". Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-5.");
            }
        } while (choice != 5);
        sc.close();
    }

    // ---------------------------------------------------------------
    // Menu helpers
    // ---------------------------------------------------------------
    private static void printMenu() {
        System.out.println();
        System.out.println("===== EXAM RESULT MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Result");
        System.out.println("2. Display All");
        System.out.println("3. Search by ID");
        System.out.println("4. Sort by Percentage (high to low)");
        System.out.println("5. Exit");
        System.out.print("Menu option: ");
    }

    private static int readMenuChoice() {
        String line = sc.hasNextLine() ? sc.nextLine().trim() : "5";
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ---------------------------------------------------------------
    // 1. Add Result
    // ---------------------------------------------------------------
    private static void addResult() {
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        // keep '|' out of names so the file format stays safe
        name = name.replace("|", " ");

        System.out.print("Marks: ");
        String[] parts = sc.nextLine().trim().split("\\s+");
        int[] marks = parseMarks(parts);
        if (marks == null) {
            System.out.println("Invalid marks. Each mark must be between 0 and 100.");
            return;
        }

        String id = "ER" + nextIdNumber++;
        Result r = new Result(id, name, marks[0], marks[1], marks[2]);
        results.add(r);
        saveToFile();   // safe-save after every add (also saved again on exit)
        System.out.println("Result added. " + r.fullDetails());
    }

    /** Returns 3 valid marks or null if the input is wrong. */
    private static int[] parseMarks(String[] parts) {
        if (parts.length != 3) return null;
        int[] marks = new int[3];
        for (int i = 0; i < 3; i++) {
            try {
                marks[i] = Integer.parseInt(parts[i]);
            } catch (NumberFormatException e) {
                return null;
            }
            if (marks[i] < 0 || marks[i] > 100) return null;
        }
        return marks;
    }

    // ---------------------------------------------------------------
    // 2. Display All
    // ---------------------------------------------------------------
    private static void displayAll() {
        if (results.isEmpty()) {
            System.out.println("No results available.");
            return;
        }
        System.out.println("---- All Results ----");
        for (Result r : results) {
            System.out.println(r.fullDetails());
        }
    }

    // ---------------------------------------------------------------
    // 3. Search by ID  (manual linear search)
    // ---------------------------------------------------------------
    private static void searchById() {
        System.out.print("Enter Result ID: ");
        String key = sc.nextLine().trim();
        Result found = linearSearch(key);
        if (found == null) {
            System.out.println("Result not found for ID: " + key);
        } else {
            System.out.println("Found: " + found.fullDetails());
        }
    }

    private static Result linearSearch(String key) {
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getId().equalsIgnoreCase(key)) {
                return results.get(i);
            }
        }
        return null;
    }

    // ---------------------------------------------------------------
    // 4. Sort by Percentage  (manual bubble sort, high -> low)
    // ---------------------------------------------------------------
    private static void sortByPercentage() {
        if (results.isEmpty()) {
            System.out.println("No results available.");
            return;
        }
        ArrayList<Result> sorted = new ArrayList<>(results);   // keep original order untouched
        bubbleSortDescending(sorted);

        System.out.println("---- Sorted by Percentage (High to Low) ----");
        for (int i = 0; i < sorted.size(); i++) {
            System.out.println((i + 1) + ". " + sorted.get(i).shortDetails());
        }
    }

    private static void bubbleSortDescending(ArrayList<Result> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).getPercentage() < list.get(j + 1).getPercentage()) {
                    Result temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break;   // already sorted
        }
    }

    // ---------------------------------------------------------------
    // File handling
    // ---------------------------------------------------------------
    private static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;   // first run: nothing to load

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] p = line.split("\\|");
                if (p.length != 5) continue;   // skip corrupted line
                try {
                    String id = p[0];
                    Result r = new Result(id, p[1],
                            Integer.parseInt(p[2]), Integer.parseInt(p[3]), Integer.parseInt(p[4]));
                    results.add(r);
                    int num = Integer.parseInt(id.substring(2));
                    if (num >= nextIdNumber) nextIdNumber = num + 1;
                } catch (RuntimeException e) {
                    // ignore bad line and continue
                }
            }
            System.out.println(results.size() + " record(s) loaded from " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Could not read file: " + e.getMessage());
        }
    }

    private static void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Result r : results) {
                pw.println(r.toFileLine());
            }
        } catch (IOException e) {
            System.out.println("Could not save file: " + e.getMessage());
        }
    }
}
