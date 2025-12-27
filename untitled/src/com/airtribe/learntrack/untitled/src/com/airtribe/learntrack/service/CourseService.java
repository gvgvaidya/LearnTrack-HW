
package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
   private final List<Course> courses = new ArrayList<>();

   // Overloaded add methods
   public Course addCourse(String name, String description, int durationInWeeks) {
      if (!InputValidator.notBlank(name)) {
         throw new IllegalArgumentException("Course name cannot be blank.");
      }
      int id = IdGenerator.getNextCourseId();
      Course c = new Course(id, name, description, durationInWeeks, true);
      courses.add(c);
      return c;
   }

   public Course addCourse(Course course) {
      if (course.getId() == 0) {
         course.setId(IdGenerator.getNextCourseId());
      }
      courses.add(course);
      return course;
   }

   public List<Course> listCourses() {
      return new ArrayList<>(courses);
   }

   public Course findById(int id) {
      return courses.stream()
              .filter(c -> c.getId() == id)
              .findFirst()
              .orElseThrow(() -> new EntityNotFoundException("Course not found: " + id));
   }

   public void setActive(int id, boolean active) {
      Course c = findById(id);
      c.setActive(active);
   }

   public void updateCourse(int id, String name, String description, Integer durationInWeeks) {
      Course c = findById(id);
      if (InputValidator.notBlank(name)) c.setCourseName(name);
      if (InputValidator.notBlank(description)) c.setDescription(description);
      if (durationInWeeks != null && durationInWeeks > 0) c.setDurationInWeeks(durationInWeeks);
   }
}
