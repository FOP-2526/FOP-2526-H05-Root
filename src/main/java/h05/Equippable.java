package h05;

public interface Equippable {

    RobotEquipment[] getEquipments();

    RobotEquipment getEquipment(int index);

    RobotEquipment getEquipment(String name);

    void equip(RobotEquipment equipment);
}
