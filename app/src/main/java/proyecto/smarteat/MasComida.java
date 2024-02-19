package proyecto.smarteat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MasComida extends AppCompatActivity {

    Button btAñadir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mas_comida);

        btAñadir = findViewById(R.id.ispbtCrear);

        btAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComidasFragment fragment = new ComidasFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.sefrFragment, fragment);
                fragmentTransaction.commit();
            }
        });
    }
}