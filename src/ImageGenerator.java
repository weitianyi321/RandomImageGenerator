/**
 * Created by tianyi on 2018/2/4.
 */


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.*;

public class ImageGenerator {
    final int width = 128;
    final int height = 128;

    /* Gets connection with the Random.org API*/

    public HttpURLConnection connect(String s) throws Exception {
        URL url = new URL(s);
        return (HttpURLConnection) url.openConnection();
    }

    public List<Integer> getRandom(HttpURLConnection conn) throws Exception {
        if (conn == null) return new ArrayList<>();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        List<Integer> randomarrayray = new LinkedList<>();

        String curr = rd.readLine();
        while (curr != null) {
            randomarrayray.add(Integer.valueOf(curr));
            curr = rd.readLine();
        }
        rd.close();
        return randomarrayray;
    }

    public void generate(List<Integer> array) throws Exception {
        if (array == null || array.size() == 0) return;
        BufferedImage obj = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        File f;
        int i = 0;
        /* reference : https://stackoverflow.com/questions/19277010/bit-shift-and-bitwise-operations-to-encode-rgb-values*/
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                int alpha = array.get((i++) % 1400);
                int red = array.get((i++) % 1400);
                int green = array.get((i++) % 1400);
                int blue = array.get((i++) % 1400);
                int p = (alpha<<24) | (red<<16) | (green<<8) | blue;
                obj.setRGB(x, y, p);
            }
        }

        try{
            f = new File("MyImage.png");
            ImageIO.write(obj, "png", f);
        }catch(IOException e){
            System.out.println("Error: " + e);
        }
    }

    public void gen(String url) throws Exception{
        generate(getRandom(connect(url)));
    }

    public static void main(String[] arraygs) {
        try {
            ImageGenerator obj = new ImageGenerator();
            obj.gen("https://www.random.org/integers/?num=1400&min=0&max=256&col=1&base=10&format=plain&rnd=new");
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }
}
