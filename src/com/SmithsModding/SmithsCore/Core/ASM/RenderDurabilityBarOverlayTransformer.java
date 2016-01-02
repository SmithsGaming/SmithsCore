package com.SmithsModding.SmithsCore.Core.ASM;

import net.minecraft.launchwrapper.*;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/**
 * Created by Marc on 30.12.2015.
 */
public class RenderDurabilityBarOverlayTransformer implements IClassTransformer {

    @Override
    public byte[] transform (String s, String s1, byte[] bytes) {
        if (!s1.equals("net.minecraft.client.renderer.entity.RenderItem"))
            return bytes;

        ClassNode node = new ClassNode();
        ClassReader reader = new ClassReader(bytes);

        reader.accept(node, 0);

        for (MethodNode methodNode : node.methods) {
            if (!methodNode.name.equals("func_180453_a") && !methodNode.name.equals("renderItemOverlayIntoGUI"))
                continue;

            InsnList list = new InsnList();

            list.add(new VarInsnNode(Opcodes.ALOAD, 1));
            list.add(new VarInsnNode(Opcodes.ALOAD, 2));
            list.add(new VarInsnNode(Opcodes.ILOAD, 3));
            list.add(new VarInsnNode(Opcodes.ILOAD, 4));
            list.add(new VarInsnNode(Opcodes.ALOAD, 5));
            list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/SmithsModding/SmithsCore/Client/Events/OverlayRendererEvent", "onPreRender", "(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", false));

            methodNode.instructions.insert(list);
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        node.accept(writer);

        return writer.toByteArray();
    }
}
