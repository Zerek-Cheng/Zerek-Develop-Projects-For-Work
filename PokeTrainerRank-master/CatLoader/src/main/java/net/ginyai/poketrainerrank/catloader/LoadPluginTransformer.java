package net.ginyai.poketrainerrank.catloader;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ASM5;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

public class LoadPluginTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (name.equals("org.bukkit.plugin.SimplePluginManager")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            ClassVisitor visitor = new ClassVisitor(ASM5, writer) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    if (name.equals("loadPlugins") && desc.equals("(Ljava/io/File;)[Lorg/bukkit/plugin/Plugin;")) {
                        return new MethodVisitor(ASM5, super.visitMethod(access, name, desc, signature, exceptions)) {
                            @Override
                            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                                super.visitMethodInsn(opcode, owner, name, desc, itf);
                                if (name.equals("listFiles") && owner.equals("java/io/File") && desc.equals("()[Ljava/io/File;")) {
                                    super.visitMethodInsn(
                                            INVOKESTATIC,
                                            "net/ginyai/poketrainerrank/catloader/PokeTrainerRankCatLoader",
                                            "getPluginFiles",
                                            "([Ljava/io/File;)[Ljava/io/File;",
                                            false
                                    );
                                }
                            }
                        };
                    } else {
                        return super.visitMethod(access, name, desc, signature, exceptions);
                    }
                }
            };
            reader.accept(visitor, 0);
            return writer.toByteArray();
        } else {
            return basicClass;
        }
    }


}
