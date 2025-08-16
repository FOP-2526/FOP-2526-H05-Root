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
                                                    criterion("Die Methode getCondition() gibt für mindestens einen Fall die richtige EquipmentCondition zurück.", 1
                                                    ),
                                                    criterion("Die Methode getCondition() gibt für alle Fälle die richtige EquipmentCondition zurück.", 1
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
                                                    criterion("Powerbank und TelephotoLens sind korrekt definiert.", 1
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
                                                    criterion("Die Klassen Axe und Pickaxe sind korrekt definiert und initialisiert.", 1
                                                    )
                                            )
                                            .build(),
                                    Criterion.builder()
                                            .shortDescription("H5.2.5 | Wallbreaker")
                                            .addChildCriteria(
                                                    criterion("Das Feld, an welchem die Wand entfernt werden soll, wird in use() korrekt berechnet.", 1
                                                    ),
                                                    criterion("Die Wand, und nur die Wand, wird in use() korrekt aus der Welt entfernt.", 1
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
                                                    criterion("Punkt, an welchem gemined wird, wird korrekt berechnet und liegt nur innerhalb der Welt.", 1
                                                    ),
                                                    criterion("Die Methode bricht ab, sollte an diesem Punkt kein mineable existieren.", 1
                                                    ),
                                                    criterion("Die Methode onMined() der Klasse mineable wird korrekt aufgerufen, wenn  es nicht vollständig abgebaut ist.", 1
                                                    )
                                                    , criterion("Nach dem minen wird das mineable korrekt in das Inventar aufgenommen.", 1
                                                    )
                                            )
                                            .build(),
                                    Criterion.builder()
                                            .shortDescription("H5.4.2 | Sichtweite aktualisieren")
                                            .addChildCriteria(
                                                    criterion("Die Größe des Arrays der sichtbaren Felder in getVision() wird korrekt bestimmt.", 1
                                                    ),
                                                    criterion("Die sichtbaren Felder werden in getVision() korrekt berechnet.", 1
                                                    ),
                                                    criterion("Die alten und neuen sichtbaren Felder werden in updateVision() korrekt berechnet.", 1
                                                    ),
                                                    criterion("Die neu sichtbaren Felder werden in updateVision() korrekt aufgedeckt, die nichtmehr sichtbaren Felder korrekt verdeckt.", 1
                                                    )
                                            )
                                            .build(),
                                    Criterion.builder()
                                            .shortDescription("H5.4.3 | move()")
                                            .addChildCriteria(
                                                    criterion("updateVision wird mit den korrekten Parametern aufgerufen.", 1
                                                    ),
                                                    criterion("Die Haltbarkeit der Batterie wird um den korrekten Wert reduziert.", 1
                                                    )
                                            )
                                            .build(),
                                    Criterion.builder()
                                            .shortDescription("H5.4.4 | Anwenden von Ausrüstung")
                                            .addChildCriteria(
                                                    criterion("Es wird das richtige Equipment in use() benutzt.", 1
                                                    ),
                                                    criterion("Bei einer TelephotoLens wird updateVision mit den korrekten Parametern aufgerufen.", 1
                                                    )
                                            )
                                            .build()
                            )
                            .build(),
                    Criterion.builder()
                            .shortDescription("H5.5 | First Aid Bot – Ihr persönlicher Gesundheitsbegleiter")
                            .addChildCriteria(
                                    criterion("Die Methode scan() in der Klasse gibt MineBots mit beschädigter Ausrüstung im korrekten Radius zurück.", 1
                                    ),
                                    criterion("Die Methode scan() in der Klasse gibt null zurück, sollte es keine MineBots mit beschädigter Ausrüstung im Radius geben.", 1
                                    ),
                                    criterion("Die Methode repair() equippt eine neue Kamera/Batterie, sollte die alte kaputt sein.", 1
                                    ),
                                    criterion("Die Methode repair() entfernt alle beschädigten Equipments.", 1
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
