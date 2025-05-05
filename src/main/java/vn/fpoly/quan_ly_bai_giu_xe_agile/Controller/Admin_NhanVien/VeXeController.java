package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller.Admin_NhanVien;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.LoaiPhuongTien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.LoaiVe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlBaiXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlVeXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class VeXeController {

    @Autowired
    private VeXeService veXeService;
    @Autowired
    private QLBaiXeService QLBXService;
    @Autowired
    private LoaiVeService loaiVeService;
    @Autowired
    private LoaiPhuongTienService loaiPTService;

    @GetMapping("/ql_veXe")
    public String ql_veXe(Model model, @RequestParam(name = "page", defaultValue = "0") int pageVX,
                          @RequestParam(name = "size", defaultValue = "10") int sizeVX) {
        Page<QlVeXe> list = veXeService.getAllVeXe(pageVX, sizeVX);
        model.addAttribute("trangDau", pageVX);
        model.addAttribute("tongTrang", list.getTotalPages());
        model.addAttribute("listVX", list);
        model.addAttribute("listLoaiVe", loaiVeService.getAllLoaiVe());
        model.addAttribute("listQLBX", QLBXService.getAllQLBaiXe());
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("newVX", new QlVeXe());
        return "View/page/QL_veXe";
    }

    @GetMapping("/ql_veXeKH")
    public String ql_veXeKH(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int pageVX,
                            @RequestParam(name = "size", defaultValue = "10") int sizeVX) {
        Page<QlVeXe> list = veXeService.getAllVeXe(pageVX, sizeVX);
        model.addAttribute("trangDau", pageVX);
        model.addAttribute("tongTrang", list.getTotalPages());
        model.addAttribute("listVX", list);
        model.addAttribute("listLoaiVe", loaiVeService.getAllLoaiVe());
        model.addAttribute("listQLBX", QLBXService.getAllQLBaiXe());
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("newVX", new QlVeXe());
        return "View/page/DangKyVeXe";
    }


    @PostMapping("/saveVeXe")
    public String saveVeXe(@Valid @ModelAttribute("newVX") QlVeXe veXe, Errors errors, Model model,
                           @RequestParam(name = "page", defaultValue = "0") int pageVX,
                           @RequestParam(name = "size", defaultValue = "10") int sizeVX, RedirectAttributes redirectAttributes) {
        model.addAttribute("listLoaiVe", loaiVeService.getAllLoaiVe());
        model.addAttribute("listQLBX", QLBXService.getAllQLBaiXe());
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        if(errors.hasErrors()){
            Page<QlVeXe> list = veXeService.getAllVeXe(pageVX, sizeVX);
            model.addAttribute("trangDau", pageVX);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listVX", veXeService.getAllVeXe(pageVX, sizeVX));
            model.addAttribute("message", "Thêm vé xe thất bại!");
            return "View/page/QL_veXe";
        }
        if(veXeService.checkTrungMaVe(veXe.getMaVe())){
            Page<QlVeXe> list = veXeService.getAllVeXe(pageVX, sizeVX);
            model.addAttribute("trangDau", pageVX);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listVX", veXeService.getAllVeXe(pageVX, sizeVX));
            model.addAttribute("message", "Vé xe đã tồn tại!");
            return "View/page/QL_veXe";
        }
        if(veXeService.checkTrungBienSo(veXe.getBienSoXe())){
            Page<QlVeXe> list = veXeService.getAllVeXe(pageVX, sizeVX);
            model.addAttribute("trangDau", pageVX);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listVX", veXeService.getAllVeXe(pageVX, sizeVX));
            model.addAttribute("message", "Biển số xe đã tồn tại!");
            return "View/page/QL_veXe";
        }
        //TÍNH ĐƠN GIÁ DỰA TRÊN LOẠI VÉ + LOẠI PHƯƠNG TIỆN
        LoaiVe loaiVe = veXe.getIdLoaiVe();           // Đối tượng LoaiVe
        LoaiPhuongTien loaiPT = veXe.getLoaiPT();     // Đối tượng LoaiPhuongTien

        float donGia = 0;

        if (loaiVe != null && loaiPT != null) {
            String tenLoaiVe = loaiVe.getTenLoaiVe();
            String tenLoaiPT = loaiPT.getTenPT();

            if ("Vé ngày".equalsIgnoreCase(tenLoaiVe)) {
                donGia = 3000;
            } else if ("Vé tháng".equalsIgnoreCase(tenLoaiVe)) {
                if ("Xe máy".equalsIgnoreCase(tenLoaiPT)) {
                    donGia = 80000;
                } else if ("Xe điện".equalsIgnoreCase(tenLoaiPT)) {
                    donGia = 120000;
                } else if ("Xe đạp".equalsIgnoreCase(tenLoaiPT)) {
                    donGia = 60000;
                }
            }
        }
        veXe.setDonGia(donGia);
        veXeService.saveVeXe(veXe);
        model.addAttribute("listVX", veXeService.getAllVeXe(pageVX, sizeVX));
        model.addAttribute("newVX", new QlVeXe());
        redirectAttributes.addFlashAttribute("type","Thêm vé xe thành công!");
        return "redirect:/ql_veXe";
    }

    @PostMapping("/saveVeXeKH")
    public String saveVeXeKH(@Valid @ModelAttribute("newVX") QlVeXe veXe, Errors errors, Model model,
                           @RequestParam(name = "page", defaultValue = "0") int pageVX,
                           @RequestParam(name = "size", defaultValue = "10") int sizeVX, RedirectAttributes redirectAttributes) {
        model.addAttribute("listLoaiVe", loaiVeService.getAllLoaiVe());
        model.addAttribute("listQLBX", QLBXService.getAllQLBaiXe());
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        if(errors.hasErrors()){
            Page<QlVeXe> list = veXeService.getAllVeXe(pageVX, sizeVX);
            model.addAttribute("trangDau", pageVX);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listVX", veXeService.getAllVeXe(pageVX, sizeVX));
            model.addAttribute("message", "Đăng ký vé xe thất bại!");
            return "View/page/DangKyVeXe";
        }
        if(veXeService.checkTrungBienSo(veXe.getBienSoXe())){
            Page<QlVeXe> list = veXeService.getAllVeXe(pageVX, sizeVX);
            model.addAttribute("trangDau", pageVX);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listVX", veXeService.getAllVeXe(pageVX, sizeVX));
            model.addAttribute("message", "Biển số xe đã tồn tại!");
            return "View/page/DangKyVeXe";
        }
        //TÍNH ĐƠN GIÁ DỰA TRÊN LOẠI VÉ + LOẠI PHƯƠNG TIỆN
        LoaiVe loaiVe = veXe.getIdLoaiVe();           // Đối tượng LoaiVe
        LoaiPhuongTien loaiPT = veXe.getLoaiPT();     // Đối tượng LoaiPhuongTien

        float donGia = 0;

        if (loaiVe != null && loaiPT != null) {
            String tenLoaiVe = loaiVe.getTenLoaiVe();
            String tenLoaiPT = loaiPT.getTenPT();

            if ("Vé ngày".equalsIgnoreCase(tenLoaiVe)) {
                donGia = 3000;
            } else if ("Vé tháng".equalsIgnoreCase(tenLoaiVe)) {
                if ("Xe máy".equalsIgnoreCase(tenLoaiPT)) {
                    donGia = 80000;
                } else if ("Xe điện".equalsIgnoreCase(tenLoaiPT)) {
                    donGia = 120000;
                } else if ("Xe đạp".equalsIgnoreCase(tenLoaiPT)) {
                    donGia = 60000;
                }
            }
        }
        veXe.setDonGia(donGia);
        veXeService.saveVeXe(veXe);
        model.addAttribute("listVX", veXeService.getAllVeXe(pageVX, sizeVX));
        model.addAttribute("newVX", new QlVeXe());
        redirectAttributes.addFlashAttribute("type","Đăng ký vé xe thành công!");
        return "redirect:/ql_veXeKH";
    }


    @PostMapping("/updateVeXe")
    public String updateVeXe(@Valid @ModelAttribute("newVX") QlVeXe veXe, Errors errors, Model model,
                           @RequestParam(name = "page", defaultValue = "0") int pageVX,
                           @RequestParam(name = "size", defaultValue = "10") int sizeVX, RedirectAttributes redirectAttributes) {
        model.addAttribute("listLoaiVe", loaiVeService.getAllLoaiVe());
        model.addAttribute("listQLBX", QLBXService.getAllQLBaiXe());
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        if(errors.hasErrors()){
            Page<QlVeXe> list = veXeService.getAllVeXe(pageVX, sizeVX);
            model.addAttribute("trangDau", pageVX);
            model.addAttribute("tongTrang", list.getTotalPages());
            model.addAttribute("listVX", veXeService.getAllVeXe(pageVX, sizeVX));
            model.addAttribute("message", "Cập nhật vé xe thất bại!");
            return "View/page/VeXeDetail";
        }
        // 🔥 TÍNH ĐƠN GIÁ DỰA TRÊN LOẠI VÉ + LOẠI PHƯƠNG TIỆN
        LoaiVe loaiVe = veXe.getIdLoaiVe();           // Đối tượng LoaiVe
        LoaiPhuongTien loaiPT = veXe.getLoaiPT();     // Đối tượng LoaiPhuongTien

        float donGia = 0;

        if (loaiVe != null && loaiPT != null) {
            String tenLoaiVe = loaiVe.getTenLoaiVe();         // hoặc dùng getTenLoaiVe() nếu bạn muốn
            String tenLoaiPT = loaiPT.getTenPT();         // hoặc dùng getTenPT()

            if ("Vé ngày".equalsIgnoreCase(tenLoaiVe)) {
                donGia = 3000;
            } else if ("Vé tháng".equalsIgnoreCase(tenLoaiVe)) {
                if ("Xe máy".equalsIgnoreCase(tenLoaiPT)) {
                    donGia = 80000;
                } else if ("Xe điện".equalsIgnoreCase(tenLoaiPT)) {
                    donGia = 120000;
                } else if ("Xe đạp".equalsIgnoreCase(tenLoaiPT)) {
                    donGia = 60000;
                }
            }
        }
        veXe.setDonGia(donGia);
        veXeService.saveVeXe(veXe);
        model.addAttribute("listVX", veXeService.getAllVeXe(pageVX, sizeVX));
        model.addAttribute("newVX", new QlVeXe());
        redirectAttributes.addFlashAttribute("type","Cập nhật vé xe thành công!");
        return "redirect:/ql_veXe";
    }
    @GetMapping("/editVX/{id}")
    public String editVeXe(@PathVariable("id") Integer id, Model model){
        QlVeXe veXe = veXeService.getByIdVeXe(id);
        model.addAttribute("listLoaiVe", loaiVeService.getAllLoaiVe());
        model.addAttribute("listQLBX", QLBXService.getAllQLBaiXe());
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("newVX",veXe);
        return "View/page/VeXeDetail";
    }

    @GetMapping("/deleteVX/{id}")
    public String deleteVeXe(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes){
        QlVeXe veXe = veXeService.getByIdVeXe(id);
        if(veXe == null){
            redirectAttributes.addFlashAttribute("message","Xóa vé xe thất bại!");
            return "redirect:/ql_veXe";
        }
        veXeService.deleteVeXe(id);
        redirectAttributes.addFlashAttribute("type","Xóa vé xe thành công!");
        return "redirect:/ql_veXe";
    }

    @GetMapping("/locVeXeByLoaiVe")
    public String locVXByLoaiVe(@RequestParam("idLoaiVe") LoaiVe idLoaiVe, Model model,
                                @RequestParam(name = "page", defaultValue = "0") int pageVX,
                                @RequestParam(name = "size", defaultValue = "10") int sizeVX){
        Page<QlVeXe> locVXBYLoaiVe = veXeService.locVeXeByLoaive(idLoaiVe, pageVX, sizeVX);
        model.addAttribute("trangDau", pageVX);
        model.addAttribute("tongTrang", locVXBYLoaiVe.getTotalPages());
        model.addAttribute("listVX", locVXBYLoaiVe);
        model.addAttribute("listLoaiVe", loaiVeService.getAllLoaiVe());
        model.addAttribute("listQLBX", QLBXService.getAllQLBaiXe());
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("newVX", new QlVeXe());
        return "View/page/QL_veXe";
    }

    @GetMapping("/locVeXeByLoaiPT")
    public String locVXByLoaiPT(@RequestParam("loaiPT") LoaiPhuongTien loaiPT, Model model,
                                @RequestParam(name = "page", defaultValue = "0") int pageVX,
                                @RequestParam(name = "size", defaultValue = "10") int sizeVX){
        Page<QlVeXe> locVXBYLoaiPT = veXeService.locVeXeByLoaiPT(loaiPT, pageVX, sizeVX);
        model.addAttribute("trangDau", pageVX);
        model.addAttribute("tongTrang", locVXBYLoaiPT.getTotalPages());
        model.addAttribute("listVX", locVXBYLoaiPT);
        model.addAttribute("listLoaiVe", loaiVeService.getAllLoaiVe());
        model.addAttribute("listQLBX", QLBXService.getAllQLBaiXe());
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("newVX", new QlVeXe());
        return "View/page/QL_veXe";
    }

    @GetMapping("/locVeXeByDonGia")
    public String locVXByDonGia(@RequestParam("donGia") Float donGia, Model model, @RequestParam(name = "page", defaultValue = "0") int pageVX,
                                @RequestParam(name = "size", defaultValue = "10") int sizeVX){
        Page<QlVeXe> locVXByDonGia = veXeService.locVeXeByDonGia(donGia, pageVX, sizeVX);
        model.addAttribute("trangDau", pageVX);
        model.addAttribute("tongTrang", locVXByDonGia.getTotalPages());
        model.addAttribute("listVX", locVXByDonGia);
        model.addAttribute("listLoaiVe", loaiVeService.getAllLoaiVe());
        model.addAttribute("listQLBX", QLBXService.getAllQLBaiXe());
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("newVX", new QlVeXe());
        return "View/page/QL_veXe";
    }


    @GetMapping("/locVeXeByTrangThai")
    public String locVXByTrangThai(@RequestParam("trangThaiVe") String trangThaiVe, Model model, @RequestParam(name = "page", defaultValue = "0") int pageVX,
                                   @RequestParam(name = "size", defaultValue = "10") int sizeVX){
        Page<QlVeXe> locVXByTrangThaiVe = veXeService.locVeXeByTrangThaiVe(trangThaiVe, pageVX, sizeVX);
        model.addAttribute("trangDau", pageVX);
        model.addAttribute("tongTrang", locVXByTrangThaiVe.getTotalPages());
        model.addAttribute("listVX", locVXByTrangThaiVe);
        model.addAttribute("listLoaiVe", loaiVeService.getAllLoaiVe());
        model.addAttribute("listQLBX", QLBXService.getAllQLBaiXe());
        model.addAttribute("listLoaiPT", loaiPTService.getAllLoaiPT());
        model.addAttribute("newVX", new QlVeXe());
        return "View/page/QL_veXe";
    }
}
