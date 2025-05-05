package vn.fpoly.quan_ly_bai_giu_xe_agile.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.BaiXe;

public interface BaiXeRepository extends JpaRepository<BaiXe, Integer> {
    @Modifying
    @Query("UPDATE BaiXe bx SET bx.trangThai = :trangThai WHERE bx.id = :id")
    void updateTrangThaiById(@Param("id") Integer id, @Param("trangThai") String trangThai);
}