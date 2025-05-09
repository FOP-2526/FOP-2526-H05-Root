package h05;

public interface UpgradeableEquipment extends Equipment {

    AttachableEquipment[] getUpgrades();

    int getNumberOfUpgrades();

    void attach(AttachableEquipment upgrade);

    void detach(AttachableEquipment upgrade);
}
