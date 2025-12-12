package h05;

import com.google.common.base.Suppliers;
import h05.entity.Miner;
import kotlin.Pair;
import org.tudalgo.algoutils.tutor.general.match.MatchingUtils;
import org.tudalgo.algoutils.tutor.general.reflections.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Links {

    private static final double MIN_SIMILARITY = 0.85;
    private static final Set<String> TEST_CLASSES = Set.of(
        "h05.entity.AbstractRepairBotTest",
        "h05.entity.MineBotTest",
        "h05.equipment.AxeTest",
        "h05.equipment.BatteryTest",
        "h05.equipment.CameraTest",
        "h05.equipment.PickaxeTest",
        "h05.equipment.PowerbankTest",
        "h05.equipment.TelephotoLensTest",
        "h05.equipment.TestEquipmentImpls",
        "h05.equipment.WallBreakerTest",
        "h05.mineable.RockTest",
        "h05.mineable.TreeTest",
        "h05.CallTransformer",
        "h05.H05_RubricProviderPrivate",
        "h05.Links",
        "h05.MineBotTransformer",
        "h05.TestDurableImpls",
        "h05.TestUtils"
    );
    private static final Set<Class<?>> CLASSES = getClasses();

    // h05.equipment.Axe

    public static final Supplier<TypeLink> AXE_TYPE_LINK = Suppliers.memoize(() -> typeLinkForClassName("h05.equipment.Axe"));
    public static final Supplier<ConstructorLink> AXE_CONSTRUCTOR_LINK = Suppliers.memoize(() ->
        constructorLinkForConstructor(AXE_TYPE_LINK));
    public static final Supplier<MethodLink> AXE_GET_MINING_POWER_METHOD_LINK = Suppliers.memoize(() ->
        methodLinkForMethod(AXE_TYPE_LINK, "getMiningPower"));

    // h05.equipment.Pickaxe

    public static final Supplier<TypeLink> PICKAXE_TYPE_LINK = Suppliers.memoize(() -> typeLinkForClassName("h05.equipment.Pickaxe"));
    public static final Supplier<ConstructorLink> PICKAXE_CONSTRUCTOR_LINK = Suppliers.memoize(() ->
        constructorLinkForConstructor(PICKAXE_TYPE_LINK));
    public static final Supplier<MethodLink> PICKAXE_GET_MINING_POWER_METHOD_LINK = Suppliers.memoize(() ->
        methodLinkForMethod(PICKAXE_TYPE_LINK, "getMiningPower"));

    // h05.equipment.Powerbank

    public static final Supplier<TypeLink> POWERBANK_TYPE_LINK = Suppliers.memoize(() -> typeLinkForClassName("h05.equipment.Powerbank"));
    public static final Supplier<ConstructorLink> POWERBANK_CONSTRUCTOR_LINK = Suppliers.memoize(() ->
        constructorLinkForConstructor(POWERBANK_TYPE_LINK, double.class));
    public static final Supplier<FieldLink> POWERBANK_CAPACITY_FIELD_LINK = Suppliers.memoize(() ->
        fieldLinkForField(POWERBANK_TYPE_LINK, "capacity"));
    public static final Supplier<MethodLink> POWERBANK_GET_CAPACITY_METHOD_LINK = Suppliers.memoize(() ->
        methodLinkForMethod(POWERBANK_TYPE_LINK, "getCapacity"));
    public static final Supplier<MethodLink> POWERBANK_USE_METHOD_LINK = Suppliers.memoize(() ->
        methodLinkForMethod(POWERBANK_TYPE_LINK, "use", Miner.class));

    // h05.equipment.TelephotoLens

    public static final Supplier<TypeLink> TELEPHOTO_LENS_TYPE_LINK = Suppliers.memoize(() -> typeLinkForClassName("h05.equipment.TelephotoLens"));
    public static final Supplier<ConstructorLink> TELEPHOTO_LENS_CONSTRUCTOR_LINK = Suppliers.memoize(() ->
        constructorLinkForConstructor(TELEPHOTO_LENS_TYPE_LINK, int.class));
    public static final Supplier<FieldLink> TELEPHOTO_LENS_RANGE_ENHANCEMENT_FIELD_LINK = Suppliers.memoize(() ->
        fieldLinkForField(TELEPHOTO_LENS_TYPE_LINK, "rangeEnhancement"));
    public static final Supplier<MethodLink> TELEPHOTO_LENS_GET_RANGE_ENHANCEMENT_METHOD_LINK = Suppliers.memoize(() ->
        methodLinkForMethod(TELEPHOTO_LENS_TYPE_LINK, "getRangeEnhancement"));
    public static final Supplier<MethodLink> TELEPHOTO_LENS_USE_METHOD_LINK = Suppliers.memoize(() ->
        methodLinkForMethod(TELEPHOTO_LENS_TYPE_LINK, "use", Miner.class));

    private static Set<Class<?>> getClasses() {
        try {
            return Arrays.stream(ReflectUtils.getClassesRecursive("h05"))
                .filter(clazz -> !TEST_CLASSES.contains(clazz.getName()))
                .collect(Collectors.toUnmodifiableSet());
        } catch (IOException e) {
            throw new RuntimeException(e); // not gonna happen
        }
    }

    private static TypeLink typeLinkForClassName(String className) {
        return CLASSES.stream()
            .map(clazz -> new Pair<>(clazz, MatchingUtils.similarity(className, clazz.getName())))
            .filter(pair -> pair.getSecond() >= MIN_SIMILARITY)
            .sorted(Comparator.comparingDouble((Pair<?, Double> pair) -> pair.getSecond()).reversed())
            .map(Pair::getFirst)
            .findFirst()
            .map(BasicTypeLink::of)
            .orElse(null);
    }

    private static FieldLink fieldLinkForField(Supplier<TypeLink> typeLink, String name) {
        return Optional.ofNullable(typeLink.get())
            .map(TypeLink::reflection)
            .flatMap(type -> Stream.concat(Arrays.stream(type.getFields()), Arrays.stream(type.getDeclaredFields()))
                .distinct()
                .filter(field -> MatchingUtils.similarity(name, field.getName()) >= MIN_SIMILARITY)
                .findAny())
            .map(BasicFieldLink::of)
            .orElse(null);
    }

    private static ConstructorLink constructorLinkForConstructor(Supplier<TypeLink> typeLink, Class<?>... paramTypes) {
        return Optional.ofNullable(typeLink.get())
            .map(TypeLink::reflection)
            .flatMap(type -> Stream.concat(Arrays.stream(type.getConstructors()), Arrays.stream(type.getDeclaredConstructors()))
                .distinct()
                .filter(method -> Arrays.equals(method.getParameterTypes(), paramTypes))
                .findAny())
            .map(BasicConstructorLink::of)
            .orElse(null);
    }

    private static MethodLink methodLinkForMethod(Supplier<TypeLink> typeLink, String name, Class<?>... paramTypes) {
        return Optional.ofNullable(typeLink.get())
            .map(TypeLink::reflection)
            .flatMap(type -> Stream.concat(Arrays.stream(type.getMethods()), Arrays.stream(type.getDeclaredMethods()))
                .distinct()
                .filter(method -> MatchingUtils.similarity(name, method.getName()) >= MIN_SIMILARITY)
                .filter(method -> Arrays.equals(method.getParameterTypes(), paramTypes))
                .findAny())
            .map(BasicMethodLink::of)
            .orElse(null);
    }
}
