package vn.fpoly.quan_ly_bai_giu_xe_agile.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.DoanhThu;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Repository.DoanhThuRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DoanhThuService {
    @Autowired
    private DoanhThuRepository doanhThuRepo;

    public List<DoanhThu> getAllDoanhThu() {
        return doanhThuRepo.findAll();
    }

    public List<DoanhThu> getDoanhThuByMonthAndYear(int month, int year) {
        return doanhThuRepo.findByMonthAndYear(month, year);
    }

    public DoanhThu addDoanhThu(DoanhThu doanhThu) {
        Optional<DoanhThu> latest = doanhThuRepo.findTopByOrderByMaDoanhThuDesc();
        String nextMa = "DT001";
        if (latest.isPresent()) {
            try {
                String maCu = latest.get().getMaDoanhThu(); // VD: "DT010"
                if (maCu != null && maCu.length() > 2 && maCu.startsWith("DT")) {
                    int so = Integer.parseInt(maCu.substring(2));
                    nextMa = String.format("DT%03d", so + 1);
                } else {
                    System.err.println("Warning: Unexpected maDoanhThu format found: " + maCu + ". Using default.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error parsing number from maDoanhThu: " + latest.get().getMaDoanhThu());
            }

        }
        doanhThu.setMaDoanhThu(nextMa);
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atTime(8, 0); // 8:00 AM today
        LocalDateTime end = today.atTime(18, 0); // 6:00 PM today
        doanhThu.setTgBatDauHoatDong(start);
        doanhThu.setTgKetThucHoatDong(end);
        doanhThu.setTgLuu(LocalDateTime.now());
        return doanhThuRepo.save(doanhThu);
    }

    public List<DoanhThu> findDoanhThuById(int id) {
        return doanhThuRepo.findById(id);
    }
}
