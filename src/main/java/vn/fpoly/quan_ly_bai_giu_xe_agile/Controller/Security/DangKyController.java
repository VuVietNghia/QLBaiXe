package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller.Security;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.NguoiDung;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.NguoiDungRepository;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.NguoiDungService;

import java.util.List;

@Controller
public class DangKyController {
    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @GetMapping("/dangKy")
    public String dangKy(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<NguoiDung> listND = nguoiDungService.getAllNguoiDung(page, size);
        model.addAttribute("listND", listND);
        model.addAttribute("newND", new NguoiDung());
        return "View/Security/dangKy";
    }

    @PostMapping("/dangKy_register")
    public String dangKyRegister(@Valid @ModelAttribute("newND") NguoiDung nguoiDung, Model model, RedirectAttributes redirectAttributes) {
        if(nguoiDungService.checkTrungUserName(nguoiDung.getUserName())){
            model.addAttribute("message","Tên đăng nhập đã tồn tại!");
            return "View/Security/dangKy";
        }else if(nguoiDungService.checkTrungEmail(nguoiDung.getEmail())){
            model.addAttribute("message","Email đã tồn tại!");
            return "View/Security/dangKy";
        }
        nguoiDungService.saveNguoiDung(nguoiDung);
        model.addAttribute("newND", nguoiDung);
        redirectAttributes.addFlashAttribute("type","Đăng ký thành công!");
        return "redirect:/dangKy";
    }
}
