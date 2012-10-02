package com.dna.asm.core.generic;

import com.dna.asm.core.adapters.AddGetterAdapter;
import com.dna.asm.core.adapters.AddInterfaceAdapter;
import com.dna.asm.core.adapters.ChangeSuperClassAdapter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * A set of manipulation utilities. See MainTransform.java on how to use.
 * All methods that have a String parameter must be expressed in type descriptor form.
 *
 * Uses the Core API.
 *
 * @author trDna
 */

public abstract class AbstractClassTransform implements Opcodes{

    private static String clazz;
    private static String theJar; // JarFile dir goes here.

    private static JarFile jf;
    private static JarEntry entry;
    private static ClassReader cr = null;
    private ClassWriter cw = null;

    private ClassVisitor changer = null;  // ChangeSuperAdapter
    private ClassVisitor interfaceAdder = null; // AddInterfaceAdapter
    private ClassVisitor adder = null; // AddGetterAdapter

    private ClassVisitor combinedAdapter = null; //Mixture of all adapters

    private LinkedList <ClassVisitor> adapterList = new LinkedList<ClassVisitor>();

    public abstract void runTransform();

    public void setup(final String theJar, final String clazz){
        this.theJar = theJar;
        this.clazz = clazz;
    }

    public void start(){
        try{
            jf = new JarFile(theJar);
            entry = new JarEntry(clazz);
            Enumeration<JarEntry> en = jf.entries();

            cr = new ClassReader(jf.getInputStream(entry));
            cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            cr.accept(cw, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void addGetter(final String targetVar, final String descriptor, final String intrFace, final String getterName, final int varInsn, final int retInsn) {
        try{
            interfaceAdder = new AddInterfaceAdapter(combinedAdapter == null ? cw : combinedAdapter, intrFace);
            adder = new AddGetterAdapter(interfaceAdder, targetVar, descriptor, getterName, entry.getName(), varInsn, retInsn);
            combinedAdapter = adder;

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void addGetter(final String targetVar, final String descriptor, final String getterName, final int varInsn, final int retInsn) {
        try{
            AddGetterAdapter adder = new AddGetterAdapter(combinedAdapter == null ? cw : combinedAdapter, targetVar, descriptor, getterName, entry.getName(), varInsn, retInsn);  //adds filter to CW
            combinedAdapter = adder;

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void changeSuperClass(final String superClass){
        try{
            changer = new ChangeSuperClassAdapter(combinedAdapter == null ? cw : combinedAdapter, superClass);
            combinedAdapter = changer;

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void finish(){
        try{
            cr.accept(combinedAdapter == null ? cw : combinedAdapter, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

            byte[] outStream = cw.toByteArray();
            File outDir = new File("Inject Tests ASM");  // Change this part to whatever you like. Remember to output the data stream.
            outDir.mkdirs();
            DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(outDir, entry.getName())));
            out.write(outStream);
            out.flush();
            out.close();

        }catch (Exception ex){}
    }
}

