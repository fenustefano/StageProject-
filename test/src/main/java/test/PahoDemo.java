package test;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.google.gson.Gson ;
//import java.lang.Thread;


public class PahoDemo  { /* implementare interfaccia PAHO */

	MqttClient client;
	MqttClient subClient; 
	
	  public PahoDemo() {}

	  public static void main(String[] args) {
	    
		try {
			new PahoDemo().send();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
	    
		   
	    
	  }

	  public void send() throws InterruptedException {
	    try {
	      client = new MqttClient("tcp://localhost:1883", "pahomqttpublish1");
	      client.connect();

	      /* lettura messaggi ( subscriber) */
	      client.subscribe("conf");
		  client.setCallback( new SimpleMqttCallBack() );
	      
	      MqttMessage message = new MqttMessage();
	      message.setQos(2); //excactly once 
	      
	      //for (int i=0; i<5; i++) {
	      message.setPayload("{\"TipoMex\":\"text\",\"Period\":10,\"TextMex\":\"ciao\",\"NumMex\":5}".getBytes());
	      client.publish("conf", message);
		      
		    //  Thread.sleep(5000);
	    	  
	    //  } 
	      
	    /* prendo in input un json */
	      
	      MqttMessage message2 = new MqttMessage();
	      
	      Gson gson = new Gson();
	      ConfJson target2 = gson.fromJson(message.toString(), ConfJson.class); // deserializes json into target2
	      
	      
	      String tipo =  target2.getTipoMex();
	      
	      if (tipo.equals("text")) {
	    	  message2.setPayload("Testo".getBytes());
		      client.publish("conf", message2);
	      }else {
	    	  
	    	  message2.setPayload("Numero".getBytes());
		      client.publish("conf", message2);
	      }
	      
	      //message.setPayload("Prova".getBytes());
	      //client.publish("PahoDemo/test", message);
	      //client.disconnect();
	    } catch (MqttException e) {
	      e.printStackTrace();
	    }
	  }
	  

}
