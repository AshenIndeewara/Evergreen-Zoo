package com.evergreen.zoo.util;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lombok.AllArgsConstructor;
import org.controlsfx.control.Notifications;

@AllArgsConstructor
public class ShowNotification extends Thread{
    private String title;
    private String text;
    private String image;
    private String actione;

    public void run() {
        Image img = new Image(ShowNotification.class.getResource("/asserts/images/"+image).toExternalForm());
        ImageView imageView = new ImageView(img);

        imageView.setFitWidth(40);
        imageView.setFitHeight(40);

        Notifications notification = Notifications.create()
                .title(title)
                .text(text)
                .graphic(imageView)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(event -> System.out.println(actione));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(10.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.color(0.0, 0.0, 0.0, 0.5)); // Semi-transparent black shadow
        imageView.setEffect(dropShadow);

        notification.darkStyle();
        Platform.runLater(() -> notification.show());
    }
}
