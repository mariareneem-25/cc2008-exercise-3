public class ImageNotFoundException extends Exception { //extends es que hereda
    public ImageNotFoundException (String message){
            super (message);
    }

    public ImageNotFoundException (String m, Throwable cause) {
        super(m, cause);
    }
}


