package splat;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import splat.factories.*;
import splat.multiplayer.Communicator;
import splat.multiplayer.GameState;
import splat.multiplayer.InGameListenerThread;
import splat.multiplayer.OtherSquidsManager;
import splat.objects.mainChar.MainLign;
import splat.updating.Updater;

import java.awt.Color;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Logger;

public class Main extends BasicGame{
    public static final boolean DEBUG = false;
    public static boolean CONTROLLER_USED;
    public static int squidsKilled = 0;
    public static GameModes modes;

    //Helpers and factories
    public TileMapHelper mapHelper;
    public MainLign mainChar;
    public ColorSplatFactory splatFactory;
    public MusicPlayer musicPlayer;
    public SoundEffectPlayer soundPlayer;
    private NPCManager npcManager;
    private InkShootHelper splatHelper;
    private GUIRenderer guiRenderer;

    //Render and other stuff
    private Updater updater = new Updater();
    private Image background;
    private boolean shootButtonDown;

    //Network stuff
    public GameState gameState;
    public Communicator communicator;
    public OtherSquidsManager networkedSquidManager;
    public int players = 0;
    private int framesPassed = 0;
    private Thread inGameUpdateListenerThread;

    public enum GameModes {
        SINGLEPLAYER, MULTIPLAYER;
    }

    public Main(String title, boolean controllerUsed, String mode) {
        super(title);
        CONTROLLER_USED = controllerUsed;
        if (mode != null) {
            switch (mode.toLowerCase()) {
                case "singleplayer":
                    modes = GameModes.SINGLEPLAYER;
                    break;
                case "multiplayer":
                    modes = GameModes.MULTIPLAYER;
                    break;
            }
        }else {
            modes = GameModes.SINGLEPLAYER;
        }
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        //Load stuff
        soundPlayer = new SoundEffectPlayer();
        mapHelper = new TileMapHelper();
        splatFactory = new ColorSplatFactory();
        mainChar = new MainLign(Color.ORANGE, this);
        npcManager = new NPCManager(this);
        musicPlayer = new MusicPlayer();
        splatHelper = new InkShootHelper(this);
        guiRenderer = new GUIRenderer(gameContainer);
        background = new Image("data/back.jpg").getScaledCopy(gameContainer.getWidth(), gameContainer.getHeight());

        updater.addUpdateAble(mainChar);
        updater.addUpdateAble(splatFactory);
        updater.addUpdateAble(npcManager);

        musicPlayer.start();

        //Multiplayer
        if (modes == GameModes.MULTIPLAYER){
            gameState = GameState.LOBBY;
            communicator = new Communicator("127.0.0.1");
            networkedSquidManager = new OtherSquidsManager(this);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Logger.getLogger("Network thread").info("Starting networking.");
                    try {
                        communicator.out.write(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                    do {
                        try {
                            int code = communicator.in.read();
                            if (code == 0){
                                players = communicator.in.read();
                            }else if (code == 2){
                                //Construct other squids
                                networkedSquidManager.startSquidCreation();
                                while (networkedSquidManager.tempColorArray[networkedSquidManager.tempColorArray.length-1] == null){
                                    int i = communicator.in.read();
                                    if (i == 1){
                                        int clientId = communicator.in.read();
                                        int colorId = communicator.in.read();
                                        if (colorId == 0)
                                            networkedSquidManager.tempColorArray[clientId] = Color.ORANGE;
                                        else
                                            networkedSquidManager.tempColorArray[clientId] = Color.BLUE;
                                    }
                                }
                                networkedSquidManager.finalizeSquidCreation();
                                //Start game
                                inGameUpdateListenerThread = new InGameListenerThread(Main.this);
                                inGameUpdateListenerThread.start();
                                gameState = GameState.IN_GAME;
                                return;
                            }else if (code == 3){
                                System.out.println("Client ID of this client: " + communicator.in.read());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                    }while (true);
                }
            }).start();
        }
    }

    @Override
    public void update(GameContainer gameContainer, int tpf) throws SlickException {
        if (modes == GameModes.SINGLEPLAYER || gameState == GameState.IN_GAME) {
            if (mainChar.health > 0)
                updater.update(gameContainer, tpf);

            //Exit check
            Input input = gameContainer.getInput();
            if (input.isKeyDown(Input.KEY_ESCAPE)) {
                System.exit(1);
            }

            if ((input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || (input.isButtonPressed(5, 0) && !shootButtonDown && CONTROLLER_USED)) && !mainChar.inkTank.isEmpty()) {
                shootButtonDown = true;
                //TODO IMPORTANT!!! SPLAT LOCATION PLACEMENT!!!!!
                //splatFactory.createNewSplat(input.getMouseX() + (int)mainChar.x * -1, input.getMouseY() + (int)mainChar.y * -1, Color.ORANGE);
                //mainChar.inkTank.useUp(1);
                int absoluteCharX = (int) mainChar.x * -1 + (gameContainer.getWidth() / 2) - 12, absoluteCharY = (int) mainChar.y * -1 + (gameContainer.getHeight() / 2) - 12;
                mainChar.inkTank.useUp(splatHelper.generateSplatPath(absoluteCharX, absoluteCharY,
                        input.getMouseX() + (int) mainChar.x * -1, input.getMouseY() + (int) mainChar.y * -1, mainChar.rotation) / 3);
                soundPlayer.playSound(SoundEffectPlayer.sounds.SHOOT);
            }

            //Controller debug
            if (CONTROLLER_USED) {
                if (!input.isButtonPressed(5, 0)) {
                    shootButtonDown = false;
                }

                if (DEBUG) {
                    System.out.println("Axis " + input.getAxisName(0, 4) + ":");
                    System.out.println(input.getAxisValue(0, 4));
                    System.out.println("Axis " + input.getAxisName(0, 5) + ":");
                    System.out.println(input.getAxisValue(0, 5));
                }
            }

            if (modes == GameModes.MULTIPLAYER){
                networkedSquidManager.update(gameContainer, tpf);
                if (framesPassed == 6){
                    framesPassed = 0;
                    //Send an update to server
                    try {
                        communicator.out.write(10);
                        communicator.out.write((int) mainChar.x);
                        communicator.out.write((int) mainChar.y);
                        communicator.out.write(11);
                        communicator.out.write((int) mainChar.rotation);
                    } catch (SocketException se){
                        System.err.println("Server shutdown. Exiting.");
                        System.exit(2);
                    } catch (IOException e) {
                        System.err.println("Failed to send update to server:");
                        e.printStackTrace();
                    }
                }else {
                    framesPassed ++;
                }
            }
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics g) throws SlickException {
        if (modes == GameModes.SINGLEPLAYER || gameState == GameState.IN_GAME) {
            if (mainChar.health > 0) {
                background.draw(0, 0);
                mapHelper.getBackgroundMap().render((int) mainChar.x, (int) mainChar.y);
                splatFactory.renderSplats((int) mainChar.x, (int) mainChar.y);
                npcManager.renderSquids((int) mainChar.x, (int) mainChar.y);

                mainChar.getRepresentation().draw((gameContainer.getWidth() / 2) - (mainChar.getRepresentation().getWidth() / 2), (gameContainer.getHeight() / 2) - (mainChar.getRepresentation().getHeight() / 2));
                mainChar.inkTank.render(gameContainer);

                guiRenderer.renderGui((int) mainChar.health, 100);
            } else {
                g.drawString("GAME OVER!", 50, 50);
            }

            g.drawString("Squids splatted: " + squidsKilled, 20, gameContainer.getHeight() - 20 - g.getFont().getHeight("Squids"));
        }else if (gameState == GameState.LOBBY){
            g.drawString("Players in lobby: " + players, 100, 100);
        }
    }

    public static void main(String [] args){
        String mode = null;
        if (args.length > 0){
            mode = args[0];
        }
        try {
            AppGameContainer cont = new AppGameContainer(new Main("Splatoon 2D", false, mode));
            //cont.setDisplayMode(cont.getScreenWidth(), cont.getScreenHeight(), true);
            cont.setDisplayMode(800, 500, false);
            cont.setVSync(true);
            cont.setShowFPS(true);
            cont.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
