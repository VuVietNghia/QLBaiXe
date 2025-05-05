package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller;

public class main {
    public static void main(String[] args) {
        try {
            QRCodeGenerator.generateQRCode("V003", "V003.png", 300, 300);
            System.out.println("Đã tạo mã QR thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
