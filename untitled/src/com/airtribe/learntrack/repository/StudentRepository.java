
package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
   private final List<Student> students = new ArrayList<>();

   public void save(Student student) {
      students.add(student);
   }

   public List<Student> findAll() {
      return new ArrayList<>(students);
   }

   public Student findById(int id) {
      return students.stream()
              .filter(s -> s.getId() == id)
              .findFirst()
              .orElse(null);
   }

   public void delete(Student student) {
      students.remove(student);
   }
}
