package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SpielerObjektImg {
        @FXML
        public Label spielerName;
        @FXML
        public ImageView userImage;

        /**
         * Initialize die Profil Bilder der Spieler in dem Spielraum.
         * @param name Name des Spielers
         * @param path Path von dem Bild
         */
        public void set(String name, String path){
            userImage.setImage(new Image(this.getClass().getResource(path).toString()));
            spielerName.setText(name);
        }
}
