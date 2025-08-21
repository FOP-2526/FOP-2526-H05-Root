package h05;


import fopbot.Direction;
import h05.entity.MineBotTest;
import h05.equipment.*;
import h05.mineable.MiningProgress;
import h05.mineable.RockTest;
import h05.mineable.TreeTest;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

import java.util.Optional;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H05_RubricProviderPrivate implements RubricProvider {
    public static final Rubric RUBRIC = Rubric.builder()
        .title("H05 | MineBot")
        .addChildCriteria(
            Criterion.builder()
                .shortDescription("H5.1 | Haltbarkeitsdatum abgelaufen")
                .addChildCriteria(
                    criterion("Die Methoden von Durable sind in mindestens 4 Klassen korrekt und vollständig implementiert.",
                        1,
                        JUnitTestRef.ofMethod(() -> TestDurableImpls.class.getDeclaredMethod("testDurableImplsMinimum"))),
                    criterion("Die Methoden von Durable sind in allen Klassen korrekt und vollständig implementiert.",
                        1,
                        JUnitTestRef.ofMethod(() -> TestDurableImpls.class.getDeclaredMethod("testDurableImplsAll")))
                )
                .build(),
            Criterion.builder()
                .shortDescription("H5.2 | Gear up!")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H5.2.1 | Zustand von Ausrüstungen")
                        .addChildCriteria(
                            criterion("Die Methode getCondition() des Interfaces Equipments gibt für mindestens einen Fall die richtige EquipmentCondition zurück.",
                                1,
                                JUnitTestRef.ofMethod(() -> TestEquipmentImpls.class.getDeclaredMethod("testEquipmentImplsMinimum"))),
                            criterion("Die Methode getCondition() des Interfaces Equipments gibt für alle Fälle die richtige EquipmentCondition zurück.",
                                1,
                                JUnitTestRef.ofMethod(() -> TestEquipmentImpls.class.getDeclaredMethod("testEquipmentImplsAll")))
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.2.2 | Batterie und Kamera")
                        .addChildCriteria(
                            criterion("Die Methode increaseDurability(double) der Klasse Battery ist korrekt und vollständig implementiert.",
                                1,
                                JUnitTestRef.ofMethod(() -> BatteryTest.class.getDeclaredMethod("testIncreaseDurability"))),
                            criterion("Die Methoden getVisibilityRange() und setVisibilityRange(double) der Klasse Camera sind korrekt und vollständig implementiert.",
                                1,
                                JUnitTestRef.ofMethod(() -> CameraTest.class.getDeclaredMethod("testGetVisibility")),
                                JUnitTestRef.ofMethod(() -> CameraTest.class.getDeclaredMethod("testSetVisibility", int.class)))
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.2.3 | Powerbank und TelephotoLens")
                        .addChildCriteria(
                            criterion("Die Klassen Powerbank und TelephotoLense sind vollständig und korrekt erstellt, implementiert (getCapacity() bzw. getRangeEnhancement()) und initialisiert.",
                                1,
                                JUnitTestRef.ofMethod(() -> PowerbankTest.class.getDeclaredMethod("testDefinition")),
                                JUnitTestRef.ofMethod(() -> PowerbankTest.class.getDeclaredMethod("testConstructor", double.class)),
                                JUnitTestRef.ofMethod(() -> PowerbankTest.class.getDeclaredMethod("testGetCapacity", double.class)),
                                JUnitTestRef.ofMethod(() -> TelephotoLensTest.class.getDeclaredMethod("testDefinition")),
                                JUnitTestRef.ofMethod(() -> TelephotoLensTest.class.getDeclaredMethod("testConstructor", int.class)),
                                JUnitTestRef.ofMethod(() -> TelephotoLensTest.class.getDeclaredMethod("testGetRangeEnhancement", int.class))),
                            criterion("Die Methode use(Miner) der Klasse Powerbank ist vollständig und korrekt implementiert.",
                                1,
                                JUnitTestRef.ofMethod(() -> PowerbankTest.class.getDeclaredMethod("testUse", EquipmentCondition.class))),
                            criterion("Die Methode use(Miner) der Klasse TelephotoLens ist vollständig und korrekt implementiert.",
                                1,
                                JUnitTestRef.ofMethod(() -> TelephotoLensTest.class.getDeclaredMethod("testUse", EquipmentCondition.class)))
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.2.4 | Tools")
                        .addChildCriteria(
                            criterion("Die Klassen Axe und Pickaxe sind vollständig und korrekt erstellt, implementiert und initialisiert.",
                                1,
                                JUnitTestRef.ofMethod(() -> AxeTest.class.getDeclaredMethod("testDefinition")),
                                JUnitTestRef.ofMethod(() -> AxeTest.class.getDeclaredMethod("testGetMiningPower")),
                                JUnitTestRef.ofMethod(() -> PickaxeTest.class.getDeclaredMethod("testDefinition")),
                                JUnitTestRef.ofMethod(() -> PickaxeTest.class.getDeclaredMethod("testGetMiningPower")))
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.2.5 | Wallbreaker")
                        .addChildCriteria(
                            criterion("Die Methode use(Miner) der Klasse Wallbreaker ruft settings.getWallAt(int,int) mit den richtigen Parametern auf.",
                                1,
                                JUnitTestRef.ofMethod(() -> WallBreakerTest.class.getDeclaredMethod("testUse_callsGetWallsAt", Direction.class))),
                            criterion("Die Methode use(Miner) der Klasse Wallbreaker entfernt die Wand aus der Welt.",
                                1,
                                JUnitTestRef.ofMethod(() -> WallBreakerTest.class.getDeclaredMethod("testUse_removesWalls", Direction.class)))
                        )
                        .build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H5.3 | Breaking Blocks")
                .addChildCriteria(
                    criterion("Die Methoden getName() und getProgress() sind in der Klasse Rock vollständig und korrekt implementiert.",
                        1,
                        JUnitTestRef.ofMethod(() -> RockTest.class.getDeclaredMethod("testGetName")),
                        JUnitTestRef.ofMethod(() -> RockTest.class.getDeclaredMethod("testGetProgress", double.class, MiningProgress.class))),
                    criterion("Die Methoden getName() und getProgress() sind in der Klasse Tree vollständig und korrekt implementiert.",
                        1,
                        JUnitTestRef.ofMethod(() -> TreeTest.class.getDeclaredMethod("testGetName")),
                        JUnitTestRef.ofMethod(() -> TreeTest.class.getDeclaredMethod("testGetProgress", double.class, MiningProgress.class))),
                    criterion("Die Methode onMined(Tool) sind in der Klasse Rock vollständig und korrekt implementiert.",
                        1,
                        JUnitTestRef.ofMethod(() -> RockTest.class.getDeclaredMethod("testOnMined", Utils.ToolClass.class, Optional.class))),
                    criterion("Die Methode onMined(Tool) sind in der Klasse Tree vollständig und korrekt implementiert.",
                        1,
                        JUnitTestRef.ofMethod(() -> TreeTest.class.getDeclaredMethod("testOnMined", Utils.ToolClass.class, Optional.class)))
                )
                .build(),
            Criterion.builder()
                .shortDescription("H5.4 | You’re telling me a ro mined this bot?")
                .addChildCriteria(
                    Criterion.builder()
                        .shortDescription("H5.4.1 | mine()")
                        .addChildCriteria(
                            criterion("Die Methode mine() der Klasse MineBot ruft settings.getLootAt(int, int) mit den richtigen Parametern auf.",
                                1,
                                JUnitTestRef.ofMethod(() -> MineBotTest.class.getDeclaredMethod("testMine_callsGetLootAt", Direction.class))),
                            criterion("Die Methode mine() der Klasse MineBot bricht ab, sollte dort kein Rohstoff existieren.",
                                1,
                                JUnitTestRef.ofMethod(() -> MineBotTest.class.getDeclaredMethod("testMine_returnsOnNoLoot", Direction.class))),
                            criterion("Die Methode mine() der Klasse MineBot ruft die Methode onMined(Mineable) des Rohstoffes auf, sollte er nicht vollständig abgebaut sein.",
                                1,
                                JUnitTestRef.ofMethod(() -> MineBotTest.class.getDeclaredMethod("testMine_callsOnMined", Direction.class))),
                            criterion("Die Methode mine() der Klasse MineBot nimmt den Rohstoff nach dem Abbauen ins Inventar auf, sollte dort noch Platz sein.",
                                1,
                                JUnitTestRef.ofMethod(() -> MineBotTest.class.getDeclaredMethod("testMine_placesInInventory", Direction.class)))
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.4.2 | Sichtweite aktualisieren")
                        .addChildCriteria(
                            criterion("Die Methode getVision(int ,int) der Klasse MineBot berechnet die korrekte Größe des Arrays der sichtbaren Punkte.",
                                1,
                                JUnitTestRef.ofMethod(() -> MineBotTest.class.getDeclaredMethod("testGetVision_correctNumberOfPoints", int.class))),
                            criterion("Die Methode getVision(int ,int) der Klasse MineBot berechnet die sichtbaren Punkte korrekt.",
                                1,
                                JUnitTestRef.ofMethod(() -> MineBotTest.class.getDeclaredMethod("testGetVision_correctPoints", int.class))),
                            criterion("Die Methode updateVision(int, int, int, int) der Klasse MineBot berechnet die abzudeckenden Felder korrekt und platziert dort Fog.",
                                1,
                                JUnitTestRef.ofMethod(() -> MineBotTest.class.getDeclaredMethod("testUpdateVision_placeFog", int.class))),
                            criterion("Die Methode updateVision(int, int, int, int) der Klasse MineBot berechnet die aufzudeckenden Felder korrekt und entfernt dort Fog.",
                                1,
                                JUnitTestRef.ofMethod(() -> MineBotTest.class.getDeclaredMethod("testUpdateVision_removeFog", int.class)))
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.4.3 | move()")
                        .addChildCriteria(
                            criterion("Die Methode move() der Klasse MineBot ruft updateVision(int, int , int , int) mit den korrekten Parametern auf", 1),
                            criterion("Die Methode move() der Klasse MineBot reduziert die Haltbarkeit der Batterie um den korrekten Wert.", 1)
                        )
                        .build(),
                    Criterion.builder()
                        .shortDescription("H5.4.4 | Anwenden von Ausrüstung")
                        .addChildCriteria(
                            criterion("Die Methode use(int index) der Klasse MineBot benutzt das richtige Equipment.", 1),
                            criterion("Die Methode use(int) der Klasse MineBot ruft, sollte das zu benutzende Equipment eine TelephotoLens sein, die Methode updateVision(int, int, int, int) mit den richtigen Parametern auf.", 1)
                        )
                        .build()
                )
                .build(),
            Criterion.builder()
                .shortDescription("H5.5 | First Aid Bot – Ihr persönlicher Gesundheitsbegleiter")
                .addChildCriteria(
                    criterion("Die Methode scan() in der Klasse AbstractRepairBot gibt MineBots mit beschädigter Ausrüstung im korrekten Radius zurück.", 1),
                    criterion("Die Methode scan() in der Klasse AbstractRepairBot gibt null zurück, sollte es keine MineBots mit beschädigter Ausrüstung im Radius geben.", 1),
                    criterion("Die Methode repair(Point) in der Klasse AbstractRepairBot rüstet den MineBot mit einer neuen Kamera/Batterie aus, sollte die alte kaputt sein.", 1),
                    criterion("Die Methode repair(Point) in der Klasse AbstractRepairBot entfernt alle beschädigten Equipments.", 1)
                )
                .build()

        )
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
