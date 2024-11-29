package proyecto.smarteat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

/**
 * La siguiente clase será usada para almacenar constantes, o incluso
 * métodos que sean reiterados y usados en otras actividades, fragmentos
 * o clases.
 */

public class ConstantUtils {
    /* LOGIN e INICIO PANTALLA*/
    public static final String LOGIN_SHARED_PREFS = "user_session";
    public static final String LOGIN_KEY_USER_ID = "user_id";
    public static final String LOGIN_DATOS_INCORRECTOS="El correo o la contraseña son incorrectos";
    public static final String LOGIN_ERROR_VACIO = "El campo está vacío:";
    public static final String LOGIN_ID_USUARIO = "id del usuario" ;

    /* REGISTER */
    public static final String REGISTER_MSG_CAMPOS_REQUERIDOS = "Todos los campos son requeridos";
    public static final String REGISTER_MSG_CONTRASENAS_NO_COINCIDEN = "Las contraseñas no coinciden";
    public static final String REGISTER_MSG_EMAIL_INVALIDO = "Correo electrónico inválido";
    public static final String REGISTER_MSG_USUARIO_CREADO = "Usuario creado exitosamente";
    public static final String REGISTER_MSG_ERROR_CREAR_USUARIO = "Error al crear el usuario. Inténtelo de nuevo.";
    public static final String REGISTER_EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    /* CREAR */
    public static final String CREAR_MSG_ERROR_CAMPO_OBLIGATORIO = "Este campo es obligatorio";
    public static final String CREAR_TAG_RESPUESTA_API = "API respuesta";
    public static final String CREAR_TAG_ERROR_API = "Error al realizar la solicitud a la API";
    public static final String CREAR_TAG_RESPUESTA_GALERIA = "GALERIA respuesta";
    public static final String CREAR_LOG_ERROR_GALERIA = "Error al cargar la imagen desde la galería";


    /* TUS COMIDAS */
    public static final String TUS_COMIDAS_TAG = "TusComidasFragment"; // Para logs
    public static final String TUS_COMIDAS_TAG_ERROR = "La actividad host no es MenuPantalla";
    public static final String TUS_COMIDAS_TAG_RESPUESTA_API = "API Error"; // Para logs
    public static final String TUS_COMIDAS_TAG_ERROR_API = "Error en la respuesta de la API: ";
    public static final String TUS_COMIDAS_PIECHART_CENTER_TEXT = "Nutrientes Totales";
    public static final float TUS_COMIDAS_PIECHART_CENTER_TEXT_SIZE = 14f;
    public static final float TUS_COMIDAS_PIECHART_TRANSPARENT_CIRCLE_RADIUS = 58f;
    public static final int TUS_COMIDAS_PIECHART_TEXT_COLOR = Color.WHITE;
    public static final int TUS_COMIDAS_LEGEND_TEXT_COLOR = Color.BLACK;
    public static final String TUS_COMIDAS_LABEL_GRASAS = "Grasas";
    public static final String TUS_COMIDAS_LABEL_HIDRATOS = "Hidratos";
    public static final String TUS_COMIDAS_LABEL_PROTEINAS = "Proteínas";

    /* SELECCION COMIDAS */
    public static final String SELECCION_COMIDAS_TAG = "API Error";
    public static final String SELECCION_COMIDAS_TAG_ERROR_API = "Error en la respuesta de la API: ";
    public static final int SELECCION_COMIDAS_GRID_SPAN_COUNT = 2; // Columnas en el GridLayout
    public static final String SELECCION_COMIDAS_LABEL_GRASAS = "Grasas";
    public static final String SELECCION_COMIDAS_LABEL_HIDRATOS = "Hidratos";
    public static final String SELECCION_COMIDAS_LABEL_PROTEINAS = "Proteínas";

    /* PERFIL */
    public static final String PERFIL_TITLE_GALERIA = "Seleccion una imagen"; // Para logs
    public static final String PERFIL_MSG_ERROR_CONTRASENA_INCORRECTA = "La contraseña actual es incorrecta.";
    public static final String PERFIL_MSG_ERROR_CAMPOS_VACIOS = "Los campos no pueden estar vacíos.";
    public static final String PERFIL_BT_EDITAR_TEXT_EDITAR = "Editar Perfil";
    public static final String PERFIL_BT_EDITAR_TEXT_GUARDAR = "Guardar";
    /* Otros valores de uso constante */
    public static final String BASE_URL = "http://192.168.80.113:8080/";
    public static final int CREAR_PICK_IMAGE_REQUEST = 1;  // Código de solicitud para abrir la galería

    /*Los siguientes dos metodos se usan para obtener el SnackBar (resultado operacion) personalizado*/
    public static void customSnackBarExito (Context context, View parentView, int layoutResId){

        // Crea un SnackBar básico
        Snackbar snackbar = Snackbar.make(parentView, "", Snackbar.LENGTH_SHORT);

        // Obtiene el layout del Snackbar
        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout =
                (Snackbar.SnackbarLayout) snackbar.getView();

        // Infla el layout personalizado
        LayoutInflater inflater = LayoutInflater.from(context);
        View customSnackBarView = inflater.inflate(R.layout.csb_operacion_exitosa, null);

        // Limpia el contenido existente del layout y añade el diseño personalizado
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
