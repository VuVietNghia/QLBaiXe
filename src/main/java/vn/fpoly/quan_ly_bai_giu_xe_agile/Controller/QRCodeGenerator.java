package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.nio.file.Path;
import java.util.Hashtable;

public class QRCodeGenerator {
    public static void generateQRCode(String data, String path, int width, int height) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hints);
        Path outputPath = Path.of(path);
        MatrixToImageWriter.writeToPath(matrix, "PNG", outputPath);
    }
}

