/*
* The Pixel class represents an RGB pixel.
* We use `int` as the data type to back up every
* color channel.
*/
public class Pixel {
    public int r;
    public int g;
    public int b;

    public Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}