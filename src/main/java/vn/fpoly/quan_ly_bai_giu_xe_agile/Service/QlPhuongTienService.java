package vn.fpoly.quan_ly_bai_giu_xe_agile.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.LoaiPhuongTien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlPhuongTien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.QlVeXe;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.QLPhuongTienRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class QlPhuongTienService {
    @Autowired
    private QLPhuongTienRepository QLPTRepo;
    private final String UPLOAD_DIR = "src/main/resources/image/";

    public QlPhuongTienService(QLPhuongTienRepository QLPTRepo) {
        this.QLPTRepo = QLPTRepo;
    }
    public Page<QlPhuongTien> getAllQLPT(int pagePT, int sizePT) {
        Pageable pageable = PageRequest.of(pagePT, sizePT);
        return QLPTRepo.findAll(pageable);
    }
    public void saveQLPT(QlPhuongTien QLPT, MultipartFile file) {
        if(!file.isEmpty()){
            try{
                String fileName = file.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);

                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                QLPT.setAnhPT("/image/"+fileName);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        QLPTRepo.save(QLPT);
    }

    public QlPhuongTien getByIdPT(int id){
        return QLPTRepo.findById(id).orElseGet(() -> null);
    }

    public void deletePT(int id){
            QLPTRepo.deleteById(id);
    }

    public Page<QlPhuongTien> locPTByTenPT(LoaiPhuongTien loaiPT, int pagePT, int sizePT){
        Pageable pageable = PageRequest.of(pagePT, sizePT);
        return QLPTRepo.findQlPhuongTienByLoaiPT(loaiPT, pageable);
    }
    public Page<QlPhuongTien> locPTByBienSo(QlVeXe qlvexe, int pagePT, int sizePT){
        Pageable pageable = PageRequest.of(pagePT, sizePT);
        return QLPTRepo.findQlPhuongTienByQlvexe(qlvexe, pageable);
    }
}
