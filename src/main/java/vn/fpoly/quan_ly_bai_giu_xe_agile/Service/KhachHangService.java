package vn.fpoly.quan_ly_bai_giu_xe_agile.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.KhachHang;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.NguoiDung;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.NhanVien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.KhachHangRepository;

import java.util.List;

@Service
public class KhachHangService {
    @Autowired
    private KhachHangRepository KHRepo;

    public KhachHangService(KhachHangRepository KHRepo) {
        this.KHRepo = KHRepo;
    }
    public Page<KhachHang> getAllKhachHang(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return KHRepo.findAll(pageable);
    }
    public void saveKhachHang(KhachHang khachHang) {
        KHRepo.save(khachHang);
    }
    public KhachHang getByIdKhachHang(int id){
        return KHRepo.findById(id).orElseGet(() -> null);
    }
    public void deleteKhachHang(int id) {
        KHRepo.deleteById(id);
    }

    public Page<KhachHang> locKH(String trangThai, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return KHRepo.findKhachHangByTrangThai(trangThai, pageable);
    }

    public boolean checkTrung(String maKH){
        return KHRepo.existsByMaKH(maKH);
    }
}
