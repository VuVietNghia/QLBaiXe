package vn.fpoly.quan_ly_bai_giu_xe_agile.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.ChucVu;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.NhanVien;

import java.util.List;

public interface QLNhanVienRepository extends JpaRepository<NhanVien, Integer> {
    Page<NhanVien> findAll(Pageable pageable);
    boolean existsByMaNV(String maNV);

    Page<NhanVien> findNhanVienByChucvu(ChucVu chucvu, Pageable pageable);
    Page<NhanVien> findNhanVienByTrangThai(String trangThai, Pageable pageable);
}
