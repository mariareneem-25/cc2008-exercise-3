//construye interfaz gráfica, importa de la libreria
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageEditorView extends JFrame { // vista extiende de la clase JFrame, relación de herencia
    JPanel mainPanel = new JPanel();
    JButton loadImageButton = new JButton("Load Image"); //como se declara un botón 
    JFileChooser inputImageChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png"); //solo se permite archivos png
    JButton negativeFilterButton = new JButton("Negative");
    JButton grayscaleFilterButton = new JButton("Grayscale");
    // Keep only one channel BUTTON
    JButton onlyonechannelFilterButton = new JButton("Keep Only one channel");
    //BRIGHTNESS BUTTON
    JButton brightnessFilterButton = new JButton("Brightness");
    //BLACK AND WHITE BUTTON
    JButton blackAndWhiteFilterButton = new JButton("Black & White");
    //MIRROR BUTTON
    JButton mirrorFilterButton = new JButton("Mirror");
    //ROTATE BUTTON
    JButton rotateFilterButton = new JButton("Rotate");
    JButton undoButton = new JButton("<-");
    //RESET BUTTON
    JButton resetButton = new JButton("Reset");
    //SAVE BUTTON
    JButton saveButton = new JButton("Save");
    JFileChooser saveImageChooser = new JFileChooser();
    ImagePanel imagePanel;

    public ImageEditorView() {
        // We are extending the JFrame class, so we MUST call the parent constructor.
        super("Image Editor"); //nombre de la página, llamar al constructor de la clase padre, en este caso jframe

        // orientation of main panel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); //donde saldrá el contenido, en que eje

        // methods on the parent JFrame class
        setSize(800, 600); //tamaño de ventana 
        setResizable(true); // para que no se cambie con el mouse el tamañao de la ventana 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //que clase de accion queremos que pase cuando el usuario apache la "x"

        inputImageChooser.setFileFilter(filter); //escoger un archivo, que use el filtro de tipo de imagen

        mainPanel.add(loadImageButton); //agrega el botón al panel

        // add the main panel and make the window visible

        //NEW BUTTONS
        //negative, grayscale, keep only one channel
        mainPanel.add(negativeFilterButton);
        mainPanel.add(grayscaleFilterButton);
        mainPanel.add(onlyonechannelFilterButton);
        mainPanel.add(brightnessFilterButton);
        mainPanel.add(blackAndWhiteFilterButton);
        mainPanel.add(mirrorFilterButton);
        mainPanel.add(rotateFilterButton);
        mainPanel.add(undoButton);
        mainPanel.add(resetButton);
        mainPanel.add(saveButton);


        add(mainPanel); //agrega el panel al frame, se mostrará en pantalla, lo que quiera que se muestre en pantalla va al frame
    }

    //METODOS
    // ################## A section to register action listeners ################
    public void addLoadImageListener(ActionListener listener) { //En el load image, se pasa el parametro listener, para que los botones cuando se apachan se manda una noti a todos los que se registraron como listener
        loadImageButton.addActionListener(listener);
    }

    public void addNegativeListener(ActionListener listener) {
        negativeFilterButton.addActionListener(listener); //para saber cuando se apacha el boton de negative
    }

    //GRAYSCALE
    public void addGrayScaleListener(ActionListener listener){
        grayscaleFilterButton.addActionListener(listener);
    }

    //UNDO
    public void addUndoListener(ActionListener listener){
        undoButton.addActionListener(listener);
    }

    //KEEP ONLY ONE
    public void addOnlyOneChannel(ActionListener listener){
        onlyonechannelFilterButton.addActionListener(listener);
    }

    //BRIGHTNESS
    public void addBrightnessListener(ActionListener listener){
        brightnessFilterButton.addActionListener(listener);
    }

    //BLACK AND WHITE
    public void addBlackAndWhiteListener(ActionListener listener){
        blackAndWhiteFilterButton.addActionListener(listener);
    }

    //MIRROR
    public void addMirrorListener(ActionListener listener){
        mirrorFilterButton.addActionListener(listener);
    }

    //ROTATE
    public void addRotateListener(ActionListener listener){
        rotateFilterButton.addActionListener(listener);
    }

    //RESET
    public void addResetListener(ActionListener listener){
        resetButton.addActionListener(listener);
    }

    //SAVE
    public void addSaveListener(ActionListener listener){
        saveButton.addActionListener(listener);
    }

    public void addInputImageChooserListener(ActionListener listener) {
        inputImageChooser.addActionListener(listener);
    }

    // ############### A section to trigger actions in the GUI ##################
    public File showInputImageChooser() {
        int returnVal = inputImageChooser.showOpenDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        return inputImageChooser.getSelectedFile();
    }

    //SAVE
    public File showSaveFileChooser() {
        int returnVal = saveImageChooser.showSaveDialog(this);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        return saveImageChooser.getSelectedFile();
    }

    //KEEP ONLY CHANNEL
    public int showChannelChooser() {
        String[] options = {"Rojo", "Verde", "Azul"};
        return JOptionPane.showOptionDialog(
            this,
            "Selecciona el canal que se desea conservar",
            "Keep Only Channel",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
    }

    //pide un número entero al usuario, usado por brightness y black&white
    public Integer showAmountInputDialog(String message) {
        String input = JOptionPane.showInputDialog(this, message);
        if (input == null) {
            return null; //el usuario canceló
        }

        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void showInputImage(BufferedImage image) {
        if (imagePanel != null) {
            mainPanel.remove(imagePanel);
        }

        imagePanel = new ImagePanel(image);
        imagePanel.setPreferredSize(new Dimension(600, 400));
        mainPanel.add(imagePanel);
        pack();
    }

    //Para el error
    public void ShowInfoDialogue(String msg) {
        JOptionPane.showMessageDialog(
            this,
            msg,
            "Info",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
 