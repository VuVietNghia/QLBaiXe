package vn.fpoly.quan_ly_bai_giu_xe_agile.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "doanh_thu")
public class DoanhThu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ma_doanh_thu")
    private String maDoanhThu;

    @Column(name = "tien_doanh_thu")
    private Double tienDoanhThu;

    @Column(name = "thoi_gian_bat_dau_hoat_dong")
    private LocalDateTime tgBatDauHoatDong;

    @Column(name = "thoi_gian_ket_thuc_hoat_dong")
    private LocalDateTime tgKetThucHoatDong;

    @Column(name = "thoi_gian_luu")
    private LocalDateTime tgLuu;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien idNhanVien;

    @ManyToOne
    @JoinColumn(name = "id_bai_xe")
    private BaiXe idBaiXe;
}
