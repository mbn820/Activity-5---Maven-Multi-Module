package com.exist.ecc.service;

public class StringCounter {

      public static int countOccurencesOf(String text, String target) {
          if(text.equals("") && !target.equals("")) { return 0; }
          if(text.length() > target.length()) { return 0; }

          int numberOfCompares = target.length() - text.length() + 1;
          int count = 0;

          for(int i = 0; i < numberOfCompares; i++) {
              String sub = target.substring(i, i + text.length());
              if(text.equals(sub)) count++;
          }

          return count;
      }


}//endClass
