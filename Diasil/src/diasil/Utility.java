package diasil;

import java.util.List;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utility
{
    public static List<String> readAllLines(String file_path)
    {
        try
        {
            return Files.readAllLines(Paths.get(file_path), StandardCharsets.US_ASCII);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
