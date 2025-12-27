
package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Enrollment;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentRepository {
   private final List<Enrollment> enrollments = new ArrayList<>();

   public void save(Enrollment enrollment) {
      enrollments.add(enrollment);
   }

   public Enrollment findById(int id) {
      return enrollments.stream()
              .filter(e -> e.getId() == id)
              .findFirst()
              .orElse(null);
   }

   public List<Enrollment> findAll() {
      return new ArrayList<>(enrollments);
   }

   public List<Enrollment> findByStudentId(int studentId) {
      return enrollments.stream()
              .filter(e -> e.getStudentId() == studentId)
              .collect(Collectors.toList());
   }
}
