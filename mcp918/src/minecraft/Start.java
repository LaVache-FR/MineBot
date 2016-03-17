
import java.util.Arrays;
import minebot.util.Out;
import net.minecraft.client.main.Main;

public class Start {
    public static void main(String[] args) {
        Out.log(Arrays.asList(args));
        Out.log(System.getProperty("java.library.path"));
        //System.setProperty("java.library.path", System.getProperty("user.home") + "/Dropbox/MineBot/mcp918/jars/versions/1.8.8/1.8.8-natives/");
        //Out.log(System.getProperty("java.library.path"));
        Main.main(concat(new String[]{"--version", "mcp", "--accessToken", "", "--assetsDir", "assets", "--assetIndex", "1.8", "--userProperties", "{}", "--username", "Elon_Musk", "--uuid", "5ee18e4e014347b39a6bdd544f28f4b4"}, args));
    }
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}
