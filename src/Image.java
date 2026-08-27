/*
* An image is a matrix of pixels.
* Create getters and setters appropiately to get and set individual pixels.
*/
public class Image {
    private Pixel[][] pixels;

    public Image(int height, int width) {
        this.pixels = new Pixel[height][width];
    }

    public Image(Pixel[][] pixels) {
        this.pixels = pixels;
    }

    public int getHeight() {
        return this.pixels.length;
    }

    public int getWidth() {
        return this.pixels[0].length;
    }

    public Pixel getPixel(int row, int col) {
        return this.pixels[row][col];
    }

    public void setPixel(int row, int col, Pixel p) {
        this.pixels[row][col] = p;
    }
}
 
