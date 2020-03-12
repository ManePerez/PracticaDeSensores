package com.example.practicadesensores;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class DibujoPelota extends View implements SensorEventListener {

    Paint dib = new Paint();
    int altura, ancho;
    int tamanio = 15;
    int borde=15;
    float ejX=500, ejY=1000, ejZ1=0, ejZ=0;
    String x, y, z;
    private Drawable imagen;

    public DibujoPelota(Context interfaz) {
        super(interfaz);
        SensorManager sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        Display pantalla = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        ancho= pantalla.getWidth();
        altura = pantalla.getHeight();
        imagen = interfaz.getResources().getDrawable(R.drawable.cancha);
    }


    @Override
    public void onSensorChanged(SensorEvent cambio) {
        ejX-=cambio.values[0];
        x=Float.toString(ejX);
        if(ejX <(tamanio+borde)){
            ejX =(tamanio+borde);
        }
        else if(ejX > (ancho- (tamanio+borde))){
            ejX = ancho- (tamanio+borde);
        }
        ejY+=cambio.values[1];
        y=Float.toString(ejY);
        if(ejY < (tamanio + borde)){
            ejY =(tamanio+borde);
        }
        else  if (ejY >(altura-tamanio-170)){
            ejY = altura-tamanio-170;
        }
        ejZ=cambio.values[2];
        z=String.format("%.2f", ejZ);
        invalidate();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onDraw(Canvas lienzo){
        int ancho = imagen.getIntrinsicWidth();
        int alto = imagen.getIntrinsicHeight();
        imagen.setBounds(0,0,1100,2000);
        imagen.draw(lienzo);
        dib.setColor(Color.WHITE);
        lienzo.drawCircle(ejX, ejY, ejZ+tamanio, dib);
    }
}
