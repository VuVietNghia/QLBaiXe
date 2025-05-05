package vn.fpoly.quan_ly_bai_giu_xe_agile.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "loai_phuong_tien")
public class LoaiPhuongTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "ma_phuong_tien")
    private String maPT;
    @Column(name = "ten_loai_phuong_tien")
    private String tenPT;
    @Column(name = "mo_ta_pt")
    private String moTaPT;
}
