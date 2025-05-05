package vn.fpoly.quan_ly_bai_giu_xe_agile.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Data
@Getter
@Setter
@Entity
@Table(name = "bai_xe")
public class BaiXe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "ma_khu_vuc", length = 20)
    private String maKhuVuc;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ten_khu_vuc", length = 50)
    private String tenKhuVuc;

    @Column(name = "suc_chua_toi_da")
    private Integer sucChuaToiDa;

    @Size(max = 50)
    @Nationalized
    @Column(name = "trang_thai", length = 50)
    private String trangThai;


    @Size(max = 255)
    @Nationalized
    @Column(name = "dia_chi")
    private String diaChi;

}