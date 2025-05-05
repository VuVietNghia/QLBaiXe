package vn.fpoly.quan_ly_bai_giu_xe_agile.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name = "ql_nhan_vien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "ma_nhan_vien")
    @NotBlank(message = "Mã nhân viên không được bỏ trống!")
    private String maNV;
    @Column(name = "ho_ten_nhan_vien")
    @NotBlank(message = "Họ tên nhân viên không được bỏ trống!")
    private String hoTenNV;
    @Column(name = "ngay_sinh")
    @NotNull(message = "Ngày sinh nhân viên không được bỏ trống!")
    @PastOrPresent(message = "Ngày sinh phải trước hiện tại!")
    private LocalDate ngaySinh;
    @Column(name = "gioi_tinh")
    @NotBlank(message = "Giới tính không được bỏ trống!")
    private String gioiTinh;
    @Column(name = "sdt")
    @NotBlank(message = "Số điện thoại không được bỏ trống!")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải gồm đúng 10 chữ số!")
    private String sdt;
    @Column(name = "email")
    @NotBlank(message = "Email không được bỏ trống!")
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_chuc_vu")
    private ChucVu chucvu;

    @Column(name = "anh_nhan_vien")
    private String anhNV;
    @Column(name = "thoi_gian_bat_dau_lam")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate thoiGianBatDauLam = LocalDate.now();
    @Column(name = "trang_thai")
    private String trangThai = "Đang làm việc";

}
