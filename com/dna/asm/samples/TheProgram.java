package com.dna.asm.samples;

/**
 * Created with IntelliJ IDEA.
 * User: trDna
 * Date: 9/28/12
 * Time: 7:28 AM
 */
public class TheProgram { // Implement an interface (for an accessor method).
    public static String theSecretString = "Hello. My name is Bob."; //Get this method.

    public static void main(String[]args){ //Pretend its some sort of epic program. It's in a jar called "EpicProgram".
        System.out.println("TheProgram's string value = " + theSecretString);
    }

}