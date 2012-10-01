package com.dna.asm.samples;

import com.dna.asm.transforms.MainTransform;

import java.net.MalformedURLException;

/**
 * Transforms TheProgram.java by injecting an accessor method.
 * Displays the accessor method's value.
 *
 * @author trDna
 */
public class Updater {
    public static void main(String[]args) throws MalformedURLException {
        MainTransform m = new MainTransform();
        m.runTransform();
    }
}
