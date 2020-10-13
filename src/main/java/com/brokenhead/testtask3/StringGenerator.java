package com.brokenhead.testtask3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author brokenhead
 */
public class StringGenerator {
    
    private final static HashMap<Integer, Character> symbolsMap = new HashMap<Integer, Character>();
    
    public StringGenerator() {
        symbolsMap.put(0, 'a');
        symbolsMap.put(1, 'b');
        symbolsMap.put(2, 'c');
        symbolsMap.put(3, 'd');
        symbolsMap.put(4, 'e');
        symbolsMap.put(5, 'f');
        symbolsMap.put(6, 'g');
        symbolsMap.put(7, 'h');
        symbolsMap.put(8, 'i');
        symbolsMap.put(9, 'j');
        symbolsMap.put(10, 'k');
        symbolsMap.put(11, 'l');
        symbolsMap.put(12, 'm');
        symbolsMap.put(13, 'n');
        symbolsMap.put(14, 'o');
        symbolsMap.put(15, 'p');
        symbolsMap.put(16, 'q');
        symbolsMap.put(17, 'r');
        symbolsMap.put(18, 's');
        symbolsMap.put(19, 't');
        symbolsMap.put(20, 'u');
        symbolsMap.put(21, 'v');
        symbolsMap.put(22, 'w');
        symbolsMap.put(23, 'x');
        symbolsMap.put(24, 'y');
        symbolsMap.put(25, 'z');
    }
    
    public void generateStrings(File writeIn) {
        Random rnd = new Random();
        int counter = 0;
        int boundary = 800000;
        try (BufferedWriter bwr = new BufferedWriter(new FileWriter(writeIn))) {
            // generate string for source file source1.txt
            while(counter < boundary) { // adding complete string
                StringBuilder str = new StringBuilder();
                int stringSize = rnd.nextInt(12) + 3; // 3-15 symbols in string
                int sizeCounter = 0;
                while(sizeCounter <= stringSize) {
                    char randomChar = symbolsMap.get(rnd.nextInt(26));
                    str.append(randomChar);
                    sizeCounter++;
                }
                str.append("\n");
                bwr.write(str.toString());
                counter++ ;
            } 
        } catch (FileNotFoundException exc) {
            System.out.println("FileNotFoundException!");
        } catch (IOException iox) {
            System.out.println("IOException!");
        }
    }
}
