import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentDatabaseApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();
        
        int choice;
        do {
            System.out.println("Menu:");
            System.out.println("[1] Add student");
            System.out.println("[2] Show database");
            System.out.println("[3] Delete database");
            System.out.println("[4] Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    addStudent(students, scanner);
                    break;
                case 2:
                    showDatabase(students);
                    break;
                case 3:
                    students.clear();
                    System.out.println("Database cleared.");
                    break;
                case 4:
                    saveDatabase(students);
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        } while (choice != 4);
    }
    
    public static void addStudent(List<Student> students, Scanner scanner) {
        System.out.println("Adding a new student:");
        
        System.out.print("Enter student's name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter student's year (1 - Freshman, 2 - Sophomore, 3 - Junior, 4 - Senior): ");
        int year = scanner.nextInt();
        scanner.nextLine();
        
        Student student = new Student(name, year);
        enrollStudent(student, scanner);
        students.add(student);
        
        System.out.println("Student added.");
    }
    
    public static void enrollStudent(Student student, Scanner scanner) {
        boolean isEnrolling = true;
        while (isEnrolling) {
            System.out.println("Enrollment Menu:");
            System.out.println("[1] History 101");
            System.out.println("[2] Mathematics 101");
            System.out.println("[3] English 101");
            System.out.println("[4] Chemistry 101");
            System.out.println("[5] Computer Programming 101");
            System.out.println("[Q] Exit");

            System.out.print("Enter course number to enroll: ");
            String choice = scanner.nextLine();

            switch (choice.toLowerCase()) {
                case "1":
                    student.enroll("History 101");
                    break;
                case "2":
                    student.enroll("Mathematics 101");
                    break;
                case "3":
                    student.enroll("English 101");
                    break;
                case "4":
                    student.enroll("Chemistry 101");
                    break;
                case "5":
                    student.enroll("Computer Programming 101");
                    break;
                case "q":
                    isEnrolling = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        }
    }
    
    public static void showDatabase(List<Student> students) {
        for (Student student : students) {
            student.showInfo();
            System.out.println();
        }

         System.out.println("Student Database from student_database.txt:");
        try {
            BufferedReader reader = new BufferedReader(new FileReader("student_database.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the database.");
        }
    }

    public static void saveDatabase(List<Student> students) {
        try {
            FileWriter writer = new FileWriter("student_database.txt", true); // Append mode
            for (Student student : students) {
                writer.write(student.toString());
                writer.write("\n");
            }
            writer.close();
            System.out.println("Database appended to student_database.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the database.");
        }
    }
}
    

class Student {
    private static int id = 1000;
    private String name;
    private int year;
    private String studentID;
    private String courses = "";
    private double balance = 0;
    private static double courseCost = 600.00;
    
    public Student(String name, int year) {
        this.name = name;
        this.year = year;
        id++;
        generateStudentID();
    }
    
    private void generateStudentID() {
        studentID = year + "" + id;
    
    }
    
    public void enroll(String course) {
        courses += "\n " + course;
        balance += courseCost;
    }
    
    public void viewBalance() {
        System.out.println("Your balance is: $" + balance);
    }
    
    public void showInfo() {
        System.out.println("Name: " + name +
                           "\nStudent ID: " + studentID +
                           "\nCourses Enrolled:" + courses +
                           "\nBalance: $" + balance);
    }
    
    @Override
    public String toString() {
        return "Name: " + name +
               "\nStudent ID: " + studentID +
               "\nCourses Enrolled:" + courses +
               "\nBalance: $" + balance;
    }
}
