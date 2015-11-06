package splat.client;

import splat.client.factories.*;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import splat.client.objects.mainChar.MainLign;
import splat.client.updating.Updater;

import java.awt.Color;

public class Main extends BasicGame{
    public TileMapHelper mapHelper;
    public MainLign mainChar;
    private Updater updater = new Updater();
    public ColorSplatFactory splatFactory;
    private NPCManager npcManager;
    private MusicPlayer musicPlayer;
    private InkShootHelper splatHelper;
    private Image background;
    GUIRenderer guiRenderer;
    public static int squidsKilled = 0;

    public Main(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        //Load stuff
        mapHelper = new TileMapHelper();
        splatFactory = new ColorSplatFactory();
        npcManager = new NPCManager(this);
        mainChar = new MainLign(Color.ORANGE, this);
        musicPlayer = new MusicPlayer();
        splatHelper = new InkShootHelper(splatFactory);
        guiRenderer = new GUIRenderer(gameContainer);
        background = new Image("data/back.jpg").getScaledCopy(gameContainer.getWidth(), gameContainer.getHeight());

        updater.addUpdateAble(mainChar);
        updater.addUpdateAble(splatFactory);
        updater.addUpdateAble(npcManager);

        musicPlayer.start();
    }

    @Override
    public void update(GameContainer gameContainer, int tpf) throws SlickException {
        if (mainChar.health > 0)
            updater.update(gameContainer, tpf);

        //Exit check
        Input input = gameContainer.getInput();
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            musicPlayer.stop();
            System.exit(1);
        }

        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && !mainChar.inkTank.isEmpty()){
            //TODO IMPORTANT!!! SPLAT LOCATION PLACEMENT!!!!!
            //splatFactory.createNewSplat(input.getMouseX() + (int)mainChar.x * -1, input.getMouseY() + (int)mainChar.y * -1, Color.ORANGE);
            //mainChar.inkTank.useUp(1);
            int absoluteCharX = (int)mainChar.x * -1 + (gameContainer.getWidth() / 2) - 12, absoluteCharY = (int)mainChar.y * -1 + (gameContainer.getHeight() / 2) -12;
            mainChar.inkTank.useUp(splatHelper.generateSplatPath(absoluteCharX, absoluteCharY,
                    input.getMouseX() + (int) mainChar.x * -1, input.getMouseY() + (int)mainChar.y * -1, mainChar.rotation));
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics g) throws SlickException {
        if (mainChar.health > 0) {
            background.draw(0, 0);
            mapHelper.getBackgroundMap().render((int) mainChar.x, (int) mainChar.y);
            splatFactory.renderSplats((int) mainChar.x, (int) mainChar.y);
            npcManager.renderSquids((int) mainChar.x, (int) mainChar.y);

            mainChar.getRepresentation().draw((gameContainer.getWidth() / 2) - (mainChar.getRepresentation().getWidth() / 2), (gameContainer.getHeight() / 2) - (mainChar.getRepresentation().getHeight() / 2));
            mainChar.inkTank.render(gameContainer);

            guiRenderer.renderGui((int) mainChar.health, 100);
        }else {
            g.drawString("GAME OVER!", 50, 50);
        }

        g.drawString("Squids splatted: " + squidsKilled, 20, gameContainer.getHeight() - 20 - g.getFont().getHeight("Squids"));
    }

    public static void main(String [] args){
        try {
            AppGameContainer cont = new AppGameContainer(new Main("Splatoon 2D"));
            //cont.setDisplayMode(cont.getScreenWidth(), cont.getScreenHeight(), true);
            cont.setDisplayMode(800, 500, false);
            cont.setVSync(true);
            cont.setShowFPS(false);
            cont.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
