package vn.fpoly.quan_ly_bai_giu_xe_agile.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.KhachHang;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.NguoiDung;

import java.util.List;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    boolean existsByMaKH(String maKH);
    Page<KhachHang> findAll(Pageable pageable);
    Page<KhachHang> findKhachHangByTrangThai(String trangThai, Pageable pageable);
}
