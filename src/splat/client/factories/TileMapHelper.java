package splat.client.factories;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Handles everything related to tilemap.
 */
public class TileMapHelper {
    private TiledMap backgroundMap;
    private boolean [] [] blockedTiles;
    private static final int tileSize = 32;

    public TileMapHelper() {
        try {
            backgroundMap = new TiledMap("data/tile/map.tmx");
        } catch (SlickException e) {
            System.exit(-1);
        }
        //Init blocked tiles.
        blockedTiles = new boolean[backgroundMap.getWidth()][backgroundMap.getHeight()];
        int layerID = backgroundMap.getLayerIndex("Collidables");
        for (int xAxis = 0; xAxis < backgroundMap.getWidth(); xAxis ++){
            for (int yAxis = 0; yAxis < backgroundMap.getHeight(); yAxis ++){
                int tileID = backgroundMap.getTileId(xAxis, yAxis, layerID);
                String isBlocked = backgroundMap.getTileProperty(tileID, "blocked", "false");
                if (isBlocked.equals("true")){
                    blockedTiles [xAxis] [yAxis] = true;
                }
            }
        }
    }

    public TiledMap getBackgroundMap() {
        return backgroundMap;
    }

    public boolean isBlocked(double x, double y){
        try {
            int colX = (int)Math.round(x / tileSize), colY = (int)Math.round(y / tileSize);
            return blockedTiles[colX][colY];
        }catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }
}
