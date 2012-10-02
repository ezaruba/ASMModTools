package com.dna.asm.tree.adapters;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * Alternative to adapters. Uses the Tree API instead of the Core API.
 * @author trDna
 */
public class AddGetterTransformationAdapter extends ClassVisitor implements Opcodes {

    private String fieldName = null;
    private String getterName = null;
    private String fieldDescriptor = null;
    private String signature = null;
    private String targetClazz = null;

    private ClassNode cn = null;

    private boolean isFieldPresent = false, isInterface = false, isMethodPresent = false;

    private int retInsn, varInsn;

    public AddGetterTransformationAdapter(final ClassNode cn, final String targetClazz, final String fieldName, final String fieldDescriptor, final String getterName, final int varInsn, final int retInsn) {
        super(ASM4, cn);
        this.fieldName = fieldName;
        this.getterName = getterName;
        this.targetClazz = targetClazz;
        this.retInsn = retInsn;
        this.varInsn = varInsn;
        this.fieldDescriptor = fieldDescriptor;
        this.cn = cn;
    }

    @Override
    public void visitEnd(){
        for(FieldNode f : cn.fields){
            if(f.name.equals(fieldName) && f.desc.equals(fieldDescriptor)){
                isFieldPresent = true;
                fieldName = f.name;
                break;
            }
        }

        for(MethodNode mn : cn.methods){
            if(mn.name.equals(getterName)){
                isMethodPresent = true;
                break;
            }
        }

        if(isFieldPresent && !isMethodPresent){
            MethodNode mn = new MethodNode(ACC_PUBLIC, getterName, "()"+fieldDescriptor, signature, null);
            mn.instructions.add(new VarInsnNode(varInsn, 0));
            mn.instructions.add(new FieldInsnNode(GETFIELD, targetClazz, getterName, fieldDescriptor));
            mn.instructions.add(new InsnNode(retInsn));
            cn.methods.add(mn);
        }

    }
}
