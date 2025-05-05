package vn.fpoly.quan_ly_bai_giu_xe_agile.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.HoTro;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.HoTroRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HoTroService {
    @Autowired
    private HoTroRepository hoTroRepository;

    public List<HoTro> getAllHoTro() {
        return hoTroRepository.findAll();
    }
    public void addHoTro(HoTro hoTro){
        hoTroRepository.save(hoTro);
    }
    public HoTro getById(int id){
        return hoTroRepository.findById(id).orElseGet(()->null);
    }
    public void deleteHoTro(int id){
        hoTroRepository.deleteById(id);
    }
    public List<HoTro> getHoTroBysdt(String sdt) {
        return hoTroRepository.findBySdt(sdt);
    }

    public HoTro updateTrangThaiHoTro(int id, Integer trangThaiHoTro) {
        Optional<HoTro> optionalHoTro = hoTroRepository.findById(id);
        if (optionalHoTro.isPresent()) {
            HoTro hoTro = optionalHoTro.get();
            hoTro.setTrangThaiHoTro(trangThaiHoTro);
            return hoTroRepository.save(hoTro);
        } else {
            throw new RuntimeException("HoTro not found with id: " + id);
        }
    }
}
