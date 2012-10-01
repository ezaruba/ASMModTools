package com.dna.asm.transforms;

import com.dna.asm.generic.AbstractClassTransform;
import org.objectweb.asm.Type;

/**
 * @author trDna
 */

public class MainTransform extends AbstractClassTransform {


    @Override
    public void runTransform() {
        setup("EpicProgram.jar", "TheProgram.class");
        start();
        addGetter("theSecretString", Type.getType(String.class).getDescriptor(), "com/dna/asm/Accessor", "getSecretString", ALOAD, ARETURN);
        changeSuperClass("com/dna/asm/accessors/SuperClass");
        finish();
    }
}
