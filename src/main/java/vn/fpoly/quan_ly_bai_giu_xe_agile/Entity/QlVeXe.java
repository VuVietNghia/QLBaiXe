package vn.fpoly.quan_ly_bai_giu_xe_agile.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "ql_ve_xe")
public class QlVeXe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "ma_ve")
    @NotBlank(message = "Mã vé không được để trống!")
    private String maVe = "VKH";
    @Column(name = "ten_khach_hang")
    @NotBlank(message = "Tên khách hàng không được để trống!")
    private String tenKH;

    @ManyToOne
    @JoinColumn(name = "id_loai_ve")
    @NotNull(message = "Loại vé không được để trống!")
    private LoaiVe idLoaiVe;


    @Column(name = "bien_so_xe")
    @NotBlank(message = "Biển số xe không được để trống!")
    private String bienSoXe;


    @Column(name = "don_gia")
    private Float donGia;

    @Column(name = "ngay_dang_ky")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayDangKy = new Date();

    @Column(name = "ngay_het_han")
    @NotNull(message = "Ngày hết hạn không được để trống!")
    @FutureOrPresent(message = "Ngày hết hạn phải là hôm nay hoặc sau này")
    private LocalDate ngayHetHan;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_ql_bai")
    @NotNull(message = "Khu vực không được để trống!")
    private QlBaiXe idQlBai;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_loai_pt")
    @NotNull(message = "Loại xe không được để trống!")
    private LoaiPhuongTien loaiPT;

    @Column(name = "trang_thai_ve")
    @Builder.Default
    private String trangThaiVe = "Còn hạn";
}