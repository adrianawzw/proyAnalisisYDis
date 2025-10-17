package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.Launch;
import com.jfoenix.controls.JFXButton;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.*;

public class LoginController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    public String rol = "";
    @FXML
    private AnchorPane parent;
    @FXML
    private Pane content_area;
    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_password;
    @FXML
    private JFXButton loginBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        makeStageDragebale();
    }

    public void makeStageDragebale() {
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Launch.stage.setX(event.getScreenX() - xOffset);
                Launch.stage.setY(event.getScreenX() - yOffset);
                Launch.stage.setOpacity(0.7f);
            }
        });
        parent.setOnDragDone((e) -> {
            Launch.stage.setOpacity(1.0f);
        });
        parent.setOnMouseReleased((e) -> {
            Launch.stage.setOpacity(1.0f);
        });
    }

    @FXML
    private void cerrar_app(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void login(MouseEvent event) {
        String username = txt_username.getText().trim();
        String password = txt_password.getText().trim();
        if (!validarCampos()) {
            return;
        }

        UsuarioDAO userDAO = new UsuarioDAO();
        Usuario usuario = userDAO.login(username, password);

        if (usuario != null) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Inicio de sesión exitoso",
                    "¡Bienvenido " + usuario.getNombre() + " (" + usuario.getRol() + ")!");

            // cerrar login y abrir dashboard PASANDO EL USUARIO
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.close();
            abrirDashboard(usuario); 
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Usuario o contraseña incorrectos.");
            txt_password.clear();
        }
    }

    private void abrirDashboard(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController dashboardController = loader.getController();

            dashboardController.setUsuarioLogueado(usuario);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema de Inventario - Dashboard");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar el dashboard: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType alertType, String titulo, String mensaje) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private boolean validarCampos() {
        String username = txt_username.getText().trim();
        String password = txt_password.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Por favor, complete todos los campos");
            return false;
        }

        if (!username.matches("[a-zA-Z0-9]{8}")) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "El username debe contener exactamente 8 caracteres (letras y números)");
            txt_username.setText("");
            return false;
        }

        if (password.length() > 12) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "La contraseña no puede tener más de 12 caracteres");
            txt_password.setText("");
            return false;
        }

        return true;
    }

}
