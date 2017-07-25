//-------------------------------------Libs-------------------------------------------------#
#include <Dhcp.h>
#include <Dns.h>
#include <Ethernet.h>
#include <EthernetClient.h>
#include <EthernetServer.h>
#include <EthernetUdp.h>
#include <SPI.h>
#include <Servo.h>  // servo library
//-----------------------------------Ethernet Shield---------------------------------------#
EthernetClient client = EthernetClient();
byte mac[] = {0x90, 0xA2, 0xDA, 0x0E, 0x98, 0x91};
char server[] = "idg2017.herokuapp.com";

//-----------------------------------Led's-------------------------------------------------#
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

  // Led's
  pinMode(led,OUTPUT);

  // Servo
  lockDoor.attach(4,900,2100);  // Puerto 4

  //Timbre
  pinMode(timbre,INPUT);
  pinMode(buzzer,OUTPUT);
}
float getVoltage(int pin) 
{
  return (analogRead(pin) * 0.004882814); 
}
//-----------------------------------Door Method-------------------------------------------#
void Termometro(){
  float voltage, degreesC;
  voltage = getVoltage(temperatura);  //  Obtiene el voltaje del pin Analogico 
  degreesC = (voltage - 0.5) * 100.0; //  Convierte el voltaje a grados Celsius
  if(client.connect(server,80)){  //  Si hay conexión al API
    client.println("POST /AddTemperatura?ID=0&Dato="+String(degreesC)+" HTTP/1.1"); // Consulta al API-REST #cambiar ID
    client.println("Host: idg2017.herokuapp.com"); // Host al que se consulta
    client.println();
    client.stop();  //  Se detiene la conexión al API
    Serial.println(degreesC);
  }else{
    Serial.println("Error de conexion");
  }
}
/*
//-----------------------------------Door Method-------------------------------------------#
void Timbre(){
  if(digitalRead(timbre) == LOW){
    tone(buzzer,'c',113);
    delay(113);
    Serial.println("Sending..");
    if(client.connect(server,80)){  //  Si hay conexión al API
      client.println("POST /UpdateTimbre?ID=0&Estado=1 HTTP/1.1"); // Consulta al API-REST #cambiar ID
      client.println("Host: idg2017.herokuapp.com"); // Host al que se consulta
      client.println();
      client.stop();  //  Se detiene la conexión al API
    }else{
      Serial.println("Error de conexion");
    }
    Serial.println("Done..");
    //PITA
    tone(buzzer,'a',113);
    delay(113);
  }
}

//-----------------------------------Door Method-------------------------------------------#
void Puerta(){
  String mensaje = "";
  if(client.connect(server,80)){  //  Si hay conexión al API
    client.println("POST /GetPuerta?ID=0 HTTP/1.1"); // Consulta al API-REST #cambiar ID
    client.println("Host: idg2017.herokuapp.com"); // Host al que se consulta
    client.println();
    while(client.connected() && !client.available()) delay(1);  // Espera a que el API este disponible
    while (client.connected() && client.available()){
      char c =  client.read();  // Lee la respuesta del API
      mensaje.concat(c);
    }
    client.stop();  // Se detiene la conexión al API
    Serial.println("Puerta: "+mensaje.substring(173,174));
    if (mensaje.substring(173,174) == "1"){
      lockDoor.write(0);
      digitalWrite(led,HIGH);
    }else{
      lockDoor.write(90);
      digitalWrite(led,LOW);
    }
  }else{
    Serial.println("No se concretó la conexion");
  }
}

//-----------------------------------Light Method-----------------------------------------#
void Luz(){
  String mensaje = "";
  if(client.connect(server,80)){  //  Si hay conexión al API
    client.println("POST /GetLuz?ID=0 HTTP/1.1"); // Consulta al API-REST #Cambiar ID
    client.println("Host: idg2017.herokuapp.com"); // Host al que se consulta
    client.println();
    while(client.connected() && !client.available()) delay(1);  // Espera a que el API este disponible
    while (client.connected() && client.available()){
      char c =  client.read();  // Lee la respuesta del API
      mensaje.concat(c);
    }
    client.stop();  // Se detiene la conexión al API
    Serial.println("Luz: "+(mensaje.substring(173,174)));
    if (mensaje.substring(173,174) == "1"){
      Serial.println("Encendido");
      digitalWrite(led,HIGH);
    }else{
      Serial.println("Apagado");
      digitalWrite(led,LOW);
    }
  }else{
    Serial.println("No se concretó la conexion");
  }
}
*/
//-----------------------------------Loop Method------------------------------------------#
void loop() {
  //Luz();
  //Puerta();
  //Timbre();
  Termometro();
  delay(5000);
}
