package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller.Admin_NhanVien;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class QuanLyController {
    @GetMapping("/quanLy")
    public String quanLy(){
        return "View/admin/QuanLy";
    }

    @PostMapping("/api/voice-command")
    @ResponseBody
    public String handleVoiceCommand(@RequestBody Map<String, String> payload) {
        String text = payload.get("text").toLowerCase();

        if (text.contains("nhân viên")) {
            return "/ql_nhanVien";
        } else if (text.contains("người dùng")) {
            return "/ql_nguoiDung";
        }else if (text.contains("khách hàng")) {
            return "/ql_khachHang";
        }else if (text.contains("vé xe")) {
            return "/ql_veXe";
        }else if (text.contains("bãi xe")) {
            return "/ql_baiXe";
        }else if (text.contains("xe ra vào")) {
            return "/ql_xeRa_Vao";
        }else if (text.contains("doanh thu")) {
            return "/ql_doanhThu";
        }else if (text.contains("hỗ trợ")) {
            return "/hoTro";
        }else if (text.contains("phương tiện")) {
            return "/ql_phuongTien";
        }else if (text.contains("trang chủ")) {
            return "/trangChu";
        }else if (text.contains("tin tức")) {
            return "/tinTuc";
        }else if (text.contains("giới thiệu")) {
            return "/gioiThieu";
        }else if (text.contains("liên hệ")) {
            return "/lienHe";
        }else {
            return "Không hiểu yêu cầu.";
        }
    }

    @GetMapping("/quanLyNV")
    public String quanLyNV(){
        return "View/NhanVien/QuanLy";
    }
    @GetMapping("/thongTinSangLap")
    public String thongTinSangLap(){
        return "View/page/ThongTinSangLap";
    }
}
