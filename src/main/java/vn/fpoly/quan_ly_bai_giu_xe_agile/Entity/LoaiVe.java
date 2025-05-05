package vn.fpoly.quan_ly_bai_giu_xe_agile.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@Entity
@Table(name = "loai_ve")
public class LoaiVe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @Column(name = "ma_loai_ve", length = 20)
    private String maLoaiVe;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ten_loai_ve", length = 50)
    private String tenLoaiVe;

    @Size(max = 50)
    @Nationalized
    @Column(name = "trang_thai", length = 50)
    private String trangThai;

}