package com.dna.asm.generic;

import com.dna.asm.adapters.AddGetterAdapter;
import com.dna.asm.adapters.AddInterfaceAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * A set of manipulation utilities. All transforms must begin with <tt>public void setClass(final String Clazz)</tt>.
 * All methods that have a String parameter must be expressed in type descriptor form.
 * @author trDna
 */
public abstract class AbstractClassTransform implements Opcodes{

    private static String clazz;
    private static String theJar; // JarFile dir goes here.

    private static JarFile jf;
    private static JarEntry entry;
    private static ClassReader cr;
    private static ClassWriter cw;

    public abstract void runTransform();

    public void setup(final String theJar, final String clazz){
        this.theJar = theJar;
        this.clazz = clazz;
    }

    public void addGetter(final String targetVar, final String descriptor, final String intrFace, final String getterName) {
        try{
            jf = new JarFile(theJar);
            entry = new JarEntry(clazz);

            Enumeration<JarEntry> en = jf.entries();

            cr = new ClassReader(jf.getInputStream(entry));
            cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            cr.accept(cw, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

            AddInterfaceAdapter interfaceAdder = new AddInterfaceAdapter(cw, intrFace);
            AddGetterAdapter adder = new AddGetterAdapter(interfaceAdder, targetVar, descriptor, getterName, entry.getName());
            cr.accept(adder, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void addGetter(final String targetVar, final String descriptor, final String getterName) {
        try{
            jf = new JarFile(theJar);
            entry = new JarEntry(clazz);
            cr = new ClassReader(jf.getInputStream(entry));
            cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            cr.accept(cw, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

            AddGetterAdapter adder = new AddGetterAdapter(cw, targetVar, descriptor, getterName, entry.getName());
            cr.accept(adder, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void finish(){
        try{
            byte[] outStream = cw.toByteArray();
            File outDir = new File("Inject Tests");
            outDir.mkdirs();
            DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(outDir, entry.getName())));
            out.write(outStream);
            out.flush();
            out.close();
        }catch (Exception ex){}
    }
}

