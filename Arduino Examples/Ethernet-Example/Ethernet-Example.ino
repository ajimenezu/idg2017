//-------------------------------------Libs------------------------------------------------#
#include <Ethernet.h>
#include <EthernetClient.h>
#include <SPI.h>
#include <Servo.h>  // servo library
//-----------------------------------Ethernet Shield---------------------------------------#
EthernetClient client = EthernetClient();
byte mac[] = {0x90, 0xA2, 0xDA, 0x0E, 0x98, 0x91};
char server[] = "idg2017.herokuapp.com";

//-----------------------------------Led---------------------------------------------------#
const int led = 3;  

//-----------------------------------Servo-------------------------------------------------#
Servo lockDoor; //4

//-----------------------------------Timbre------------------------------------------------#
const int timbre = 5;
const int buzzer = 6;

//-----------------------------------Timbre------------------------------------------------#
const int temperatura = A0;

//-----------------------------------Setup Method------------------------------------------#
void setup() {
  // Serial Connection
  Serial.begin(9600);

  // Ethernet Connection
  Ethernet.begin(mac);
  Serial.println(Ethernet.localIP()); // Shows the IP Address

  // Led
  pinMode(led,OUTPUT);

  // ServoMotor
  lockDoor.attach(4,900,2100);  // Puerto 4

  // Doorbell
  pinMode(timbre,INPUT);
  pinMode(buzzer,OUTPUT);
}
//----------------------------------Functions----------------------------------------------#
float getVoltage(int pin) 
{
  return (analogRead(pin) * 0.004882814); 
}
//-----------------------------------Thermometer method------------------------------------#
void thermometer(){
  float voltage, degreesC;
  voltage = getVoltage(temperatura);  //  Get the volage from the analog pin
  degreesC = (voltage - 0.5) * 100.0; //  Convert the voltage to celsius degrees
  if(client.connect(server,80)){  //  connect with the API-REST
    client.println("POST /AddTemperatura?ID=0&Dato="+String(degreesC)+" HTTP/1.1"); // Query #change the ID
    client.println("Host: idg2017.herokuapp.com"); // Host to query
    client.println();
    client.stop();  //  Stop the conexion to the API
    Serial.println(degreesC);
  }else{
    Serial.println("Error de conexion");
  }
}

//-----------------------------------Doorbell Method---------------------------------------#
void doorbell(){
  if(digitalRead(timbre) == LOW){
    tone(buzzer,'c',113);
    delay(113);
    Serial.println("Sending..");
    if(client.connect(server,80)){  //  connect with the API-REST
      client.println("POST /UpdateTimbre?ID=0&Estado=1 HTTP/1.1"); // Query #change the ID
      client.println("Host: idg2017.herokuapp.com"); // Host to query
      client.println();
      client.stop();  //  Stop the conexion to the API
    }else{
      Serial.println("Error de conexion");
    }
    Serial.println("Done..");
    
    tone(buzzer,'a',113);
    delay(113);
  }
}

//-----------------------------------Door Method-------------------------------------------#
void doors(){
  String mensaje = "";
  if(client.connect(server,80)){  //  connect with the API-REST
    client.println("POST /GetPuerta?ID=0 HTTP/1.1"); // Query #change the ID
    client.println("Host: idg2017.herokuapp.com"); // Host to query
    client.println();
    while(client.connected() && !client.available()) delay(1);  // wait until client is available
    while (client.connected() && client.available()){
      char c =  client.read();  // reads the response 
      mensaje.concat(c);
    }
    client.stop();  // Stop the conexion to the API
    Serial.println("Puerta: "+mensaje.substring(173,174));
    if (mensaje.substring(173,174) == "1"){
      lockDoor.write(0);  // Open the door
      digitalWrite(led,HIGH);
    }else{
      lockDoor.write(90); // Close the door
      digitalWrite(led,LOW);
    }
  }else{
    Serial.println("No se concretó la conexion");
  }
}

//-----------------------------------Light Method-----------------------------------------#
void lights(){
  String mensaje = "";
  if(client.connect(server,80)){  //  connect with the API-REST
    client.println("POST /GetLuz?ID=0 HTTP/1.1"); // Query #change the ID
    client.println("Host: idg2017.herokuapp.com"); // Host to query
    client.println();
    while(client.connected() && !client.available()) delay(1);  // wait until client is available
    while (client.connected() && client.available()){
      char c =  client.read();  // reads the response 
      mensaje.concat(c);
    }
    client.stop();  // Stop the conexion to the API
    Serial.println("Luz: "+(mensaje.substring(173,174)));
    if (mensaje.substring(173,174) == "1"){   // turn on the lights
      Serial.println("Encendido");
      digitalWrite(led,HIGH);
    }else{
      Serial.println("Apagado");    // turn off the lights
      digitalWrite(led,LOW);
    }
  }else{
    Serial.println("No se concretó la conexion");
  }
}

//-----------------------------------Loop Method------------------------------------------#
void loop() {
  lights();
  doors();
  doorbelldoorbell();
  thermometer();
  delay(5000);
}
