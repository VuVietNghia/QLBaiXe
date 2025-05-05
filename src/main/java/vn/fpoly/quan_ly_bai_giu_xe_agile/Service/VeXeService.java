package vn.fpoly.quan_ly_bai_giu_xe_agile.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.LoaiPhuongTien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.LoaiVe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlBaiXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlVeXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.QlVeXeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class VeXeService {
    @Autowired
    private QlVeXeRepository qlVeXeRepository;

    public VeXeService(QlVeXeRepository qlVeXeRepository) {
        this.qlVeXeRepository = qlVeXeRepository;
    }

    public Page<QlVeXe> getAllVeXe(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<QlVeXe> danhSach = qlVeXeRepository.findAll(pageable);
        LocalDate today = LocalDate.now();

        for (QlVeXe ve : danhSach) {
            if ("Còn hạn".equals(ve.getTrangThaiVe())
                    && ve.getNgayHetHan() != null
                    && today.isAfter(ve.getNgayHetHan())) {
                // Tránh gọi save() gây lỗi validation
                qlVeXeRepository.updateTrangThaiVe("Hết hạn", ve.getId());
            }
        }
        return danhSach;
    }


    public void saveVeXe(QlVeXe qlVeXe){
        qlVeXeRepository.save(qlVeXe);
    }

    public QlVeXe getByIdVeXe(int id){
        return qlVeXeRepository.findById(id).orElseGet(() -> null);
    }

    public void deleteVeXe(int id){
        qlVeXeRepository.deleteById(id);
    }

    public Page<QlVeXe> locVeXeByLoaive(LoaiVe loaiVe, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return qlVeXeRepository.findQlVeXeByIdLoaiVe(loaiVe, pageable);
    }

    public Page<QlVeXe> locVeXeByLoaiPT(LoaiPhuongTien loaiPT, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return qlVeXeRepository.findQlVeXeByLoaiPT(loaiPT, pageable);
    }

    public Page<QlVeXe> locVeXeByDonGia(Float donGia, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return qlVeXeRepository.findQlVeXeByDonGia(donGia, pageable);
    }

    public Page<QlVeXe> locVeXeByTrangThaiVe(String trangThaiVe, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return qlVeXeRepository.findQlVeXeByTrangThaiVe(trangThaiVe, pageable);
    }

    public boolean checkTrungMaVe(String maVe){
        return qlVeXeRepository.existsByMaVe(maVe);
    }

    public boolean checkTrungBienSo(String bienSoXe){
        return qlVeXeRepository.existsByBienSoXe(bienSoXe);
    }
}
