package vn.fpoly.quan_ly_bai_giu_xe_agile.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.HoTro;

import java.util.List;

public interface HoTroRepository extends JpaRepository<HoTro, Integer> {

    @Query("SELECT ht FROM HoTro ht WHERE ht.sdt = :sdt")
    List<HoTro> findBySdt(@Param("sdt") String sdt);
}
