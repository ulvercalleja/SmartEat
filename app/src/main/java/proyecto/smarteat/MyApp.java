package proyecto.smarteat;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Inicializa Firebase
        FirebaseApp.initializeApp(this);
    }
}
