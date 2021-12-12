/**
 * Source :
 * https://github.com/journeyapps/zxing-android-embedded/blob/master/sample/
 * src/main/java/example/zxing/ContinuousCaptureActivity.java
 */

package ch.heigvd.iict.sym.lab.labo3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;

import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;

import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;

import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BarcodeActivity extends AppCompatActivity {
    private DecoratedBarcodeView barcodeView;
    private String lastText;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if(result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }
            lastText = result.getText();
            barcodeView.setStatusText(result.getText());

            //Added preview of scanned barcode
            ImageView imageView = findViewById(R.id.barcodePreview);
            imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_barcode);

        barcodeView = findViewById(R.id.barcode_scanner);
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeView.initializeFromIntent(getIntent());
        barcodeView.decodeContinuous(callback);

    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}