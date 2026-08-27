public class ImageEditor {
    private Image og;

    public ImageEditor(Image og) {
        this.og = og;
    }

    /**
    * Negative: every color value is replaced by its opposite.
    * A very dark pixel (0) becomes very bright (255), and the other way around.
    */
    public Image negative() {
        Image transformed = new Image(og.getHeight(), og.getWidth());

        for (int row = 0; row < og.getHeight(); row++) {
            for (int col = 0; col < og.getWidth(); col++) {
                Pixel p = og.getPixel(row, col);
                int r = 255 - p.r;
                int g = 255 - p.g;
                int b = 255 - p.b;
                transformed.setPixel(row, col, new Pixel(r, g, b));
            }
        }

        return transformed;
    }

    /**
    * TASK 1 - Grayscale.
    * average = (red + green + blue) / 3
    */
    public Image grayscale() {
        Image transformed = new Image(og.getHeight(), og.getWidth());

        for (int row = 0; row < og.getHeight(); row++) {
            for (int col = 0; col < og.getWidth(); col++) {
                Pixel p = og.getPixel(row, col);
                int avg = (p.r + p.g + p.b) / 3;
                transformed.setPixel(row, col, new Pixel(avg, avg, avg));
            }
        }

        return transformed;
    }

    /**
    * TASK 2 - Keep only one channel.
    *
    * @param channel 0 = red, 1 = green, 2 = blue
    */
    public Image keepOnlyChannel(int channel) {
        Image transformed = new Image(og.getHeight(), og.getWidth());

        for (int row = 0; row < og.getHeight(); row++) {
            for (int col = 0; col < og.getWidth(); col++) {
                Pixel p = og.getPixel(row, col);
                int r = (channel == 0) ? p.r : 0;
                int g = (channel == 1) ? p.g : 0;
                int b = (channel == 2) ? p.b : 0;
                transformed.setPixel(row, col, new Pixel(r, g, b));
            }
        }

        return transformed;
    }

    /**
    * TASK 3 - Brightness.
    * Add 'amount' to every color value, clamped between 0 and 255.
    */
    public Image brightness(int amount) {
        Image transformed = new Image(og.getHeight(), og.getWidth());

        for (int row = 0; row < og.getHeight(); row++) {
            for (int col = 0; col < og.getWidth(); col++) {
                Pixel p = og.getPixel(row, col);
                int r = clamp(p.r + amount);
                int g = clamp(p.g + amount);
                int b = clamp(p.b + amount);
                transformed.setPixel(row, col, new Pixel(r, g, b));
            }
        }

        return transformed;
    }

    // helper usado solo por brightness() para mantener los valores en rango
    private int clamp(int value) {
        if (value > 255) return 255;
        if (value < 0) return 0;
        return value;
    }

    /**
    * TASK 4 - Black and white (threshold).
    *
    * @param limit a value between 0 and 255
    */
    public Image blackAndWhite(int limit) {
        Image transformed = new Image(og.getHeight(), og.getWidth());

        for (int row = 0; row < og.getHeight(); row++) {
            for (int col = 0; col < og.getWidth(); col++) {
                Pixel p = og.getPixel(row, col);
                int avg = (p.r + p.g + p.b) / 3;

                if (avg > limit) {
                    transformed.setPixel(row, col, new Pixel(255, 255, 255));
                } else {
                    transformed.setPixel(row, col, new Pixel(0, 0, 0));
                }
            }
        }

        return transformed;
    }

    public Image mirrorHorizontal() {
        int height = og.getHeight();
        int width = og.getWidth();
        Image transformed = new Image(height, width);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel p = og.getPixel(row, col);
                int newCol = width - 1 - col;
                transformed.setPixel(row, newCol, new Pixel(p.r, p.g, p.b));
            }
        }

        return transformed;
    }


    public Image rotate90() {
        int height = og.getHeight();
        int width = og.getWidth();
        Image transformed = new Image(width, height);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel p = og.getPixel(row, col);
                int newRow = col;
                int newCol = height - 1 - row;
                transformed.setPixel(newRow, newCol, new Pixel(p.r, p.g, p.b));
            }
        }

        return transformed;
    }

    public void blur() {
        int height = og.getHeight();
        int width = og.getWidth();

        Pixel[][] copy = new Pixel[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel p = og.getPixel(row, col);
                copy[row][col] = new Pixel(p.r, p.g, p.b);
            }
        }

        for (int row = 1; row < height - 1; row++) {
            for (int col = 1; col < width - 1; col++) {
                int sumR = 0, sumG = 0, sumB = 0;

                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        Pixel neighbor = copy[row + dr][col + dc];
                        sumR += neighbor.r;
                        sumG += neighbor.g;
                        sumB += neighbor.b;
                    }
                }

                og.setPixel(row, col, new Pixel(sumR / 9, sumG / 9, sumB / 9));
            }
        }
    }

    //
    // @throws IllegalArgumentException si filterName no es un filtro soportado.
    ///
    public Image applyFilter(String filterName, int param) {
        switch (filterName) {
            case "grises":
                return grayscale();
            case "negativo":
                return negative();
            case "rojo":
                return keepOnlyChannel(0);
            case "verde":
                return keepOnlyChannel(1);
            case "azul":
                return keepOnlyChannel(2);
            case "brillo":
                return brightness(param);
            case "umbral":
                return blackAndWhite(param);
            case "espejo":
                return mirrorHorizontal();
            case "rotar":
                return rotate90();
            default:
                throw new IllegalArgumentException("Filtro no reconocido: " + filterName);
        }
    }


    public static Image copy(Image source) {
        int height = source.getHeight();
        int width = source.getWidth();
        Image copy = new Image(height, width);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel p = source.getPixel(row, col);
                copy.setPixel(row, col, new Pixel(p.r, p.g, p.b));
            }
        }

        return copy;
    }
    public static double averageBrightness(Image image) {
        long sum = 0;
        int height = image.getHeight();
        int width = image.getWidth();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel p = image.getPixel(row, col);
                sum += p.r + p.g + p.b;
            }
        }

        return (double) sum / (height * width * 3);
    }

    /**
    * @return 
    */
    public static int[] findLightestPixel(Image image) {
        return findExtremePixel(image, true);
    }

    /**
    * @return 
    */
    public static int[] findDarkestPixel(Image image) {
        return findExtremePixel(image, false);
    }

    private static int[] findExtremePixel(Image image, boolean lightest) {
        int height = image.getHeight();
        int width = image.getWidth();

        int bestRow = 0;
        int bestCol = 0;
        int bestAvg = lightest ? -1 : 256;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Pixel p = image.getPixel(row, col);
                int avg = (p.r + p.g + p.b) / 3;

                if (lightest && avg > bestAvg) {
                    bestAvg = avg;
                    bestRow = row;
                    bestCol = col;
                } else if (!lightest && avg < bestAvg) {
                    bestAvg = avg;
                    bestRow = row;
                    bestCol = col;
                }
            }
        }

        return new int[] { bestRow, bestCol };
    }
}