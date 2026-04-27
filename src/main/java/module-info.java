module mx.unison {
    // Librerías necesarias
    requires javafx.controls;
    requires javafx.fxml;
    requires ormlite.jdbc;
    requires java.sql;

    // Abrir paquetes para que JavaFX pueda cargar los FXML
    opens mx.unison.controladores to javafx.fxml;
    
    // Abrir paquetes para que ORMLite pueda leer tus @DatabaseTable
    opens mx.unison.modelos to ormlite.jdbc;

    // Exportar el paquete principal para que la JVM encuentre tu App.java
    exports mx.unison;
}