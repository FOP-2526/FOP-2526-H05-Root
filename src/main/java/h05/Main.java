package h05;

import fopbot.Block;
import fopbot.Coin;
import fopbot.DrawingRegistry;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.Wall;
import fopbot.World;
import h05.iu.EquipmentDrawing;
import h05.iu.EquippedBotDrawing;
import h05.iu.FogDrawing;
import h05.iu.WallWithFogDrawing;

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
            case Fog fog -> 5;
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
                    Map.entry(EquippedBot.class, new EquippedBotDrawing()),
                    Map.entry(Battery.class, new EquipmentDrawing()),
                    Map.entry(Camera.class, new EquipmentDrawing()),
                    Map.entry(Wall.class, new WallWithFogDrawing()),
                    Map.entry(Fog.class, new FogDrawing())
                ))
                .build(Comparator.comparingInt(Main::getDrawingPriority))
        );

        //   MazeGenerator.generate();

        int rX = 5;
        int rY = 5;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                World.getGlobalWorld().placeEntity(new Fog(x, y));
            }
        }
        World.getGlobalWorld().placeEntity(new Battery(3, 3));
        World.setVisible(true);
        EquippedBot r = new EquippedBot(rX, rY);
r.turnLeft();

    }
}
