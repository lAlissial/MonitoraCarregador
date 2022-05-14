package br.com.ifpb.si.pdm.monitoracarregador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var receiverCable: ReceiverCable? = null
    private lateinit var ifCable: IntentFilter
    private lateinit var tvReceiver: TextView
    private lateinit var ivChargerStatus: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("APP_CARREGADOR", "OnCreate")
        this.tvReceiver = findViewById(R.id.tvReceiver)
        this.ivChargerStatus = findViewById(R.id.ivChargerStatus)
    }

    override fun onResume() {
        super.onResume()
        Log.i("APP_CARREGADOR", "OnResume")
        if (this.receiverCable == null){
            this.receiverCable= ReceiverCable()
            this.ifCable= IntentFilter().apply {
                addAction(Intent.ACTION_POWER_CONNECTED)
                addAction(Intent.ACTION_POWER_DISCONNECTED)
            }
        }
        registerReceiver(this.receiverCable, this.ifCable)
    }

    override fun onStop() {
        super.onStop()
        Log.i("APP_CARREGADOR", "OnStop")
        unregisterReceiver(this.receiverCable)
    }


    inner class ReceiverCable: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.equals(Intent.ACTION_POWER_CONNECTED)){
                this@MainActivity.tvReceiver.text = "Carregador Conectado!"
                this@MainActivity.ivChargerStatus.setImageResource(getResources().getIdentifier("batterycharging" , "drawable", getPackageName()))

            }else{
                this@MainActivity.tvReceiver.text = "Carregador Desconectado!"
                this@MainActivity.ivChargerStatus.setImageResource(getResources().getIdentifier("disconnectedunplugged" , "drawable", getPackageName()))

            }
        }
    }
}