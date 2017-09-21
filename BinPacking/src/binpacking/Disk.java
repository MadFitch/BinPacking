package binpacking;

/**
 *  Authors: Madison Fichtner
 *  Disk class to keep track of disks
 */
public class Disk implements Comparable<Disk>{
    // Creating variables to keep track of the files found in each disk, and the remaining size in each 1GB disk
    private int remainingSize = 1000000;
    private int[] files = new int[10];
    private int i = 0;
    
    //Constructor, sets the size to 1GB
    public Disk()
    {
        this.remainingSize = remainingSize;
    }
    
    //Returns size
    public int getSize()
    {
        return (remainingSize);
    }
    
    //Returns the array of files
    public String getFile()
    {
        String fileList = "";
        for(int i = 0; i < 10; i++)
        {
            if(files[i] != 0)
                fileList += files[i] + " ";
            else
                break;
        }
        return fileList;
    }
    
    //Add file to disk, and decrement the size remaining on the disk
    public int add(int fileSize)
    {
        if(fileSize <= remainingSize)
        {
            remainingSize = remainingSize - fileSize;
        }
        files[i] = fileSize;
        i++;
        return 0;
    }

    //Comparable method
    public int compareTo(Disk o)
    {
        return this.remainingSize - o.remainingSize;
    }
    
}
