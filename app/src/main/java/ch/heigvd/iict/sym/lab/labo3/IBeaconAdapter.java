package ch.heigvd.iict.sym.lab.labo3;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;


/***
 * Adaptateur pour le tableau de beacon
 *
 * Inspiré de //https://stackoverflow.com/questions/8166497/custom-adapter-for-list-view
 *
 * Auteurs : Derder Hakim, Penalva Carl, Tomic Mario
 */
public class IBeaconAdapter extends ArrayAdapter<Beacon> {

    private int resourceLayout;
    private Context mContext;

    public IBeaconAdapter(Context context, int resource, ArrayList<Beacon> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View view = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            view = vi.inflate(resourceLayout, null);
        }
        Beacon b = getItem(position);

        TextView bluetoothAddress = view.findViewById(R.id.bluetoothAddress);
        TextView uuid = view.findViewById(R.id.uuid);
        TextView majmin = view.findViewById(R.id.majmin);
        TextView rssi = view.findViewById(R.id.rssi);

        bluetoothAddress.setText(b.getBluetoothAddress());
        uuid.setText("uuid : " + b.getId1().toString());
        majmin.setText("Majeur : " + b.getId2().toString() + " Mineur : " + b.getId3().toString());
        rssi.setText("RSSI : " + b.getRssi());

        return view;
    }
}
