package vn.fpoly.quan_ly_bai_giu_xe_agile.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "ho_tro")
public class HoTro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ma_ho_tro")
    private String maHoTro = "HTKH";

    @Column(name = "ho_ten_nguoi_can_ho_tro")
    @NotBlank(message = "Họ tên không được trống!")
    private String hoTenNguoiHoTro;

    @Column(name = "sdt")
    @NotBlank(message = "Số điện thoại không được trống!")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải gồm đúng 10 chữ số!")
    private String sdt;

    @Column(name = "email")
    @NotBlank(message = "Enail không được trống!")
    private String email;

    @Column(name = "noi_dung")
    @NotBlank(message = "Nội dung không được trống!")
    private String noiDung;

    @Column(name = "trang_thai_ho_tro")
    private Integer trangThaiHoTro = 3;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    @NotNull(message = "Khách hành không được trống!")
    private KhachHang idKhachHang;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    @NotNull(message = "Nhân viên không được trống!")
    private NhanVien idNhanVien;
}
