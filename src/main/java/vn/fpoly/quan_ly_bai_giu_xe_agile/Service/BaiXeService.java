package vn.fpoly.quan_ly_bai_giu_xe_agile.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.BaiXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.BaiXeRepository;

import java.util.List;

@Service
public class BaiXeService {
    @Autowired
    private BaiXeRepository baiXeRepo;

    public List<BaiXe> getAllBaiXe() {
        return baiXeRepo.findAll();
    }

    @Transactional
    public void updateTrangThai(Integer id, String trangThai) {
        baiXeRepo.updateTrangThaiById(id, trangThai);
    }
}
