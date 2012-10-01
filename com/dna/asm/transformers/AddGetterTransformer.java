package com.dna.asm.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Alternative to adapters. Uses the Tree API instead of the Core API.
 * @author trDna
 */
public class AddGetterTransformer extends ClassTransformer implements Opcodes {

    private String fieldName = null;
    private String getterName = null;
    private String fieldDescriptor = null;
    private String signature = null;
    private String targetClazz = null;

    private boolean isFieldPresent = false, isInterface = false, isMethodPresent = false;

    public AddGetterTransformer(final ClassTransformer ct, final String fieldName, final String fieldDescriptor, final String getterName, final String targetClazz) {
        super(ct);
        this.fieldName = fieldName;
        this.getterName = getterName;
        this.fieldDescriptor = fieldDescriptor;
        this.targetClazz = targetClazz;
    }

    @Override
    public void transform(ClassNode cn){
        for(FieldNode f : cn.fields){
            if(f.name.equals(fieldName)){
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
            cn.methods.add(new MethodNode(ACC_PUBLIC, getterName, "()"+fieldDescriptor, signature, null));
        }

        super.transform(cn);
    }
}
