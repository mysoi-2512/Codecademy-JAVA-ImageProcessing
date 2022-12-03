import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
public class ImageProcessing {
    public static void main(String[] args) {

        int[][] imageData = imgToTwoD("./apple.jpg");

        int[][] trimmed = trimBorders(imageData, 60);
        twoDToImage(trimmed, "./trimmed_apple.jpg");

        int[][] negative = negativeColor(imageData);
        twoDToImage(negative, "./negative_apple.jpg");

        int[][] stretch = stretchHorizontally(imageData);
        twoDToImage(stretch, "./stretch_apple.jpg");

        int[][] shrink = shrinkVertically(imageData);
        twoDToImage(shrink, "./shrink_apple.jpg");

        int[][] invert = invertImage(imageData);
        twoDToImage(invert, "./invert_apple.jpg");

        int[][] colorFilterImage = colorFilter(imageData, -75, 30, -30);
        twoDToImage(colorFilterImage, "./color_filter_apple.jpg");

        int[][] paintArrays = new int[500][500];
        int[][] paintImage = paintRandomImage(paintArrays);
        twoDToImage(paintImage, "./paint_random_image.jpg");

        int[][] newArray = new int[500][500];
        int[][] generateRectanglesImage= generateRectangles(newArray, 1000);
        twoDToImage(generateRectanglesImage, "./generate_reg_image.jpg");
    }

    // Image Processing Methods
    public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
        // Example Method
        if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
            int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
            for (int i = 0; i < trimmedImg.length; i++) {
                for (int j = 0; j < trimmedImg[i].length; j++) {
                    trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
                }
            }
            return trimmedImg;
        } else {
            System.out.println("Cannot trim that many pixels from the given image.");
            return imageTwoD;
        }
    }
    public static int[][] negativeColor(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int [][] negativeChangeColor = new int[imageTwoD.length][imageTwoD[0].length];

        for(int i = 0; i < negativeChangeColor.length; i++) {
            for(int j = 0; j < negativeChangeColor[0].length; j++) {
                int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
                rgba[0] = 255 - rgba[0];
                rgba[1] = 255 - rgba[1];
                rgba[2] = 255 - rgba[2];
                negativeChangeColor[i][j] = getColorIntValFromRGBA(rgba);
            }
        }
        return negativeChangeColor;
    }
    public static int[][] stretchHorizontally(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int[][] stretchImage = new int[imageTwoD.length][imageTwoD[0].length*2];
        int it = 0;
        for(int i = 0; i < imageTwoD.length; i++) {
            for(int j = 0; j < imageTwoD[0].length; j++) {
                it = j*2;
                stretchImage[i][it] = imageTwoD[i][j];
                stretchImage[i][it+1] = imageTwoD[i][j];
            }
        }
        return stretchImage;
    }
    public static int[][] shrinkVertically(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int[][] shrinkImage = new int[imageTwoD.length/2][imageTwoD[0].length];
        for(int i = 0; i < imageTwoD[0].length; i++) {
            for(int j = 0; j < imageTwoD.length - 1; j+=2) {
                shrinkImage[j/2][i] = imageTwoD[j][i];
            }
        }
        return shrinkImage;
    }
    public static int[][] invertImage(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int[][] invertImage = new int[imageTwoD.length][imageTwoD[1].length];
        for(int i = 0; i < imageTwoD.length; i++) {
            for(int j = 0; j < imageTwoD[0].length; j++) {
                invertImage[i][j] = imageTwoD[(imageTwoD.length-1)-i][(imageTwoD[0].length-1)-j];
            }
        }
        return invertImage;
    }
    public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
        // TODO: Fill in the code for this method
        int[][] colorFilterImage = new int[imageTwoD.length][imageTwoD[0].length];
        for(int i = 0; i < imageTwoD.length; i++) {
            for(int j = 0; j < imageTwoD[0].length; j++) {
                int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
                rgba[0] = rgba[0] - 75;
                rgba[1] = rgba[0] + 30;
                rgba[2] = rgba[0] - 30;

                for(int z = 0; z < rgba.length; z++) {
                    if(rgba[z] > 255) {
                        rgba[z] = 255;
                    } else if (rgba[z] < 0) {
                        rgba[z] = 0;
                    }
                }
                colorFilterImage[i][j] = getColorIntValFromRGBA(rgba);
            }
        }
        return colorFilterImage;
    }

    // Painting Methods
    public static int[][] paintRandomImage(int[][] canvas) {
        // TODO: Fill in the code for this method
        Random rand = new Random();
        int[][] paintRandomImage = new int[canvas.length][canvas[0].length];
        for(int i = 0; i < canvas.length; i++) {
            for(int j = 0; j < canvas[0].length; j++) {
                int randRed = rand.nextInt(256);
                int randGreen = rand.nextInt(256);
                int randBlue = rand.nextInt(256);
                int[] rbga = {randRed, randGreen, randBlue, 255};
                paintRandomImage[i][j] = getColorIntValFromRGBA(rbga);
            }
        }
        return paintRandomImage;
    }
    public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
        // TODO: Fill in the code for this method
        for(int i = 0; i < canvas.length; i++) {
            for(int j = 0; j < canvas[0].length; j++) {
                if (i>=rowPosition && i<=rowPosition) {
                    if (j>=colPosition && j<=colPosition + height) {
                        canvas[i][j] = color;
                    }
                }
            }
        }
        return canvas;
    }
    public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
        // TODO: Fill in the code for this method
        Random rand = new Random();
        for (int i = 0; i < numRectangles; i++) {
            int randWidth = rand.nextInt(canvas[0].length);
            int randHeight = rand.nextInt(canvas.length);
            int randRow = rand.nextInt(canvas.length - randHeight);
            int randCol = rand.nextInt(canvas[0].length - randWidth);
            int[] rgba = {rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), 255};
            int randColor = getColorIntValFromRGBA(rgba);
            canvas = paintRectangle(canvas, randWidth, randHeight, randRow, randCol, randColor );
        }
        return canvas;
    }

    // Utility Methods
    public static int[][] imgToTwoD(String inputFileOrLink) {
        try {
            BufferedImage image = null;
            if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
                URL imageUrl = new URL(inputFileOrLink);
                image = ImageIO.read(imageUrl);
                if (image == null) {
                    System.out.println("Failed to get image from provided URL.");
                }
            } else {
                image = ImageIO.read(new File(inputFileOrLink));
            }
            int imgRows = image.getHeight();
            int imgCols = image.getWidth();
            int[][] pixelData = new int[imgRows][imgCols];
            for (int i = 0; i < imgRows; i++) {
                for (int j = 0; j < imgCols; j++) {
                    pixelData[i][j] = image.getRGB(j, i);
                }
            }
            return pixelData;
        } catch (Exception e) {
            System.out.println("Failed to load image: " + e.getLocalizedMessage());
            return null;
        }
    }
    public static void twoDToImage(int[][] imgData, String fileName) {
        try {
            int imgRows = imgData.length;
            int imgCols = imgData[0].length;
            BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < imgRows; i++) {
                for (int j = 0; j < imgCols; j++) {
                    result.setRGB(j, i, imgData[i][j]);
                }
            }
            File output = new File(fileName);
            ImageIO.write(result, "jpg", output);
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e.getLocalizedMessage());
        }
    }
    public static int[] getRGBAFromPixel(int pixelColorValue) {
        Color pixelColor = new Color(pixelColorValue);
        return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
    }
    public static int getColorIntValFromRGBA(int[] colorData) {
        if (colorData.length == 4) {
            Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
            return color.getRGB();
        } else {
            System.out.println("Incorrect number of elements in RGBA array.");
            return -1;
        }
    }
    public static void viewImageData(int[][] imageTwoD) {
        if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
            int[][] rawPixels = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rawPixels[i][j] = imageTwoD[i][j];
                }
            }
            System.out.println("Raw pixel data from the top left corner.");
            System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
            int[][][] rgbPixels = new int[3][3][4];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
                }
            }
            System.out.println();
            System.out.println("Extracted RGBA pixel data from top the left corner.");
            for (int[][] row : rgbPixels) {
                System.out.print(Arrays.deepToString(row) + System.lineSeparator());
            }
        } else {
            System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
        }
    }
}