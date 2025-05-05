package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller.Admin_NhanVien;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.LoaiPhuongTien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlPhuongTien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlVeXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.LoaiPhuongTienService;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.QlPhuongTienService;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.VeXeService;

import java.util.List;

@Controller
public class QLPhuongTienController {
    @Autowired
    private LoaiPhuongTienService loaiPTService;

    @Autowired
    private QlPhuongTienService QLPTService;
    @Autowired
    private VeXeService QLVXService;

    @GetMapping("/ql_phuongTien")
    public String ql_phuongTien(Model model, @RequestParam(name = "pageVX", defaultValue = "0") int pageVX,
                                @RequestParam(name = "sizeVX", defaultValue = "10") int sizeVX,
                                @RequestParam(name = "pagePT", defaultValue = "0") int pagePT,
                                @RequestParam(name = "sizePT", defaultValue = "5") int sizePT) {
        Page<QlPhuongTien> list = QLPTService.getAllQLPT(pagePT, sizePT);
        model.addAttribute("trangDau", pagePT);
        model.addAttribute("tongTrang", list.getTotalPages());
        model.addAttribute("listPT", list);
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("listQLVX", QLVXService.getAllVeXe(pageVX, sizeVX));
        model.addAttribute("newPT", new QlPhuongTien());
        return "View/page/QL_phuongTien";
    }

    @PostMapping("/savePT")
    public String savePT(@Valid @ModelAttribute("newPT") QlPhuongTien qlPT, Errors errors, Model model, @RequestParam("file") MultipartFile file,
                         @RequestParam(name = "pageVX", defaultValue = "0") int pageVX,
                         @RequestParam(name = "sizeVX", defaultValue = "10") int sizeVX,
                         @RequestParam(name = "pagePT", defaultValue = "0") int pagePT,
                         @RequestParam(name = "sizePT", defaultValue = "5") int sizePT, RedirectAttributes redirectAttributes) {
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("listQLVX", QLVXService.getAllVeXe(pageVX, sizeVX));
        if(errors.hasErrors()){
            Page<QlPhuongTien> list = QLPTService.getAllQLPT(pagePT, sizePT);
            model.addAttribute("trangDau", pagePT);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listPT", QLPTService.getAllQLPT(pagePT, sizePT));
            model.addAttribute("message","Thêm phương tiện thất bại!");
            return "View/page/QL_phuongTien";
        }
        QLPTService.saveQLPT(qlPT, file);
        model.addAttribute("listPT", QLPTService.getAllQLPT(pagePT, sizePT));
        redirectAttributes.addFlashAttribute("type", "Thêm phương tiện thành công!");
        return "redirect:/ql_phuongTien";
    }

    @PostMapping("/updatePT")
    public String updatePT(@Valid @ModelAttribute("newPT") QlPhuongTien qlPT, Errors errors, Model model, @RequestParam("file") MultipartFile file,
                         @RequestParam(name = "pageVX", defaultValue = "0") int pageVX,
                         @RequestParam(name = "sizeVX", defaultValue = "10") int sizeVX,
                         @RequestParam(name = "pagePT", defaultValue = "0") int pagePT,
                         @RequestParam(name = "sizePT", defaultValue = "5") int sizePT, RedirectAttributes redirectAttributes) {
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("listQLVX", QLVXService.getAllVeXe(pageVX, sizeVX));
        if(errors.hasErrors()){
            Page<QlPhuongTien> list = QLPTService.getAllQLPT(pagePT, sizePT);
            model.addAttribute("trangDau", pagePT);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listPT", QLPTService.getAllQLPT(pagePT, sizePT));
            model.addAttribute("message","Cập nhật phương tiện thất bại!");
            return "View/page/PhuongTienDetail";
        }
        QLPTService.saveQLPT(qlPT, file);
        model.addAttribute("listPT", QLPTService.getAllQLPT(pagePT, sizePT));
        redirectAttributes.addFlashAttribute("type", "Cập nhật phương tiện thành công!");
        return "redirect:/ql_phuongTien";
    }

    @GetMapping("/editPT/{id}")
    public String editPT(@PathVariable("id") Integer id, Model model, @RequestParam(name = "pageVX", defaultValue = "0") int pageVX,
                         @RequestParam(name = "sizeVX", defaultValue = "10") int sizeVX,
                         @RequestParam(name = "pagePT", defaultValue = "0") int pagePT,
                         @RequestParam(name = "sizePT", defaultValue = "5") int sizePT){
        QlPhuongTien pt = QLPTService.getByIdPT(id);
        model.addAttribute("newPT",pt);
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("listQLVX", QLVXService.getAllVeXe(pageVX, sizeVX));
        model.addAttribute("listPT", QLPTService.getAllQLPT(pagePT, sizePT));
        return "View/page/PhuongTienDetail";
    }

    @GetMapping("/deletePT/{id}")
    public String deletePT(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes){
        QlPhuongTien pt = QLPTService.getByIdPT(id);
        if(pt == null){
            model.addAttribute("message","Xóa phương tiện thất bại!");
            return "View/page/QL_phuongTien";
        }
        QLPTService.deletePT(id);
        redirectAttributes.addFlashAttribute("type","Xóa phương tiện thành công!");
        return "redirect:/ql_phuongTien";
    }

    @GetMapping("/locPTByTenPT")
    public String locPTByTenPT(@RequestParam("loaiPT") LoaiPhuongTien loaiPT, Model model,
                               @RequestParam(name = "pageVX", defaultValue = "0") int pageVX,
                               @RequestParam(name = "sizeVX", defaultValue = "10") int sizeVX,
                               @RequestParam(name = "pagePT", defaultValue = "0") int pagePT,
                               @RequestParam(name = "sizePT", defaultValue = "5") int sizePT){
        Page<QlPhuongTien> locPTByTenPT = QLPTService.locPTByTenPT(loaiPT, pagePT,sizePT);
        model.addAttribute("trangDau", pagePT);
        model.addAttribute("tongTrang", locPTByTenPT.getTotalPages());
        model.addAttribute("listPT", locPTByTenPT);
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("listQLVX", QLVXService.getAllVeXe(pageVX, sizeVX));
        model.addAttribute("newPT", new QlPhuongTien());
        return "View/page/QL_phuongTien";
    }

    @GetMapping("/locPTByBienSo")
    public String locPTByBienSo(@RequestParam("qlvexe") QlVeXe qlvexe, Model model,
                                @RequestParam(name = "pageVX", defaultValue = "0") int pageVX,
                                @RequestParam(name = "sizeVX", defaultValue = "10") int sizeVX,
                                @RequestParam(name = "pagePT", defaultValue = "0") int pagePT,
                                @RequestParam(name = "sizePT", defaultValue = "5") int sizePT){
        Page<QlPhuongTien> locPTByBienSo = QLPTService.locPTByBienSo(qlvexe, pagePT, sizePT);
        model.addAttribute("trangDau", pagePT);
        model.addAttribute("tongTrang", locPTByBienSo.getTotalPages());
        model.addAttribute("listPT", locPTByBienSo);
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("listQLVX", QLVXService.getAllVeXe(pageVX, sizeVX));
        model.addAttribute("newPT", new QlPhuongTien());
        return "View/page/QL_phuongTien";
    }
}
