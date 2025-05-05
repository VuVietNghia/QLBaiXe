package vn.fpoly.quan_ly_bai_giu_xe_agile.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlXeRaVao;


public interface QlXeRaVaoRepository extends JpaRepository<QlXeRaVao, Integer> {
    Page<QlXeRaVao> findAll(Pageable pageable);
}