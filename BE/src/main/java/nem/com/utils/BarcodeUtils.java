package nem.com.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class BarcodeUtils {

    public static String  generateBarcode(String data,Double price, int wid, int hei) {
        StringBuilder result = new StringBuilder();
        if (!data.isEmpty()) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            try {
//                EAN8Writer write = new EAN8Writer();
//                BitMatrix bitMatrix = write.encode(data, BarcodeFormat.EAN_8, wid, hei);
//                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
//                Graphics graphics = bufferedImage.getGraphics();
//                graphics.setColor(Color.WHITE);
//                graphics.fillRect(0, 70, 300, 30);
//                graphics.setColor(Color.BLACK);
//                graphics.setFont(new Font("SansSerif", Font.PLAIN, 20));
//                int x = price<100000?100:price<1000000?95:price<10000000?85:80;
//                graphics.drawString(String.format("%,.0f", price)+" VND", x, hei-10);
//                ImageIO.write(bufferedImage,"png",os);
//                result.append("data:image/png;base64,");
//                result.append(new String(Base64.getEncoder().encode(os.toByteArray())));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            try {
                EAN8Writer write = new EAN8Writer();
                BitMatrix bitMatrix = write.encode(data, BarcodeFormat.EAN_8, wid, hei);
                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                Graphics graphics = bufferedImage.getGraphics();
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, hei-30, wid, 30);
                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("SansSerif", Font.PLAIN, 20));
                int x = price<100000?wid/2-50:price<1000000?wid/2-55:price<10000000?wid/2-65:wid/2-70;
                int x2 = wid/2-35;
                graphics.drawString(data, x2, hei-10);
                ImageIO.write(bufferedImage,"png",os);
                result.append("data:image/png;base64,");
                result.append(new String(Base64.getEncoder().encode(os.toByteArray())));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result.toString();
    }
}
