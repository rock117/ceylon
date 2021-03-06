package org.eclipse.ceylon.compiler.java.codegen;

import org.eclipse.ceylon.langtools.tools.javac.tree.JCTree.JCAnnotation;
import org.eclipse.ceylon.langtools.tools.javac.tree.JCTree.JCExpression;
import org.eclipse.ceylon.langtools.tools.javac.tree.JCTree.JCIdent;
import org.eclipse.ceylon.langtools.tools.javac.tree.JCTree.JCPrimitiveTypeTree;
import org.eclipse.ceylon.langtools.tools.javac.util.List;

/**
 * A type expression and {@code @TypeInfo} and/or nullability annotations
 */
public class TransformedType {
    
    private final JCExpression typeExpression;
    private final JCAnnotation typeInfo;
    private final JCAnnotation nullability;
    
    public TransformedType(JCExpression typeExpression, JCAnnotation typeInfo, JCAnnotation nullability) {
        super();
        this.typeExpression = typeExpression;
        this.typeInfo = typeInfo;
        this.nullability = nullability;
    }
    
    public TransformedType(JCExpression typeExpression, JCAnnotation typeInfo) {
        this(typeExpression, typeInfo, null);
    }
    
    public TransformedType(JCExpression typeExpression) {
        this(typeExpression, null);
    }

    public JCExpression getTypeExpression() {
        return typeExpression;
    }

    public JCAnnotation getTypeInfo() {
        return typeInfo;
    }

    public JCAnnotation getNullability() {
        return nullability;
    }
    
    /**
     * The {@code @TypeInfo}, {@code @Nullable}, and/or {@code @NonNull} 
     * annotations.
     */
    public List<JCAnnotation> getTypeAnnotations() {
        List<JCAnnotation> result = List.nil();
        if (typeInfo != null) {
            result = result.prepend(typeInfo);
        }
        if (nullability != null && !isPrimitiveOrVoid()) {
            result = result.prepend(nullability);
        }
        return result;
    }

    
    public boolean isPrimitive() {
        return isPrimitive(typeExpression);
    }
    
    public static boolean isPrimitive(JCExpression typeExpression) {
        if (typeExpression instanceof JCIdent) {
            JCIdent ident = (JCIdent)typeExpression;
            if (ident.sym != null
                    && ident.sym.type.isPrimitive()) {
                return true;
            }
        } else if (typeExpression instanceof JCPrimitiveTypeTree) {
            return true;
        }
        return false;
    }
    
    public boolean isPrimitiveOrVoid() {
        return isPrimitiveOrVoid(typeExpression);
    }
    
    public static boolean isPrimitiveOrVoid(JCExpression typeExpression) {
        if (typeExpression instanceof JCIdent) {
            JCIdent ident = (JCIdent)typeExpression;
            if (ident.sym != null
                    && ident.sym.type.isPrimitiveOrVoid()) {
                return true;
            }
        } else if (typeExpression instanceof JCPrimitiveTypeTree) {
            return true;
        }
        return false;
    }
}
