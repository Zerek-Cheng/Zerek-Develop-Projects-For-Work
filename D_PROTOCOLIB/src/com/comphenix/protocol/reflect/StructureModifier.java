package com.comphenix.protocol.reflect;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolLogger;
import com.comphenix.protocol.error.PluginContext;
import com.comphenix.protocol.reflect.compiler.BackgroundCompiler;
import com.comphenix.protocol.reflect.instances.BannedGenerator;
import com.comphenix.protocol.reflect.instances.DefaultInstances;
import com.comphenix.protocol.reflect.instances.InstanceProvider;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class StructureModifier<TField> {
    protected Class targetType;
    protected Object target;
    protected EquivalentConverter<TField> converter;
    protected Class fieldType;
    protected List<Field> data = new ArrayList();
    protected Map<Field, Integer> defaultFields;
    protected Map<Class, StructureModifier> subtypeCache;
    protected boolean customConvertHandling;
    protected boolean useStructureCompiler;
    private static DefaultInstances DEFAULT_GENERATOR = null;

    private static DefaultInstances getDefaultGenerator() {
        try {
            List<InstanceProvider> providers = Lists.newArrayList();

            providers.add(new BannedGenerator(new Class[]{MinecraftReflection.getItemStackClass(), MinecraftReflection.getBlockClass()}));
            providers.addAll(DefaultInstances.DEFAULT.getRegistered());
            return DefaultInstances.fromCollection(providers);
        } catch (Exception e) {
            return null;
        }
    }

    public StructureModifier(Class targetType) {
        this(targetType, null, true);
    }

    public StructureModifier(Class targetType, boolean useStructureCompiler) {
        this(targetType, null, true, useStructureCompiler);
    }

    public StructureModifier(Class targetType, Class superclassExclude, boolean requireDefault) {
        this(targetType, superclassExclude, requireDefault, true);
    }

    public StructureModifier(Class targetType, Class superclassExclude, boolean requireDefault, boolean useStructureCompiler) {
        List<Field> fields = getFields(targetType, superclassExclude);
        Map<Field, Integer> defaults = requireDefault ? generateDefaultFields(fields) : new HashMap();

        initialize(targetType, Object.class, fields, defaults, null, new ConcurrentHashMap(), useStructureCompiler);
    }

    protected StructureModifier() {
    }

    protected void initialize(StructureModifier<TField> other) {
        initialize(other.targetType, other.fieldType, other.data, other.defaultFields, other.converter, other.subtypeCache, other.useStructureCompiler);
    }

    protected void initialize(Class targetType, Class fieldType, List<Field> data, Map<Field, Integer> defaultFields, EquivalentConverter<TField> converter, Map<Class, StructureModifier> subTypeCache) {
        initialize(targetType, fieldType, data, defaultFields, converter, subTypeCache, true);
    }

    protected void initialize(Class targetType, Class fieldType, List<Field> data, Map<Field, Integer> defaultFields, EquivalentConverter<TField> converter, Map<Class, StructureModifier> subTypeCache, boolean useStructureCompiler) {
        this.targetType = targetType;
        this.fieldType = fieldType;
        this.data = data;
        this.defaultFields = defaultFields;
        this.converter = converter;
        this.subtypeCache = subTypeCache;
        this.useStructureCompiler = useStructureCompiler;
    }

    public TField read(int fieldIndex)
            throws FieldAccessException {
        try {
            return (TField) readInternal(fieldIndex);
        } catch (FieldAccessException ex) {
            String plugin = PluginContext.getPluginCaller(ex);
            if (ProtocolLibrary.INCOMPATIBLE.contains(plugin)) {
                ProtocolLogger.log(Level.WARNING, "Encountered an exception caused by incompatible plugin {0}.", new Object[]{plugin});
                ProtocolLogger.log(Level.WARNING, "It is advised that you remove it.", new Object[0]);
            }
            throw ex;
        }
    }

    private TField readInternal(int fieldIndex)
            throws FieldAccessException {
        if (this.target == null) {
            throw new IllegalStateException("Cannot read from a null target!");
        }
        if (fieldIndex < 0) {
            throw new FieldAccessException(String.format("Field index (%s) cannot be negative.", new Object[]{Integer.valueOf(fieldIndex)}));
        }
        if (this.data.size() == 0) {
            throw new FieldAccessException(String.format("No field with type %s exists in class %s.", new Object[]{this.fieldType.getName(), this.target
                    .getClass().getSimpleName()}));
        }
        if (fieldIndex >= this.data.size()) {
            throw new FieldAccessException(String.format("Field index out of bounds. (Index: %s, Size: %s)", new Object[]{Integer.valueOf(fieldIndex), Integer.valueOf(this.data.size())}));
        }
        try {
            Object result = FieldUtils.readField((Field) this.data.get(fieldIndex), this.target, true);
            if (needConversion()) {
                return (TField) this.converter.getSpecific(result);
            }
            return (TField) result;
        } catch (IllegalAccessException e) {
            throw new FieldAccessException("Cannot read field due to a security limitation.", e);
        }
    }

    public TField readSafely(int fieldIndex)
            throws FieldAccessException {
        if ((fieldIndex >= 0) && (fieldIndex < this.data.size())) {
            return (TField) read(fieldIndex);
        }
        return null;
    }

    public Optional<TField> optionRead(int fieldIndex) {
        try {
            return Optional.ofNullable(read(fieldIndex));
        } catch (FieldAccessException ex) {
        }
        return Optional.empty();
    }

    public boolean isReadOnly(int fieldIndex) {
        return Modifier.isFinal(getField(fieldIndex).getModifiers());
    }

    public boolean isPublic(int fieldIndex) {
        return Modifier.isPublic(getField(fieldIndex).getModifiers());
    }

    public void setReadOnly(int fieldIndex, boolean value)
            throws FieldAccessException {
        if ((fieldIndex < 0) || (fieldIndex >= this.data.size())) {
            throw new IllegalArgumentException("Index parameter is not within [0 - " + this.data.size() + ")");
        }
        try {
            setFinalState((Field) this.data.get(fieldIndex), value);
        } catch (IllegalAccessException e) {
            throw new FieldAccessException("Cannot write read only status due to a security limitation.", e);
        }
    }

    protected static void setFinalState(Field field, boolean isReadOnly)
            throws IllegalAccessException {
        if (isReadOnly) {
            FieldUtils.writeField(field, Optional.of("modifiers"), Integer.valueOf(field.getModifiers() | 0x10), true);
        } else {
            FieldUtils.writeField(field, Optional.of("modifiers"), Integer.valueOf(field.getModifiers() & 0xFFFFFFEF), true);
        }
    }

    public StructureModifier<TField> write(int fieldIndex, TField value)
            throws FieldAccessException {
        try {
            return writeInternal(fieldIndex, value);
        } catch (FieldAccessException ex) {
            String plugin = PluginContext.getPluginCaller(ex);
            if (ProtocolLibrary.INCOMPATIBLE.contains(plugin)) {
                ProtocolLogger.log(Level.WARNING, "Encountered an exception caused by incompatible plugin {0}.", new Object[]{plugin});
                ProtocolLogger.log(Level.WARNING, "It is advised that you remove it.", new Object[0]);
            }
            throw ex;
        }
    }

    private StructureModifier<TField> writeInternal(int fieldIndex, TField value)
            throws FieldAccessException {
        if (this.target == null) {
            throw new IllegalStateException("Cannot read from a null target!");
        }
        if (fieldIndex < 0) {
            throw new FieldAccessException(String.format("Field index (%s) cannot be negative.", new Object[]{Integer.valueOf(fieldIndex)}));
        }
        if (this.data.size() == 0) {
            throw new FieldAccessException(String.format("No field with type %s exists in class %s.", new Object[]{this.fieldType.getName(), this.target
                    .getClass().getSimpleName()}));
        }
        if (fieldIndex >= this.data.size()) {
            throw new FieldAccessException(String.format("Field index out of bounds. (Index: %s, Size: %s)", new Object[]{Integer.valueOf(fieldIndex), Integer.valueOf(this.data.size())}));
        }
        Object obj = needConversion() ? this.converter.getGeneric(value) : value;
        try {
            FieldUtils.writeField((Field) this.data.get(fieldIndex), this.target, obj, true);
        } catch (IllegalAccessException e) {
            throw new FieldAccessException("Cannot read field due to a security limitation.", e);
        }
        return this;
    }

    protected Class<?> getFieldType(int index) {
        return ((Field) this.data.get(index)).getType();
    }

    private final boolean needConversion() {
        return (this.converter != null) && (!this.customConvertHandling);
    }

    public StructureModifier<TField> writeSafely(int fieldIndex, TField value)
            throws FieldAccessException {
        if ((fieldIndex >= 0) && (fieldIndex < this.data.size())) {
            write(fieldIndex, value);
        }
        return this;
    }

    public StructureModifier<TField> modify(int fieldIndex, Function<TField, TField> select)
            throws FieldAccessException {
        TField value = read(fieldIndex);
        return write(fieldIndex, select.apply(value));
    }

    public <T> StructureModifier<T> withType(Class fieldType) {
        return withType(fieldType, null);
    }

    public StructureModifier<TField> writeDefaults()
            throws FieldAccessException {
        DefaultInstances generator = DefaultInstances.DEFAULT;
        for (Field field : this.defaultFields.keySet()) {
            try {
                if (field.getType().getCanonicalName().equals("net.md_5.bungee.api.chat.BaseComponent[]")) {
                    FieldUtils.writeField(field, this.target, null, true);
                } else {
                    FieldUtils.writeField(field, this.target, generator.getDefault(field.getType()), true);
                }
            } catch (IllegalAccessException e) {
                throw new FieldAccessException("Cannot write to field due to a security limitation.", e);
            }
        }
        return this;
    }

    public <T> StructureModifier<T> withType(Class fieldType, EquivalentConverter<T> converter) {
        if (fieldType == null) {
            new StructureModifier() {
                public T read(int index) {
                    return null;
                }

                public StructureModifier<T> write(int index, Object value) {
                    return this;
                }
            };
        }
        StructureModifier<T> result = (StructureModifier) this.subtypeCache.get(fieldType);
        if (result == null) {
            List<Field> filtered = new ArrayList();
            Map<Field, Integer> defaults = new HashMap();
            int index = 0;
            for (Field field : this.data) {
                if (fieldType.isAssignableFrom(field.getType())) {
                    filtered.add(field);
                    if (this.defaultFields.containsKey(field)) {
                        defaults.put(field, Integer.valueOf(index));
                    }
                }
                index++;
            }
            result = withFieldType(fieldType, filtered, defaults);

            this.subtypeCache.put(fieldType, result);
            if ((this.useStructureCompiler) && (BackgroundCompiler.getInstance() != null)) {
                BackgroundCompiler.getInstance().scheduleCompilation(this.subtypeCache, fieldType);
            }
        }
        result = result.withTarget(this.target);
        if (!Objects.equal(result.converter, converter)) {
            result = result.withConverter(converter);
        }
        return result;
    }

    public Class getFieldType() {
        return this.fieldType;
    }

    public Class getTargetType() {
        return this.targetType;
    }

    public Object getTarget() {
        return this.target;
    }

    public int size() {
        return this.data.size();
    }

    protected <T> StructureModifier<T> withFieldType(Class fieldType, List<Field> filtered, Map<Field, Integer> defaults) {
        return withFieldType(fieldType, filtered, defaults, null);
    }

    protected <T> StructureModifier<T> withFieldType(Class fieldType, List<Field> filtered, Map<Field, Integer> defaults, EquivalentConverter<T> converter) {
        StructureModifier<T> result = new StructureModifier();
        result.initialize(this.targetType, fieldType, filtered, defaults, converter, new ConcurrentHashMap(), this.useStructureCompiler);

        return result;
    }

    public StructureModifier<TField> withTarget(Object target) {
        StructureModifier<TField> copy = new StructureModifier();

        copy.initialize(this);
        copy.target = target;
        return copy;
    }

    private <T> StructureModifier<T> withConverter(EquivalentConverter<T> converter) {
        StructureModifier copy = withTarget(this.target);

        copy.setConverter(converter);
        return copy;
    }

    protected void setConverter(EquivalentConverter<TField> converter) {
        this.converter = converter;
    }

    public List<Field> getFields() {
        return ImmutableList.copyOf(this.data);
    }

    public Field getField(int fieldIndex) {
        if ((fieldIndex < 0) || (fieldIndex >= this.data.size())) {
            throw new IllegalArgumentException("Index parameter is not within [0 - " + this.data.size() + ")");
        }
        return (Field) this.data.get(fieldIndex);
    }

    public List<TField> getValues()
            throws FieldAccessException {
        List<TField> values = new ArrayList();
        for (int i = 0; i < size(); i++) {
            values.add(read(i));
        }
        return values;
    }

    private static Map<Field, Integer> generateDefaultFields(List<Field> fields) {
        Map<Field, Integer> requireDefaults = new HashMap();
        DefaultInstances generator = DEFAULT_GENERATOR;
        int index = 0;
        for (Field field : fields) {
            Class<?> type = field.getType();
            int modifier = field.getModifiers();
            if ((!type.isPrimitive()) && (!Modifier.isFinal(modifier))) {
                if (generator.getDefault(type) != null) {
                    requireDefaults.put(field, Integer.valueOf(index));
                }
            }
            index++;
        }
        return requireDefaults;
    }

    private static List<Field> getFields(Class type, Class superclassExclude) {
        List<Field> result = new ArrayList();
        for (Field field : FuzzyReflection.fromClass(type, true).getDeclaredFields(superclassExclude)) {
            int mod = field.getModifiers();
            if ((!Modifier.isStatic(mod)) && ((superclassExclude == null) ||
                    (!field.getDeclaringClass().equals(superclassExclude)))) {
                result.add(field);
            }
        }
        return result;
    }

    public String toString() {
        return "StructureModifier[fieldType=" + this.fieldType + ", data=" + this.data + "]";
    }
}
