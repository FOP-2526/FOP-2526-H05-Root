package h05;

import fopbot.Block;
import fopbot.Coin;
import fopbot.DrawingRegistry;
import fopbot.FieldEntity;
import fopbot.Robot;
import fopbot.Wall;
import fopbot.World;
import h05.entitity.Loot;
import h05.entitity.MineBot;
import h05.ui.FogDrawing;
import h05.ui.LootDrawing;
import h05.ui.WallFogDrawing;

import java.util.Comparator;
import java.util.Map;

public class Main {

    private static int getDrawingPriority(FieldEntity entity) {
        return switch (entity) {
            case Wall w -> 0;
            case Loot l -> 1;
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
                    Map.entry(Wall.class, new WallFogDrawing()),
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

        World.getGlobalWorld().placeEntity(new Loot(3, 3, new Battery()));
        World.setVisible(true);
        MineBot steve = new MineBot(rX, rY);
        steve.move();

        World.setVisible(true);
    }
}
