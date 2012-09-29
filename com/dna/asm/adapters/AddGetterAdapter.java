package com.dna.asm.adapters;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Adds a getter to a given class.
 * @author trDna
 */
public class AddGetterAdapter extends ClassVisitor implements Opcodes {

    private String fieldName = null;
    private String getterName = null;
    private String fieldDescriptor = null;
    private String signature = null;
    private String targetClazz = null;

    private boolean isFieldPresent = false, isInterface = false, isMethodPresent = false;


    public AddGetterAdapter(final ClassVisitor cv, final String fieldName, final String fieldDescriptor, final String getterName, final String targetClazz) {
        super(ASM4, cv);
        this.fieldName = fieldName;
        this.getterName = getterName;
        this.fieldDescriptor = fieldDescriptor;
        this.targetClazz = targetClazz;
    }

    @Override
    public FieldVisitor visitField(int access, java.lang.String name, java.lang.String desc, java.lang.String signature, java.lang.Object value){
        if(name.equals(fieldName) && desc.equals(fieldDescriptor)){
            isFieldPresent = true;
            fieldDescriptor = desc;
            this.signature = signature;
        }

        return null;
    }
    @Override
    public MethodVisitor visitMethod(int access, String name,
                                     String desc, String signature, String[] exceptions) {
        if (name.equals(getterName) && desc.equals(fieldDescriptor)) {
            isMethodPresent = true;
        }
        return null;
    }

    @Override
    public void visitEnd(){
        if(isFieldPresent && !isMethodPresent){
            MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, getterName, "()"+fieldDescriptor, signature, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, targetClazz, fieldName, fieldDescriptor);
            mv.visitInsn(ARETURN);
            mv.visitEnd();
            if(mv != null){
                mv.visitEnd();
            }
        } else {
            cv.visitEnd();
        }
        cv.visitEnd();
    }
}