package com.example.root.appprueba;

import android.app.NotificationManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {
    int mNotificationId = 001;
    Switch luzSwitch, puertaSwitch;
    int idGrupo = 0;

    public String temperatura = "";
    NotificationCompat.Builder mBuilder =
            (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                    .setContentTitle("Alerta de Temperatura")
                    .setContentText("La temperatura actual es: "+temperatura);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        luzSwitch = (Switch) findViewById(R.id.switchLuces);
        puertaSwitch = (Switch) findViewById(R.id.switchPuertas);

        luzSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    //configurar conn con el servidor
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint("https://idg2017.herokuapp.com").build();
                    ServiceConn serviceConn = restAdapter.create(ServiceConn.class);

                    serviceConn.updateLuz(idGrupo,1, new Callback<Boolean>(){
                        @Override
                        public void success(Boolean aBoolean, Response response) {
                            if (aBoolean) {
                                Toast.makeText(getApplicationContext(), "Luz Encendida", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "ERROR al encender la luz", Toast.LENGTH_SHORT).show();
                                luzSwitch.setChecked(false);
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            System.out.println(retrofitError);
                        }

                    });

                }else{
                    RestAdapter restAdapter = new RestAdapter .Builder()
                            .setEndpoint("https://idg2017.herokuapp.com").build();
                    ServiceConn serviceConn = restAdapter.create(ServiceConn.class);

                    serviceConn.updateLuz(idGrupo,0, new Callback<Boolean>(){
                        @Override
                        public void success(Boolean aBoolean, Response response) {
                            if (aBoolean) {
                                Toast.makeText(getApplicationContext(), "Luz Apagada", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "ERROR al apagar la luz", Toast.LENGTH_SHORT).show();
                                luzSwitch.setChecked(true);
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            System.out.println(retrofitError);
                        }

                    });
                }

            }
        });

        puertaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    //configurar conn con el servidor
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint("https://idg2017.herokuapp.com").build();
                    ServiceConn serviceConn = restAdapter.create(ServiceConn.class);
                    serviceConn.updatePuerta(idGrupo,1, new Callback<Boolean>(){
                        @Override
                        public void success(Boolean aBoolean, Response response) {
                            if (aBoolean) {
                                Toast.makeText(getApplicationContext(), "Puerta Abierta", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "ERROR al abrir la puerta", Toast.LENGTH_SHORT).show();
                                luzSwitch.setChecked(false);
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            System.out.println(retrofitError);
                        }

                    });

                }else{
                    RestAdapter restAdapter = new RestAdapter .Builder()
                            .setEndpoint("https://idg2017.herokuapp.com").build();
                    ServiceConn serviceConn = restAdapter.create(ServiceConn.class);

                    serviceConn.updatePuerta(idGrupo,0, new Callback<Boolean>(){
                        @Override
                        public void success(Boolean aBoolean, Response response) {
                            if (aBoolean) {
                                Toast.makeText(getApplicationContext(), "Puerta Cerrada", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "ERROR al cerrar la puerta", Toast.LENGTH_SHORT).show();
                                luzSwitch.setChecked(true);
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            System.out.println(retrofitError);
                        }

                    });
                }

            }
        });
        setRepeatingAsyncTask();
    }

    /*
CREATE DEFINER=`hqqmdit0b3lvs7f3`@`%` PROCEDURE `updateTemperatura`(IN ID smallint)
BEGIN
	UPDATE Temperatura t SET t.Estado = 0 WHERE t.ID = ID;
END

CREATE DEFINER=`hqqmdit0b3lvs7f3`@`%` PROCEDURE `getTemperatura`(IN ID smallint)
BEGIN
	SELECT * FROM Temperatura t WHERE t.ID = ID ;
END



     */
    private void setRepeatingAsyncTask() {
        final NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            RestAdapter restAdapter = new RestAdapter.Builder()
                                    .setEndpoint("https://idg2017.herokuapp.com").build();
                            final ServiceConn serviceConn = restAdapter.create(ServiceConn.class);
                            serviceConn.getTemperatura(idGrupo, new Callback<String>(){
                                @Override
                                public void success(String s, Response response) {
                                    System.out.println("1"+s+"1");
                                    if (s != "false") {
                                        temperatura = s;
                                        mNotifyMgr.notify(mNotificationId, mBuilder.build());

                                        serviceConn.updateTemperatura(idGrupo, new Callback<Boolean>() {
                                            @Override
                                            public void success(Boolean aBoolean, Response response) {

                                            }

                                            @Override
                                            public void failure(RetrofitError retrofitError) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void failure(RetrofitError retrofitError) {

                                }
                            });



                        } catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, 1000);  // interval of one minute
    }




}
