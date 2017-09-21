/**
 * Authors: Madison Fichtner
 * Integer Sorter uses a heapsort algorithm to sort the file sizes
 */
package binpacking;

import java.io.File;
import java.io.FileInputStream;
import static java.lang.System.in;
import java.util.Scanner;


public class IntegerSorter {
    /**
     * Instance variables to help sort, and keep track of what is returned
     */
    private static int[] numbers;
    private static int[] helper;
    
    private static int number;
    
    private static int[] unsorted;
    private static int[] sorted;
    private static int[] temp;
    private static int totalFiles;
    
    private static String[] arguments;
    
    public static void main(String[] args)
    {
        /**
         * Algorithm for taking input from user
         */
        if (args.length > 0) {
            FileInputStream is = null;
            try {
                File file = new File(args[0]);
                is = new FileInputStream(file);
            } catch (Exception ex) {
                System.err.println(ex);
            }
            System.setIn(is);
        }
        Scanner scanner = new Scanner(in);
        //n is used to increment through the integers in the file received
        int n = 0;
        //temp is a temporary array of the integers in the file, can only receive x number of files
        temp = new int[150];
        
        //While there are integers in the file, add the next one to temp and increment the number of files
        while(scanner.hasNextInt())
        {
            totalFiles++;
            temp[n] = scanner.nextInt();
            n++;
        }
        
        //create an unsorted array the same size as there are files, and then input the files into it
        unsorted = new int[totalFiles];
        for(int i = 0; i < totalFiles; i++)
        {
            unsorted[i] = temp[i];
        }
        
        //sort the unsorted integers
        sort(unsorted);
        sorted = new int[numbers.length];
        int k = 0;
        System.out.println("Sorted elements:");
        for(int i = numbers.length-1; i >= 0; i--)
        {
            sorted[k] = numbers[i];
            System.out.println(sorted[k]);
            k++;
        }
        System.out.print("\n");
    }
    
    //Main function to be called from other classes
    public static void main()
    {
        sort(unsorted);
        sorted = new int[numbers.length];
        int k = 0;
        System.out.println("Sorted elements:");
        //Flips the order of the sorted array, and returns it
        for(int i = numbers.length-1; i >= 0; i--)
        {
            sorted[k] = numbers[i];
            System.out.println(sorted[k]);
            k++;
        }
        System.out.print("\n");
    }
    
    //Returns the sorted files
    public int[] getFiles()
    {
        return sorted;
    }
    
    //Constructor
    public IntegerSorter(int[] array)
    {
        this.unsorted = array;
        arguments = new String[0];
        main();
    }
    
    //Sorts an array
    public static void sort(int[] values) {
                IntegerSorter.numbers = values;
                number = values.length;
                IntegerSorter.helper = new int[number];
                mergesort(0, number - 1);
        }
    
    private static void mergesort(int low, int high) {
                // check if low is smaller then high, if not then the array is sorted
                if (low < high) {
                        // Get the index of the element which is in the middle
                        int middle = low + (high - low) / 2;
                        // Sort the left side of the array
                        mergesort(low, middle);
                        // Sort the right side of the array
                        mergesort(middle + 1, high);
                        // Combine them both
                        merge(low, middle, high);
                }
        }
    
    private static void merge(int low, int middle, int high) {

                // Copy both parts into the helper array
                for (int i = low; i <= high; i++) {
                        helper[i] = numbers[i];
                }

                int i = low;
                int j = middle + 1;
                int k = low;
                // Copy the smallest values from either the left or the right side back
                // to the original array
                while (i <= middle && j <= high) {
                        if (helper[i] <= helper[j]) {
                                numbers[k] = helper[i];
                                i++;
                        } else {
                                numbers[k] = helper[j];
                                j++;
                        }
                        k++;
                }
                // Copy the rest of the left side of the array into the target array
                while (i <= middle) {
                        numbers[k] = helper[i];
                        k++;
                        i++;
                }

        }
}
