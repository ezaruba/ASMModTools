package com.dna.asm.transforms;

import com.dna.asm.generic.AbstractClassTransform;
import org.objectweb.asm.Type;

/**
 * Created with IntelliJ IDEA.
 * User: trDna
 * Date: 9/29/12
 * Time: 4:58 PM
 */
public class MainTransform extends AbstractClassTransform {


    @Override
    public void runTransform() {
        setup("EpicProgram.jar", "DummyClass.class");
        start();
        addGetter("theSecretString", Type.getType(String.class).getDescriptor(), "com/dna/asm/Accessor", "getSecretString", ALOAD, ARETURN);
        changeSuperClass("com/dna/asm/accessors/SuperClass");
        finish();
    }
}
