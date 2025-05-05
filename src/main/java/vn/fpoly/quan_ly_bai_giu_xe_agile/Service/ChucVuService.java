package vn.fpoly.quan_ly_bai_giu_xe_agile.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.ChucVu;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.ChucVuRepository;

import java.util.List;

@Service
public class ChucVuService {
    @Autowired
    private ChucVuRepository chucVuRepo;

    public ChucVuService(ChucVuRepository chucVuRepo) {
        this.chucVuRepo = chucVuRepo;
    }

    public List<ChucVu> getAllChucVu() {
        return chucVuRepo.findAll();
    }
}
