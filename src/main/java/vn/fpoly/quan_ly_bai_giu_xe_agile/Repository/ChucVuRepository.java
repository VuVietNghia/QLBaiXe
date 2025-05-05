package vn.fpoly.quan_ly_bai_giu_xe_agile.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.ChucVu;

@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu, Integer> {
}
