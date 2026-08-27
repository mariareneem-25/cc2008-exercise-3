import java.io.File;

// try catch va acá 
public class ImageEditorController {

    private ImageEditorView view;
    private ImageEditorModel model;

    public ImageEditorController(ImageEditorModel model, ImageEditorView view) {
        this.view = view;
        this.model = model;

        // hookup action listeners
        this.view.addLoadImageListener(e -> handleLoadImage()); // llama al metodo para registrase como listener
        this.view.addNegativeListener(e-> handleNegativeFilter());
        //GARYSCALE
        this.view.addGrayScaleListener(e-> handleGrayScaleFilter()); //GRAYSCALE
        this.view.addOnlyOneChannel(e -> handleOnlyOneChannelFilter()); //KEEP ONLY CHANNEL
        this.view.addBrightnessListener(e -> handleBrightnessFilter()); //BRIGHTNESS
        this.view.addBlackAndWhiteListener(e -> handleBlackAndWhiteFilter()); //BLACK AND WHITE
        this.view.addMirrorListener(e -> handleMirrorFilter()); //MIRROR
        this.view.addRotateListener(e -> handleRotateFilter()); //ROTATE
        this.view.addUndoListener(e-> handleUndoButton());
        this.view.addResetListener(e -> handleResetButton()); //RESET
        this.view.addSaveListener(e -> handleSaveButton()); //SAVE
    }

    public void handleLoadImage() { //código para cargar imagen 
        File selectedFile = view.showInputImageChooser(); //mostra la ventana para escoger archivo
        if (selectedFile == null) {
            return;
        }

        try {
            // mutate the application state
            //llama métodos
            model.setInputFileName(selectedFile.getAbsolutePath());
            model.setInputImage(ImageUtils.load(selectedFile.getAbsolutePath()));
        } catch (Exception e) {
            // view.showErrorDialog("couldn't load image: " + e.getMessage());
        }

        // we updated the state of the model, we must re-draw the view layer
        refresh(); //volver a dibujar la interfaz gráfica
    }
    
    //Qué queremos que pase cuando alguien apache el botón de negative filter
private void handleNegativeFilter () { //aquí ocurre el error, se hace el try catch
    try{
        Image negative = this.model.negativeFilter();
        //aplication state changed, the view MUST be updated
        this.view.showInputImage(ImageUtils.toBufferedImage(negative));

    } catch (ImageNotFoundException e){
        //mostrar un error al usuario
        this.view.ShowInfoDialogue(e.getMessage());
    } catch (Exception e) {
        //mostrar el error al usuario
        this.view.ShowInfoDialogue(e.getMessage());
    }
}

    // call the view to re-draw the application state
    private void refresh() {
        view.showInputImage(ImageUtils.toBufferedImage(model.getInputImage()));
    }

    //GRAYSCALE
    private void handleGrayScaleFilter (){ //aquí ocurre el error
        try{
            Image grayscale = this.model.grayscaleFilter();
            this.view.showInputImage(ImageUtils.toBufferedImage(grayscale));
        } catch (ImageNotFoundException e){
            //mostrar un error al usuario
            this.view.ShowInfoDialogue(e.getMessage());
        } catch (Exception e){
            //mostrar el error al usuario
            this.view.ShowInfoDialogue(e.getMessage());
        }
    }

    //botón Keep Only Channel
    private void handleOnlyOneChannelFilter (){
        int channel = view.showChannelChooser();
        if (channel == -1) { //el usuario cerró el diálogo sin escoger
            return;
        }

        try{
            Image result = this.model.keepOnlyChannelFilter(channel);
            this.view.showInputImage(ImageUtils.toBufferedImage(result));
        } catch (ImageNotFoundException e){
            //este es el error
            this.view.ShowInfoDialogue(e.getMessage());
        } catch (Exception e){
            this.view.ShowInfoDialogue(e.getMessage());
        }
    }

    //botón Brightness
    private void handleBrightnessFilter (){
        Integer amount = view.showAmountInputDialog("Cantidad de brillo a agregar (-255 a 255):");
        if (amount == null) {
            return;
        }

        try{
            Image result = this.model.brightnessFilter(amount);
            this.view.showInputImage(ImageUtils.toBufferedImage(result));
        } catch (ImageNotFoundException e){
            //este es el error
            this.view.ShowInfoDialogue(e.getMessage());
        } catch (Exception e){
            this.view.ShowInfoDialogue(e.getMessage());
        }
    }

    //botón Black and White
    private void handleBlackAndWhiteFilter (){
        Integer limit = view.showAmountInputDialog("Umbral (valor entre 0 y 255):");
        if (limit == null) {
            return;
        }

        try{
            Image result = this.model.blackAndWhiteFilter(limit);
            this.view.showInputImage(ImageUtils.toBufferedImage(result));
        } catch (ImageNotFoundException e){
            //este es el error
            this.view.ShowInfoDialogue(e.getMessage());
        } catch (Exception e){
            this.view.ShowInfoDialogue(e.getMessage());
        }
    }

    //botón Mirror
    private void handleMirrorFilter (){
        try{
            Image result = this.model.mirrorFilter();
            this.view.showInputImage(ImageUtils.toBufferedImage(result));
        } catch (ImageNotFoundException e){
            //este es el error
            this.view.ShowInfoDialogue(e.getMessage());
        } catch (Exception e){
            this.view.ShowInfoDialogue(e.getMessage());
        }
    }

    //botón Rotate
    private void handleRotateFilter (){
        try{
            Image result = this.model.rotateFilter();
            this.view.showInputImage(ImageUtils.toBufferedImage(result));
        } catch (ImageNotFoundException e){
            //este es el error
            this.view.ShowInfoDialogue(e.getMessage());
        } catch (Exception e){
            this.view.ShowInfoDialogue(e.getMessage());
        }
    }

    //botón Undo
    private void handleUndoButton(){
        try{
            Image result = this.model.undo();
            this.view.showInputImage(ImageUtils.toBufferedImage(result));
        } catch (HistoryException e){
            //este es el error
            this.view.ShowInfoDialogue(e.getMessage());
        } catch (Exception e){
            this.view.ShowInfoDialogue(e.getMessage());
        }
    }

    //botón Reset
    private void handleResetButton(){
        try{
            Image result = this.model.reset();
            this.view.showInputImage(ImageUtils.toBufferedImage(result));
        } catch (HistoryException e){
            //este es el error
            this.view.ShowInfoDialogue(e.getMessage());
        } catch (Exception e){
            this.view.ShowInfoDialogue(e.getMessage());
        }
    }

    //botón Save
    private void handleSaveButton(){
        File selectedFile = view.showSaveFileChooser();
        if (selectedFile == null) {
            return;
        }

        try{
            this.model.saveImage(selectedFile.getAbsolutePath());
            this.view.ShowInfoDialogue("Imagen guardada con éxito");
        } catch (SaveException e){
            //este es el error
            this.view.ShowInfoDialogue(e.getMessage());
        } catch (ImageNotFoundException e){
            this.view.ShowInfoDialogue(e.getMessage());
        } catch (Exception e){
            this.view.ShowInfoDialogue(e.getMessage());
        }
    }

}



      