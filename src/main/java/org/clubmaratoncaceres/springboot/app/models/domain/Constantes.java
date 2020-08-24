package org.clubmaratoncaceres.springboot.app.models.domain;

import org.springframework.stereotype.Component;

@Component
public final class Constantes {

    public static final String COMUNICACION_VISTA_TIPO_ERROR = "error";
    public static final String COMUNICACION_VISTA_TIPO_EXITO = "success";
    public static final String COMUNICACION_VISTA_TIPO_WARNING = "warning";
    public static final String COMUNICACION_VISTA_TIPO_INFO = "info";
    
    public static final String DIRECTORIO_UPLOADS = "uploads";
    public static final String DIRECTORIO_UPLOADS_SOCIO = "uploads/socios";
    
    public static final String PASSWORD_TEMPORAL_PREFIJO = "555-";
    public static final int PASSWORD_TEMPORAL_MIN = 1000;
    public static final int PASSWORD_TEMPORAL_MAX = 9999;
    
    public static final int MAYOR_EDAD = 18;
    
    public static final String EMAIL_FROM = "noresponder@clubmaratoncaceres.org";
    public static final String EMAIL_PASSWORD = "G!p7vcmaVVeymR5";
    public static final String EMAIL_REPLY_TO = "clubmaratoncaceres@gmail.com";
    public static final String EMAIL_CMC = "Club Maratón Cáceres";
    public static final String EMAIL_HOST = "smtp.ionos.es";
    public static final int EMAIL_PORT = 587;
    
    public static final String PARAMETRO_SEXO = "SEXO";
    public static final int SEXO_HOMBRE = 1;
    public static final int SEXO_MUJER = 2;
    
    public static final String PARAMETRO_CUOTA = "CUOTA";
    public static final String PARAMETRO_TALLA = "TALLA";
    public static final String PARAMETRO_ROL = "ROL";
    
    public static final String PARAMETRO_ROL_USER = "ROLE_USER";
    public static final String PARAMETRO_ROL_MANAGER = "ROLE_MANAGER";
    public static final String PARAMETRO_ROL_ADMIN = "ROLE_ADMIN";
    
    public static final String PARAMETRO_ROL_USER_NUM = "1";
    public static final String PARAMETRO_ROL_MANAGER_NUM = "2";
    public static final String PARAMETRO_ROL_ADMIN_NUM = "3";
    

    private Constantes() {
    }
}