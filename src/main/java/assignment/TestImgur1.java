package assignment;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.imageio.ImageIO;

/**
 *
 * @author J
 */
public class TestImgur1 {
	
	public static void main(String[] args) throws Exception {
		TestImgur1 img1=new TestImgur1();
		String s=getImgurContent("xxx");
		System.out.println(s);
		
	}

     public static String getImgurContent(String clientID) throws Exception {

         clientID = "8dca6f99990e0f0";

    URL url;

    url = new URL("https://api.imgur.com/3/image");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    String data = URLEncoder.encode("image", "UTF-8") + "="
            + URLEncoder.encode("http://i.imgur.com/FB9OZWQ.jpg", "UTF-8");

    conn.setDoOutput(true);
    conn.setDoInput(true);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Authorization", "Client-ID " + clientID);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type",
            "application/x-www-form-urlencoded");

    conn.connect();
    StringBuilder stb = new StringBuilder();
    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    wr.write(data);
    wr.flush();

    // Get the response
    BufferedReader rd = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
    String line;
    while ((line = rd.readLine()) != null) {
        stb.append(line).append("\n");
    }
    wr.close();
    rd.close();

    System.out.println(stb.toString());

    return stb.toString();
}

}