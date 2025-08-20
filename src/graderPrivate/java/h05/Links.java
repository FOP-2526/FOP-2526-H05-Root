package h05;

import com.google.common.base.Suppliers;
import h05.entity.Miner;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.reflections.*;

import java.util.List;
import java.util.function.Supplier;

public class Links {

    private static final PackageLink H05_EQUIPMENT_PACKAGE = BasicPackageLink.of("h05.equipment");

    // h05.equipment.Axe

    public static final Supplier<TypeLink> AXE_TYPE_LINK = Suppliers.memoize(() ->
        H05_EQUIPMENT_PACKAGE.getType(Matcher.of(typeLink -> typeLink.name().equals("Axe"))));
    public static final Supplier<ConstructorLink> AXE_CONSTRUCTOR_LINK = Suppliers.memoize(() ->
        AXE_TYPE_LINK.get().getConstructor(Matcher.of(constructorLink -> constructorLink.typeList().isEmpty())));
    public static final Supplier<MethodLink> AXE_GET_MINING_POWER_METHOD_LINK = Suppliers.memoize(() ->
        AXE_TYPE_LINK.get().getMethod(Matcher.of(methodLink ->
            methodLink.name().equals("getMiningPower") && methodLink.typeList().isEmpty())));

    // h05.equipment.Pickaxe

    public static final Supplier<TypeLink> PICKAXE_TYPE_LINK = Suppliers.memoize(() ->
        H05_EQUIPMENT_PACKAGE.getType(Matcher.of(typeLink -> typeLink.name().equals("Pickaxe"))));
    public static final Supplier<ConstructorLink> PICKAXE_CONSTRUCTOR_LINK = Suppliers.memoize(() ->
        PICKAXE_TYPE_LINK.get().getConstructor(Matcher.of(constructorLink -> constructorLink.typeList().isEmpty())));
    public static final Supplier<MethodLink> PICKAXE_GET_MINING_POWER_METHOD_LINK = Suppliers.memoize(() ->
        PICKAXE_TYPE_LINK.get().getMethod(Matcher.of(methodLink ->
            methodLink.name().equals("getMiningPower") && methodLink.typeList().isEmpty())));

    // h05.equipment.Powerbank

    public static final Supplier<TypeLink> POWERBANK_TYPE_LINK = Suppliers.memoize(() ->
        H05_EQUIPMENT_PACKAGE.getType(Matcher.of(typeLink -> typeLink.name().equals("Powerbank"))));
    public static final Supplier<ConstructorLink> POWERBANK_CONSTRUCTOR_LINK = Suppliers.memoize(() ->
        POWERBANK_TYPE_LINK.get().getConstructor(Matcher.of(constructorLink ->
            constructorLink.typeList().equals(List.of(BasicTypeLink.of(double.class))))));
    public static final Supplier<FieldLink> POWERBANK_CAPACITY_FIELD_LINK = Suppliers.memoize(() ->
        POWERBANK_TYPE_LINK.get().getField(Matcher.of(fieldLink -> fieldLink.name().equals("capacity"))));
    public static final Supplier<MethodLink> POWERBANK_GET_CAPACITY_METHOD_LINK = Suppliers.memoize(() ->
        POWERBANK_TYPE_LINK.get().getMethod(Matcher.of(methodLink ->
            methodLink.name().equals("getCapacity") && methodLink.typeList().isEmpty())));
    public static final Supplier<MethodLink> POWERBANK_USE_METHOD_LINK = Suppliers.memoize(() ->
        POWERBANK_TYPE_LINK.get().getMethod(Matcher.of(methodLink ->
            methodLink.name().equals("use") && methodLink.typeList().equals(List.of(BasicTypeLink.of(Miner.class))))));

    // h05.equipment.TelephotoLens

    public static final Supplier<TypeLink> TELEPHOTO_LENS_TYPE_LINK = Suppliers.memoize(() ->
        H05_EQUIPMENT_PACKAGE.getType(Matcher.of(typeLink -> typeLink.name().equals("TelephotoLens"))));
    public static final Supplier<ConstructorLink> TELEPHOTO_LENS_CONSTRUCTOR_LINK = Suppliers.memoize(() ->
        TELEPHOTO_LENS_TYPE_LINK.get().getConstructor(Matcher.of(constructorLink ->
            constructorLink.typeList().equals(List.of(BasicTypeLink.of(int.class))))));
    public static final Supplier<FieldLink> TELEPHOTO_LENS_RANGE_ENHANCEMENT_FIELD_LINK = Suppliers.memoize(() ->
        TELEPHOTO_LENS_TYPE_LINK.get().getField(Matcher.of(fieldLink -> fieldLink.name().equals("rangeEnhancement"))));
    public static final Supplier<MethodLink> TELEPHOTO_LENS_GET_RANGE_ENHANCEMENT_METHOD_LINK = Suppliers.memoize(() ->
        TELEPHOTO_LENS_TYPE_LINK.get().getMethod(Matcher.of(methodLink ->
            methodLink.name().equals("getRangeEnhancement") && methodLink.typeList().isEmpty())));
    public static final Supplier<MethodLink> TELEPHOTO_LENS_USE_METHOD_LINK = Suppliers.memoize(() ->
        TELEPHOTO_LENS_TYPE_LINK.get().getMethod(Matcher.of(methodLink ->
            methodLink.name().equals("use") && methodLink.typeList().equals(List.of(BasicTypeLink.of(Miner.class))))));
}
