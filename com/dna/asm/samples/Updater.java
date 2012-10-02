package com.dna.asm.samples;

import com.dna.asm.core.transformers.MainTransform;

import java.net.MalformedURLException;

/**
 * Transforms TheProgram.java by injecting an accessor method.
 * Displays the accessor method's value.
 *
 * Uses the Core API.
 *
 * @author trDna
 */
public class Updater {
    public static void main(String[]args) throws MalformedURLException {
        MainTransform m = new MainTransform();
        m.runTransform();
    }
}
