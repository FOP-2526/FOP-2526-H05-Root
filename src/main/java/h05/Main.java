package h05;

import fopbot.DrawingRegistry;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.Wall;
import fopbot.World;
import h05.entitity.Gear;
import h05.entitity.Loot;
import h05.entitity.MiningRobot;
import h05.equipment.Battery;
import h05.ui.FogDrawing;
import h05.ui.GearDrawing;
import h05.ui.LootDrawing;
import h05.ui.WallFogDrawing;

import java.util.Comparator;
import java.util.Map;

public class Main {

    private static int getDrawingPriority(FieldEntity entity) {
        return switch (entity) {
            case Wall w -> 0;
            case Gear g -> 1;
            case Loot l -> 1;
            case Robot c -> 2;
            case Fog fog -> 3;
            default -> -1;
        };
    }

    public static void main(String[] args) {
        int width = 10;
        int height = 10;
        World.setSize(width, height);
        World.getGlobalWorld().setDrawingRegistry(
            DrawingRegistry.builder(DrawingRegistry.DEFAULT)
                .addAll(Map.ofEntries(
                    Map.entry(Wall.class, new WallFogDrawing()),
                    Map.entry(Gear.class, new GearDrawing()),
                    Map.entry(Loot.class, new LootDrawing()),
                    Map.entry(Fog.class, new FogDrawing())
                ))
                .build(Comparator.comparingInt(Main::getDrawingPriority))
        );

        int rX = 5;
        int rY = 5;
        boolean fog = true;
        if (fog) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    World.getGlobalWorld().placeEntity(new Fog(x, y));
                }
            }
        }

        World.getGlobalWorld().placeEntity(new Gear(3, 3, new Battery()));
        World.setVisible(true);
        MiningRobot steve = new MiningRobot(rX, rY);
        steve.move();

        World.setVisible(true);
    }
}
