package vn.fpoly.quan_ly_bai_giu_xe_agile.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.NguoiDung;

import java.util.List;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Page<NguoiDung> findAll(Pageable pageable);
    NguoiDung findByUserNameAndEmail(String userName, String email);
    Page<NguoiDung> findNguoiDungByVaiTro(String vaiTro, Pageable pageable);
}
