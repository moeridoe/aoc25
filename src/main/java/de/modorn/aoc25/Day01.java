package de.modorn.aoc25;

import java.util.Iterator;

public class Day01 {
  public static int solvePartOne(String input) {
    int dialPointer = 50; // start position is 50
    int dialAtZeroCounter = 0; // counts how often the dial points at zero
    
    Iterator<String> lineIterator = input.lines().iterator();
    
    while(lineIterator.hasNext()) {
      String line = lineIterator.next();
      
      boolean isPlus = line.charAt(0) == 'R'; // R -> Plus, L -> Minus
      int distance = Integer.parseInt(line.substring(1));
      distance %= 100; // ignore full cycle turns
      
      // turn the dial
      dialPointer += isPlus ? distance : -distance;
      
      // correct overflow to the right
      dialPointer %= 100; 
      
      // correct overflow to the left
      if (dialPointer < 0) {
        dialPointer += 100; 
      }
      
      // check for counter
      if (dialPointer == 0) {
        dialAtZeroCounter++;
      }
    }
    
    return dialAtZeroCounter;
  }
  
  public static int solvePartTwo(String input) {
    int dialPointer = 50; // start position is 50
    int dialCrossingZeroCounter = 0; // counts how often the dial crossed over zero
    int previousDialPointer = dialPointer; // used to prevent counting left movement from 0 as a crossing
    
    Iterator<String> lineIterator = input.lines().iterator();
    
    while(lineIterator.hasNext()) {
      String line = lineIterator.next();
      
      boolean isPlus = line.charAt(0) == 'R'; // R -> Plus, L -> Minus
      int distance = Integer.parseInt(line.substring(1));
      
      // count full cycles of the distance alone and set distance to remainder
      dialCrossingZeroCounter += Integer.divideUnsigned(distance, 100);
      distance %= 100;
      
      // turn the dial
      dialPointer += isPlus ? distance : -distance;
      
      // count in case dial is pointing directly to zero
      if (dialPointer == 0) {
        dialCrossingZeroCounter++;
      }
      
      // count possible overflow to the right and adjust dial afterwards
      dialCrossingZeroCounter += Integer.divideUnsigned(Math.abs(dialPointer), 100);
      dialPointer %= 100; 
      
      // count possible overflow to the left and adjust dial afterwards
      if (dialPointer < 0) {
        // if the previous pointer was at exactly 0, 
        // ending up on the left does not mean we passed zero
        dialCrossingZeroCounter += previousDialPointer == 0 ? 0 : 1;
        dialPointer += 100; 
      }
      
      previousDialPointer = dialPointer;
    }
    
    return dialCrossingZeroCounter;
  }
}
