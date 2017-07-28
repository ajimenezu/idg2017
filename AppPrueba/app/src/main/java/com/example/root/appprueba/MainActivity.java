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


    NotificationCompat.Builder mBuilder =
            (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.ic_notification_clear_all);



    RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("https://idg2017.herokuapp.com").build();

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
                    actualizarLuz(1,"Luz Encendida","ERROR al encender la luz");
                }else{
                    actualizarLuz(0, "Luz Apagada","ERROR al apagar la luz");
                }
            }
        });

        puertaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    actualizarPuertas(1, "Puerta Abierta","ERROR al abrir la puerta");
                }else{

                    actualizarPuertas(0, "Puerta Cerrada","ERROR al cerrar la puerta");
                }
            }
        });
        setRepeatingAsyncTask();
    }

    private void setRepeatingAsyncTask() {

        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        try {
                            alertaTemperatura();
                        } catch (Exception e) {
                            // error, do something
                        }

                        try {
                            alertaTimbre();
                        }catch (Exception e) {
                            // error, do something
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000);  // interval of one minute
    }

    public void alertaTemperatura (){
        final NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final ServiceConn serviceConn = restAdapter.create(ServiceConn.class);

        serviceConn.getTemperatura(idGrupo, new Callback<String>(){
            @Override
            public void success(String s, Response response) {
                if (s != "false") {
                    mBuilder.setContentTitle("Alerta de Temperatura")
                            .setContentText("La temperatura actual es: "+s);
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
    }

    public void alertaTimbre(){
        final NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        final ServiceConn serviceConn = restAdapter.create(ServiceConn.class);

        serviceConn.getTimbre(idGrupo, new Callback<String>(){
            @Override
            public void success(String s, Response response) {
                if (s != "false") {
                    mBuilder.setContentTitle("Alerta de Timbre")
                            .setContentText("El timbre ha sonado");
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
                    serviceConn.updateTimbre(idGrupo,0, new Callback<Boolean>() {
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
    }

    public void actualizarLuz(int valor, final String msj1, final String msj2){
        ServiceConn serviceConn = restAdapter.create(ServiceConn.class);
        serviceConn.updateLuz(idGrupo,valor, new Callback<Boolean>(){
            @Override
            public void success(Boolean aBoolean, Response response) {
                if (aBoolean) {
                    Toast.makeText(getApplicationContext(),msj1, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), msj2, Toast.LENGTH_SHORT).show();
                    luzSwitch.setChecked(false);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                System.out.println(retrofitError);
            }

        });
    }
    public void actualizarPuertas(int valor, final String msj1, final String msj2){
        ServiceConn serviceConn = restAdapter.create(ServiceConn.class);
        serviceConn.updatePuerta(idGrupo,valor, new Callback<Boolean>(){
            @Override
            public void success(Boolean aBoolean, Response response) {
                if (aBoolean) {
                    Toast.makeText(getApplicationContext(), msj1, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), msj2, Toast.LENGTH_SHORT).show();
                    puertaSwitch.setChecked(false);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                System.out.println(retrofitError);
            }

        });
    }
}
