
package com.airtribe.learntrack.util;

public class InputValidator {

   public static boolean notBlank(String s) {
      return s != null && !s.trim().isEmpty();
   }

   public static boolean isValidEmail(String email) {
      if (!notBlank(email)) return false;
      return email.contains("@") && email.contains(".") && email.length() >= 5;
   }

   public static int parseIntOrThrow(String value, String fieldName) {
      try {
         return Integer.parseInt(value.trim());
      } catch (NumberFormatException e) {
         throw new IllegalArgumentException("Invalid number for " + fieldName + ": " + value);
      }
   }
}
