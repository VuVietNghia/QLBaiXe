package vn.fpoly.quan_ly_bai_giu_xe_agile.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "ql_phuong_tien")
public class QlPhuongTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_loai_pt")
    @NotNull(message = "Loại phương tiện không được trống!")
    private LoaiPhuongTien loaiPT;

    @ManyToOne
    @JoinColumn(name = "id_ve_xe")
    @NotNull(message = "Không được bỏ trống!")
    private QlVeXe qlvexe;

    @Column(name = "hang_xe")
    @NotBlank(message = "Hãng xe không được trống!")
    private String hangXe;
    @Column(name = "anh_phuong_tien")
    private String anhPT;
}
