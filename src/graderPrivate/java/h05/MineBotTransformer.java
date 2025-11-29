package h05;

import org.objectweb.asm.*;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class MineBotTransformer implements ClassTransformer {

    @Override
    public String getName() {
        return "MineBotTransformer";
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        if (reader.getClassName().equals("h05/entity/MineBot")) {
            reader.accept(new CV(writer), 0);
        } else {
            reader.accept(writer, 0);
        }
    }

    private static class CV extends ClassVisitor {

        protected CV(ClassVisitor classVisitor) {
            super(Opcodes.ASM9, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
            if (name.equals("<init>")) {
                return new MethodVisitor(Opcodes.ASM9, mv) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                        if (opcode == Opcodes.INVOKEVIRTUAL &&
                                owner.equals("h05/entity/MineBot") &&
                                name.equals("getVision") &&
                                descriptor.equals("(II)[Ljava/awt/Point;")) {
                            super.visitInsn(Opcodes.POP);
                            super.visitInsn(Opcodes.POP);
                            super.visitInsn(Opcodes.POP);
                            super.visitInsn(Opcodes.ICONST_0);
                            super.visitTypeInsn(Opcodes.ANEWARRAY, "java/awt/Point");
                        } else {
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    }
                };
            } else {
                return mv;
            }
        }
    }
}
