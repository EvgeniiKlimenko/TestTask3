/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brokenhead.testtask3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * By default BufferedReader uses 8192 (8 KB) buffer size!
 *
 * @author brokenhead
 */
public class Sorty {

    int nameCount = 1;  // to avoid name collisions
    private final String BASIC_SPLIT_PATH = "SortingFiles/sortedPart";    // add number in the end
    private final String SPLITTED_FOLDER = "SortingFiles";
    private final String BASIC_MERGED_PATH = "SortingFiles/mergedFile";

    // split and sort
    public void splitFileByMemoryLimit(int memoryLimit, File fileToSplit) {
        final int memo = (memoryLimit - 50) / 2; // to be sure not to exceed the real limit and for primary sort
        final long sizeF = fileToSplit.length();
        System.out.println("File to split size: " + sizeF);
        //int nameCount = 1;
        long totalRead = 0L;    // compare this to sizeF
        long currentSize = 0L;  // for each file 
        try (BufferedReader bfr = new BufferedReader(new FileReader(fileToSplit))) {
            String lineRead = null;
            while (totalRead < sizeF) { // or while(true)
                System.out.println("Currently totalRead: " + totalRead);
                File filePart = new File(BASIC_SPLIT_PATH + nameCount);
                BufferedWriter bwr = new BufferedWriter(new FileWriter(filePart));
                List<String> stringList = new LinkedList();
                while (memo >= currentSize) { // each file work
                    lineRead = bfr.readLine();  // returns null if nothing to read
                    if (lineRead == null) { // end of file
                        break;
                    }
                    currentSize += (long) lineRead.getBytes("UTF-8").length;
                    stringList.add(lineRead);
                } // end reading file
                if (lineRead == null) {
                    System.out.println("***Caught null string!");
                    break;
                }
                sortIndividualFile(stringList);
                for (String strs : stringList) {
                    bwr.write(strs);
                    bwr.write("\n");
                }
                totalRead += currentSize; // Add currentSize ONLY AFTER while finished
                currentSize = 0L;
                bwr.close();
                stringList.clear();
                nameCount++;

                if (nameCount == 30) //fuse check
                {
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sorty.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sorty.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sortIndividualFile(List strs) {
        // Comparator with regExp.
        Comparator<String> comp = (str1, str2) -> {
            return str1.compareTo(str2);
        };
        strs.sort(comp);

    }

    public void doMergeSort() {
        File folder = new File(SPLITTED_FOLDER);
        File[] firstFiles = folder.listFiles();
        ArrayList<File> filesList = new ArrayList(Arrays.asList(firstFiles));
        recurciveMerge(filesList);
    }

    private void recurciveMerge(ArrayList<File> filesList) {
        int sizeOfList = filesList.size();
        System.out.println("-----> Size of list: " + sizeOfList);
        if (sizeOfList == 1) {
            return; // we have only one file with all strings. Sort is ended.
        }
        //ArrayList<File> newFilesList = new ArrayList<>();   // add merged files here

        for (int i = 1; i < sizeOfList; i += 2) {   // run through the list
            System.out.println("----> For iteration");
            File fileOne = filesList.get(i);
            File fileTwo = filesList.get(i - 1);
            System.out.println("-----> First file: " + fileOne.toString());
            System.out.println("-----> Second file: " + fileTwo.toString());
            File newFile = new File(BASIC_MERGED_PATH + nameCount);

            try (BufferedReader bfr1 = new BufferedReader(new FileReader(fileOne));
                    BufferedReader bfr2 = new BufferedReader(new FileReader(fileTwo));
                    BufferedWriter bwr = new BufferedWriter(new FileWriter(newFile))) {
                String firstFileLine = bfr1.readLine(); // initialize first lines
                String secondFileLine = bfr2.readLine();
                System.out.println("-----> First file line: " + firstFileLine);
                System.out.println("-----> Second file line: " + secondFileLine);
                int res = 0;

                while (true) {  //run through the files
                    if (secondFileLine == null) {   // if second file has ended first
                        while ((firstFileLine = bfr1.readLine()) != null) { // and write all what left
                            bwr.write(firstFileLine);
                            bwr.write("\n");
                        }
                        break;
                    } 
                    while ((res = firstFileLine.compareTo(secondFileLine)) == -1) { // while first is less than second
                        bwr.write(firstFileLine);
                        bwr.write("\n");
                        firstFileLine = bfr1.readLine(); // read next line from file 1

                        if (firstFileLine == null) { // if end of file...
                            bwr.write(secondFileLine); // write current secondFile line
                            bwr.write("\n");
                            while ((secondFileLine = bfr2.readLine()) != null) { // and write all what left
                                bwr.write(secondFileLine);
                                bwr.write("\n");
                            }
                            break;
                        }
                    }

                    if (secondFileLine == null) {   // works only when both file are ended
                        break;
                    } 
                        bwr.write(secondFileLine);
                        bwr.write("\n");
                        secondFileLine = bfr2.readLine();   // read next line from file 2
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Sorty.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Sorty.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                fileOne.delete();   // Remove used files
                fileTwo.delete();
            }
            nameCount++;
            //newFilesList.add(newFile);
        }// for() ends here
        
        File folder = new File(SPLITTED_FOLDER);
        File[] firstFiles = folder.listFiles();
        ArrayList<File> anotherList = new ArrayList(Arrays.asList(firstFiles));
        recurciveMerge(anotherList);   // recurcive call until size == 1
        System.out.println("***** End of method");
    }
}
