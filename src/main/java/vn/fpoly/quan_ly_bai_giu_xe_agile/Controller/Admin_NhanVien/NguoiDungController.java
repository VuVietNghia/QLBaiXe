package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller.Admin_NhanVien;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.NguoiDung;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.NguoiDungService;

import java.util.List;

@Controller
public class NguoiDungController {
    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("/ql_nguoiDung")
    public String nguoiDung(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<NguoiDung> listND = nguoiDungService.getAllNguoiDung(page, size);
        model.addAttribute("trangDau", page);
        model.addAttribute("tongTrang", listND.getTotalPages());
        model.addAttribute("listND", listND);
        model.addAttribute("newND", new NguoiDung());
        return "View/page/ql_nguoiDung";
    }

    @PostMapping("/updateND")
    public String updateND(@Valid @ModelAttribute("newND") NguoiDung nguoiDung, Errors errors, Model model,@RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "10") int size, RedirectAttributes redirectAttributes) {
        if(errors.hasErrors()){
            Page<NguoiDung> listND = nguoiDungService.getAllNguoiDung(page, size);
            model.addAttribute("trangDau", page);
            model.addAttribute("tongTrang", listND.getTotalPages());
            model.addAttribute("listND", nguoiDungService.getAllNguoiDung(page, size));
            model.addAttribute("message","Cập nhật người dùng thất bại!");
            return "View/page/NguoiDungDetail";
        }
        nguoiDungService.saveNguoiDung(nguoiDung);
        model.addAttribute("newND", nguoiDung);
        redirectAttributes.addFlashAttribute("type","Cập nhật người dùng thành công");
        return "redirect:/ql_nguoiDung";
    }
    @GetMapping("/editND/{id}")
    public String editND(@PathVariable("id") int id, Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "10") int size) {
        NguoiDung nd = nguoiDungService.getByIdNguoiDung(id);
        model.addAttribute("newND", nd);
        model.addAttribute("listND", nguoiDungService.getAllNguoiDung(page, size));
        return "View/page/NguoiDungDetail";
    }
    @GetMapping("/deleteND/{id}")
    public String deleteND(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        NguoiDung nd = nguoiDungService.getByIdNguoiDung(id);
        if(nd == null) {
            redirectAttributes.addFlashAttribute("message","Xóa thất bại!");
            return "redirect:/ql_nguoiDung";
        }
        nguoiDungService.deleteNguoiDung(id);
        redirectAttributes.addFlashAttribute("type","Xóa thành công!");
       return "redirect:/ql_nguoiDung";
    }

    @GetMapping("/locNguoiDung")
    public String locNguoiDung(@RequestParam("vaiTro") String vaiTro, Model model,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "10") int size){
        Page<NguoiDung> locNguoiDung = nguoiDungService.locVaiTro(vaiTro, page, size);
        model.addAttribute("trangDau", page);
        model.addAttribute("tongTrang", locNguoiDung.getTotalPages());
        model.addAttribute("listND",locNguoiDung);
        model.addAttribute("newND", new NguoiDung());
        return "View/page/ql_nguoiDung";
    }
}
