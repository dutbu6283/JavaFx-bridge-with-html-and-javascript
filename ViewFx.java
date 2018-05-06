/**
 * Creator: Dutsadi Bunliang, Created: 2018-05-06 13:08:04 Updated: 
 */
package JavaFx_bridge_with_html_and_javascript;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;


public class ViewFx extends Application {
	
	//VARIABLES
	private WebView webView;
	private WebEngine webEngine; 
	private WebEngineListener webEngineListener;
	private JsBridge jsBridge;

	//CONTRUCTOR

	//METHODS
	@Override
	public void start(Stage stage) {
		webView = new WebView();
		webEngine = webView.getEngine();
		webEngine.load(getClass().getResource("view.html").toExternalForm());
		jsBridge = new JsBridge();
		webEngineListener = new WebEngineListener(jsBridge);
		webEngine.getLoadWorker().stateProperty().addListener(webEngineListener);
		
        stage.setScene(new Scene(webView));
        stage.setWidth(600);
        stage.setHeight(700);
        stage.show();
	}
	
	//INNER CLASS
	private class WebEngineListener implements ChangeListener<State> {
		//VARIABLES
		private JsBridge jsBridge;
		private JSObject jsobj;
		
		//CONSTRUCTOR
		private WebEngineListener(JsBridge jsBridge) {
			this.jsBridge = jsBridge;
		}
		
		//METHODS
		@Override
		public void changed(ObservableValue<? extends State> observable, State oldState, State newState) {
			if(newState == State.SUCCEEDED) {
				jsobj = (JSObject) webEngine.executeScript("window");
				jsobj.setMember("java", jsBridge);
			}
		}
	}
	
	public class JsBridge {
		//VARIABLES
		
		//CONTRUCTOR

		//METHODS
		public void onSendMsg(String msg) {
			webEngine.executeScript("onRecieveMsg(" + toJavaScriptString(msg) + ")");
		}
		
		private String toJavaScriptString(String value) {
		    value = value.replace("\u0000", "\\0")
		            .replace("'", "\\'")
		            .replace("\\", "\\\\")
		            .replace("\"", "\\\"")
		            .replace("\n", "\\n")
		            .replace("\r", "\\r")
		            .replace("\t", "\\t");
		    return "\""+ value+"\"";
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
