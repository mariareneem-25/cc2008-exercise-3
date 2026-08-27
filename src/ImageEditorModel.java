import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
public class ImageEditorModel {
    private String inputFileName;
    //private Image inputImage;
    private ImageEditor editor;
    //Historial
    private List<Image> history;
 
 
    public ImageEditorModel() {
        this.history = new ArrayList<>();
    }
 
    public String getInputFileName() {
        return this.inputFileName;
    }
 
    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }
 
    public Image getInputImage() {
        return this.history.getFirst();
    }
 
    //cuando se carga la imagen 
    public void setInputImage(Image inputImage) {
        history.add(inputImage);
        this.editor = new ImageEditor(this.getInputImage());
    }
 
    public Image negativeFilter() throws ImageNotFoundException{
        if (this.history.isEmpty()) {  //is empty se pone en lugar de == null
            throw new ImageNotFoundException("Image");
        }
          //PARA CADA BOTON 
    this.editor = new ImageEditor(history.getLast()); //error: se aplicaba siempre sobre la imagen original, ahora se aplica sobre el último estado
    Image negative = this.editor.negative();
    history.add(negative);
    return history.getLast(); //get last método que funciona sobre la lista, y devuelve el último elemento de la lista
 
    //return this.editor.negative(); // aquí se debe de verificar si se subió la imagen
    }
 
 
 
    
 
    //GRAYSCALE
    public Image grayscaleFilter() throws ImageNotFoundException{
        if (this.history.isEmpty()){
            throw new ImageNotFoundException("Image");
        } 
        this.editor = new ImageEditor(history.getLast()); //error: mismo fix, aplicar sobre el último estado
        Image grayscale = this.editor.grayscale();
        history.add(grayscale);
        return history.getLast(); 
    }
 
    //KEEP ONLY ONE CHANNEL
    public Image keepOnlyChannelFilter(int channel) throws ImageNotFoundException {
        if (this.history.isEmpty()){
            throw new ImageNotFoundException("Image");
        }
        this.editor = new ImageEditor(history.getLast());
        Image result = this.editor.keepOnlyChannel(channel);
        history.add(result);
        return history.getLast();
    }
 
    //BRIGHTNESS
    public Image brightnessFilter(int amount) throws ImageNotFoundException {
        if (this.history.isEmpty()){
            throw new ImageNotFoundException("Image");
        }
        this.editor = new ImageEditor(history.getLast());
        Image result = this.editor.brightness(amount);
        history.add(result);
        return history.getLast();
    }
 
    //BLACK AND WHITE
    public Image blackAndWhiteFilter(int limit) throws ImageNotFoundException {
        if (this.history.isEmpty()){
            throw new ImageNotFoundException("Image");
        }
        this.editor = new ImageEditor(history.getLast());
        Image result = this.editor.blackAndWhite(limit);
        history.add(result);
        return history.getLast();
    }
 
    //MIRROR
    public Image mirrorFilter() throws ImageNotFoundException {
        if (this.history.isEmpty()){
            throw new ImageNotFoundException("Image");
        }
        this.editor = new ImageEditor(history.getLast());
        Image result = this.editor.mirrorHorizontal();
        history.add(result);
        return history.getLast();
    }
 
    //ROTATE
    public Image rotateFilter() throws ImageNotFoundException {
        if (this.history.isEmpty()){
            throw new ImageNotFoundException("Image");
        }
        this.editor = new ImageEditor(history.getLast());
        Image result = this.editor.rotate90();
        history.add(result);
        return history.getLast();
    }
 
    public Image undo() throws HistoryException {// este es el error: no se validaba si ya no había nada que deshacer
        if (this.history.isEmpty() || this.history.size() <= 1) {
            throw new HistoryException("No hay más operaciones para deshacer");
        }
        history.removeLast(); //quita el ultimo elemento
        return history.getLast(); //retorna el ultimo de la lista
    }
 
    //RESET
    public Image reset() throws HistoryException {
        if (this.history.isEmpty() || this.history.size() <= 1) {
            throw new HistoryException("La imagen ya está en su estado original, no hay nada que reiniciar");
        }
        Image original = history.getFirst();
        history.clear();
        history.add(original);
        return original;
    }
 
    //SAVE
    public void saveImage(String filename) throws SaveException, ImageNotFoundException {
        if (this.history.isEmpty()) {
            throw new ImageNotFoundException("Image");
        }
        try {
            ImageUtils.save(history.getLast(), filename);
        } catch (IOException e) {
            //este es el error
            throw new SaveException("No se pudo guardar la imagen: " + e.getMessage(), e);
        } finally {
            //se ejecuta siempre, haya o no error al guardar
            System.out.println("Proceso de guardado finalizado para: " + filename);
        }
    }
}