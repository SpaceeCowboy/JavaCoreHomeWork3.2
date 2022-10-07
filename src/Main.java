import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;


public class Main {
    public static void main(String[] args) {

        List<GameProgress> gameProgresses = Arrays.asList(
                new GameProgress(5, 5, 20, 12.2),
                new GameProgress(6, 4, 55, 43.2),
                new GameProgress(10, 3, 23, 12.3)
        );
        GameProgress.saveGame("D:/Games/savegames/", gameProgresses);
        GameProgress.zipFiles("D:/Games/savegames/", "zip_output.zip", gameProgresses);
        GameProgress.deleteSaveFile("D:/Games/savegames/");
        GameProgress.openZip("zip_output.zip", "D:/Games/savegames/");
        GameProgress.openProgress("D:/Games/savegames/", gameProgresses);


    }
}
