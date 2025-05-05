package vn.fpoly.quan_ly_bai_giu_xe_agile.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.LoaiPhuongTien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.LoaiPhuongTienRepository;

import java.util.List;

@Service
public class LoaiPhuongTienService {
    @Autowired
    private LoaiPhuongTienRepository loaiPTRepository;

    public LoaiPhuongTienService(LoaiPhuongTienRepository loaiPTRepository) {
        this.loaiPTRepository = loaiPTRepository;
    }
    public List<LoaiPhuongTien> getAllLoaiPT(){
        return loaiPTRepository.findAll();
    }
}
