package org.eclipse.ceylon.langtools.classfile;

import java.io.IOException;

/**
 * See JVMS, section 4.8.15.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class ModuleTarget_attribute extends Attribute {
    ModuleTarget_attribute(ClassReader cr, int name_index, int length) throws IOException {
        super(name_index, length);
        target_platform_index = cr.readUnsignedShort();
    }

    @Override
    public <R, D> R accept(Visitor<R, D> visitor, D data) {
        return visitor.visitModuleTarget(this, data);
    }

    public final int target_platform_index;
}