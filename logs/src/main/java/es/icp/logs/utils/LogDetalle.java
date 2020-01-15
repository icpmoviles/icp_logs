package es.icp.logs.utils;

public class LogDetalle {
    public static final int NADA               = 0; //No almacena nada
    public static final int ERROR_WS          = 1; //Almacena los errores y los envios y llamdas de WS
    public static final int WARNING           = 2; //+ Warnings
    public static final int INFORMACION_DEBUG = 3; // + Information + Debug
    public static final int VERBOSE           = 4; //+Verbose
    public static final int COMPLETO          = 99; //Todas las salidas
}
