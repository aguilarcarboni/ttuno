package leomessi.uno.GUI;

import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import com.jfoenix.controls.JFXButton;
import leomessi.uno.App;
import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/Splash.fxml", title = "TTUno Splash")
public class SplashController {
    
    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private JFXButton enterButton;

    private FlowHandler flowHandler;

    @PostConstruct
    public void init() {
        enterButton.setOnAction(event -> {
            try {
                Flow flow = new Flow(MainController.class);
                DefaultFlowContainer container = new DefaultFlowContainer();
                flowHandler = flow.createHandler(context);
                flowHandler.start(container);
                
                Scene scene = App.getPrimaryStage().getScene();
                scene.setRoot(container.getView());
                
            } catch (FlowException e) {
                e.printStackTrace();
            }
        });
    }
} 