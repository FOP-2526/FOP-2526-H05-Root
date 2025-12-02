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
                boolean noop = false;  // Remove calls to System.out.*

                @Override
                public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                    if (opcode == Opcodes.GETSTATIC && owner.equals("java/lang/System") && name.equals("out")) {
                        // Start no-ops
                        this.noop = true;
                    } else {
                        super.visitFieldInsn(opcode, owner, name, descriptor);
                    }
                }

                @Override
                public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                    // Stop no-ops
                    if (opcode == Opcodes.INVOKEVIRTUAL && owner.equals("java/io/PrintStream") && name.startsWith("print")) {
                        this.noop = false;
                    } else if (!this.noop) {
                        // Replace calls to {GameSettings, BasicGameSettings}.getMineableAt(...)
                        if (((opcode == Opcodes.INVOKEVIRTUAL && owner.equals("h05/base/game/BasicGameSettings")) ||
                            (opcode == Opcodes.INVOKEINTERFACE && owner.equals("h05/base/game/GameSettings"))) &&
                            name.equals("getMineableAt") && descriptor.equals("(II)Lh05/mineable/Mineable;")) {
                            super.visitMethodInsn(opcode, owner, "getLootAt", descriptor, isInterface);
                        } else {
                            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                        }
                    }
                }

                @Override
                public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
                    if (!this.noop) super.visitFrame(type, numLocal, local, numStack, stack);
                }

                @Override
                public void visitInsn(int opcode) {
                    if (!this.noop) super.visitInsn(opcode);
                }

                @Override
                public void visitIntInsn(int opcode, int operand) {
                    if (!this.noop) super.visitIntInsn(opcode, operand);
                }

                @Override
                public void visitVarInsn(int opcode, int varIndex) {
                    if (!this.noop) super.visitVarInsn(opcode, varIndex);
                }

                @Override
                public void visitTypeInsn(int opcode, String type) {
                    if (!this.noop) super.visitTypeInsn(opcode, type);
                }

                @Override
                public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
                    if (!this.noop) super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
                }

                @Override
                public void visitJumpInsn(int opcode, Label label) {
                    if (!this.noop) super.visitJumpInsn(opcode, label);
                }

                @Override
                public void visitLabel(Label label) {
                    if (!this.noop) super.visitLabel(label);
                }

                @Override
                public void visitLdcInsn(Object value) {
                    if (!this.noop) super.visitLdcInsn(value);
                }

                @Override
                public void visitIincInsn(int varIndex, int increment) {
                    if (!this.noop) super.visitIincInsn(varIndex, increment);
                }

                @Override
                public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
                    if (!this.noop) super.visitTableSwitchInsn(min, max, dflt, labels);
                }

                @Override
                public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
                    if (!this.noop) super.visitLookupSwitchInsn(dflt, keys, labels);
                }

                @Override
                public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
                    if (!this.noop) super.visitMultiANewArrayInsn(descriptor, numDimensions);
                }

                @Override
                public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
                    if (!this.noop) super.visitTryCatchBlock(start, end, handler, type);
                }

                @Override
                public void visitLocalVariable(String name, String descriptor, String signature, Label start, Label end, int index) {
                    if (!this.noop) super.visitLocalVariable(name, descriptor, signature, start, end, index);
                }
            };
        }
    }
}
