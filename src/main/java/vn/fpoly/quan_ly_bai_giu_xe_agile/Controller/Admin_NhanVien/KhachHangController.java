package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller.Admin_NhanVien;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.KhachHang;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.NguoiDung;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.KhachHangService;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.NguoiDungService;

import java.util.List;

@Controller
public class KhachHangController {
    @Autowired
    private KhachHangService KHService;
    @GetMapping("/ql_khachHang")
    public String ql_khachHang(Model model,
                               @RequestParam(name = "pageKH", defaultValue = "0") int pageKH,
                               @RequestParam(name = "sizeKH", defaultValue = "10") int sizeKH) {
        Page<KhachHang> list = KHService.getAllKhachHang(pageKH, sizeKH);
        model.addAttribute("trangDau", pageKH);
        model.addAttribute("tongTrang", list.getTotalPages());
        model.addAttribute("listKH", list);
        model.addAttribute("newKH", new KhachHang());
        return "View/page/QL_khachHang";
    }

    @PostMapping("/saveKH")
    public String saveKH(@Valid @ModelAttribute("newKH") KhachHang khachHang, Errors errors, Model model,
                         @RequestParam(name = "pageKH", defaultValue = "0") int pageKH,
                         @RequestParam(name = "sizeKH", defaultValue = "10") int sizeKH, RedirectAttributes redirectAttributes) {
        if(errors.hasErrors()) {
            Page<KhachHang> list = KHService.getAllKhachHang(pageKH, sizeKH);
            model.addAttribute("trangDau", pageKH);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listKH", KHService.getAllKhachHang(pageKH, sizeKH));
            model.addAttribute("message","Thêm khách hàng thất bại!");
            return "View/page/QL_khachHang";
        }
        if(KHService.checkTrung(khachHang.getMaKH())){
            Page<KhachHang> list = KHService.getAllKhachHang(pageKH, sizeKH);
            model.addAttribute("trangDau", pageKH);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listKH", KHService.getAllKhachHang(pageKH, sizeKH));
            redirectAttributes.addFlashAttribute("message","Khách hàng đã tồn tại!");
            return "redirect:/ql_khachHang";
        }
        KHService.saveKhachHang(khachHang);
        model.addAttribute("newKH",khachHang);
        model.addAttribute("listKH", KHService.getAllKhachHang(pageKH, sizeKH));
        redirectAttributes.addFlashAttribute("type","Thêm khách hàng thành công!");
        return "redirect:/ql_khachHang";
    }
    @PostMapping("/updateKH")
    public String updateKH(@Valid @ModelAttribute("newKH") KhachHang khachHang, Errors errors, Model model,
                         @RequestParam(name = "pageKH", defaultValue = "0") int pageKH,
                         @RequestParam(name = "sizeKH", defaultValue = "10") int sizeKH, RedirectAttributes redirectAttributes) {
        if(errors.hasErrors()) {
            Page<KhachHang> list = KHService.getAllKhachHang(pageKH, sizeKH);
            model.addAttribute("trangDau", pageKH);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listKH", KHService.getAllKhachHang(pageKH, sizeKH));
            model.addAttribute("message","Cập nhật khách hàng thất bại!");
            return "View/page/KhachHangDetail";
        }
        KHService.saveKhachHang(khachHang);
        model.addAttribute("newKH",khachHang);
        model.addAttribute("listKH", KHService.getAllKhachHang(pageKH, sizeKH));
        redirectAttributes.addFlashAttribute("type","Cập nhật khách hàng thành công!");
        return "redirect:/ql_khachHang";
    }

    @GetMapping("/editKH/{id}")
    public String editKH(@PathVariable("id") int id, Model model) {
        KhachHang kh = KHService.getByIdKhachHang(id);
        model.addAttribute("newKH", kh);
        return "View/page/KhachHangDetail";
    }

    @GetMapping("/deleteKH/{id}")
    public String deleteKH(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        KhachHang kh = KHService.getByIdKhachHang(id);
        if(kh == null) {
            redirectAttributes.addFlashAttribute("message","Xóa thất bại!");
            return "redirect:/ql_khachHang";
        }
        KHService.deleteKhachHang(id);
        redirectAttributes.addFlashAttribute("type","Xóa thành công!");
        return "redirect:/ql_khachHang";
    }

    @GetMapping("/locKH")
    public String locKhachHang(@RequestParam("trangThai") String trangThai, Model model,
                               @RequestParam(name = "pageKH", defaultValue = "0") int pageKH,
                               @RequestParam(name = "sizeKH", defaultValue = "10") int sizeKH){
        Page<KhachHang> locKH = KHService.locKH(trangThai, pageKH, sizeKH);
        model.addAttribute("trangDau", pageKH);
        model.addAttribute("tongTrang", locKH.getTotalPages());
        model.addAttribute("listKH",locKH);
        model.addAttribute("newKH", new KhachHang());
        return "View/page/QL_khachHang";
    }
}
