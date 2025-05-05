package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller.Security;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.NguoiDung;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.NguoiDungService;

@Controller
public class DangNhapController {
    @Autowired
    private NguoiDungService NDService;

    @GetMapping("/dangNhap")
    public String dangNhap() {
        return "View/Security/dangNhap";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userName,
                        @RequestParam String password,
                        @RequestParam String email,
                        HttpSession session,
                        Model model, RedirectAttributes redirectAttributes) {
        NguoiDung nguoidung = NDService.dangNhap(userName, password, email);
        if(nguoidung != null) {
            session.setAttribute("nguoidung", nguoidung);
            if(nguoidung.getVaiTro().equalsIgnoreCase("Quản lý")){
                model.addAttribute("type","Đăng nhập thành công!");
                return "View/admin/HomeAdmin";
            }else if(nguoidung.getVaiTro().equalsIgnoreCase("Nhân viên")){
                model.addAttribute("type","Đăng nhập thành công!");
                return "View/NhanVien/HomeNhanVien";
            }else{
                model.addAttribute("type","Đăng nhập thành công!");
                return "View/KhachHang/HomeKhachHang";
            }
        }
        redirectAttributes.addFlashAttribute("message","Đăng nhập thất bại, vui lòng đăng nhập lại!");
            return "redirect:/dangNhap";
    }

    @GetMapping("/dangXuat")
    public String dangXuat(Model model) {
        model.addAttribute("type","Đăng xuất thành công!");
        return "View/Security/dangNhap";
    }
}
