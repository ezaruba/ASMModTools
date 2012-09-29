package com.dna.asm.samples;

import com.dna.asm.transformers.MainTransform;

/**
 * An updater that transforms TheProgram.java
 * @author trDna
 */
public class Updater {
    public static void main(String[]args){
        MainTransform m = new MainTransform();
        m.runTransform();
    }
}
