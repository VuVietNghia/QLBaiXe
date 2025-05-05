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
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "ql_khach_hang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "ma_khach_hang")
    @NotBlank(message = "Mã khách hàng không được bỏ trống!")
    private String maKH;
    @Column(name = "ho_ten_khach_hang")
    @NotBlank(message = "Họ tên khách hàng không được bỏ trống!")
    private String hoTen;
    @Column(name = "so_cccd")
    @NotBlank(message = "Số CCCD không được bỏ trống!")
    private String cccd;
    @Column(name = "ngay_sinh")
    @NotNull(message = "Ngày sinh không được bỏ trống!")
    @PastOrPresent(message = "Ngày sinh phải trước hiện tại!")
    private LocalDate ngaySinh;
    @Column(name = "sdt_kh")
    @NotBlank(message = "Số điện thoại không được bỏ trống!")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải gồm đúng 10 chữ số!")
    private String sdt;
    @Column(name = "email")
    @NotBlank(message = "Email không được bỏ trống!")
    private String email;


    @Column(name = "ngay_dang_ky")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayDangKy = new Date();
    @Column(name = "trang_thai")
    @NotBlank(message = "Trạng thái không được bỏ trống!")
    private String trangThai;

}
