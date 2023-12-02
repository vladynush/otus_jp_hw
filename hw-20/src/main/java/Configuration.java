import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.Map;

public class Configuration {
    private static final Config config = ConfigFactory.load();

    public static final String host = config.getString("host");
    public static final int port = config.getInt("port");
    public static final int executorsNum = config.getInt("executorsNum");
    public static final String EOF = config.getString("EOF");

    public static final String filesBasePath = config.getString("filesBasePath");

    public static final Map<String, String> uriToFileMapping = Map.of(
            "/marvel", buildFilePath("marvel.txt"),
            "/dc", buildFilePath("dc.txt"),
            "/bubble", buildFilePath("bubble.txt")
    );

    private static String buildFilePath(String fileName) {
        return filesBasePath + File.separator + fileName;
    }
}