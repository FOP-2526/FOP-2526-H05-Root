package h05;

import fopbot.Block;
import fopbot.Coin;
import fopbot.Direction;
import fopbot.DrawingRegistry;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.Wall;
import fopbot.World;
import h05.maze.MazeGenerator;

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
                    Map.entry(Battery.class, new EquipmentDrawing()),
                    Map.entry(Camera.class, new EquipmentDrawing()),
                    Map.entry(Wall.class, new WallWithFogDrawing()),
                    Map.entry(Fog.class, new FogDrawing())
                ))
                .build(Comparator.comparingInt(Main::getDrawingPriority))
        );
        MazeGenerator.generate();
        World.setVisible(true);

        int rX = 5;
        int rY = 5;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == rX && y == rY) {
                    continue;
                }
                World.getGlobalWorld().placeEntity(new Fog(x, y));
            }
        }
        EquippedBot r = new EquippedBot(rX, rY, Direction.UP);
    }
}
