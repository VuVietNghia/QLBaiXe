package vn.fpoly.quan_ly_bai_giu_xe_agile.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.ChucVu;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.NhanVien;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.QLNhanVienRepository;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class QLNhanVienService {
    @Autowired
    private QLNhanVienRepository QLNVRepo;
    private final String UPLOAD_DIR = "src/main/resources/image/";

    public QLNhanVienService(QLNhanVienRepository QLNVRepo){
        this.QLNVRepo = QLNVRepo;
    }
    public Page<NhanVien> getAllNhanVien(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return QLNVRepo.findAll(pageable);
    }
    public void saveNhanVien(NhanVien nhanVien, MultipartFile file){
        if(!file.isEmpty()){
            try{
                String fileName = file.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);

                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                nhanVien.setAnhNV("/image/"+fileName);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        QLNVRepo.save(nhanVien);
    }

    public void updateNV(NhanVien nhanvien){
        QLNVRepo.save(nhanvien);
    }

    public NhanVien getByIdNhanVien(int id){
        return QLNVRepo.findById(id).orElseGet(() -> null);
    }

    public void deleteNhanVien(int id){
        QLNVRepo.deleteById(id);
    }
    public boolean existsByMaNV(String maNV) {
        return QLNVRepo.existsByMaNV(maNV);
    }

    public Page<NhanVien> locNhanVienByChucVu(ChucVu chucvu, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return QLNVRepo.findNhanVienByChucvu(chucvu, pageable);
    }

    public Page<NhanVien> locNhanVienByTrangThai(String trangThai, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return QLNVRepo.findNhanVienByTrangThai(trangThai, pageable);
    }

}
