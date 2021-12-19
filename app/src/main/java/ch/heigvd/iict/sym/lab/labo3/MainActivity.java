package ch.heigvd.iict.sym.lab.labo3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/***
 * Auteurs : Derder Hakim, Penalva Carl, Tomic Mario
 */
public class MainActivity extends AppCompatActivity {

    private Button NFC;
    private Button codebarre;
    private Button iBeacon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NFC = findViewById(R.id.NFC_button);
        codebarre = findViewById(R.id.codebarre_button);
        iBeacon = findViewById(R.id.iBeacon_button);

        codebarre.setOnClickListener(view -> {
            Intent intent = new Intent(this, BarcodeActivity.class);
            startActivity(intent);
        });
        iBeacon.setOnClickListener(view -> {
            Intent intent = new Intent(this, IBeaconActivity.class);
            startActivity(intent);
        });
        NFC.setOnClickListener(view -> {
            Intent intent = new Intent(this, NFCActivity.class);
            startActivity(intent);
        });

    }
}
