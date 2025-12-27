
package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
   private final List<Student> students = new ArrayList<>();

   // Overloaded add methods
   public Student addStudent(String firstName, String lastName, String email, String batch) {
      if (!InputValidator.notBlank(firstName) || !InputValidator.notBlank(lastName)) {
         throw new IllegalArgumentException("First/Last name cannot be blank.");
      }
      if (!InputValidator.isValidEmail(email)) {
         throw new IllegalArgumentException("Invalid email.");
      }
      int id = IdGenerator.getNextStudentId();
      Student s = new Student(id, firstName, lastName, email, batch, true);
      students.add(s);
      return s;
   }

   public Student addStudent(Student student) {
      if (student.getId() == 0) {
         student.setId(IdGenerator.getNextStudentId());
      }
      students.add(student);
      return student;
   }

   public List<Student> listStudents() {
      return new ArrayList<>(students);
   }

   public Student findById(int id) {
      return students.stream()
              .filter(s -> s.getId() == id)
              .findFirst()
              .orElseThrow(() -> new EntityNotFoundException("Student not found: " + id));
   }

   public Student updateStudent(int id, String email, String batch) {
      Student s = findById(id);
      if (InputValidator.notBlank(email) && InputValidator.isValidEmail(email)) {
         s.setEmail(email);
      }
      if (InputValidator.notBlank(batch)) {
         s.setBatch(batch);
      }
      return s;
   }

   public void deactivateStudent(int id) {
      Student s = findById(id);
      s.setActive(false);
   }

   public void removeStudent(int id) {
      Student s = findById(id);
      students.remove(s);
   }
}
