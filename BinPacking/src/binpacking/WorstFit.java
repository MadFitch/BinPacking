/**
 * Authors: Madison Fichtner, Grant Baker, Cody Stoner
 */

package binpacking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.System.in;
import java.util.Scanner;

/*
 * Worst Fit
 * 1. Put each item into the emptiest bin among those with something in them. Only start
* a new bin if the item doesn't fit into any bin that's already been started.
* 2. If there are two or more bins already started which are tied for emptiest, use the bin
* opened earliest from among those tied.
* Note: You can think of this as putting each piece into the box which is lightest so far,
* trying to "even things out
 */

public class WorstFit {
    /**
     * Creates:
     * 1) a max pq
     * 2) the sum of all file sizes
     * 3) the number of disks required
     * 4) the current disk being added to
     * 5) an array of disks
     * 6) a variable to keep track of immediate files
     * 7) and totalfiles
     */
    private static MaxPQ<Disk> pq;
    private static double sum;
    private static int totalDisks = 0;
    private static Disk currentDisk;
    private static Disk[] disks;
    private static int i = 0;
    private static int totalFiles;
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        /**
         * Algorithm to take in user input and implement the integers from the file inputed
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
        
        //Creates an array of possible nececessary disks
        disks = new Disk[50];
        
        //Creates an empty priority queue
        pq = new MaxPQ<Disk>();
        
        //Creates a temporary array to hold the files, and n increments through the txt file
        int[] tempFiles = new int[150];
        int n = 0;
        
        //While there are integers left in the file, place the next integer into the tempFiles array
        while(scanner.hasNextInt())
        {
            totalFiles++;
            tempFiles[n] = scanner.nextInt();
            n++;
        }
        //Create a files array of the size of all the files read in
        int[] files = new int[totalFiles];
        
        //Put files from the temporary array into the permanent one
        for(int i = 0; i < totalFiles; i++)
        {
            files[i] = tempFiles[i];
        }
        
        //Uses the IntegerSorter class to sort the array of files before processing them into disks
        IntegerSorter sorter = new IntegerSorter(files);
        files = sorter.getFiles();
        
        //Get the sum of all files in array
        fileSum(files);
        
        //For each file, add them to a pre-existing disk or create a new disk.
        for(int i = 0; i < totalFiles; i++)
        {
            addToDisk(files[i]);
        }
        
        printResults(disks);
    }
    
    public static void addToDisk(int size)
    {
        //Flag keeps track of whether it is the first file passed into the disk creation algorithm
        boolean flag = false;
        
        //If the currentDisk is null, or in other words, if there are no disks created.
        if(currentDisk == null)
        {
            /**
             * Create a new disk in disks array, increment currentDisk to keep track of what disk
             * is currently being manipulated, increment the totalDisks being used to print out later,
             * add the size of the file into the currentDisk and then finally insert the disk into the PQ
             */
            disks[i] = new Disk();
            currentDisk = disks[i++];
            totalDisks++;
            currentDisk.add(size);
            pq.insert(currentDisk);
            flag = true;
        }
        
        //If the size of the file is less then the remaining size of the largest disk in the pq, and it isn't the first number being entered
        if(size < ((pq.max()).getSize()) && flag == false)
        {
            /**
             * Set current disk automatically to the max disk in the PQ, delete it, add the file to the disk that 
             * was removed, and then re insert the Disk into the PQ
            **/
            currentDisk = pq.max();
            pq.delMax();
            currentDisk.add(size);
            pq.insert(currentDisk);
        }
        
        //If the size of the file doesn't fit into a disk in the PQ
        else if(size > ((pq.max()).getSize()))
        {
            /**
             * Create a new disk and update the currentDisk to the new disk, as well as increment the totalDisks
             * to print out the total number later. Add the file size to the new disk and enter the disk into the PQ
             */
            disks[i] = new Disk();
            currentDisk = disks[i++];
            totalDisks++;
            currentDisk.add(size);
            pq.insert(currentDisk);
        }
        
    }
    
    public static void printResults(Disk[] disks)
    {
        /**
        * Prints out the total sum of all the files from the .txt
        * Prints out the total disks used to store these files
        **/
        System.out.println("Sum of all files = " + sum + " GB");
        System.out.println("Disks used       = " + totalDisks);
        
        //Prints out the disks, their remaining sizes, and the files stored in them. In order of 
        //greatest to least space left in the disk
        for(int k = 0; k < disks.length; k++)
        {
            if(disks[k] == null)
                break;
            System.out.println("    " + pq.max().getSize() + ": " + pq.delMax().getFile());
        
        }
    }
    
    //Simply calculates the totalSum of the files
    public static double fileSum(int files[])
    {
        for(int i = 0; i < totalFiles; i++)
        {
            sum+=files[i];
        }
        sum = sum / 1000000;
        return sum;
    }
    
}
