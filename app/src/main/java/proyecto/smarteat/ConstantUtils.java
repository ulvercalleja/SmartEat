package proyecto.smarteat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

/**
 * La siguiente clase será usada para almacenar constantes, o incluso
 * métodos que sean reiterados y usados en otras actividades, fragmentos
 * o clases.
 */

public class ConstantUtils {



    /*Los siguientes dos metodos se usan para obtener el SnackBar (resultado operacion) personalizado*/
    public static void customSnackBarExito (Context context, View parentView, int layoutResId){

        // Crea un SnackBar básico
        Snackbar snackbar = Snackbar.make(parentView, "", Snackbar.LENGTH_SHORT);

        // Obtén el layout del Snackbar
        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout =
                (Snackbar.SnackbarLayout) snackbar.getView();

        // Infla tu layout personalizado
        LayoutInflater inflater = LayoutInflater.from(context);
        View customSnackBarView = inflater.inflate(R.layout.csb_operacion_exitosa, null);

        // Limpia el contenido existente del layout y añade tu diseño personalizado
        snackbarLayout.removeAllViews();
        snackbarLayout.addView(customSnackBarView);

        // Muestra el SnackBar
        snackbar.show();

    }

    public static void customSnackBarError(Context context, View parentView, int layoutResId) {
        Snackbar snackbar = Snackbar.make(parentView, "", Snackbar.LENGTH_SHORT);

        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout =
                (Snackbar.SnackbarLayout) snackbar.getView();

        LayoutInflater inflater = LayoutInflater.from(context);
        View customSnackBarView = inflater.inflate(layoutResId, null);

        snackbarLayout.removeAllViews();
        snackbarLayout.addView(customSnackBarView);

        snackbar.show();
    }

}
