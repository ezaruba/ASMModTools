package com.dna.asm.samples;

import com.dna.asm.generic.MyClassLoader;
import org.objectweb.asm.*;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Reads any given class and prints it in bytecode.
 * @author trDna
 */

public class ClassPrinter extends ClassVisitor {

    String source = null;

    public static void main(String[]args){
        ClassWriter cw = new ClassWriter(0);
        PrintWriter pw = new PrintWriter(System.out);
        TraceClassVisitor tcv = new TraceClassVisitor(cw, pw);
        tcv.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE,
                "com/dna/asm/Comparable", null, "java/lang/Object",
                null);
        tcv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "LESS", "I",
                null, new Integer(-1)).visitEnd();
        tcv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        tcv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();
        tcv.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd();
        byte[] b = cw.toByteArray();
        MyClassLoader mcl = new MyClassLoader();
        Class c = mcl.defineClass("com.dna.asm.Comparable", b);
        ClassReader cr = new ClassReader(b);
        System.out.println(cr);
    }
    public ClassPrinter(final String clazz) throws IOException {
        super(Opcodes.ASM4);

    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println("////~ Generation start ~////");
        System.out.println(name + " extends " + superName + (interfaces == null ? "" : " implements " + interfaces) + " {");
    }

    public void visitSource(String source, String debug) {
        this.source = source;
    }

    public void visitOuterClass(String owner, String name, String desc) {

    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    public void visitAttribute(Attribute attr) {
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        System.out.println("      [InnerClass] " + name + " " + outerName + " " + innerName + " " + access);
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println("[Field]" + " " + desc + " " + name + " == " + value);
        return null;
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("[Method]"+ " " + name + desc);
        return null;
    }

    public void visitEnd() {
        System.out.println("}");
        System.out.println("////~ Generation End ~////");
        System.out.println(source == null ? "Unknown Source":"Source: " + source);
    }



}