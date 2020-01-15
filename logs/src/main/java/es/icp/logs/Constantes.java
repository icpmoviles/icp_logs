package es.icp.logs;

public class Constantes {
    public static final int FICHERO_DETALLE_NADA              = 0; //No almacena nada
    public static final int FICHERO_DETALLE_ERROR_WS          = 1; //Almacena los errores y los envios y llamdas de WS
    public static final int FICHERO_DETALLE_WARNING           = 2; //+ Warnings
    public static final int FICHERO_DETALLE_INFORMACION_DEBUG = 3; // + Information + Debug
    public static final int FICHERO_DETALLE_VERBOSE           = 4; //+Verbose
    public static final int FICHERO_DETALLE_COMPLETO          = 99; //Todas las salidas
}
