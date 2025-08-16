package h05;


import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;

public class H05_RubricProviderPrivate implements RubricProvider {
    public static final Rubric RUBRIC = Rubric.builder()
            .title("H05 | MineBot")
            .addChildCriteria(
                    Criterion.builder()
                            .shortDescription("H5.1 | Haltbarkeitsdatum abgelaufen")
                            .addChildCriteria(
                                    criterion("Die Methoden von Durable sind in mindestens 4 Klassen korrekt implementiert.", 1
                                    ),
                                    criterion("Die Methoden von Durable sind in allen Klassen korrekt implementiert.", 1
                                    )
                            )
                            .build(),
                    Criterion.builder()
                            .shortDescription("H5.2 | Gear up!")
                            .addChildCriteria(
                                    Criterion.builder()
                                            .shortDescription("H5.2.1 | Zustand von Ausrüstungen")
                                            .addChildCriteria(
                                                    criterion("Die Methode getCondition() des Interfaces Equipments gibt für mindestens einen Fall die richtige EquipmentCondition zurück.", 1
                                                    ),
                                                    criterion("Die Methode getCondition() des Interfaces Equipments gibt für alle Fälle die richtige EquipmentCondition zurück.", 1
                                                    )
                                            )
                                            .build(),
                                    Criterion.builder()
                                            .shortDescription("H5.2.2 | Batterie und Kamera")
                                            .addChildCriteria(
                                                    criterion("Die Methode increaseDurability() der Klasse Battery ist korrekt implementiert. ", 1
                                                    ),
                                                    criterion("Die Methode getVisibilityRange() der Klasse Camera ist korrekt implementiert. ", 1
                                                    )
                                            )
                                            .build(),
                                    Criterion.builder()
                                            .shortDescription("H5.2.3 | Powerbank und TelephotoLens")
                                            .addChildCriteria(
                                                    criterion("Die Klassen Powerbank und TelphotoLense müssen vollständig und korrekt erstellt, implementiert (mit Ausnahme der Methode use())) und initialisiert werden.", 1
                                                    ),
                                                    criterion("Die Methode use() der Klasse Powerbank ist korrekt implementiert.", 1
                                                    ),
                                                    criterion("Die Methode use() der Klasse TelephotoLens ist korrekt implementiert.", 1
                                                    )
                                            )
                                            .build(),
                                    Criterion.builder()
                                            .shortDescription("H5.2.4 | Tools")
                                            .addChildCriteria(
                                                    criterion("Die Klassen Axe und Pickaxe sind vollständig und korrekt erstellt, implementiert und initialisiert.", 1
                                                    )
                                            )
                                            .build(),
                                    Criterion.builder()
                                            .shortDescription("H5.2.5 | Wallbreaker")
                                            .addChildCriteria(
                                                    criterion("Die Methode use() der Klasse Wallbreaker berechnet die korrekte Position, an der eine Wand gebrochen werden soll.", 1
                                                    ),
                                                    criterion("Die Methode use() der Klasse Wallbreaker entfernt die Wand aus der Welt.", 1
                                                    )
                                            )
                                            .build()
                            )
                            .build(),
                    Criterion.builder()
                            .shortDescription("H5.3 | Breaking Blocks")
                            .addChildCriteria(
                                    criterion("Die Methoden getName() und getProgress() sind in der Klasse Rock korrekt implementiert.", 1
                                    ),
                                    criterion("Die Methoden getName() und getProgress() sind in der Klasse Tree korrekt implementiert.", 1
                                    ),
                                    criterion("Die Methode onMined() sind in der Klasse Rock korrekt implementiert.", 1
                                    ),
                                    criterion("Die Methode onMined() sind in der Klasse Tree korrekt implementiert.", 1
                                    )
                            )
                            .build(),
                    Criterion.builder()
                            .shortDescription("H5.4 | You’re telling me a ro mined this bot?")
                            .addChildCriteria(
                                    Criterion.builder()
                                            .shortDescription("H5.4.1 | mine()")
                                            .addChildCriteria(
                                                    criterion("Die Methode mine() von der Klasse MineBot ruft settings.getLootAt(x,y) mit den richtigen Parametern auf.", 1
                                                    ),
                                                    criterion("Die Methode mine() von der Klasse MineBot bricht ab, sollte dort kein Rohstoff existieren.", 1
                                                    ),
                                                    criterion("Die Methode mine() von der Klasse MineBot ruft die Methode onMined() des Rohstoffes auf, sollte er nicht vollständig abgebaut sein.", 1
                                                    ),
                                                    criterion("Die Methode mine() von der Klasse MineBot nimmt den Rohstoff nach dem Abbauen ins Inventar auf, sollte dort noch Platz sein.", 1
                                                    )
                                            )
                                            .build(),
                                    Criterion.builder()
                                            .shortDescription("H5.4.2 | Sichtweite aktualisieren")
                                            .addChildCriteria(
                                                    criterion("Die Methode getVision() der Klasse MineBot berechnet die korrekte Größe des Arrays der sichtbaren Punkte.", 1
                                                    ),
                                                    criterion("Die Methode getVision() der Klasse MineBot berechnet die sichtbaren Punkte korrekt.", 1
                                                    ),
                                                    criterion("Die Methode updateVision() der Klasse MineBot berechnet die abzudeckenden Felder korrekt und platziert dort Fog.", 1
                                                    ),
                                                    criterion("Die Methode updateVision() der Klasse MineBot berechnet die aufzudeckenden Felder korrekt und entfernt dort Fog.", 1
                                                    )
                                            )
                                            .build(),
                                    Criterion.builder()
                                            .shortDescription("H5.4.3 | move()")
                                            .addChildCriteria(
                                                    criterion("Die Methode move() der Klasse MineBot ruft updateVision mit den korrekten Parametern auf", 1
                                                    ),
                                                    criterion("Die Methode move() der Klasse MineBot reduziert die Haltbarkeit der Batterie um den korrekten Wert.", 1
                                                    )
                                            )
                                            .build(),
                                    Criterion.builder()
                                            .shortDescription("H5.4.4 | Anwenden von Ausrüstung")
                                            .addChildCriteria(
                                                    criterion("Die Methode use() der Klasse MineBot benutzt das richtige Equipment.", 1
                                                    ),
                                                    criterion("Die Methode use() der Klasse MineBot ruft, sollte das zu benutzende Equipment eine TelephotoLens sein, die Methode updateVision() mit den richtigen Parametern auf.", 1
                                                    )
                                            )
                                            .build()
                            )
                            .build(),
                    Criterion.builder()
                            .shortDescription("H5.5 | First Aid Bot – Ihr persönlicher Gesundheitsbegleiter")
                            .addChildCriteria(
                                    criterion("Die Methode scan() in der Klasse AbstractRepairBot gibt MineBots mit beschädigter Ausrüstung im korrekten Radius zurück.", 1
                                    ),
                                    criterion("Die Methode scan() in der Klasse AbstractRepairBot gibt null zurück, sollte es keine MineBots mit beschädigter Ausrüstung im Radius geben.", 1
                                    ),
                                    criterion("Die Methode repair() in der Klasse AbstractRepairBot equippt eine neue Kamera/Batterie, sollte die alte kaputt sein.", 1
                                    ),
                                    criterion("Die Methode repair() in der Klasse AbstractRepairBot entfernt alle beschädigten Equipments.", 1
                                    )
                            )
                            .build()

            )
            .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}
