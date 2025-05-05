package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller.Admin_NhanVien;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSimpleField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Entity.DoanhThu;
import vn.fpoly.quan_ly_bai_giu_xe_agile.Service.*;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class DoanhThuController {
    @Autowired
    private DoanhThuService doanhThuService;

    @Autowired
    private BaiXeService baiXeService;

    @Autowired
    private QLBaiXeService qlBaixeService;

    @Autowired
    private QLNhanVienService nhanVienService;

    @Autowired
    private ChucVuService chucVuService;

    @Autowired
    private VeXeService qlVeXeService;

    @Autowired
    private LoaiVeService loaiVeService;

    @GetMapping("/ql_doanhThu")
    public String doanhThu(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size) {
        List<DoanhThu> doanhThu = doanhThuService.getAllDoanhThu();
        model.addAttribute("doanhThu", doanhThu);
        model.addAttribute("doanhThuNew", new DoanhThu());
        model.addAttribute("baiXe", baiXeService.getAllBaiXe());
        model.addAttribute("nhanVien", nhanVienService.getAllNhanVien(page, size));
        return "View/page/QL_doanhThu";
    }

    @GetMapping("/bao-cao")
    public String getBaoCaoTheoThangNam(
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            Model model) {
        int effectiveMonth = (month == null) ? LocalDate.now().getMonthValue() : month;
        int effectiveYear = (year == null) ? LocalDate.now().getYear() : year;
        List<DoanhThu> doanhThuList = doanhThuService.getDoanhThuByMonthAndYear(effectiveMonth, effectiveYear);

        model.addAttribute("doanhThu", doanhThuList);
        model.addAttribute("nhanVien", nhanVienService.getAllNhanVien(page, size));
        model.addAttribute("baiXe", baiXeService.getAllBaiXe());
        model.addAttribute("doanhThuNew", new DoanhThu());
        model.addAttribute("selectedMonth", effectiveMonth);
        model.addAttribute("selectedYear", effectiveYear);
        return "View/page/QL_doanhThu";
    }

    @GetMapping("/doanh-thu/detail")
    public String getBaoCaoTheoId(@RequestParam(value = "id") int id, Model model,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "5") int size,
                                  @RequestParam(name = "page", defaultValue = "0") int pageVX,
                                  @RequestParam(name = "size", defaultValue = "10") int sizeVX) {
        List<DoanhThu> doanhThuList = doanhThuService.findDoanhThuById(id);
        model.addAttribute("doanhThu", doanhThuList);
        model.addAttribute("baiXe", baiXeService.getAllBaiXe());
        model.addAttribute("qlBaiXe", qlBaixeService.getAllQLBaiXe());
        model.addAttribute("nhanVien", nhanVienService.getAllNhanVien(page, size));
        model.addAttribute("chucVu", chucVuService.getAllChucVu());
        model.addAttribute("qlVeXe", qlVeXeService.getAllVeXe(pageVX, sizeVX));
        model.addAttribute("loaiVe", loaiVeService.getAllLoaiVe());
        return "View/page/QL_doanhThuDetail";
    }

    @PostMapping("/doanh-thu/add")
    public String addDoanhThu(@ModelAttribute("doanhThuNew") DoanhThu doanhThu) {
        doanhThuService.addDoanhThu(doanhThu);
        return "redirect:/ql_doanhThu";
    }

    @GetMapping("/doanh-thu/export")
    public void exportDoanhThuToWord(@RequestParam("id") int id, HttpServletResponse response) {
        List<DoanhThu> doanhThuList = doanhThuService.findDoanhThuById(id);
        try (XWPFDocument document = new XWPFDocument()) {
            // Set page margins
            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            CTPageMar pageMar = sectPr.addNewPgMar();
            pageMar.setLeft(BigInteger.valueOf(720)); // 0.5 inch
            pageMar.setRight(BigInteger.valueOf(720));
            pageMar.setTop(BigInteger.valueOf(720));
            pageMar.setBottom(BigInteger.valueOf(720));

            // Create header
            XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPr);
            XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
            XWPFParagraph headerParagraph = header.createParagraph();
            headerParagraph.setAlignment(ParagraphAlignment.RIGHT);
            XWPFRun headerRun = headerParagraph.createRun();
            headerRun.setText("BÁO CÁO DOANH THU");
            headerRun.setFontSize(10);
            headerRun.setFontFamily("Times New Roman");

            // Create footer with page number
            XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);
            XWPFParagraph footerParagraph = footer.createParagraph();
            footerParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun footerRun = footerParagraph.createRun();
            footerRun.setText("Trang ");
            footerRun.setFontSize(10);
            footerRun.setFontFamily("Times New Roman");
            CTSimpleField pageField = footerParagraph.getCTP().addNewFldSimple();
            pageField.setInstr("PAGE");
            XWPFRun pageRun = footerParagraph.createRun();
            pageRun.getCTR().addNewRPr().addNewRFonts().setAscii("Times New Roman");
            pageRun.setFontSize(10);

            // Title
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            title.setSpacingAfter(200);
            XWPFRun titleRun = title.createRun();
            titleRun.setText("BÁO CÁO CHI TIẾT DOANH THU");
            titleRun.setBold(true);
            titleRun.setFontSize(18);
            titleRun.setFontFamily("Times New Roman");
            titleRun.addBreak();

            // Subtitle with date
            XWPFParagraph subtitle = document.createParagraph();
            subtitle.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun subtitleRun = subtitle.createRun();
            subtitleRun.setText("Ngày xuất báo cáo: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            subtitleRun.setFontSize(12);
            subtitleRun.setFontFamily("Times New Roman");
            subtitleRun.addBreak();
            subtitleRun.addBreak();

            // Format for currency
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            decimalFormat.setGroupingSize(3);

            if (doanhThuList.isEmpty()) {
                XWPFParagraph noData = document.createParagraph();
                noData.setAlignment(ParagraphAlignment.LEFT);
                XWPFRun noDataRun = noData.createRun();
                noDataRun.setText("Không có dữ liệu cho ID: " + id);
                noDataRun.setFontSize(12);
                noDataRun.setFontFamily("Times New Roman");
            } else {
                // Table 1: Thông Tin Doanh Thu & Bãi Xe
                XWPFParagraph table1Title = document.createParagraph();
                table1Title.setSpacingBefore(200);
                XWPFRun table1TitleRun = table1Title.createRun();
                table1TitleRun.setText("Thông Tin Doanh Thu & Bãi Xe");
                table1TitleRun.setBold(true);
                table1TitleRun.setFontSize(14);
                table1TitleRun.setFontFamily("Times New Roman");

                XWPFTable table1 = document.createTable(doanhThuList.size() + 1, 7);
                table1.setWidth(Units.toEMU(6.5 * 72)); // 6.5 inches wide

                // Table 1 Header
                XWPFTableRow headerRow1 = table1.getRow(0);
                String[] headers1 = {"ID Doanh Thu", "Doanh Thu", "Mã Khu Vực", "Tên Khu Vực", "Sức Chứa", "Địa Chỉ", "Trạng Thái BX"};
                for (int i = 0; i < headers1.length; i++) {
                    XWPFTableCell cell = headerRow1.getCell(i);
                    XWPFParagraph para = cell.addParagraph();
                    XWPFRun run = para.createRun();
                    run.setText(headers1[i]);
                    run.setBold(true);
                    run.setFontSize(12);
                    run.setFontFamily("Times New Roman");
                    para.setAlignment(ParagraphAlignment.CENTER);
                    cell.setColor("E3F2FD");
                }

                // Table 1 Data
                for (int i = 0; i < doanhThuList.size(); i++) {
                    DoanhThu dt = doanhThuList.get(i);
                    XWPFTableRow row = table1.getRow(i + 1);
                    String[] data = {
                            String.valueOf(dt.getId()),
                            decimalFormat.format(dt.getTienDoanhThu()) + " VNĐ",
                            dt.getIdBaiXe() != null ? dt.getIdBaiXe().getMaKhuVuc() : "N/A",
                            dt.getIdBaiXe() != null ? dt.getIdBaiXe().getTenKhuVuc() : "N/A",
                            dt.getIdBaiXe() != null ? String.valueOf(dt.getIdBaiXe().getSucChuaToiDa()) : "N/A",
                            dt.getIdBaiXe() != null ? dt.getIdBaiXe().getDiaChi() : "N/A",
                            dt.getIdBaiXe() != null ? dt.getIdBaiXe().getTrangThai() : "N/A"
                    };
                    for (int j = 0; j < data.length; j++) {
                        XWPFTableCell cell = row.getCell(j);
                        XWPFParagraph para = cell.addParagraph();
                        XWPFRun run = para.createRun();
                        run.setText(data[j]);
                        run.setFontSize(12);
                        run.setFontFamily("Times New Roman");
                        para.setAlignment(ParagraphAlignment.LEFT);
                    }
                }

                // Table 2: Thông Tin Nhân Viên Liên Quan
                XWPFParagraph table2Title = document.createParagraph();
                table2Title.setSpacingBefore(400);
                XWPFRun table2TitleRun = table2Title.createRun();
                table2TitleRun.setText("Thông Tin Nhân Viên Liên Quan");
                table2TitleRun.setBold(true);
                table2TitleRun.setFontSize(14);
                table2TitleRun.setFontFamily("Times New Roman");

                XWPFTable table2 = document.createTable(doanhThuList.size() + 1, 9);
                table2.setWidth(Units.toEMU(6.5 * 72)); // 6.5 inches wide

                // Table 2 Header
                XWPFTableRow headerRow2 = table2.getRow(0);
                String[] headers2 = {"ID Doanh Thu", "Mã NV", "Tên NV", "SĐT", "Email", "Chức Vụ", "Ảnh NV", "Ngày Bắt Đầu", "Trạng Thái NV"};
                for (int i = 0; i < headers2.length; i++) {
                    XWPFTableCell cell = headerRow2.getCell(i);
                    XWPFParagraph para = cell.addParagraph();
                    XWPFRun run = para.createRun();
                    run.setText(headers2[i]);
                    run.setBold(true);
                    run.setFontSize(12);
                    run.setFontFamily("Times New Roman");
                    para.setAlignment(ParagraphAlignment.CENTER);
                    cell.setColor("E3F2FD");
                }

                // Table 2 Data
                for (int i = 0; i < doanhThuList.size(); i++) {
                    DoanhThu dt = doanhThuList.get(i);
                    XWPFTableRow row = table2.getRow(i + 1);
                    String startDate = dt.getIdNhanVien() != null && dt.getIdNhanVien().getThoiGianBatDauLam() != null ?
                            dt.getIdNhanVien().getThoiGianBatDauLam().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A";
                    String[] data = {
                            String.valueOf(dt.getId()),
                            dt.getIdNhanVien() != null ? dt.getIdNhanVien().getMaNV() : "N/A",
                            dt.getIdNhanVien() != null ? dt.getIdNhanVien().getHoTenNV() : "N/A",
                            dt.getIdNhanVien() != null ? dt.getIdNhanVien().getSdt() : "N/A",
                            dt.getIdNhanVien() != null ? dt.getIdNhanVien().getEmail() : "N/A",
                            dt.getIdNhanVien() != null && dt.getIdNhanVien().getChucvu() != null ? dt.getIdNhanVien().getChucvu().getTenChucVu() : "N/A",
                            dt.getIdNhanVien() != null && dt.getIdNhanVien().getAnhNV() != null ? "[Ảnh nhân viên]" : "Không có ảnh",
                            startDate,
                            dt.getIdNhanVien() != null ? dt.getIdNhanVien().getTrangThai() : "N/A"
                    };
                    for (int j = 0; j < data.length; j++) {
                        XWPFTableCell cell = row.getCell(j);
                        XWPFParagraph para = cell.addParagraph();
                        XWPFRun run = para.createRun();
                        run.setText(data[j]);
                        run.setFontSize(12);
                        run.setFontFamily("Times New Roman");
                        para.setAlignment(ParagraphAlignment.LEFT);
                    }
                }

                // Summary paragraph
                XWPFParagraph summary = document.createParagraph();
                summary.setSpacingBefore(400);
                XWPFRun summaryRun = summary.createRun();
                summaryRun.setText("Tổng số bản ghi: " + doanhThuList.size());
                summaryRun.setFontSize(12);
                summaryRun.setFontFamily("Times New Roman");
                summaryRun.setItalic(true);
            }

            // Stream the file
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=bao-cao-doanh-thu-" + id + ".docx");
            document.write(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("❌ Lỗi khi tạo file Word: " + e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @GetMapping("/doanh-thu/export-excel")
    public void exportDoanhThuToExcel(@RequestParam("id") int id, HttpServletResponse response) {
        List<DoanhThu> doanhThuList = doanhThuService.findDoanhThuById(id);
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        decimalFormat.setGroupingSize(3);

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("ChiTietDoanhThu");

            // Create fonts
            XSSFFont font = workbook.createFont();
            font.setFontName("Times New Roman");
            font.setFontHeightInPoints((short) 12);

            XSSFFont titleFont = workbook.createFont();
            titleFont.setFontName("Times New Roman");
            titleFont.setFontHeightInPoints((short) 16);
            titleFont.setBold(true);

            XSSFFont sectionTitleFont = workbook.createFont();
            sectionTitleFont.setFontName("Times New Roman");
            sectionTitleFont.setFontHeightInPoints((short) 14);
            sectionTitleFont.setBold(true);

            // Create cell styles
            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle dateStyle = workbook.createCellStyle();
            dateStyle.setFont(font);
            dateStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(font);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setFont(font);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);

            CellStyle sectionTitleStyle = workbook.createCellStyle();
            sectionTitleStyle.setFont(sectionTitleFont);
            sectionTitleStyle.setAlignment(HorizontalAlignment.LEFT);

            // Title row
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("BÁO CÁO CHI TIẾT DOANH THU");
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13)); // Merge 14 columns

            // Date row
            Row dateRow = sheet.createRow(1);
            Cell dateCell = dateRow.createCell(0);
            dateCell.setCellValue("Ngày xuất báo cáo: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            dateCell.setCellStyle(dateStyle);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 13));

            if (doanhThuList.isEmpty()) {
                Row noDataRow = sheet.createRow(3);
                Cell noDataCell = noDataRow.createCell(0);
                noDataCell.setCellValue("Không có dữ liệu cho ID: " + id);
                noDataCell.setCellStyle(dataStyle);
                sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 13));
            } else {
                // Table 1: Thông Tin Doanh Thu & Bãi Xe
                Row table1TitleRow = sheet.createRow(3);
                Cell table1TitleCell = table1TitleRow.createCell(0);
                table1TitleCell.setCellValue("Thông Tin Doanh Thu & Bãi Xe");
                table1TitleCell.setCellStyle(sectionTitleStyle);
                sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));

                // Table 1 Headers
                String[] table1Headers = {
                        "ID Doanh Thu", "Doanh Thu", "Mã Khu Vực", "Tên Khu Vực",
                        "Sức Chứa", "Địa Chỉ", "Trạng Thái BX"
                };
                Row table1HeaderRow = sheet.createRow(5);
                for (int i = 0; i < table1Headers.length; i++) {
                    Cell cell = table1HeaderRow.createCell(i);
                    cell.setCellValue(table1Headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // Table 1 Data
                int rowNum = 6;
                for (DoanhThu dt : doanhThuList) {
                    Row row = sheet.createRow(rowNum++);
                    int col = 0;
                    row.createCell(col).setCellValue(String.valueOf(dt.getId()));
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(decimalFormat.format(dt.getTienDoanhThu()) + " VNĐ");
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(dt.getIdBaiXe() != null ? dt.getIdBaiXe().getMaKhuVuc() : "N/A");
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(dt.getIdBaiXe() != null ? dt.getIdBaiXe().getTenKhuVuc() : "N/A");
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(dt.getIdBaiXe() != null ? String.valueOf(dt.getIdBaiXe().getSucChuaToiDa()) : "N/A");
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(dt.getIdBaiXe() != null ? dt.getIdBaiXe().getDiaChi() : "N/A");
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(dt.getIdBaiXe() != null ? dt.getIdBaiXe().getTrangThai() : "N/A");
                    row.getCell(col).setCellStyle(dataStyle);
                }

                // Table 2: Thông Tin Nhân Viên Liên Quan
                Row table2TitleRow = sheet.createRow(rowNum + 1);
                Cell table2TitleCell = table2TitleRow.createCell(0);
                table2TitleCell.setCellValue("Thông Tin Nhân Viên Liên Quan");
                table2TitleCell.setCellStyle(sectionTitleStyle);
                sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 0, 8));

                // Table 2 Headers
                String[] table2Headers = {
                        "ID Doanh Thu", "Mã NV", "Tên NV", "SĐT", "Email",
                        "Chức Vụ", "Ảnh NV", "Ngày Bắt Đầu", "Trạng Thái NV"
                };
                Row table2HeaderRow = sheet.createRow(rowNum + 3);
                for (int i = 0; i < table2Headers.length; i++) {
                    Cell cell = table2HeaderRow.createCell(i);
                    cell.setCellValue(table2Headers[i]);
                    cell.setCellStyle(headerStyle);
                }

                // Table 2 Data
                rowNum += 4;
                for (DoanhThu dt : doanhThuList) {
                    Row row = sheet.createRow(rowNum++);
                    int col = 0;
                    row.createCell(col).setCellValue(String.valueOf(dt.getId()));
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(dt.getIdNhanVien() != null ? dt.getIdNhanVien().getMaNV() : "N/A");
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(dt.getIdNhanVien() != null ? dt.getIdNhanVien().getHoTenNV() : "N/A");
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(dt.getIdNhanVien() != null ? dt.getIdNhanVien().getSdt() : "N/A");
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(dt.getIdNhanVien() != null ? dt.getIdNhanVien().getEmail() : "N/A");
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(dt.getIdNhanVien() != null && dt.getIdNhanVien().getChucvu() != null ? dt.getIdNhanVien().getChucvu().getTenChucVu() : "N/A");
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(dt.getIdNhanVien() != null && dt.getIdNhanVien().getAnhNV() != null ? "[Ảnh nhân viên]" : "Không có ảnh");
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(
                            dt.getIdNhanVien() != null && dt.getIdNhanVien().getThoiGianBatDauLam() != null ?
                                    dt.getIdNhanVien().getThoiGianBatDauLam().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "N/A"
                    );
                    row.getCell(col).setCellStyle(dataStyle);
                    row.createCell(++col).setCellValue(dt.getIdNhanVien() != null ? dt.getIdNhanVien().getTrangThai() : "N/A");
                    row.getCell(col).setCellStyle(dataStyle);
                }

                // Summary row
                Row summaryRow = sheet.createRow(rowNum + 1);
                Cell summaryCell = summaryRow.createCell(0);
                summaryCell.setCellValue("Tổng số bản ghi: " + doanhThuList.size());
                summaryCell.setCellStyle(dataStyle);
                sheet.addMergedRegion(new CellRangeAddress(rowNum + 1, rowNum + 1, 0, 13));
            }

            // Auto size columns
            for (int i = 0; i < 14; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to response
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=bao-cao-doanh-thu-" + id + ".xlsx");
            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("❌ Lỗi khi tạo file Excel: " + e.getMessage());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
