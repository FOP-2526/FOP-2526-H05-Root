package h05;

import org.objectweb.asm.*;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class CallTransformer implements ClassTransformer {

    @Override
    public String getName() {
        return "CallTransformer";
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        reader.accept(new CV(writer), 0);
    }

    private static class CV extends ClassVisitor {

        protected CV(ClassVisitor classVisitor) {
            super(Opcodes.ASM9, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {

                @Override
                public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                    // Replace calls to {GameSettings, BasicGameSettings}.getMineableAt(...)
                    if (((opcode == Opcodes.INVOKEVIRTUAL && owner.equals("h05/base/game/BasicGameSettings")) ||
                        (opcode == Opcodes.INVOKEINTERFACE && owner.equals("h05/base/game/GameSettings"))) &&
                        name.equals("getMineableAt") && descriptor.equals("(II)Lh05/mineable/Mineable;")) {
                        super.visitMethodInsn(opcode, owner, "getLootAt", descriptor, isInterface);
                    } else {
                        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                    }
                }
            };
        }
    }
}
