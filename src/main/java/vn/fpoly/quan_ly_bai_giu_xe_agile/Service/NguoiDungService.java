package vn.fpoly.quan_ly_bai_giu_xe_agile.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.NguoiDung;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.NguoiDungRepository;

import java.util.List;

@Service
public class NguoiDungService {
    @Autowired
    private NguoiDungRepository nguoiDungRepo;

    public NguoiDungService(NguoiDungRepository nguoiDungRepo) {
        this.nguoiDungRepo = nguoiDungRepo;
    }

    public Page<NguoiDung> getAllNguoiDung(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return nguoiDungRepo.findAll(pageable);
    }
    public void saveNguoiDung(NguoiDung nguoiDung) {
        nguoiDungRepo.save(nguoiDung);
    }
    public NguoiDung getByIdNguoiDung(int id){
        return nguoiDungRepo.findById(id).orElseGet(()->null);
    }
    public void deleteNguoiDung(int id) {
        nguoiDungRepo.deleteById(id);
    }
    public Page<NguoiDung> locVaiTro(String vaiTro, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return nguoiDungRepo.findNguoiDungByVaiTro(vaiTro, pageable);
    }
    public boolean checkTrungUserName(String userName){
        return nguoiDungRepo.existsByUserName(userName);
    }
    public boolean checkTrungEmail(String email){
        return nguoiDungRepo.existsByEmail(email);
    }



    public NguoiDung dangNhap(String userName, String password, String email) {
        NguoiDung nguoidung = nguoiDungRepo.findByUserNameAndEmail(userName, email);

        if(nguoidung != null && nguoidung.getPassword().equals(password) && nguoidung.getEmail().equals(email)) {
            return nguoidung;
        }
        return null;
    }
}
