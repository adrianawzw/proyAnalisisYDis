package controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import models.*;

public class DashboardController implements Initializable {

    private Pane panel_registrar;
    @FXML
    private HBox btn_home;
    @FXML
    private Pane panel_home;
    @FXML
    private TextField productos_id;
    @FXML
    private TextField productos_nombre;
    @FXML
    private ImageView productos_imagen;
    @FXML
    private TableColumn<Producto, String> productos_col_id;
    @FXML
    private TableColumn<Producto, String> productos_col_nombre;
    @FXML
    private TableView<Producto> productos_tabla;
    @FXML
    private Label olaa;
    @FXML
    private TextField prov_nombre;
    @FXML
    private TextField prov_ruc;
    @FXML
    private TextField prov_telefono;
    @FXML
    private TextField prov_dir;
    @FXML
    private ComboBox<Producto> prov_prod;
    @FXML
    private TableColumn<?, ?> prov_col_id;
    @FXML
    private TableColumn<?, ?> prov_col_nom;
    @FXML
    private TableColumn<?, ?> prov_col_ruc;
    @FXML
    private TableColumn<?, ?> prov_col_tlf;
    @FXML
    private TableColumn<?, ?> prov_col_dir;
    @FXML
    private TableColumn<?, ?> prov_col_prod;
    @FXML
    private TextField productos_desc;
    @FXML
    private ComboBox<String> productos_uni;
    @FXML
    private TextField productos_smin;
    @FXML
    private TextField productos_smax;
    @FXML
    private TableColumn<?, ?> productos_col_desc;
    @FXML
    private TableColumn<?, ?> productos_col_uni;
    @FXML
    private TableColumn<?, ?> productos_col_smin;
    @FXML
    private TableColumn<?, ?> productos_col_smax;
    @FXML
    private TextField mov_prodid;
    @FXML
    private TextField mov_cant;
    @FXML
    private ComboBox<?> mov_tipo;
    @FXML
    private TextField mov_area;
    @FXML
    private TableColumn<?, ?> mov_col_id;
    @FXML
    private TableColumn<?, ?> mov_col_fecha;
    @FXML
    private TableColumn<?, ?> mov_col_tipo;
    @FXML
    private TableColumn<?, ?> mov_col_respon;
    @FXML
    private TableColumn<?, ?> mov_col_prod;
    @FXML
    private TableColumn<?, ?> mov_col_cant;
    @FXML
    private HBox btn_panel2;
    @FXML
    private HBox btn_consultas;
    @FXML
    private HBox btn_proveedores;
    @FXML
    private HBox btn_recursos;
    @FXML
    private HBox btn_movimientos;
    @FXML
    private Pane panel_num1;
    @FXML
    private Pane panel_consultas;
    @FXML
    private Pane panel_proveedores;
    @FXML
    private Pane panel_recursos;
    @FXML
    private Pane panel_movimientos;
    @FXML
    private TableView<Proveedor> proveedores_tabla;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*inicializar proveedores*/
        proveedorDAO = new ProveedorDAO();
        proveedoresData = FXCollections.observableArrayList();

        configurarTablaProv();
        cargarProveedores();

        /*inicializar productos*/
        productoDAO = new ProductoDAO();
        productosData = FXCollections.observableArrayList();
        unidadesData = FXCollections.observableArrayList();

        cargarProductos();
        configurarCbxProd();
        configurarTablaProductos();
        configurarComboBoxUnidades();
    }

    /*obj para proveedores*/
    private ProveedorDAO proveedorDAO;
    private ObservableList<Proveedor> proveedoresData;
    private Proveedor proveedorSeleccionado;

    /*obj para prod*/
    private ProductoDAO productoDAO;
    private ObservableList<Producto> productosData;
    private ObservableList<String> unidadesData;
    private Producto productoSeleccionado;

    // Agrega estas variables
    private byte[] imagenSeleccionadaData;

    @FXML
    private void cambiarPanel(MouseEvent event) {
        //desde cero
        btn_home.getStyleClass().remove("active");
        btn_panel2.getStyleClass().remove("active");
        btn_consultas.getStyleClass().remove("active");
        btn_proveedores.getStyleClass().remove("active");
        btn_recursos.getStyleClass().remove("active");
        btn_movimientos.getStyleClass().remove("active");
        //cambio de panel segun boton (hbox) y añadir clase de css
        if (event.getSource() == btn_home) {
            panel_home.setVisible(true);
            panel_num1.setVisible(false);
            panel_consultas.setVisible(false);
            panel_proveedores.setVisible(false);
            panel_recursos.setVisible(false);
            panel_movimientos.setVisible(false);
            btn_home.getStyleClass().add("active");

        } else if (event.getSource() == btn_panel2) {
            panel_home.setVisible(false);
            panel_num1.setVisible(true);
            panel_consultas.setVisible(false);
            panel_proveedores.setVisible(false);
            panel_recursos.setVisible(false);
            panel_movimientos.setVisible(false);
            btn_panel2.getStyleClass().add("active");

        } else if (event.getSource() == btn_consultas) {
            panel_home.setVisible(false);
            panel_num1.setVisible(false);
            panel_consultas.setVisible(true);
            panel_proveedores.setVisible(false);
            panel_recursos.setVisible(false);
            panel_movimientos.setVisible(false);
            btn_consultas.getStyleClass().add("active");

        } else if (event.getSource() == btn_proveedores) {
            panel_home.setVisible(false);
            panel_num1.setVisible(false);
            panel_consultas.setVisible(false);
            panel_proveedores.setVisible(true);
            panel_recursos.setVisible(false);
            panel_movimientos.setVisible(false);
            btn_proveedores.getStyleClass().add("active");

        } else if (event.getSource() == btn_recursos) {
            panel_home.setVisible(false);
            panel_num1.setVisible(false);
            panel_consultas.setVisible(false);
            panel_proveedores.setVisible(false);
            panel_recursos.setVisible(true);
            panel_movimientos.setVisible(false);
            btn_recursos.getStyleClass().add("active");

        } else if (event.getSource() == btn_movimientos) {
            panel_home.setVisible(false);
            panel_num1.setVisible(false);
            panel_consultas.setVisible(false);
            panel_proveedores.setVisible(false);
            panel_recursos.setVisible(false);
            panel_movimientos.setVisible(true);
            btn_movimientos.getStyleClass().add("active");

        }
    }

    /*crud productos*/
    private void configurarTablaProductos() {
        productos_col_id.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        productos_col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        productos_col_desc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        productos_col_uni.setCellValueFactory(new PropertyValueFactory<>("unidad"));
        productos_col_smin.setCellValueFactory(new PropertyValueFactory<>("stockMin"));
        productos_col_smax.setCellValueFactory(new PropertyValueFactory<>("stockMax"));

        productos_tabla.setItems(productosData);

        productos_tabla.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionarProducto(newValue)
        );
    }

    private void configurarComboBoxUnidades() {
        unidadesData.addAll("unidad", "kg", "litro", "metro", "galón", "bolsa",
                "caja", "millar", "par", "juego", "rollo", "lata");
        productos_uni.setItems(unidadesData);
    }

    @FXML
    private void productoAñadir(ActionEvent event) {
        if (validarCamposProducto()) {
            return;
        }

        try {
            // Verificar si el producto ya existe
            if (productoDAO.existeProducto(productos_nombre.getText())) {
                mostrarAlerta("Error", "Ya existe un producto con ese nombre", Alert.AlertType.ERROR);
                return;
            }

            Producto nuevoProducto = new Producto(
                    productos_nombre.getText(),
                    productos_desc.getText(),
                    productos_uni.getValue(),
                    Integer.parseInt(productos_smax.getText()),
                    Integer.parseInt(productos_smin.getText()),
                    0, // stock_actual inicia en 0
                    imagenSeleccionadaData // Datos binarios de la imagen
            );

            if (productoDAO.insertarProducto(nuevoProducto)) {
                mostrarAlerta("Éxito", "Producto añadido correctamente", Alert.AlertType.INFORMATION);
                cargarProductos();
                limpiarCamposProductos();
            } else {
                mostrarAlerta("Error", "No se pudo añadir el producto", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Stock mínimo y máximo deben ser números válidos", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al añadir producto: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void limpiarCamposProductos() {
        limpiarCamposProd();
        productoSeleccionado = null;
        productos_tabla.getSelectionModel().clearSelection();
        productos_imagen.setImage(null);
    }

    private byte[] convertirImageViewABinario(ImageView imageView) {
        try {
            WritableImage writableImage = imageView.snapshot(null, null);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    private void productoImportarImg(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen del producto");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            try {
                // Convertir archivo a byte[]
                imagenSeleccionadaData = productoDAO.fileToBytes(file);

                // Mostrar la imagen en el ImageView
                Image image = new Image(file.toURI().toString());
                productos_imagen.setImage(image);

                mostrarAlerta("Éxito", "Imagen cargada correctamente", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo cargar la imagen: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    // BOTON Actualizar prod
    @FXML
    private void actualizarProducto(ActionEvent event) {
        if (productoSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un producto para actualizar", Alert.AlertType.WARNING);
            return;
        }

        if (validarCamposProducto()) {
            return;
        }

        try {
            // Verificar si el nombre ya existe (excluyendo el producto actual)
            Producto productoExistente = productoDAO.obtenerProductoPorId(productoSeleccionado.getIdProducto());
            if (!productoExistente.getNombre().equals(productos_nombre.getText())
                    && productoDAO.existeProducto(productos_nombre.getText())) {
                mostrarAlerta("Error", "Ya existe otro producto con ese nombre", Alert.AlertType.ERROR);
                return;
            }

            productoSeleccionado.setNombre(productos_nombre.getText());
            productoSeleccionado.setDescripcion(productos_desc.getText());
            productoSeleccionado.setUnidad(productos_uni.getValue());
            productoSeleccionado.setStockMax(Integer.parseInt(productos_smax.getText()));
            productoSeleccionado.setStockMin(Integer.parseInt(productos_smin.getText()));

            // Actualizar la imagen solo si se seleccionó una nueva
            if (imagenSeleccionadaData != null && imagenSeleccionadaData.length > 0) {
                productoSeleccionado.setImagenData(imagenSeleccionadaData);
            }

            if (productoDAO.actualizarProducto(productoSeleccionado)) {
                mostrarAlerta("Éxito", "Producto actualizado correctamente", Alert.AlertType.INFORMATION);
                cargarProductos();
                limpiarCamposProductos();
                productoSeleccionado = null;
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el producto", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Stock mínimo y máximo deben ser números válidos", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al actualizar producto: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void seleccionarProducto(ActionEvent event) {
        if (productoSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un producto de la tabla", Alert.AlertType.WARNING);
        } else {
            mostrarAlerta("Información", "Producto seleccionado: " + productoSeleccionado.getNombre(), Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void eliminarProducto(ActionEvent event) {
        if (productoSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un producto para eliminar", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar el producto?");
        confirmacion.setContentText("Producto: " + productoSeleccionado.getNombre()
                + "\nEsta acción no se puede deshacer.");

        if (confirmacion.showAndWait().get() == ButtonType.OK) {
            try {
                if (productoDAO.eliminarProducto(productoSeleccionado.getIdProducto())) {
                    mostrarAlerta("Éxito", "Producto eliminado correctamente", Alert.AlertType.INFORMATION);
                    cargarProductos();
                    limpiarCamposProd();
                    productoSeleccionado = null;
                } else {
                    mostrarAlerta("Error", "No se pudo eliminar el producto", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al eliminar producto: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    // MÉTODOS AUXILIARES PARA PRODUCTOS
    private void seleccionarProducto(Producto producto) {
        if (producto != null) {
            productoSeleccionado = producto;
            productos_id.setText(String.valueOf(producto.getIdProducto()));
            productos_nombre.setText(producto.getNombre());
            productos_desc.setText(producto.getDescripcion());
            productos_uni.setValue(producto.getUnidad());
            productos_smin.setText(String.valueOf(producto.getStockMin()));
            productos_smax.setText(String.valueOf(producto.getStockMax()));

            // Cargar la imagen desde los datos BLOB
            if (producto.getImagenFX() != null) {
                productos_imagen.setImage(producto.getImagenFX());
                imagenSeleccionadaData = producto.getImagenData();
            } else {
                productos_imagen.setImage(null);
                imagenSeleccionadaData = null;
            }

            // El campo ID debe ser de solo lectura cuando se selecciona un producto
            productos_id.setEditable(false);
        }
    }

    private boolean validarCamposProducto() {
        // Validar nombre
        if (productos_nombre.getText().isEmpty()) {
            mostrarAlerta("Error", "El nombre del producto es obligatorio", Alert.AlertType.ERROR);
            productos_nombre.requestFocus();
            return true;
        }

        // Validar unidad
        if (productos_uni.getValue() == null) {
            mostrarAlerta("Error", "Debe seleccionar una unidad", Alert.AlertType.ERROR);
            productos_uni.requestFocus();
            return true;
        }

        // Validar stock mínimo
        if (productos_smin.getText().isEmpty()) {
            mostrarAlerta("Error", "El stock mínimo es obligatorio", Alert.AlertType.ERROR);
            productos_smin.requestFocus();
            return true;
        }

        // Validar stock máximo
        if (productos_smax.getText().isEmpty()) {
            mostrarAlerta("Error", "El stock máximo es obligatorio", Alert.AlertType.ERROR);
            productos_smax.requestFocus();
            return true;
        }

        try {
            int stockMin = Integer.parseInt(productos_smin.getText());
            int stockMax = Integer.parseInt(productos_smax.getText());

            if (stockMin < 0 || stockMax < 0) {
                mostrarAlerta("Error", "Los stocks no pueden ser negativos", Alert.AlertType.ERROR);
                return true;
            }
            if (stockMin >= stockMax) {
                mostrarAlerta("Error", "Stock máximo debe ser mayor que stock mínimo", Alert.AlertType.ERROR);
                return true;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Stock mínimo y máximo deben ser números válidos", Alert.AlertType.ERROR);
            return true;
        }
        return false;
    }

    private void limpiarCamposProd() {
        productos_id.clear();
        productos_nombre.clear();
        productos_desc.clear();
        productos_uni.setValue(null);
        productos_smin.clear();
        productos_smax.clear();
        productos_imagen.setImage(null);
        imagenSeleccionadaData = null;

        // Habilitar edición del ID para nuevos productos
        productos_id.setEditable(false);
    }

    //form stock
    @FXML
    private void registrarMovimientoStock(ActionEvent event) {

    }

    private void configurarTablaProv() {
        prov_col_id.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        prov_col_nom.setCellValueFactory(new PropertyValueFactory<>("nombreProv"));
        prov_col_ruc.setCellValueFactory(new PropertyValueFactory<>("ruc"));
        prov_col_tlf.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        prov_col_dir.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        prov_col_prod.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));

        proveedores_tabla.setItems(proveedoresData);

        proveedores_tabla.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionarProveedor(newValue)
        );
    }

    private void configurarCbxProd() {
        // Configurar cómo se muestran los productos en el ComboBox
        prov_prod.setCellFactory(param -> new ListCell<Producto>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getNombre() + " (" + item.getUnidad() + ")");
                }
            }
        });

        prov_prod.setButtonCell(new ListCell<Producto>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getNombre() + " (" + item.getUnidad() + ")");
                }
            }
        });
    }

    private void cargarProveedores() {
        proveedoresData.clear();
        List<Proveedor> proveedores = proveedorDAO.obtenerTodosLosProveedores();
        proveedoresData.addAll(proveedores);
    }

    private void cargarProductos() {
        productosData.clear();
        List<Producto> productos = productoDAO.obtenerTodosLosProductos();
        productosData.addAll(productos);
        prov_prod.setItems(productosData);
    }

    @FXML
    private void proveedorAñadir(ActionEvent event) {
        if (validarCamposProveedor()) {
            return;
        }

        try {
            int idProducto = prov_prod.getValue() != null ? prov_prod.getValue().getIdProducto() : 0;

            Proveedor nuevoProveedor = new Proveedor(
                    prov_nombre.getText(),
                    prov_ruc.getText(),
                    prov_telefono.getText(),
                    prov_dir.getText(),
                    idProducto
            );

            if (proveedorDAO.insertarProveedor(nuevoProveedor)) {
                mostrarAlerta("Éxito", "Proveedor añadido correctamente", Alert.AlertType.INFORMATION);
                cargarProveedores();
                limpiarCamposProveedor();
            } else {
                mostrarAlerta("Error", "No se pudo añadir el proveedor", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al añadir proveedor: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void proveedorEliminar(ActionEvent event) {
        if (proveedorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un proveedor para eliminar", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar el proveedor?");
        confirmacion.setContentText("Proveedor: " + proveedorSeleccionado.getNombreProv());

        if (confirmacion.showAndWait().get() == ButtonType.OK) {
            if (proveedorDAO.eliminarProveedor(proveedorSeleccionado.getIdProveedor())) {
                mostrarAlerta("Éxito", "Proveedor eliminado correctamente", Alert.AlertType.INFORMATION);
                cargarProveedores();
                limpiarCamposProveedor();
                proveedorSeleccionado = null;
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el proveedor", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void proveedorActualizar(ActionEvent event) {
        if (proveedorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un proveedor para actualizar", Alert.AlertType.WARNING);
            return;
        }

        if (validarCamposProveedor()) {
            return;
        }

        try {
            int idProducto = prov_prod.getValue() != null ? prov_prod.getValue().getIdProducto() : 0;

            proveedorSeleccionado.setNombreProv(prov_nombre.getText());
            proveedorSeleccionado.setRuc(prov_ruc.getText());
            proveedorSeleccionado.setTelefono(prov_telefono.getText());
            proveedorSeleccionado.setDireccion(prov_dir.getText());
            proveedorSeleccionado.setIdProducto(idProducto);

            if (proveedorDAO.actualizarProveedor(proveedorSeleccionado)) {
                mostrarAlerta("Éxito", "Proveedor actualizado correctamente", Alert.AlertType.INFORMATION);
                cargarProveedores();
                limpiarCamposProveedor();
                proveedorSeleccionado = null;
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el proveedor", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al actualizar proveedor: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void proveedorSeleccionar(ActionEvent event) {
        if (proveedorSeleccionado == null) {
            mostrarAlerta("Advertencia", "Seleccione un proveedor de la tabla", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void limpiarCamposProv() {
        limpiarCamposProveedor();
        proveedorSeleccionado = null;
        proveedores_tabla.getSelectionModel().clearSelection();
    }

    private void seleccionarProveedor(Proveedor proveedor) {
        if (proveedor != null) {
            proveedorSeleccionado = proveedor;
            prov_nombre.setText(proveedor.getNombreProv());
            prov_ruc.setText(proveedor.getRuc());
            prov_telefono.setText(proveedor.getTelefono());
            prov_dir.setText(proveedor.getDireccion());

            // Seleccionar el producto en el ComboBox
            if (proveedor.getIdProducto() > 0) {
                Producto productoSeleccionado = productosData.stream()
                        .filter(p -> p.getIdProducto() == proveedor.getIdProducto())
                        .findFirst()
                        .orElse(null);
                prov_prod.setValue(productoSeleccionado);
            } else {
                prov_prod.setValue(null);
            }
        }
    }

    private boolean validarCamposProveedor() {
        if (prov_nombre.getText().isEmpty() || prov_ruc.getText().isEmpty()) {
            mostrarAlerta("Error", "Nombre y RUC son campos obligatorios", Alert.AlertType.ERROR);
            return true;
        }

        if (!prov_ruc.getText().matches("\\d{11}")) {
            mostrarAlerta("Error", "El RUC debe tener 11 dígitos", Alert.AlertType.ERROR);
            return true;
        }

        return false;
    }

    private void limpiarCamposProveedor() {
        prov_nombre.clear();
        prov_ruc.clear();
        prov_telefono.clear();
        prov_dir.clear();
        prov_prod.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
