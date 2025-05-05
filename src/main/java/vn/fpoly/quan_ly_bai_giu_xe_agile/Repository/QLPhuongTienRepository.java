package vn.fpoly.quan_ly_bai_giu_xe_agile.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.LoaiPhuongTien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlPhuongTien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlVeXe;

import java.util.List;

public interface QLPhuongTienRepository extends JpaRepository<QlPhuongTien, Integer> {
    Page<QlPhuongTien> findAll(Pageable pageable);
    Page<QlPhuongTien> findQlPhuongTienByLoaiPT(LoaiPhuongTien loaiPT, Pageable pageable);
    Page<QlPhuongTien> findQlPhuongTienByQlvexe(QlVeXe qlvexe, Pageable pageable);
}
