package h05;

import fopbot.Block;
import fopbot.Coin;
import fopbot.DrawingRegistry;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.Wall;
import fopbot.World;

import java.util.Comparator;
import java.util.Map;

public class Main {

    private static int getDrawingPriority(FieldEntity entity) {
        return switch (entity) {
            case Wall w -> 0;
            case AbstractEquipment e -> 1;
            case Robot c -> 2;
            case Coin c -> 3;
            case Block b -> 4;
            default -> Integer.MAX_VALUE;
        };
    }

    public static void main(String[] args) {
        int width = 10;
        int height = 10;
        World.setSize(width, height);
        World.getGlobalWorld().setDrawingRegistry(
                DrawingRegistry.builder(DrawingRegistry.DEFAULT)
                        .addAll(Map.ofEntries(
                                Map.entry(Battery.class, new EquipmentDrawing()),
                                Map.entry(Camera.class, new EquipmentDrawing())
                        ))
                        .build(Comparator.comparingInt(Main::getDrawingPriority))
        );

        int[] durabilities = {90, 50, 20, 0};
        for (int i = 0; i < durabilities.length; i++) {
            Battery battery = new Battery(5 + i, 5);
            battery.setDurability(durabilities[i]);
        }
        World.setVisible(true);
    }
}
