package ch.heigvd.iict.sym.lab.labo3;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;


/***
 * Auteurs : Derder Hakim, Penalva Carl, Tomic Mario
 */
public class NFCActivitySecond extends AppCompatActivity {

    private long lastNfc;
    private NfcAdapter nfcAdapter;
    private NfcHandler nfcHandler;
    private static class NfcHandler extends Handler {
        private static final String TAG = "NFCActivity" ;
        private final WeakReference<NFCActivitySecond> mActivity;

        public NfcHandler(NFCActivitySecond activity){
            super(Looper.getMainLooper());
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message message){
            NFCActivitySecond activity = mActivity.get();

            if (activity != null && NFCUtils.checkNfcValues(message.getData().getStringArrayList("DATA"))){
                activity.lastNfc = new Timestamp(System.currentTimeMillis()).getTime();
                Toast.makeText(activity, "NFC scan successful", Toast.LENGTH_SHORT ).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcsecond);
        nfcHandler = new NfcHandler(this);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Button max = findViewById(R.id.max_button);
        Button medium = findViewById(R.id.medium_button);
        Button low = findViewById(R.id.low_button);
        Bundle b = getIntent().getExtras();
        lastNfc = b.getLong("lastNfcTime");

        max.setOnClickListener(view -> {
           if(checkSecurityLevel(NfcConstants.Level.MAX)){
               Toast.makeText(this, "Accepted", Toast.LENGTH_SHORT).show();
           } else {
               Toast.makeText(this, "Required : AUTHENTICATE_MAX", Toast.LENGTH_SHORT).show();
           }
        });

        medium.setOnClickListener(view -> {
            if(checkSecurityLevel(NfcConstants.Level.MEDIUM)){
                Toast.makeText(this, "Accepted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Required : AUTHENTICATE_MEDIUM", Toast.LENGTH_SHORT).show();
            }
        });

        low.setOnClickListener(view -> {
            if(checkSecurityLevel(NfcConstants.Level.LOW)){
                Toast.makeText(this, "Accepted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Required : AUTHENTICATE_LOW", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        NFCUtils.setupForegroundDispatch(this, nfcAdapter);
    }

    @Override
    protected void onPause() {
        NFCUtils.stopForegroundDispatch(this,nfcAdapter);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        new NFCUtils(intent, nfcHandler);
    }

    private boolean checkSecurityLevel(NfcConstants.Level level){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        long time = now.getTime();
        switch (level){
            case MAX: return (time - lastNfc) < NfcConstants.TIME_MAX;
            case MEDIUM: return (time - lastNfc) < NfcConstants.TIME_MEDIUM;
            case LOW: return (time - lastNfc) < NfcConstants.TIME_LOW;
        }
        return false;
    }

}