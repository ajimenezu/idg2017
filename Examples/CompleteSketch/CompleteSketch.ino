//-------------------------------------Libs-------------------------------------------------#
#include <Dhcp.h>
#include <Dns.h>
#include <Ethernet.h>
#include <EthernetClient.h>
#include <EthernetServer.h>
#include <EthernetUdp.h>
#include <SPI.h>

//-----------------------------------Ethernet Shield---------------------------------------#
EthernetClient client = EthernetClient();
byte mac[] = {0x90, 0xA2, 0xDA, 0x0E, 0x98, 0x91};
char server[] = "idg2017.herokuapp.com";

//-----------------------------------Led's-------------------------------------------------#
const int led = 3;  
//-----------------------------------Setup Method------------------------------------------#

void setup() {
  // Serial Connection
  Serial.begin(9600);

  // Ethernet Connection
  Ethernet.begin(mac);
  Serial.println(Ethernet.localIP()); // Shows the IP Address

  // Led's
  pinMode(led,INPUT);

}

void Luces(){
  String mensaje = "";
  if(client.connect(server,80)){
    client.println("POST /GetLuz?ID=0 HTTP/1.1"); // Consulta al API-REST
    client.println("Host: idg2017.herokuapp.com"); // Host al que se consulta
    client.println();
    while(client.connected() && !client.available()) delay(1);  // Espera a que el API este disponible
    while (client.connected() && client.available()){
      char c =  client.read();  // Lee la respuesta del API
      mensaje.concat(c);
    }
    client.stop();  // Se detiene la conexión al API
    Serial.println(mensaje.substring(173,174));
    if (mensaje.substring(173,174) == "1"){
      Serial.println("Encendido");
      digitalWrite(led,HIGH);
    }else{xx
      Serial.println("Apagado");
      digitalWrite(led,LOW);
    }
  }else{
    Serial.println("No se concretó la conexion");
  }
}



void loop() {
  Luces();
  delay(2000);
}
