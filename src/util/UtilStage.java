package util;


import javafx.scene.image.Image;

public class UtilStage {

    public static Image getIconStage() {
        Image _image;
        System.out.println("ASUIQsadadasd");
        _image = new Image(UtilStage.class.getClass().getResourceAsStream("/assets/images/logo-principal.png"));

        return _image;
    }
}
