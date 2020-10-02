package tk.q11mc.io;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {

    public static void save(String path, @NotNull JSONObject jsonObject) {
        File file = new File(path);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonObject.toJSONString());
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }
}
