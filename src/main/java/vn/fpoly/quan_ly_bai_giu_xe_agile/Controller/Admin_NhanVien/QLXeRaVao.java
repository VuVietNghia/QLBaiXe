package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller.Admin_NhanVien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlBaiXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlVeXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlXeRaVao;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.QlBaiXeRepository;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.QlVeXeRepository;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.QlXeRaVaoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class QLXeRaVao {
    @Autowired
    private QlXeRaVaoRepository qlXeRaVaoRepository;
    @Autowired
    private QlVeXeRepository qlVeXeRepository;
    @Autowired
    private QlBaiXeRepository qlBaiXeRepository;

    @GetMapping("/ql_xeRa_Vao")
    public String ql_xeRa_Vao(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {
        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by("thoiGianXeVao").descending());
        Page<QlXeRaVao> listXRVao = qlXeRaVaoRepository.findAll(pageable);
        model.addAttribute("listXeRaVao", listXRVao);
        model.addAttribute("currentPage", page);
        return "View/page/QlXeRaVao/QL_xeRa_Vao";
    }

    @GetMapping("/thong-tin-vao")
    public String thong_tin_vao(Model model) {
        return "View/page/QlXeRaVao/QLXeVao";
    }

    @GetMapping("/am-thanh-vao")
    public String am_thanh_vao(Model model) {
        return "View/page/QlXeRaVao/AmThanh";
    }

    @GetMapping("/thong-tin-ra")
    public String thong_tin_ra(Model model) {
        return "View/page/QlXeRaVao/QLXeRa";
    }

    private Integer GetIdByQLVe(String maVe) {
        List<QlVeXe> list = qlVeXeRepository.findAll();
        Optional<QlVeXe> xe = list.stream()
                .filter(q -> q.getMaVe().equals(maVe))
                .filter(q -> q.getTrangThaiVe().equals("Còn hạn"))
                .findFirst();
        return xe.isPresent() ? xe.get().getId() : null;
    }

    private QlXeRaVao GetIdByQLXeRaVao(int id) {
        List<QlXeRaVao> list = qlXeRaVaoRepository.findAll();
        List<QlVeXe> list1 = qlVeXeRepository.findAll();
        Optional<QlVeXe> qlVeXe = list1.stream()
                .filter(q -> q.getId() == id)
                .findFirst();
        int idXRV = qlVeXe.isPresent() ? qlVeXe.get().getId() : 0;
        Optional<QlXeRaVao> xe = list.stream()
                .filter(p -> p.getIdVeXe().getId() == idXRV && p.getThoiGianXeRa() == null)
                .findFirst();
        return xe.isPresent() ? xe.get() : null;
    }

    private Boolean soVeRaVao(String maVe) {
        List<QlXeRaVao> list = qlXeRaVaoRepository.findAll();
        boolean exists = list.stream()
                .anyMatch(s -> s.getIdVeXe().getMaVe().equals(maVe)&&s.getThoiGianXeRa() == null);
        // Nếu không tìm thấy vé trong danh sách, trả về true, nếu tìm thấy vé, trả về false
        return !exists;
    }

    private QlBaiXe getIdQlBaiXe(String maVe) {
        if (maVe == null || maVe.trim().isEmpty()) {
            return null;
        }
        Optional<QlVeXe> optionalVeXe = qlVeXeRepository.findAll()
                .stream()
                .filter(vx -> maVe.equals(vx.getMaVe()))
                .findFirst();
        if (optionalVeXe.isEmpty()) {
//            System.out.println("Không tìm thấy vé xe với mã: " + maVe);
            return null;
        }
        QlVeXe veXe = optionalVeXe.get();
        if (veXe.getIdQlBai() == null) {
//            System.out.println("Vé không chứa thông tin bãi xe.");
            return null;
        }
        Optional<QlBaiXe> optionalBaiXe = qlBaiXeRepository.findAll()
                .stream()
                .filter(bx -> bx.getId().equals(veXe.getIdQlBai().getId()))
                .findFirst();
        return optionalBaiXe.orElse(null);
    }


    @PostMapping("/thong-tin/xe-vao")
    public String thongTinXeRaVao(@RequestParam String maVe) {
        System.out.println("so ve ra vao "+soVeRaVao(maVe));
        System.out.println("id ve "+getIdQlBaiXe(maVe));
        if (GetIdByQLVe(maVe) != null && soVeRaVao(maVe)) {
            QlVeXe qlVeXe = new QlVeXe();
            qlVeXe.setId(GetIdByQLVe(maVe));
            QlXeRaVao qlxeRaVao = new QlXeRaVao();
            qlxeRaVao.setIdVeXe(qlVeXe);
            qlxeRaVao.setTrangThai("Đang gửi");
            qlxeRaVao.setThoiGianXeVao(LocalDateTime.now());
            qlXeRaVaoRepository.save(qlxeRaVao);

            QlBaiXe baiXe = getIdQlBaiXe(maVe);
            QlBaiXe qlBaiXe = new QlBaiXe();
            qlBaiXe.setId(baiXe.getId());
            qlBaiXe.setIdBaiXe(baiXe.getIdBaiXe());
            qlBaiXe.setIdNhanVien(baiXe.getIdNhanVien());
            qlBaiXe.setSlXeTrongBai(baiXe.getSlXeTrongBai()+1);
            qlBaiXe.setSlChoTrong(baiXe.getSlChoTrong());
            qlBaiXe.setTrangThaiBai(baiXe.getTrangThaiBai());
            qlBaiXeRepository.save(qlBaiXe);
            return "redirect:/ql_xeRa_Vao?type=success&loi=xevao";

        } else {

            return "redirect:/thong-tin-vao?type=error";
        }
    }


    @PostMapping("/thong-tin/xe-ra")
    public String thongTinXeRaRa(@RequestParam String maVe) {
        Integer idVeXe = GetIdByQLVe(maVe);
        if (idVeXe != null) {
            QlXeRaVao qlXeRaVaoData = GetIdByQLXeRaVao(idVeXe);

            if (qlXeRaVaoData != null) {
                Integer idXRV = qlXeRaVaoData.getId();
//                System.out.println("id xe ra :" + idXRV);

                QlXeRaVao qlxeRaVao = new QlXeRaVao();
                qlxeRaVao.setId(idXRV);
                qlxeRaVao.setIdVeXe(qlXeRaVaoData.getIdVeXe());
                qlxeRaVao.setThoiGianXeVao(qlXeRaVaoData.getThoiGianXeVao());
                qlxeRaVao.setThoiGianXeRa(LocalDateTime.now());
                qlxeRaVao.setTrangThai("Đã ra");
                qlXeRaVaoRepository.save(qlxeRaVao);

                QlBaiXe baiXe = getIdQlBaiXe(maVe);
                QlBaiXe qlBaiXe = new QlBaiXe();
                qlBaiXe.setId(baiXe.getId());
                qlBaiXe.setIdBaiXe(baiXe.getIdBaiXe());
                qlBaiXe.setIdNhanVien(baiXe.getIdNhanVien());
                qlBaiXe.setSlXeTrongBai(baiXe.getSlXeTrongBai()-1);
                qlBaiXe.setSlChoTrong(baiXe.getSlChoTrong());
                qlBaiXe.setTrangThaiBai(baiXe.getTrangThaiBai());
                qlBaiXeRepository.save(qlBaiXe);
                return "redirect:/ql_xeRa_Vao?type=success&loi=xera";
            } else {
                return "redirect:/thong-tin-ra?type=error"; // Xe chưa được vào
            }
        } else {
            return "redirect:/thong-tin-ra?type=error"; // Vé không tồn tại
        }
    }


}


