import java.util.*;
import java.lang.*;

public class MyClass {

    private static double length = 4000000;
    private static boolean print = true;
    private static boolean stillPrint = true;
    private static double mean = 0;

    public static void main(String args[]) {
      System.out.println();
      int[] assigns = new int[(int)length];
      double[] counts = new double[801];
      double[] aggCounts = new double[81];
      for(int x = 0; x < assigns.length; x++) {
        assigns[x] = assign();
      }
      for(int x = 300; x < Math.min(counts.length * 4, 500); x += 4) {
          int contains = 0;
          for(int assignees : assigns) {
            if(((int)(4 * Math.round(assignees / 4.0))) == x) {
              contains++;
            }
          }
          counts[x] = contains;
      }
      for(int x = 300; x < Math.min(aggCounts.length * 10, 500); x += 10) {
          int contains = 0;
          for(int assignees : assigns) {
            if(((int)(10 * Math.round(assignees / 10.0))) == x) {
              contains++;
            }
          }
          aggCounts[x / 10] = contains;
      }
      int k = 0;
      for(double found : aggCounts) {
        System.out.println(k + ": " + found / length);
        k += 10;
      }
      System.out.println();
      k = 0;
      for(int j = 0; j < assigns.length * 0.125; j += 12000) {
        System.out.println();
        System.out.print("|");
        for(int x = 300; x < 500; x += 4) {
            System.out.print(counts[x] >= (length * 0.3) - k ? "* " : "  ");
        }
        k += (int)(length * 0.3 / 43);
      }
      System.out.println();
      System.out.print(" ");
      for(int x = 300; x < Math.min(500, counts.length); x += 4) {
        System.out.print("- ");
      }
      System.out.println();
      checkNormal(assigns, counts, 1);
      checkNormal(assigns, counts, 2);
      checkNormal(assigns, counts, 3);
    }

    public static int assign() {
      int count = 0;
      for(int x = 0; x < 800; x++) {
          double randnum = Math.random();
          if(randnum >= 0.5) {
              count++;
          }
      }
      return count;
    }

    public static double mean(int[] assigns) {
      if(print) {
        double sum = 0;
        for(int x = 0; x < assigns.length; x++) {
          sum += assigns[x];
        }
        sum /= length;
        if(print) {
          System.out.println("μ = " + sum);
          print = false;
        }
        mean = sum;
        return sum;
      }
      else {
        return mean;
      }
    }

    public static double stDev(int[] assigns) {
      double mean = mean(assigns);
      double stDev = 0;
      for(int x = 0; x < assigns.length; x++) {
        stDev += Math.pow((assigns[x] - mean), 2);
      }
      stDev /= length;
      stDev = Math.sqrt(stDev);
      if(stillPrint) {
        System.out.println("σ = " + stDev);
        stillPrint = false;
      }
      return stDev;
    }

    public static double stDev(int[] assigns, double mean) {
      double stDev = 0;
      for(int x = 0; x < assigns.length; x++) {
        stDev += Math.pow((assigns[x] - mean), 2);
      }
      stDev /= length;
      stDev = Math.sqrt(stDev);
      if(stillPrint) {
        System.out.println("σ = " + stDev);
        stillPrint = false;
      }
      return stDev;
    }

    public static void checkNormal(int[] assigns, double[] counts, int stDevs) {
      double mean = mean(assigns);
      double stDev = stDev(assigns, mean);
      int min = (int)Math.round(mean - (stDev * stDevs));
      double max = (int)Math.round(mean + (stDev * stDevs));
      double prop = 0;
      for(int assignees : assigns) {
        if(assignees >= min && assignees <= max) {
          prop++;
        }
      }
      prop /= length;
      prop *= 100;
      System.out.println("% within " + stDevs + " StDev = " + prop);
    }
}
