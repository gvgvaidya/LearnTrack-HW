
package com.airtribe.learntrack.repository;

import com.airtribe.learntrack.entity.Course;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {
   private final List<Course> courses = new ArrayList<>();

   public void save(Course course) {
      courses.add(course);
   }

   public List<Course> findAll() {
      return new ArrayList<>(courses);
   }

   public Course findById(int id) {
      return courses.stream()
              .filter(c -> c.getId() == id)
              .findFirst()
              .orElse(null);
   }
}
