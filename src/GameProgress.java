import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;
    private static List<String> listOfSaveFiles = new ArrayList<>();
    private static String fileType = ".dat";
    private static String nameSaveFile = "save";


    public GameProgress(int health, int weapons, int lvl, double distance) {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;

    }

    public static void saveGame(String path, List<GameProgress> gameProgresses) {
        for (int i = 1; i < gameProgresses.size() + 1; i++) {
            try (FileOutputStream fos = new FileOutputStream(path + nameSaveFile + i + fileType);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(gameProgresses.get(i - 1));
                System.out.println("Идет сохранение...");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void deleteSaveFile(String path) {
        File folderForSaveFile = new File(path);
        if (folderForSaveFile.isDirectory()) {
            for (File item : folderForSaveFile.listFiles()) {
                if (item.getName().endsWith(fileType) && item.isFile()) {
                    item.delete();
                    System.out.println("Файл " + item.getName() + " удален");
                } else {
                    System.out.println("Файл " + item.getName() + " не может быть удален");
                }
            }
        }
    }

    public static void openZip(String zipName, String openZipPath) {
        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(openZipPath + zipName))) {
            ZipEntry entry;
            String name;
            while ((entry = zip.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(openZipPath + name);
                for (int c = zip.read(); c != -1; c = zip.read()) {
                    fout.write(c);
                }
                fout.flush();
                zip.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void zipFiles(String path, String nameZip, List<GameProgress> gameProgresses) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path + nameZip))) {
            for (int item = 1; item < gameProgresses.size() + 1; item++) {
                FileInputStream fis = new FileInputStream(path + nameSaveFile + item + fileType);
                ZipEntry zipEntry = new ZipEntry(nameSaveFile + item + fileType);
                zout.putNextEntry(zipEntry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void openProgress(String path, List<GameProgress> gameProgresses) {
        for (int i = 1; i < gameProgresses.size() + 1; i++) {
            try (FileInputStream fis = new FileInputStream(path + nameSaveFile + i + fileType);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                GameProgress gp = gameProgresses.get(i - 1);
                gp = (GameProgress) ois.readObject();
                System.out.println(gp);

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }
}
