package vn.fpoly.quan_ly_bai_giu_xe_agile.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.DoanhThu;

import java.util.List;
import java.util.Optional;

public interface DoanhThuRepository extends JpaRepository<DoanhThu, Integer> {
    // Báo cáo doanh thu theo tháng và năm
    @Query("SELECT d FROM DoanhThu d WHERE MONTH(d.tgLuu) = :month AND YEAR(d.tgLuu) = :year")
    List<DoanhThu> findByMonthAndYear(@Param("month") int month, @Param("year") int year);

    @Query("SELECT d FROM DoanhThu d WHERE d.id = :id")
    List<DoanhThu> findById(@Param("id") int id);

    @Query("SELECT d FROM DoanhThu d ORDER BY d.maDoanhThu DESC LIMIT 1")
    Optional<DoanhThu> findTopByOrderByMaDoanhThuDesc();
}
