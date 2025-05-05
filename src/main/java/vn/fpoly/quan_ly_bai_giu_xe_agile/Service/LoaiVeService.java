package vn.fpoly.quan_ly_bai_giu_xe_agile.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.LoaiVe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.LoaiVeRepository;

import java.util.List;

@Service
public class LoaiVeService {
    @Autowired
    private LoaiVeRepository loaiVeRepo;

    public LoaiVeService(LoaiVeRepository loaiVeRepo) {
        this.loaiVeRepo = loaiVeRepo;
    }
    public List<LoaiVe> getAllLoaiVe(){
        return loaiVeRepo.findAll();
    }
}
