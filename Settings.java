import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Settings extends Red {
    public void start () {
        Stage about = new Stage();                              // new stage
            Pane aboutPane = new Pane();                            // new pane
            VBox vb = new VBox();                                   // new vbox
            Circle icon = new Circle(75);                           // new circle
            FadeTransition ft = new                                 // making a fade transition, for the icon
                FadeTransition(Duration.millis(3000), icon);        //
            ft.setFromValue(1.0);                                   // fades from opaque
            ft.setToValue(0.0);                                     // to transparent
            ft.setCycleCount(Timeline.INDEFINITE);                  // run the animation forever
            ft.setAutoReverse(true);                                // reverse it
            ft.play();                                              // play the animation
            icon.setFill(Color.RED);                                // set the fill on the icon
            vb.getChildren().add(icon);                             // add the icon
            vb.getChildren().add(new Label("Red Web Browser"));     // label
            vb.getChildren().add(new Label("'Pathetic Penguin'"));  // label
            vb.getChildren().add(new Label("This is free software, distributed without any promise of \nsupport or fitness for a specific task. Licensed under the \nGNU Public License v3."));
            aboutPane.getChildren().add(vb);                        // add the vbox to the pane
            Scene aboutScene = new Scene(aboutPane);                // new scene
            about.setScene(aboutScene);                             // set scene
            about.setTitle("About");                                // set title
            about.setMinWidth(250);                                 // set width
            about.setMinHeight(125);                                // set height
            about.show();                                           // show
    }
}