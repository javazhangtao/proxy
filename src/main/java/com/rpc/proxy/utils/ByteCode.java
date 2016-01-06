package com.rpc.proxy.utils;

import org.objectweb.asm.*;

import java.lang.reflect.Method;

/**
 * Created by zhangtao on 2015/12/28.
 * 接口实现类字节码生成
 */
public class ByteCode implements Opcodes{

    private Class sourceInterFace;

    public <T> Class<T> createClass() throws Exception{
        try {
            String interFaceName=this.sourceInterFace.getSimpleName();//接口名称
            String interFacePath=this.sourceInterFace.getName().replaceAll("\\.","/");//接口路径    .替换/
            String className=interFaceName+"Impl";//实现类名称
            String classPath=interFacePath.replaceAll(interFaceName,"impl/"+className);
            AnnotationVisitor av=null ;
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER,classPath, null, "java/lang/Object", new String[]{interFacePath});
            structure(cw);
            Method[] methods=this.sourceInterFace.getMethods();
            for (Method method:methods) {
                method(cw,method);
            }
            cw.visitEnd();
            MyLoader loader=new MyLoader();
            byte[] code=cw.toByteArray();
            return loader.defineClassByName(classPath.replaceAll("/","\\."),code,0,code.length);
        } catch (Exception e) {
            throw(e);
        }
    }

    /**
     * 创建构造函数
     * @throws Exception
     */
    private void structure(ClassWriter cw) throws Exception{
        try {
            MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        } catch (Exception e) {
            throw(e);
        }
    }

    /**
     * 创建方法
     * @param cw
     * @param method
     * @throws Exception
     */
    private void method(ClassWriter cw , Method method) throws Exception{
        String methodName = method.getName();//方法名称
        String methodDesc = Type.getMethodDescriptor(method);//方法签名（字节码描述）
        Type methodReturn = Type.getReturnType(method);//方法返回类型
        String[] exceptions=methodExceptions(method);//方法异常
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, methodName, methodDesc, null, (exceptions.length>0)?exceptions:null);
        mv.visitCode();
        if(null==method.getGenericReturnType() || method.getGenericReturnType().getTypeName().equals("void")){
            mv.visitInsn(RETURN);
        }else{
            mv.visitInsn(ACONST_NULL);
            mv.visitInsn(ARETURN);
        }
        mv.visitMaxs(0, 0);
        mv.visitEnd();

    }

    /**
     * 方法异常
     * @param method
     * @return
     * @throws Exception
     */
    private String[] methodExceptions(Method method) throws Exception {
        try {
            Class<?>[] exceptionTypes=method.getExceptionTypes();//方法异常
            String[] exceptions=new String[exceptionTypes.length];
            for (int i=0;i<exceptionTypes.length;i++){
                exceptions[i]=exceptionTypes[i].getName().replaceAll("\\.","/");
            }
            return exceptions;
        } catch (Exception e) {
            throw(e);
        }
    }

    public Class<?> getSourceInterFace() {
        return sourceInterFace;
    }

    public void setSourceInterFace(Class<?> sourceInterFace) {
        this.sourceInterFace = sourceInterFace;
    }

    class MyLoader extends ClassLoader{
        @SuppressWarnings("unchecked")
        public  Class defineClassByName(String name,byte[] b,int off,int len){
            return super.defineClass(name,b, off, len);
        }
    }
}
