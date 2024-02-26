package io.github.syst3ms.skriptparser.types;

import io.github.syst3ms.skriptparser.registration.SkriptRegistration;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Manages the registration and usage of {@link Type}
 */
@SuppressWarnings("unchecked")
public class TypeManager {
    /**
     * The string equivalent of null
     */
    public static final String NULL_REPRESENTATION = "<none>";
    /**
     * The string equivalent of an empty array
     */
    public static final String EMPTY_REPRESENTATION = "<empty>";
    private static final Map<String, Type<?>> nameToType = new HashMap<>();
    private static final Map<Class<?>, Type<?>> classToType = new LinkedHashMap<>(); // Ordering is important for stuff like number types

    public static Map<Class<?>, Type<?>> getClassToTypeMap() {
        return classToType;
    }

    /**
     * Gets a {@link Type} by its exact name (the baseName parameter used in {@link Type#Type(Class, String, String)})
     * @param name the name to get the Type from
     * @return the corresponding Type, or {@literal null} if nothing matched
     */
    public static Optional<? extends Type<?>> getByExactName(String name) {
        return Optional.ofNullable(nameToType.get(name));
    }

    /**
     * Gets a {@link Type} using its plural forms, which means this matches any alternate and/or plural form.
     * @param name the name to get a Type from
     * @return the matching Type, or {@literal null} if nothing matched
     */
    public static Optional<? extends Type<?>> getByName(String name) {
        for (var t : nameToType.values()) {
            var patterns = t.getPatterns();
            if (patterns[0].matcher(name).matches() || patterns[1].matcher(name).matches()) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }


    /**
     * Gets a {@link Type} from its associated {@link Class}.
     * @param c the Class to get the Type from
     * @param <T> the underlying type of the Class and the returned Type
     * @return the associated Type, or {@literal null}
     */
    public static <T> Optional<? extends Type<T>> getByClassExact(Class<T> c) {
        if (c.isArray())
            c = (Class<T>) c.getComponentType();
        return Optional.ofNullable((Type<T>) classToType.get(c));
    }

    public static <T> Optional<? extends Type<? super T>> getByClass(Class<T> c) {
        Optional<? extends Type<? super T>> type = getByClassExact(c);
        if (type.isPresent())
            return type;

        int lowestDifference = Integer.MAX_VALUE;
        Type<?> lowestType = null;

        for (var entry : classToType.entrySet()) {
            if (entry.getKey() == Object.class)
                continue;

            var typeClass = entry.getKey();
            var typeType = entry.getValue();
            if (typeClass.isAssignableFrom(c)) {
                var difference = getInheritanceLevelDifference(typeClass, c);
                if (difference < lowestDifference) {
                    lowestDifference = difference;
                    lowestType = typeType;
                }
            }
        }

        if (lowestType != null)
            classToType.put(c, lowestType);

        return Optional.ofNullable((Type<? super T>) lowestType);
    }

    public static int getInheritanceLevelDifference(Class<?> class1, Class<?> class2) {
        // Get the inheritance hierarchy for class1
        List<Class<?>> hierarchy1 = getHierarchy(class1);

        // Get the inheritance hierarchy for class2
        List<Class<?>> hierarchy2 = getHierarchy(class2);

        // Find the common ancestor (lowest common superclass)
        int commonAncestorIndex = findCommonAncestorIndex(hierarchy1, hierarchy2);

        // Calculate the difference in inheritance levels
        return hierarchy1.size() - commonAncestorIndex + hierarchy2.size() - commonAncestorIndex - 2;
    }

    private static List<Class<?>> getHierarchy(Class<?> cls) {
        List<Class<?>> hierarchy = new ArrayList<>();
        Class<?> currentClass = cls;

        // Traverse the class hierarchy upwards
        while (currentClass != null && currentClass != Object.class) {
            hierarchy.add(currentClass);
            currentClass = currentClass.getSuperclass();
        }

        // Traverse the interface hierarchy
        Class<?>[] interfaces = cls.getInterfaces();
        for (Class<?> interf : interfaces) {
            hierarchy.addAll(getHierarchy(interf));
        }

        // Reverse the hierarchy list to get the hierarchy from top to bottom
        Collections.reverse(hierarchy);
        return hierarchy;
    }

    private static int findCommonAncestorIndex(List<Class<?>> hierarchy1, List<Class<?>> hierarchy2) {
        int commonAncestorIndex = 0;
        int minLength = Math.min(hierarchy1.size(), hierarchy2.size());

        // Find the index of the common ancestor
        for (int i = 0; i < minLength; i++) {
            if (hierarchy1.get(i).equals(hierarchy2.get(i))) {
                commonAncestorIndex = i;
            } else {
                break;
            }
        }

        return commonAncestorIndex;
    }

    public static String toString(Object[] objects) {
        var sb = new StringBuilder();
        for (var i = 0; i < objects.length; i++) {
            if (i > 0) {
                sb.append(i == objects.length - 1 ? " and " : ", ");
            }
            var o = objects[i];
            if (o == null) {
                sb.append(NULL_REPRESENTATION);
                continue;
            }
            var type = getByClass(o.getClass());
            sb.append(type.map(t -> (Object) t.getToStringFunction().apply(o)).orElse(o));
        }
        return sb.length() == 0 ? EMPTY_REPRESENTATION : sb.toString();
    }

    /**
     * Gets a {@link PatternType} from a name. This determines the number (single/plural) from the input.
     * If the input happens to be the base name of a type, then a single PatternType (as in "not plural") of the corresponding type is returned.
     * @param name the name input
     * @return a corresponding PatternType, or {@literal null} if nothing matched
     */
    public static Optional<PatternType<?>> getPatternType(String name) {
        for (var t : nameToType.values()) {
            Pattern[] patterns = t.getPatterns();
            if (patterns[0].matcher(name).matches())
                return Optional.of(new PatternType<>(t, true));

            if (patterns[1].matcher(name).matches())
                return Optional.of(new PatternType<>(t, false));
        }
        return Optional.empty();
    }

    public static void register(SkriptRegistration reg) {
        for (var type : reg.getTypes()) {
            nameToType.put(type.getBaseName(), type);
            classToType.put(type.getTypeClass(), type);
        }
    }
}