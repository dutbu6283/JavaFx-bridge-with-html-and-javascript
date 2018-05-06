# JavaFx-bridge-with-html-and-javascript

Info: Code example of how to use javafx webengine and how to set it up to enable java and javascript code communication.

Useful information when implementing java/javascript bridge: 

How to send java String to javascript function: 
https://stackoverflow.com/a/50199663/9748367

Bug with javafx webengine that disable anonymous object creation on: 
JSObject jsObject = JSObject) webEngine.executeScript("window");
jsObject.setMember("java", new Object()); 
                                                                     
https://bugs.openjdk.java.net/browse/JDK-8170085

