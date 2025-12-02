package de.modorn.aoc25;

import java.util.List;
import java.util.function.Function;

public class Day02 {
  public static long solvePartOne(String input) {
    String[] ranges = input.split(",");
    long result = 0;
    
    for (String range : ranges) {
      String[] bounds = range.split("-");      
      result += getInvalidIdSumOfRange(bounds[0], bounds[1], isInvalidIdPartOne);
    }
    
    return result;
  }
  
  public static long solvePartTwo(String input) {
    String[] ranges = input.split(",");
    long result = 0;
    
    for (String range : ranges) {
      String[] bounds = range.split("-");      
      result += getInvalidIdSumOfRange(bounds[0], bounds[1], isInvalidIdPartTwo);
    }
    
    return result;
  }
  
  private static long getInvalidIdSumOfRange(String start, String end, Function<String, Boolean> isInvalidFn) {
    long sum = 0;
    
    for (long i = Long.parseLong(start); i <= Long.parseLong(end); i++) {
      
      sum += isInvalidFn.apply(String.valueOf(i)) ? i : 0;
    }
    
    return sum;
  }
  
  private static Function<String, Boolean> isInvalidIdPartOne = (String id)  -> {
    
    // odd count of digits cannot be invalid
    if (id.length() % 2 == 1) {
      return false;
    }
    
    // compare if both halves of the id are the same
    return id.substring(0, id.length() / 2).compareTo(id.substring(id.length() / 2)) == 0;
  };
  
  private static Function<String, Boolean> isInvalidIdPartTwo = (String id)  -> {

    // cut the id in smaller and smaller partitions to check for repetitions
    for (int partitionSize = id.length() / 2; partitionSize >= 1; partitionSize--) {
      // skip try if the id cannot be split into parts of this size
      if (id.length() % partitionSize != 0) continue;
      
      int amountPartitions = id.length() / partitionSize;
      // split id into partitions
      String[] parts = new String[amountPartitions];
      for (int partitionIndex = 0; partitionIndex < amountPartitions; partitionIndex++) {
        parts[partitionIndex] = id.substring(partitionIndex * partitionSize, (partitionIndex + 1) * partitionSize);
      }
      
      // check if all partitions are equal to each other
      if (List.of(parts).stream().allMatch(part -> parts[0].equals(part))) {
        return true;
      }
    }
    
    // if id cannot be partitioned into equal parts
    return false;
  };
}
