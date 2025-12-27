
package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.util.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentService {
   private final List<Enrollment> enrollments = new ArrayList<>();

   public Enrollment enrollStudentToCourse(int studentId, int courseId) {
      int id = IdGenerator.getNextEnrollmentId();
      Enrollment e = new Enrollment(id, studentId, courseId, LocalDate.now(), EnrollmentStatus.ACTIVE);
      enrollments.add(e);
      return e;
   }

   public List<Enrollment> listEnrollments() {
      return new ArrayList<>(enrollments);
   }

   public Enrollment findById(int id) {
      return enrollments.stream()
              .filter(e -> e.getId() == id)
              .findFirst()
              .orElseThrow(() -> new EntityNotFoundException("Enrollment not found: " + id));
   }

   public List<Enrollment> findEnrollmentsByStudentId(int studentId) {
      return enrollments.stream()
              .filter(e -> e.getStudentId() == studentId)
              .collect(Collectors.toList());
   }

   public void updateEnrollmentStatus(int enrollmentId, EnrollmentStatus status) {
      Enrollment e = findById(enrollmentId);
      e.setStatus(status);
   }
}
