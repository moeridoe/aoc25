package de.modorn.aoc25;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Day03 {
  public static long solve(String input, int length) {
    Iterator<String> lineIterator = input.lines().iterator();
    long sum = 0;
    
    while (lineIterator.hasNext()) {
      String line = lineIterator.next();
      
      // sort digits descending while remembering their original position
      List<Indexed> digits = new ArrayList<>(line.length());
      for (int i = 0; i < line.length(); i++) {
        digits.add(new Indexed(line.substring(i, i+1), i));
      }
      Collections.sort(digits);
      
      // collecting highest possible joltage
      String joltage = "";
      int lowestPossibleIndex = 0;
      // using "remaining" to ensure we reach the max digit count
      for (int remaining = length - 1; remaining >= 0; remaining--) {
        for (int i = 0; i < digits.size(); i++) {
          // the next candidate must have enough room afterwards 
          // and must not be positioned before previous choices
          if (digits.get(i).origIndex() < digits.size() - remaining
              && digits.get(i).origIndex() >= lowestPossibleIndex) {
            
            joltage += digits.get(i).value();
            // this ensures that we do not use the same (original) digit again
            lowestPossibleIndex = digits.get(i).origIndex() + 1;
            break;
          };
        }
      }
      
      // adding joltage to total result sum
      sum += Long.parseLong(joltage);
    }
    
    return sum;
  }
  
  // Used to save the original value with its original position
  record Indexed(String value, int origIndex) implements Comparable<Indexed> {
    
    // sorts by value descending
    @Override
    public int compareTo(Indexed other) {
      return -Integer.compare(Integer.parseInt(value), Integer.parseInt(other.value));
    }
  }
}
