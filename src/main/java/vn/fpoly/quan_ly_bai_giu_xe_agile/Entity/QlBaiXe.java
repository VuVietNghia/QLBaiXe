package vn.fpoly.quan_ly_bai_giu_xe_agile.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Getter
@Setter
@Entity
@Table(name = "ql_bai_xe")
public class QlBaiXe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_bai_xe")
    private BaiXe idBaiXe;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien idNhanVien;

    @Column(name = "sl_xe_trong_bai")
    private Integer slXeTrongBai;

    @Column(name = "sl_cho_trong")
    private Integer slChoTrong;

    @Size(max = 20)
    @Nationalized
    @Column(name = "trang_thai_bai", length = 20)
    private String trangThaiBai;

}