package org.eclipse.ceylon.compiler.java.runtime.metamodel.meta;

import java.util.List;

import org.eclipse.ceylon.compiler.java.Util;
import org.eclipse.ceylon.compiler.java.metadata.Ceylon;
import org.eclipse.ceylon.compiler.java.metadata.Ignore;
import org.eclipse.ceylon.compiler.java.metadata.Name;
import org.eclipse.ceylon.compiler.java.metadata.SatisfiedTypes;
import org.eclipse.ceylon.compiler.java.metadata.TypeInfo;
import org.eclipse.ceylon.compiler.java.metadata.TypeParameter;
import org.eclipse.ceylon.compiler.java.metadata.TypeParameters;
import org.eclipse.ceylon.compiler.java.metadata.Variance;
import org.eclipse.ceylon.compiler.java.runtime.metamodel.Metamodel;
import org.eclipse.ceylon.compiler.java.runtime.metamodel.decl.ClassDeclarationImpl;
import org.eclipse.ceylon.compiler.java.runtime.model.ReifiedType;
import org.eclipse.ceylon.compiler.java.runtime.model.TypeDescriptor;

import org.eclipse.ceylon.compiler.java.runtime.metamodel.meta.ClassImpl;
import org.eclipse.ceylon.compiler.java.runtime.metamodel.meta.ClassInitializerConstructor;
import org.eclipse.ceylon.compiler.java.runtime.metamodel.meta.MemberClassImpl;
import org.eclipse.ceylon.compiler.java.runtime.metamodel.meta.MemberClassInitializerConstructor;
import org.eclipse.ceylon.compiler.java.runtime.metamodel.meta.MemberImpl;

import ceylon.language.Map;
import ceylon.language.Sequential;
import ceylon.language.empty_;
import ceylon.language.meta.declaration.CallableConstructorDeclaration;
import ceylon.language.meta.model.CallableConstructor;
import ceylon.language.meta.model.ClassModel;
import ceylon.language.meta.model.MemberClass;
import ceylon.language.meta.model.MemberClassCallableConstructor;


@Ceylon(major=8)
@org.eclipse.ceylon.compiler.java.metadata.Class
@SatisfiedTypes("ceylon.language.meta.model::MemberClassCallableConstructor<Container,Type,Arguments>")
@TypeParameters({
    @TypeParameter(value = "Container", variance = Variance.IN),
    @TypeParameter(value = "Type", variance = Variance.OUT),
    @TypeParameter(value = "Arguments", variance = Variance.IN)
})
public class MemberClassInitializerConstructor<Container, Type, Arguments extends Sequential<? extends Object>>
        extends MemberImpl<Container, ceylon.language.meta.model.CallableConstructor<? extends Type, ? super Arguments>> 
        implements MemberClassCallableConstructor<Container, Type, Arguments>, ReifiedType {

    //protected final FreeCallableConstructor declaration;
    //private Reference appliedFunction;
    @Ignore
    protected TypeDescriptor $reifiedType;
    @Ignore
    protected TypeDescriptor $reifiedArguments;
    
    //private Map<? extends ceylon.language.meta.declaration.TypeParameter, ? extends ceylon.language.meta.model.Type<?>> typeArguments;
    //private Map<? extends ceylon.language.meta.declaration.TypeParameter, ? extends Sequence<? extends Object>> typeArgumentWithVariances;
    //private Sequential<? extends ceylon.language.meta.model.Type<? extends Object>> parameterTypes;
    final MemberClassImpl<Container, Type, ?> clazz;
    
    final Sequential<? extends ceylon.language.meta.model.Type<? extends Object>> parameterTypes;
    
    @Ignore
    @Override
    public TypeDescriptor $getType$() {
        return TypeDescriptor.klass(MemberClassInitializerConstructor.class, super.$reifiedContainer, $reifiedType, $reifiedArguments);
    }
    
    @Ignore
    public MemberClassInitializerConstructor(TypeDescriptor $reifiedContainer,
            TypeDescriptor $reifiedType, TypeDescriptor $reifiedArguments,
            MemberClassImpl<Container, Type, ?> clazz) {
        super($reifiedContainer, $reifiedType, clazz);
        this.clazz = clazz;
        this.$reifiedType = $reifiedType;
        this.$reifiedArguments = $reifiedArguments;
        
        //this.declaration = declaration;
        //this.typeArguments = Metamodel.getTypeArguments(declaration, appliedFunction);
        //this.typeArgumentWithVariances = Metamodel.getTypeArgumentWithVariances(declaration, appliedFunction);
        //this.closedType = Metamodel.getAppliedMetamodel(Metamodel.getFunctionReturnType(appliedFunction));
        // get a list of produced parameter types
        List<org.eclipse.ceylon.model.typechecker.model.Type>  parameterProducedTypes = Metamodel.getParameterProducedTypes(
                ((org.eclipse.ceylon.model.typechecker.model.Class)clazz.declaration.declaration).getParameterList().getParameters(), clazz.producedType);
        this.parameterTypes = Metamodel.getAppliedMetamodelSequential(parameterProducedTypes);
        
    }
    
    @Override
    @TypeInfo("ceylon.language.meta.model::Type<Type>")
    public MemberClass<Container, Type, ?> getType() {
        return clazz;
    }
    
    @Override
    public ClassModel<Type, ?> getContainer() {
        return clazz;
    }
    
    @Override
    public CallableConstructorDeclaration getDeclaration() {
        return Util.assertExists((CallableConstructorDeclaration)((ClassDeclarationImpl)clazz.declaration).getDefaultConstructor());
    }
    
    @Override
    public CallableConstructor<? extends Type, ? super Arguments> bind(@TypeInfo("ceylon.language::Object") @Name("container") Object instance) {
        return bindTo(instance);/*(CallableConstructor<? extends Type, ? super Arguments>) 
                Metamodel.bind(clazz, 
                        this.appliedFunction.getQualifyingType().getQualifyingType(), 
                        getContainer());*/
    }
    
    @Override
    @TypeInfo("ceylon.language::Map<ceylon.language.meta.declaration::TypeParameter,ceylon.language.meta.model::Type<ceylon.language::Anything>>")
    public ceylon.language.Map<? extends ceylon.language.meta.declaration.TypeParameter, ? extends ceylon.language.meta.model.Type<?>> getTypeArguments() {
        return clazz.getTypeArguments();
    }
    
    @Override
    public ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<?>> getTypeArgumentList() {
        return clazz.getTypeArgumentList();
    }

    @Override
    @TypeInfo("ceylon.language::Map<ceylon.language.meta.declaration::TypeParameter,[ceylon.language.meta.model::Type<ceylon.language::Anything>,ceylon.language.meta.declaration::Variance]>")
    public Map<? extends ceylon.language.meta.declaration.TypeParameter, ? extends ceylon.language.Sequence<? extends Object>> getTypeArgumentWithVariances() {
        return clazz.getTypeArgumentWithVariances();
    }
    
    @Override
    @TypeInfo("ceylon.language::Sequential<[ceylon.language.meta.model::Type<ceylon.language::Anything>,ceylon.language.meta.declaration::Variance]>")
    public ceylon.language.Sequential<? extends ceylon.language.Sequence<? extends Object>> getTypeArgumentWithVarianceList() {
        return clazz.getTypeArgumentWithVarianceList();
    }


    @Override
    protected CallableConstructor<Type, Arguments> bindTo(Object instance) {
        return new ClassInitializerConstructor<Type, Arguments>(
                (ClassImpl)clazz.bind(instance));
    }

    
    @Override
    @Ignore
    public CallableConstructor<? extends Type, ? super Arguments> $callvariadic$() {
        return $callvariadic$(empty_.get_());
    }
    
    @Override
    @Ignore
    public CallableConstructor<? extends Type, ? super Arguments> $callvariadic$(
            Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public CallableConstructor<? extends Type, ? super Arguments> $callvariadic$(
            Object arg0, Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public CallableConstructor<? extends Type, ? super Arguments> $callvariadic$(
            Object arg0, Object arg1, Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public CallableConstructor<? extends Type, ? super Arguments> $callvariadic$(
            Object arg0, Object arg1, Object arg2, Sequential<?> varargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public CallableConstructor<? extends Type, ? super Arguments> $callvariadic$(
            Object... argsAndVarargs) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Ignore
    public CallableConstructor<? extends Type, ? super Arguments> $callvariadic$(
            Object arg0) {
        return $callvariadic$(arg0, empty_.get_());
    }

    @Override
    @Ignore
    public CallableConstructor<? extends Type, ? super Arguments> $callvariadic$(
            Object arg0, Object arg1) {
        return $callvariadic$(arg0, arg1, empty_.get_());
    }

    @Override
    @Ignore
    public CallableConstructor<? extends Type, ? super Arguments> $callvariadic$(
            Object arg0, Object arg1, Object arg2) {
        return $callvariadic$(arg0, arg1, arg2, empty_.get_());
    }


    @TypeInfo("ceylon.language::Sequential<ceylon.language.meta.model::Type<ceylon.language::Anything>>")
    @Override
    public ceylon.language.Sequential<? extends ceylon.language.meta.model.Type<? extends Object>> getParameterTypes(){
        return parameterTypes;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 37 * result + getDeclaringType().hashCode();
        result = 37 * result + getDeclaration().hashCode();
        result = 37 * result + getTypeArguments().hashCode();
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this)
            return true;
        if(obj instanceof ceylon.language.meta.model.MemberClassCallableConstructor == false)
            return false;
        MemberClassInitializerConstructor<?, ?, ?> other = (MemberClassInitializerConstructor<?, ?, ?>) obj;
        return getDeclaration().equals(other.getDeclaration())
                && getDeclaringType().equals(other.getDeclaringType())
                && getTypeArguments().equals(other.getTypeArguments());
    }


    @Override
    public String toString() {
        return Metamodel.toTypeString(this);
    }
}
