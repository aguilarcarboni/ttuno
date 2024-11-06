package leomessi.uno;

import com.jfoenix.assets.JFoenixResources;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import leomessi.uno.GUI.SplashController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application {
	private static Logger logger = LoggerFactory.getLogger(App.class);

	private static Stage primaryStage;

	@FXMLViewFlowContext
	private ViewFlowContext flowContext;

	public static void main(String[] args) {
		logger.info("Main started");
		launch(App.class, args);
		logger.info("Main ended");
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	static void setPrimaryStage(Stage pStage) {
		App.primaryStage = pStage;
	}

	@Override
	public void start(Stage stage) throws Exception {

		Flow flow = new Flow(SplashController.class);
		DefaultFlowContainer container = new DefaultFlowContainer();
		
		flowContext = new ViewFlowContext();
		flowContext.register("Stage", stage);
		flowContext.register("ContentPane", container.getView());
		flow.createHandler(flowContext).start(container);

		double height = 625.0;
		double width = 1000.0;

		Scene scene = new Scene(container.getView(), width, height);
		final ObservableList<String> stylesheets = scene.getStylesheets();
		stylesheets.addAll(JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
				JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
				App.class.getResource("/css/uno-dark.css").toExternalForm());

		setPrimaryStage(stage);

		stage.setTitle("UNO Game");
		stage.setScene(scene);
		stage.show();
	}
}