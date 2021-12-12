package ch.heigvd.iict.sym.lab.labo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;

public class NFCActivity extends AppCompatActivity {

    private static final String username = "User1";
    private static final String password = "password";
    private NfcAdapter nfcAdapter;
    private NfcHandler nfcHandler;
    private long lastNfc;
    private boolean nfcIsValid;
    private static class NfcHandler extends Handler {
        private static final String TAG = "NFCActivity" ;
        private final WeakReference<NFCActivity> mActivity;

        public NfcHandler(NFCActivity activity){
            super(Looper.getMainLooper());
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message message){
            NFCActivity activity = mActivity.get();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (activity != null){
               activity.nfcIsValid = NFCUtils.checkNfcValues(message.getData().getStringArrayList("DATA"));
               activity.lastNfc = now.getTime();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcactivity);
        EditText usernameET = findViewById(R.id.usernameET);
        EditText passwordET = findViewById(R.id.passwordET);
        Button connect = findViewById(R.id.connectButton);
        nfcHandler = new NfcHandler(this);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcIsValid = false;
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;

        }
        connect.setOnClickListener(view -> {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            //reset si + de 10 sec
            if(lastNfc + 10000 < now.getTime()){
                nfcIsValid = false;
            }
            if(usernameET.getText().toString().equals(username) && passwordET.getText().toString().equals(password) ){
                if(nfcIsValid){
                    //Passez le lastScan à la nouvelle activité
                    Intent intent = new Intent(NFCActivity.this, NFCActivitySecond.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this, "Invalid NFC", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
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
}