package vn.fpoly.quan_ly_bai_giu_xe_agile.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlBaiXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.QlBaiXeRepository;

import java.util.List;

@Service
public class QLBaiXeService {
    @Autowired
    private QlBaiXeRepository qlBaiXeRepository;

    public QLBaiXeService(QlBaiXeRepository qlBaiXeRepository) {
        this.qlBaiXeRepository = qlBaiXeRepository;
    }

    public List<QlBaiXe> getAllQLBaiXe() {
        return qlBaiXeRepository.findAll();
    }
}
