package com.dna.asm.adapters;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author trDna
 */

public class ChangeSuperClassAdapter extends ClassVisitor implements Opcodes {

    private String superClass;

    public ChangeSuperClassAdapter(final ClassVisitor cv, final String superClass){
        super(ASM4, cv);
        this.superClass = superClass;
    }

    @Override
    public void visit(int version, int access, java.lang.String name, java.lang.String signature, java.lang.String superName, java.lang.String[] interfaces) {
        cv.visit(version, access, name, signature, superClass, interfaces);
    }

}
