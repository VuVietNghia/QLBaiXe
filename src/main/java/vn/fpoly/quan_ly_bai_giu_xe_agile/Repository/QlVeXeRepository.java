package vn.fpoly.quan_ly_bai_giu_xe_agile.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.LoaiPhuongTien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.LoaiVe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlBaiXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlVeXe;

import java.util.List;

public interface QlVeXeRepository extends JpaRepository<QlVeXe, Integer> {
    boolean existsByMaVe(String maVe);
    boolean existsByBienSoXe(String bienSoXe);
    Page<QlVeXe> findAll(Pageable pageable);
    Page<QlVeXe> findQlVeXeByIdLoaiVe(LoaiVe loaiVe, Pageable pageable);
    Page<QlVeXe> findQlVeXeByLoaiPT(LoaiPhuongTien loaiPT, Pageable pageable);
    Page<QlVeXe> findQlVeXeByDonGia(Float donGia, Pageable pageable);
    Page<QlVeXe> findQlVeXeByTrangThaiVe(String trangThaiVe, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE QlVeXe v SET v.trangThaiVe = :trangThai WHERE v.id = :id")
    void updateTrangThaiVe(String trangThai, Integer id);
}