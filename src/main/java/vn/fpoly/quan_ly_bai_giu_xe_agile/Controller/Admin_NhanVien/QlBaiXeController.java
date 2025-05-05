package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller.Admin_NhanVien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.BaiXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlBaiXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.BaiXeRepository;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.QLNhanVienRepository;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.QlBaiXeRepository;


import java.util.List;
import java.util.Optional;

@Controller
public class QlBaiXeController {

    @Autowired
    private BaiXeRepository baiXeRepository;
    @Autowired
    private QlBaiXeRepository qlBaiXeRepository;
    @Autowired
    private QLNhanVienRepository qlNhanVienRepository;

    @GetMapping("/ql_baiXe")
    public String ql_baiXe(Model model) {
        List<QlBaiXe> list = qlBaiXeRepository.findAll();
        model.addAttribute("listBaiXe", list);
        model.addAttribute("loi", "");
        return "View/page/QLBaiXeChiTiet/QL_baiXe";
    }

    @GetMapping("/ql_baiDo")
    public String ql_baiDo(Model model) {
        model.addAttribute("listBaiDo", baiXeRepository.findAll());
        return "View/page/QLBaiXeChiTiet/HomeBaiDo";
    }

    @GetMapping("/bai-xe/them")
    public String them(Model model) {
        model.addAttribute("baiXe", new BaiXe());
        return "View/page/QLBaiXeChiTiet/ThemBaiXe";
    }

    @GetMapping("/bai-xe/them-HD")
    public String themHD(Model model) {
        model.addAttribute("baiXe", new QlBaiXe());
        model.addAttribute("listQLBaiXe", baiXeRepository.findAll());
        model.addAttribute("listNhanVien", qlNhanVienRepository.findAll());
        return "View/page/QLBaiXeChiTiet/ThemBaiXeHD";
    }

    private Boolean checkBaiTrung(String ma) {
        List<QlBaiXe> list = qlBaiXeRepository.findAll();
        Optional<QlBaiXe> ql = list.stream()
                .filter(x -> x.getIdBaiXe().getMaKhuVuc().equals(ma))
                .findFirst();
        if (ql.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    private Boolean checkTrungMaBai(String ma) {
        List<BaiXe> list = baiXeRepository.findAll();
        Optional<BaiXe> ql = list.stream()
                .filter(x -> x.getMaKhuVuc().equals(ma))
                .findFirst();
        if (ql.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkBaiXe(QlBaiXe baiXe) {
        if (
                baiXe.getIdBaiXe() == null || baiXe.getIdBaiXe().equals("") ||
                baiXe.getIdNhanVien() == null || baiXe.getIdNhanVien().equals("") ||
                baiXe.getSlXeTrongBai() == null || baiXe.getSlXeTrongBai().equals("") ||
                baiXe.getTrangThaiBai() == null || baiXe.getTrangThaiBai().equals("") ||
                baiXe.getSlChoTrong() == null || baiXe.getSlChoTrong().equals("")
        ) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkBaiDo(BaiXe baiXe) {
        if (baiXe.getMaKhuVuc() == null || baiXe.getMaKhuVuc().equals("") ||
                baiXe.getTenKhuVuc() == null || baiXe.getTenKhuVuc().equals("") ||
                baiXe.getSucChuaToiDa() == null || baiXe.getSucChuaToiDa() <= 0 ||
                baiXe.getTrangThai() == null || baiXe.getTrangThai().equals("") ||
                checkTrungMaBai(baiXe.getMaKhuVuc())
        ) {
            return false;
        } else {
            return true;
        }
    }

    @PostMapping("/bai-xe/them-bai-xe")
    public String baiXeThem(@ModelAttribute BaiXe baiXe, RedirectAttributes redirectAttributes) {
        if (checkBaiDo(baiXe)) {
            baiXeRepository.save(baiXe);
            redirectAttributes.addFlashAttribute("trangThai", "success");
            redirectAttributes.addFlashAttribute("loi", "Thêm thành công.");
            return "redirect:/bai-xe/them";
        } else {
            redirectAttributes.addFlashAttribute("trangThai", "error");
            redirectAttributes.addFlashAttribute("loi", "Thêm thất bại, thiếu thông tin cần hoặc bãi xe đã hoạt động!");
            return "redirect:/bai-xe/them";
        }
    }

    @PostMapping("/bai-xe/them-bai-xe-HD")
    public String baiXeThemHD(@ModelAttribute QlBaiXe baiXe, Model model, RedirectAttributes redirectAttributes) {
        if (checkBaiXe(baiXe)&&checkBaiTrung(baiXe.getIdBaiXe().getMaKhuVuc())) {
            qlBaiXeRepository.save(baiXe);
            redirectAttributes.addFlashAttribute("trangThai", "success");
            redirectAttributes.addFlashAttribute("loi", "Thêm thành công.");
            return "redirect:/bai-xe/them-HD";
        } else {
            redirectAttributes.addFlashAttribute("trangThai", "error");
            redirectAttributes.addFlashAttribute("loi", "Thêm thất bại, thiếu thông tin cần thiết hoặc mã khu vực đã tồn tại!");
            return "redirect:/bai-xe/them-HD";
        }
    }

    @GetMapping("/bai-xe/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Optional<QlBaiXe> baiXeOptional = qlBaiXeRepository.findById(id);
        model.addAttribute("baiXe", baiXeOptional.get());
        model.addAttribute("listQLBaiXe", baiXeRepository.findAll());
        model.addAttribute("listNhanVien", qlNhanVienRepository.findAll());
        return "View/page/QLBaiXeChiTiet/ChiTietBaiXe";
    }

    @GetMapping("/bai-do/detail/{id}")
    public String detailBaiDo(@PathVariable Integer id, Model model) {
        Optional<BaiXe> baiXeOptional = baiXeRepository.findById(id);
        model.addAttribute("baiDo", baiXeOptional.get());
        return "View/page/QLBaiXeChiTiet/ChiTietBaiDo";
    }

    @GetMapping("/bai-xe/delete/{id}")
    public String deleteBaiXe(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        qlBaiXeRepository.deleteById(id);
        return "redirect:/ql_baiXe?loi=Xóa%20thành%20công&type=success";
    }

    @GetMapping("/bai-do/delete/{id}")
    public String deleteBaiDo(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        baiXeRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("trangThai", "success");
        redirectAttributes.addFlashAttribute("loi", "Xóa thành công.");
        return "redirect:/ql_baiDo";
    }

    @PostMapping("/bai-xe/update-bai-xe-HD")
    public String baiXeUpdateHD(@ModelAttribute QlBaiXe baiXe, RedirectAttributes redirectAttributes) {
        QlBaiXe baiXe1 = new QlBaiXe();
        baiXe1.setId(baiXe.getId());
        baiXe1.setIdBaiXe(baiXe.getIdBaiXe());
        baiXe1.setIdNhanVien(baiXe.getIdNhanVien());
        baiXe1.setSlXeTrongBai(baiXe.getSlXeTrongBai());
        baiXe1.setSlChoTrong(baiXe.getSlChoTrong());
        baiXe1.setTrangThaiBai(baiXe.getTrangThaiBai());
        if (checkBaiXe(baiXe1)) {
            qlBaiXeRepository.save(baiXe1);
            redirectAttributes.addFlashAttribute("trangThai", "success");
            redirectAttributes.addFlashAttribute("loi", "Cập nhật thành công.");
            return "redirect:/bai-xe/detail/" + baiXe.getId();
        } else {
            redirectAttributes.addFlashAttribute("trangThai", "error");
            redirectAttributes.addFlashAttribute("loi", "Cập nhật thất bại, thiếu thông tin cần thiết!");
            return "redirect:/bai-xe/detail/" + baiXe.getId();
        }

    }

    @PostMapping("/bai-do/update-bai-do-HD")
    public String baiDoUpdateHD(@ModelAttribute BaiXe baiDo, RedirectAttributes redirectAttributes) {
        BaiXe baiXe1 = new BaiXe();
        baiXe1.setId(baiDo.getId());
        baiXe1.setMaKhuVuc(baiDo.getMaKhuVuc());
        baiXe1.setTenKhuVuc(baiDo.getTenKhuVuc());
        baiXe1.setSucChuaToiDa(baiDo.getSucChuaToiDa());
        baiXe1.setTrangThai(baiDo.getTrangThai());
        if (checkBaiDo(baiXe1)) {
            baiXeRepository.save(baiXe1);
            redirectAttributes.addFlashAttribute("trangThai", "success"); // Hoặc "error"
            redirectAttributes.addFlashAttribute("loi", "Cập nhật thành công.");

            return "redirect:/bai-do/detail/" + baiDo.getId();
        } else {
            redirectAttributes.addFlashAttribute("trangThai", "error");
            redirectAttributes.addFlashAttribute("loi", "Cập nhật thất bại, thiếu thông tin cần thiết!");

            return "redirect:/bai-do/detail/" + baiDo.getId();
        }

    }
}
