package tk.mamong_us;

public class Main {
    private static Program program;

    public static GameState gameState = GameState.MAIN_MENU;
    public static GameState lastState = GameState.MAIN_MENU;
    public static GameState.MultiplayerState mpState = null;

    public static void main(String[] args) {
        System.out.println("Using manifold");
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.progressBar.setString("Initializing program...");
        program = new Program();
        program.icon(Assets.icon);
        Assets.pBpp(splashScreen, "Loading assets...");
        Assets.loadAssets(splashScreen);
        splashScreen.setVisible(false);
        program.make();
    }

    public static Program getProgram() {
        return program;
    }

    public static int getMidX() {
        return (int) (program.getWindow().getWidth()/(program.getWindow().getScale()*2));
    }

    public static int getMidY() {
        return (int) (program.getWindow().getHeight()/(program.getWindow().getScale()*2));
    }
}
