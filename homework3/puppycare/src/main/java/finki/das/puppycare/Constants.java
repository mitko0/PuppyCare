package finki.das.puppycare;

import java.io.File;
import java.util.Arrays;

public class Constants {
    public static final String fileBasePath = System.getProperty("user.dir") + "/src/main/resources/static/photos";
    public static final String photosFolder = "/photos";

    public static String[] fileDetails(String target) {
        File file = new File(Constants.fileBasePath, target);
        File[] childFiles = file.listFiles();

        if (childFiles == null)
            return new String[0];

        return Arrays.stream(childFiles)
                .map(f -> String.format("%s/%s/%s", photosFolder, target, f.getName()))
                .toArray(String[]::new);
    }
}
