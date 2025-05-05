package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller.Admin_NhanVien;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.HoTro;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.HoTroService;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.KhachHangService;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.QLNhanVienService;

import java.util.List;

@Controller
public class HoTroController {
    @Autowired
    private HoTroService hoTroService;

    @Autowired
    private QLNhanVienService nhanVienService;

    @Autowired
    private KhachHangService khachHangService;

    @RequestMapping("/hoTro")
    public String hoTro(Model model,
                        @RequestParam(name = "pageKH", defaultValue = "0") int pageKH,
                        @RequestParam(name = "sizeKH", defaultValue = "10") int sizeKH,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size) {
        List<HoTro> hoTro = hoTroService.getAllHoTro();
        model.addAttribute("hoTro", hoTro);
        model.addAttribute("nhanVien", nhanVienService.getAllNhanVien(page, size));
        model.addAttribute("khachHang", khachHangService.getAllKhachHang(pageKH, sizeKH));
        return "View/page/HoTro";
    }
    @RequestMapping("/hoTroKH")
    public String hoTroKH(Model model,
                        @RequestParam(name = "pageKH", defaultValue = "0") int pageKH,
                        @RequestParam(name = "sizeKH", defaultValue = "10") int sizeKH,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size) {
        List<HoTro> hoTro = hoTroService.getAllHoTro();
        model.addAttribute("hoTro", hoTro);
        model.addAttribute("nhanVien", nhanVienService.getAllNhanVien(page, size));
        model.addAttribute("khachHang", khachHangService.getAllKhachHang(pageKH, sizeKH));
        model.addAttribute("newHT", new HoTro());
        return "View/page/KhachHangHoTro";
    }
    @PostMapping("/addHoTro")
    public String addHoTro(@Valid @ModelAttribute("newHT") HoTro hotro, Errors errors, Model model,
                           RedirectAttributes redirectAttributes,  @RequestParam(name = "pageKH", defaultValue = "0") int pageKH,
                           @RequestParam(name = "sizeKH", defaultValue = "10") int sizeKH,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size){
        model.addAttribute("nhanVien", nhanVienService.getAllNhanVien(page, size));
        model.addAttribute("khachHang", khachHangService.getAllKhachHang(pageKH, sizeKH));
        if(errors.hasErrors()){
            model.addAttribute("message","Gửi thông tin hỗ trợ thất bại!");
            return "View/page/KhachHangHoTro";
        }
        hoTroService.addHoTro(hotro);
        model.addAttribute("newHT", new HoTro());
        redirectAttributes.addFlashAttribute("type","Gửi thông tin hỗ trợ thành công! Quản lý sẽ hỗ trợ bạn sớm nhất.");
        return "redirect:/hoTroKH";
    }
    @GetMapping("/deleteHT/{id}")
    public String deleteHoTro(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes){
        HoTro getByIdHT = hoTroService.getById(id);
        if(getByIdHT == null){
            model.addAttribute("message","Xóa thất bại!");
            return "View/page/HoTro";
        }
        hoTroService.deleteHoTro(id);
        redirectAttributes.addFlashAttribute("type","Xóa thành công!");
        return "redirect:/hoTro";
    }

    @RequestMapping("/ho-tro/sdt")
    public String searchBySdt(@RequestParam("sdt") String sdt, Model model) {
        List<HoTro> hoTroList = hoTroService.getHoTroBysdt(sdt);
        model.addAttribute("hoTro", hoTroList);
        model.addAttribute("sdt", sdt != null ? sdt : "");
        return "View/page/HoTro";
    }

    @RequestMapping("/ho-tro/updateTrangThai")
    public String updateTrangThaiHoTro(
            @RequestParam("id") int id,
            @RequestParam("trangThaiHoTro") Integer trangThaiHoTro,
            @RequestParam(name = "pageKH", defaultValue = "0") int pageKH,
            @RequestParam(name = "sizeKH", defaultValue = "10") int sizeKH,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            Model model) {
        try {
            hoTroService.updateTrangThaiHoTro(id, trangThaiHoTro);
            return "redirect:/hoTro";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            List<HoTro> hoTro = hoTroService.getAllHoTro();
            model.addAttribute("hoTro", hoTro);
            model.addAttribute("nhanVien", nhanVienService.getAllNhanVien(page, size));
            model.addAttribute("khachHang", khachHangService.getAllKhachHang(pageKH, sizeKH));
            return "View/page/HoTro";
        }
    }
}
