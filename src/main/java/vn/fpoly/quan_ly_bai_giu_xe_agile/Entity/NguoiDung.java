package vn.fpoly.quan_ly_bai_giu_xe_agile.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "nguoi_dung")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    @NotBlank(message = "Tên đăng nhập không được bỏ trống!")
    private String userName;
    @Column(name = "password")
    @NotBlank(message = "Mật khẩu không được bỏ trống!")
    private String password;
    @Column(name = "fullname")
    @NotBlank(message = "Họ & tên không được bỏ trống!")
    private String fullName;
    @Column(name = "email")
    @NotBlank(message = "Email không được bỏ trống!")
    private String email;
    @Column(name = "vai_tro")
    @NotBlank(message = "Vai trò không được bỏ trống!")
    private String vaiTro = "Khách hàng";
    @Column(name = "ngay_dang_ky")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ngayDangKy = new Date();
    @Column(name = "mo_ta")
    private String moTa = "Người dùng";
}
