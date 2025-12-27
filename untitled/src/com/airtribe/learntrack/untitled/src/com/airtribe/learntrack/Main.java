package com.airtribe.learntrack;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.EnrollmentStatus;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;

import java.util.List;
import java.util.Scanner;

public class Main {
   private final StudentService studentService = new StudentService();
   private final CourseService courseService = new CourseService();
   private final EnrollmentService enrollmentService = new EnrollmentService();

   public static void main(String[] args) {
      new Main().run();
   }

   private void run() {
      Scanner scanner = new Scanner(System.in);
      boolean exit = false;

      while (!exit) {
         printMainMenu();
         System.out.print("Choose an option: ");
         String choice = scanner.nextLine();

         try {
            switch (choice) {
               case "1": studentMenu(scanner); break;
               case "2": courseMenu(scanner); break;
               case "3": enrollmentMenu(scanner); break;
               case "0":
                  System.out.println("Exiting LearnTrack. Goodbye!");
                  exit = true;
                  break;
               default:
                  System.out.println("Invalid option. Please try again.");
            }
         } catch (IllegalArgumentException e) {
            System.out.println("[Input Error] " + e.getMessage());
         } catch (EntityNotFoundException e) {
            System.out.println("[Not Found] " + e.getMessage());
         } catch (Exception e) {
            System.out.println("[Error] Something went wrong: " + e.getMessage());
         }
         System.out.println();
      }

      scanner.close();
   }

   private void printMainMenu() {
      System.out.println("=== LearnTrack: Student & Course Management ===");
      System.out.println("1) Student Management");
      System.out.println("2) Course Management");
      System.out.println("3) Enrollment Management");
      System.out.println("0) Exit");
   }

   // ---------- Student Menu ----------
   private void studentMenu(Scanner scanner) {
      System.out.println("--- Student Management ---");
      System.out.println("1) Add new student");
      System.out.println("2) View all students");
      System.out.println("3) Search student by ID");
      System.out.println("4) Update student's email/batch");
      System.out.println("5) Deactivate a student");
      System.out.println("0) Back");
      System.out.print("Option: ");
      String opt = scanner.nextLine();

      switch (opt) {
         case "1":
            System.out.print("First name: ");
            String fn = scanner.nextLine();
            System.out.print("Last name: ");
            String ln = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Batch: ");
            String batch = scanner.nextLine();
            Student s = studentService.addStudent(fn, ln, email, batch);
            System.out.println("Added student: " + s.getDisplayName() + " [id=" + s.getId() + "]");
            break;
         case "2":
            List<Student> list = studentService.listStudents();
            if (list.isEmpty()) {
               System.out.println("No students found.");
            } else {
               list.forEach(st -> System.out.println(st.getId() + ": " + st.getDisplayName() + " | Active=" + st.isActive()));
            }
            break;
         case "3":
            int sid = askInt(scanner, "Student ID");
            Student found = studentService.findById(sid);
            System.out.println("Found: " + found.getDisplayName() + " [Email=" + found.getEmail() + "]");
            break;
         case "4":
            int usid = askInt(scanner, "Student ID");
            System.out.print("New email (leave blank to skip): ");
            String newEmail = scanner.nextLine();
            System.out.print("New batch (leave blank to skip): ");
            String newBatch = scanner.nextLine();
            Student updated = studentService.updateStudent(usid, newEmail, newBatch);
            System.out.println("Updated: " + updated.getDisplayName() + " [Email=" + updated.getEmail() + "]");
            break;
         case "5":
            int dsid = askInt(scanner, "Student ID");
            studentService.deactivateStudent(dsid);
            System.out.println("Deactivated student id=" + dsid);
            break;
         case "0":
            return;
         default:
            System.out.println("Invalid option.");
      }
   }

   // ---------- Course Menu ----------
   private void courseMenu(Scanner scanner) {
      System.out.println("--- Course Management ---");
      System.out.println("1) Add new course");
      System.out.println("2) View all courses");
      System.out.println("3) Activate/Deactivate a course");
      System.out.println("4) Update course details");
      System.out.println("0) Back");
      System.out.print("Option: ");
      String opt = scanner.nextLine();

      switch (opt) {
         case "1":
            System.out.print("Course name: ");
            String name = scanner.nextLine();
            System.out.print("Description: ");
            String desc = scanner.nextLine();
            int weeks = askInt(scanner, "Duration in weeks");
            Course c = courseService.addCourse(name, desc, weeks);
            System.out.println("Added course: " + c.getCourseName() + " [id=" + c.getId() + "]");
            break;
         case "2":
            List<Course> list = courseService.listCourses();
            if (list.isEmpty()) {
               System.out.println("No courses found.");
            } else {
               list.forEach(cr -> System.out.println(cr.getId() + ": " + cr.getCourseName() + " | Weeks=" + cr.getDurationInWeeks() + " | Active=" + cr.isActive()));
            }
            break;
         case "3":
            int cid = askInt(scanner, "Course ID");
            System.out.print("Set active? (true/false): ");
            boolean active = Boolean.parseBoolean(scanner.nextLine().trim());
            courseService.setActive(cid, active);
            System.out.println("Course id=" + cid + " active=" + active);
            break;
         case "4":
            int ucid = askInt(scanner, "Course ID");
            System.out.print("New name (blank to skip): ");
            String nname = scanner.nextLine();
            System.out.print("New description (blank to skip): ");
            String ndesc = scanner.nextLine();
            System.out.print("New duration weeks (blank to skip): ");
            String dweeksStr = scanner.nextLine();
            Integer dweeks = null;
            if (dweeksStr != null && !dweeksStr.trim().isEmpty()) {
               try { dweeks = Integer.parseInt(dweeksStr.trim()); }
               catch (NumberFormatException e) { System.out.println("Invalid weeks. Skipping."); }
            }
            courseService.updateCourse(ucid, nname, ndesc, dweeks);
            System.out.println("Updated course id=" + ucid);
            break;
         case "0":
            return;
         default:
            System.out.println("Invalid option.");
      }
   }

   // ---------- Enrollment Menu ----------
   private void enrollmentMenu(Scanner scanner) {
      System.out.println("--- Enrollment Management ---");
      System.out.println("1) Enroll a student in a course");
      System.out.println("2) View enrollments for a student");
      System.out.println("3) Mark enrollment as completed/cancelled");
      System.out.println("0) Back");
      System.out.print("Option: ");
      String opt = scanner.nextLine();

      switch (opt) {
         case "1":
            int sid = askInt(scanner, "Student ID");
            int cid = askInt(scanner, "Course ID");
            Enrollment e = enrollmentService.enrollStudentToCourse(sid, cid);
            System.out.println("Created enrollment id=" + e.getId() + " on " + e.getEnrollmentDate());
            break;
         case "2":
            int esid = askInt(scanner, "Student ID");
            List<Enrollment> list = enrollmentService.findEnrollmentsByStudentId(esid);
            if (list.isEmpty()) {
               System.out.println("No enrollments found for student id=" + esid);
            } else {
               list.forEach(en -> System.out.println(en.getId() + ": courseId=" + en.getCourseId() + " | status=" + en.getStatus() + " | date=" + en.getEnrollmentDate()));
            }
            break;
         case "3":
            int eid = askInt(scanner, "Enrollment ID");
            System.out.print("New status (ACTIVE/COMPLETED/CANCELLED): ");
            String statusStr = scanner.nextLine().trim().toUpperCase();
            try {
               EnrollmentStatus status = EnrollmentStatus.valueOf(statusStr);
               enrollmentService.updateEnrollmentStatus(eid, status);
               System.out.println("Updated enrollment id=" + eid + " status=" + status);
            } catch (IllegalArgumentException ex) {
               System.out.println("Invalid status. Use ACTIVE, COMPLETED, or CANCELLED.");
            }
            break;
         case "0":
            return;
         default:
            System.out.println("Invalid option.");
      }
   }

   private int askInt(Scanner scanner, String label) {
      System.out.print(label + ": ");
      String value = scanner.nextLine();
      try {
         return Integer.parseInt(value.trim());
      } catch (NumberFormatException e) {
         throw new IllegalArgumentException(label + " must be a number.");
      }
   }
}
