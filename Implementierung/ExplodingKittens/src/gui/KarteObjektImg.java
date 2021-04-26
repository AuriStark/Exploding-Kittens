package gui;

import client.Mensch;
import client.Spieler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class KarteObjektImg {

    @FXML
    private ImageView karteImage;

    private int id;
    private Mensch sp;

    public void set(String path, int id, Mensch sp){
        karteImage.setImage(new Image(this.getClass().getResource(path).toString()));
        this.id = id;
        this.sp = sp;
    }


    public void click(MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            sp.set_karte(id);
        }
    }
}
