package pt.isec.pd.projetopd.cliente.ui.resources;

import javafx.scene.media.Media;

import java.util.HashMap;

public class MediaManager {
    private MediaManager() { }

    private static final HashMap<String, Media> mediaFiles = new HashMap<>();

    public static Media getMedia(String filename) {
        Media media = mediaFiles.get(filename);
        if (media == null) {
            try {
                media = new Media(MediaManager.class.getResource("media/" + filename).toString());
                mediaFiles.put(filename, media);
            } catch (Exception e) {
                return null;
            }
        }
        return media;
    }

    public static Media getExternalMedia(String filename) {
        Media media = mediaFiles.get(filename);
        if (media == null) {
            try {
                media = new Media(filename);
                mediaFiles.put(filename, media);
            } catch (Exception e) {
                return null;
            }
        }
        return media;
    }

    public static void purgeMedia(String filename) {
        mediaFiles.remove(filename);
    }
}
