package ch.heigvd.iict.sym.lab.labo3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;


/***
 * Partie 4 du labo, permet d'afficher différentes caractéristiques des iBeacon à proximité
 *
 * Inspiré de https://altbeacon.github.io/android-beacon-library/samples.html
 *
 * Auteurs : Derder Hakim, Penalva Carl, Tomic Mario
 */
public class IBeaconActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;

    private ArrayList<Beacon> allBeacons;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibeacon);

        CheckPermissions();

        listView = findViewById(R.id.beaconList);

        allBeacons = new ArrayList<>();

        IBeaconAdapter iba = new IBeaconAdapter(this, R.layout.beacon_display, allBeacons);
        listView.setAdapter(iba);
        BeaconManager bm = BeaconManager.getInstanceForApplication(this);
        bm.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        Region region = new Region("all-beacons-region", null, null, null);
        bm.getRegionViewModel(region).getRangedBeacons().observe(this, monitoringObserver);
        bm.startRangingBeacons(region);

    }

    // Récupère la liste des iBeacons actifs
    Observer monitoringObserver = (Observer<Collection<Beacon>>) beacons -> {
        allBeacons.clear();
        allBeacons.addAll(beacons);
        // https://stackoverflow.com/a/4198569
        ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
    };


    // Demande la permission pour la localisation
    private void CheckPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    PERMISSION_REQUEST_FINE_LOCATION);
        }
    }

    // Affiche un toast si l'utilisateur refuse à l'application d'accéder à la localisation
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}