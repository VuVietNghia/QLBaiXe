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
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.ChucVu;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.NhanVien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.ChucVuService;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.QLNhanVienService;

import java.util.List;

@Controller
public class QLNhanVienController {

    @Autowired
    private QLNhanVienService qlNVService;

    @Autowired
    private ChucVuService ChucvuService;

    @GetMapping("/ql_nhanVien")
    public String nhanVienHienThi(Model model,@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<NhanVien> list = qlNVService.getAllNhanVien(page, size);
        model.addAttribute("trangDau", page);
        model.addAttribute("tongTrang", list.getTotalPages());
        model.addAttribute("listNV", list);
        model.addAttribute("listChucVu", ChucvuService.getAllChucVu());
        model.addAttribute("newNV", new NhanVien());
        return "View/page/qlNhanVien";
    }
    @PostMapping("/saveNV")
    public String saveNhanVien(Model model,
                               @Valid @ModelAttribute("newNV") NhanVien nhanVien,
                               Errors errors, @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "5") int size,
                               @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        model.addAttribute("listChucVu", ChucvuService.getAllChucVu());
        if (errors.hasErrors()) {
            Page<NhanVien> list = qlNVService.getAllNhanVien(page, size);
            model.addAttribute("trangDau", page);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listNV", qlNVService.getAllNhanVien(page, size));
            model.addAttribute("message", "Thêm nhân viên thất bại!");
            return "View/page/qlNhanVien";
        }
        if (qlNVService.existsByMaNV(nhanVien.getMaNV())) {
            Page<NhanVien> list = qlNVService.getAllNhanVien(page, size);
            model.addAttribute("trangDau", page);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listNV", qlNVService.getAllNhanVien(page, size));
            model.addAttribute("message", "Nhân viên đã tồn tại!");
            return "View/page/qlNhanVien";
        }
        qlNVService.saveNhanVien(nhanVien, file);
        model.addAttribute("listNV", qlNVService.getAllNhanVien(page, size));
        model.addAttribute("newNV", new NhanVien());
        redirectAttributes.addFlashAttribute("type", "Thêm nhân viên thành công!");
        return "redirect:/ql_nhanVien";
    }

    @PostMapping("/updateNV")
    public String updateNhanVien(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "5") int size,
                               @Valid @ModelAttribute("newNV") NhanVien nhanVien,
                               Errors errors, RedirectAttributes redirectAttributes) {
        model.addAttribute("listChucVu", ChucvuService.getAllChucVu());
        if (errors.hasErrors()) {
            Page<NhanVien> list = qlNVService.getAllNhanVien(page, size);
            model.addAttribute("trangDau", page);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listNV", qlNVService.getAllNhanVien(page, size));
            model.addAttribute("message", "Cập nhật nhân viên thất bại!");
            return "View/page/CapNhatNhanVien";
        }
        qlNVService.updateNV(nhanVien);
        model.addAttribute("listNV", qlNVService.getAllNhanVien(page, size));
        model.addAttribute("newNV", new NhanVien());
        redirectAttributes.addFlashAttribute("type","Cập nhật nhân viên thành công");
        return "redirect:/ql_nhanVien";
    }


    @GetMapping("/editNV/{id}")
    public String editNV(@PathVariable("id") int id, Model model,@RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "5") int size) {
        NhanVien nv = qlNVService.getByIdNhanVien(id);
        model.addAttribute("newNV", nv);
        model.addAttribute("listChucVu", ChucvuService.getAllChucVu());
        model.addAttribute("listNV", qlNVService.getAllNhanVien(page, size));
        return "View/page/CapNhatNhanVien";
    }

    @GetMapping("/detailNV/{id}")
    public String DetailNV(@PathVariable("id") int id, Model model,@RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size) {
        NhanVien nv = qlNVService.getByIdNhanVien(id);
        model.addAttribute("newNV", nv);
        model.addAttribute("listChucVu", ChucvuService.getAllChucVu());
        model.addAttribute("listNV", qlNVService.getAllNhanVien(page, size));
        return "View/page/NhanVienDetail";
    }
    @GetMapping("/deleteNhanVien/{id}")
    public String daleteNhanVien(Model model, @PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        NhanVien nv = qlNVService.getByIdNhanVien(id);
        if(nv == null) {
            redirectAttributes.addFlashAttribute("message","Xóa nhân viên thất bại!");
            return "redirect:/ql_nhanVien";
        }
        qlNVService.deleteNhanVien(id);
        redirectAttributes.addFlashAttribute("type","Xóa nhân viên thành công!");
        return "redirect:/ql_nhanVien";
    }

    @GetMapping("/locNhanVienByCV")
    public String locNhanVienByCV(@RequestParam("chucvu") ChucVu chucvu, Model model,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "5") int size){
        Page<NhanVien> locNhanVienByCV = qlNVService.locNhanVienByChucVu(chucvu,page, size);
        model.addAttribute("trangDau", page);
        model.addAttribute("tongTrang", locNhanVienByCV.getTotalPages());
        model.addAttribute("listNV", locNhanVienByCV);
        model.addAttribute("listChucVu", ChucvuService.getAllChucVu());
        model.addAttribute("newNV", new NhanVien());
        return "View/page/qlNhanVien";
    }

    @GetMapping("/locNhanVienByTrangThai")
    public String locNhanVienByTrangThai(@RequestParam("trangThai") String trangThai, Model model,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "5") int size){
        Page<NhanVien> locNhanVienByTrangThai = qlNVService.locNhanVienByTrangThai(trangThai, page, size);
        model.addAttribute("trangDau", page);
        model.addAttribute("tongTrang", locNhanVienByTrangThai.getTotalPages());
        model.addAttribute("listNV", locNhanVienByTrangThai);
        model.addAttribute("listChucVu", ChucvuService.getAllChucVu());
        model.addAttribute("newNV", new NhanVien());
        return "View/page/qlNhanVien";
    }
}
