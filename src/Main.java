import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    // must come before any component is created
    FlatLightLaf.setup(); //libreria, activa el tema

    SwingUtilities.invokeLater(() -> { //el codigo va dentro del bloque, se instancia el modelo la vista y controlador
      // Instantiate our 3 separate MVC classes
      ImageEditorModel model = new ImageEditorModel(); //modelo
      ImageEditorView view = new ImageEditorView(); // vista
      new ImageEditorController(model, view); // controlador

      // all ready, make the window visible
      view.setVisible(true); //para que todo sea visible 
    });
  }
}