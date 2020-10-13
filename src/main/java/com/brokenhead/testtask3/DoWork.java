/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brokenhead.testtask3;

import java.io.File;

/**
 *
 * @author brokenhead
 */
public class DoWork {
    private final static String GENERATED_SOURCE_PATH = "GeneratedFiles/source1.txt";
    private final static String FINAL_FILE_PATH = "SortingFiles/AppOut.txt";
    private final StringGenerator strg;
    private final Sorty srt;
    
    public DoWork() {
        strg = new StringGenerator();
        srt = new Sorty();
    }
    
    public void doWorkMain() {
        File generatedFile = new File(GENERATED_SOURCE_PATH);
        File finalOutFile = new File(FINAL_FILE_PATH);
        strg.generateStrings(generatedFile);
        srt.splitFileByMemoryLimit(1024*1024, generatedFile); // 1 Mb memory limit
        srt.doMergeSort();
    }
}
