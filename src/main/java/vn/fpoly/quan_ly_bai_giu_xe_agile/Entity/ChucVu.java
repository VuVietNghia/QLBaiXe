package vn.fpoly.quan_ly_bai_giu_xe_agile.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "chuc_vu")
public class ChucVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "ma_chuc_vu")
    private String maChucVu;
    @Column(name = "ten_chuc_vu")
    private String tenChucVu;
    @Column(name = "mo_ta")
    private String moTa;
}
