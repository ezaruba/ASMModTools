package com.dna.asm.adapters;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;

/**
 * @author trDna
 */

public class AddInterfaceAdapter extends ClassVisitor implements Opcodes {

    private String[] interfacesToAdd;

    public AddInterfaceAdapter(final ClassVisitor cv, final String... interfacesToAdd){
        super(ASM4, cv);
        this.interfacesToAdd = interfacesToAdd;
    }

    @Override
    public void visit(int version, int access, java.lang.String name, java.lang.String signature, java.lang.String superName, java.lang.String[] interfaces){
        ArrayList<String> interfaceList = new ArrayList<String>();

        if(interfaces != null){
            for(String i : interfaceList){
                interfaceList.add(i);
            }
        }

        for(String i : interfacesToAdd){
            interfaceList.add(i);
        }

        cv.visit(version, access, name, signature, superName, interfaceList.toArray(new String[interfaceList.size()]));

    }
}
