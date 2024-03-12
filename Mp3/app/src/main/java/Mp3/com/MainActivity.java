package Mp3.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    Button mainBtn;
    boolean key = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
    }

    public void mainBtn(View view) {
        mainBtn = findViewById(R.id.mainBtn);
        if(key == true){
            mainBtn.setText("Puse");
            key = false;
        }else{
            mainBtn.setText("Play");
            key = true;
        }

    }

    @Override
    // нужен
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//        Toast.makeText(this, "" + progress, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}