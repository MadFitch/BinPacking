/**
 * Authors: Madison Fichtner
 */

package binpacking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.in;
import java.util.Scanner;

public class BestFitDecreasing 
{
    private static RedBlackBST tree;
    private static double sum;
    private static int totalDisks = 0;
    private static Disk[] disks;
    private static int i = 0;
    private static int totalFiles;
    private static Disk currentDisk;
    
    public static void main(String[] args) throws FileNotFoundException, IOException // deals with errors with the file input
    {
        if (args.length >= 0) {
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
        
        int[] tempFiles = new int[50]; // creates 50 new slots in the array
        int n = 0;
        
        while(scanner.hasNextInt())
        {
            totalFiles++;
            tempFiles[n] = scanner.nextInt();
            n++;
        }
        int[] files = new int[totalFiles];
        for(int i = 0; i < totalFiles; i++)
        {
            files[i] = tempFiles[i];
        }
        IntegerSorter sorter = new IntegerSorter(files);
        files = sorter.getFiles();
        
        disks = new Disk[30]; // adds 30 disks
        tree = new RedBlackBST(); // calls RedBlk tree function
        
        for(int i = 0; i < totalFiles; i++)
        {
            addToTree(files[i]); // adds the files to the tree using the addToTree function
        }
        
        fileSum(files);
        printResults(disks);
    }
    
    public static void addToTree(int size) // adds the disks into the Red and Black trees
    {
        boolean flag = false;
        if(currentDisk == null)
        {
            disks[i] = new Disk();
            currentDisk = disks[i];
            totalDisks++;
            currentDisk.add(size);
            tree.put(currentDisk.getSize(), currentDisk);
            
            //System.out.println(tree.size());
            //System.out.println(tree.get(tree.max())); returns disks[i]
            //System.out.println((tree.ceiling(size)));
            //tree.delete(tree.ceiling(size));
            i++;
            flag=true;
        }
        int d;
        if(tree.ceiling(size) != null)// && ((Disk)tree.get(tree.ceiling(size))) != null)
        {
            d = (int)((Disk)tree.get(tree.ceiling(size))).getSize();
            if(size <= d && flag == false)
            {
                //currentDisk = (Disk)tree.get(tree.ceiling(size));
                //currentDisk = (Disk)tree.get(tree.max());
                tree.delete(tree.ceiling(size));
                currentDisk.add(size);
                tree.put(currentDisk.getSize(), currentDisk);
                flag = true;
            }
        }
        if(tree.ceiling(size) == null && flag == false)
            {
            
                disks[i] = new Disk();
                currentDisk = disks[i];
                totalDisks++;
                currentDisk.add(size);
                tree.put(currentDisk.getSize(), currentDisk);
                i++;
            
        }
        // min() - > returns min key in tree
        // get(Key key) - > returns value of a key
    }
    
    public static void printResults(Disk[] disks) // prints the amout of disks used and sum of the files along with the memory, if disks == 0 stops print statement. 
    {
        System.out.println("Sum of all files = " + sum + " GB");
        System.out.println("Disks used       = " + totalDisks);
        
        for(int k = 0; k < disks.length; k++)
        {
            if(disks[k] == null)
                break;
            System.out.println("    " + disks[k].getSize() + ": " + disks[k].getFile()); 
        }
    
    }
    
    public static double fileSum(int files[]) // returns the files
    {
        for(int i = 0; i < totalFiles; i++)
        {
            sum+=files[i];
        }
        sum = sum / 1000000;
        return sum;
    }
}
