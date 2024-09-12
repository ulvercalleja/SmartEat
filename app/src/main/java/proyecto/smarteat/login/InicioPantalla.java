package proyecto.smarteat.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import proyecto.smarteat.R;
import proyecto.smarteat.home.MenuPantalla;

public class InicioPantalla extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "GoogleActivity";
    ImageButton btGoogle, btRegistroCorreo, btInicioSesion;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_pantalla);

        btGoogle = findViewById(R.id.ipibBotonGoogle);
        btRegistroCorreo = findViewById(R.id.ipibBotonCorreo);
        btInicioSesion = findViewById(R.id.ipibIniciarSesion);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        checkUserLogged(); //Esta funcion verificar si el usuario ya ha iniciado sesión

        // Configuración de Google Sign-In
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleClient = GoogleSignIn.getClient(this, options);

        btGoogle.setOnClickListener(v -> {
            signIn();
        });

        btInicioSesion.setOnClickListener(v -> {
            // Abre la pantalla de inicio de sesión
            Intent intent = new Intent(InicioPantalla.this, LoginPantalla.class);
            startActivity(intent);
        });

        btRegistroCorreo.setOnClickListener(v -> {
            // Abre la pantalla de registro
            Intent intent = new Intent(InicioPantalla.this, RegistroPantalla.class);
            startActivity(intent);
        });
    }

    private void signIn() {
        Intent signInIntent = googleClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Resultado devuelto de iniciar el Intent de Google Sign-In
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign-In fue exitoso, autenticar con Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Error de Google Sign-In
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in successful, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // El usuario ha iniciado sesión correctamente, puedes navegar a la siguiente pantalla
            Intent intent = new Intent(InicioPantalla.this, MenuPantalla.class);
            startActivity(intent);
            finish();
        }
    }

    private void checkUserLogged() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Verificar si el usuario ya ha iniciado sesión
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // Si ya hay un usuario autenticado, redirigir a la pantalla principal
            Intent intent = new Intent(InicioPantalla.this, MenuPantalla.class);
            startActivity(intent);
            finish();  // Cierra la actividad de login
        }
    }
}