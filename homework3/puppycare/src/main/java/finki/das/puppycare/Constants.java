package finki.das.puppycare;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String fileBasePath = System.getProperty("user.dir") + "/src/main/resources/static/photos";

    @SuppressWarnings("ConstantConditions")
    public static List<File> fileDetails(String target) {
        File file = new File(Constants.fileBasePath, target);

        return Arrays.asList(file.listFiles());
    }
}
