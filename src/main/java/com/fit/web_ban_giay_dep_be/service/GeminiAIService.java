package com.fit.web_ban_giay_dep_be.service;

import com.fit.web_ban_giay_dep_be.entity.ChiTietSanPham;
import com.fit.web_ban_giay_dep_be.entity.SanPham;
import com.fit.web_ban_giay_dep_be.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeminiAIService {

    private final ChatClient chatClient;
    // Giả định bạn có SanPhamRepository (cần đảm bảo nó tải ChiTietSanPham và LoaiSanPham)
    private final SanPhamRepository sanPhamRepo;

    public String ask(String prompt) {

        // 1. Lấy dữ liệu sản phẩm (Cần đảm bảo FETCH JOIN để lấy ChiTietSanPham và LoaiSanPham)
        // Lưu ý: findAll() có thể không tải Lazy-Loaded entities.
        // Cần dùng truy vấn custom trong Repository để FETCH JOIN KhachHang, LoaiSanPham, ChiTietSanPham
        List<SanPham> allProducts = sanPhamRepo.findAll();

        // 2. Định dạng Context Sản phẩm chi tiết
        String productContext = allProducts.stream()
                .map(p -> {
                    // Định dạng Chi tiết Sản phẩm (Size, Màu, Tồn kho)
                    String details = p.getChiTietSanPhams().stream()
                            .map(ctsp -> String.format(
                                    "[Size: %s, Màu: %s, Tồn: %d]",
                                    ctsp.getSize(),
                                    ctsp.getMau(),
                                    ctsp.getSoLuongTonKho()
                            ))
                            .collect(Collectors.joining("; "));

                    return String.format(
                            "ID: %s | Tên: %s | Giá: %s VNĐ | Loại: %s | Chi tiết tồn kho: {%s}",
                            p.getMaSanPham(),
                            p.getTenSanPham(),
                            String.format("%.0f", p.getGiaBan()),
                            p.getLoaiSanPham() != null ? p.getLoaiSanPham().getTenLoai() : "Không rõ",
                            details
                    );
                })
                .reduce("", (a, b) -> a + "\n" + b);

        // 3. Xây dựng System Prompt (Huấn luyện Mô hình)
        String systemPrompt = """
        Bạn là chatbot tư vấn thông minh cho cửa hàng bán giày dép thời trang.
        
        Dưới đây là danh sách sản phẩm chi tiết hiện có trong kho:
        %s
        
        ---
        NGUYÊN TẮC:
        1. Trả lời CHÍNH XÁC chỉ dựa trên dữ liệu được cung cấp.
        2. [Chi tiết] Nếu người dùng hỏi về SIZE/MÀU/TỒN KHO, hãy trích xuất thông tin từ phần 'Chi tiết tồn kho'.
        3. [Lọc] Nếu người dùng hỏi theo LOẠI (ví dụ: "giày sneaker", "dép sandal"), hãy liệt kê các sản phẩm thuộc loại đó.
        4. [So sánh/Giá] Nếu người dùng hỏi về giá, hãy so sánh giá trị 'Giá' và sử dụng định dạng tiền tệ Việt Nam.
        5. [Tồn kho] Chỉ trả lời "Còn hàng" nếu có ít nhất 1 biến thể (size/màu) có số lượng tồn kho > 0.
        6. [Không có] Nếu không tìm thấy sản phẩm hoặc thông tin không có trong danh sách, hãy trả lời lịch sự rằng cửa hàng không có sản phẩm đó.
        7. [Giới hạn] TUYỆT ĐỐI KHÔNG trả lời các câu hỏi về tài khoản, hóa đơn, hoặc thông tin cá nhân.
        ---
        Khách hàng hỏi: %s
        """.formatted(productContext, prompt);

        // 4. Gọi API Gemini
        return chatClient
                .prompt(systemPrompt)
                .call()
                .content();
    }
}