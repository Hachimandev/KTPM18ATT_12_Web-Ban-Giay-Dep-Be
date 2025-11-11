package com.fit.web_ban_giay_dep_be.controller;

import com.fit.web_ban_giay_dep_be.dto.Cart;
import com.fit.web_ban_giay_dep_be.dto.CartItemDTO;
import com.fit.web_ban_giay_dep_be.entity.ChiTietSanPham;
import com.fit.web_ban_giay_dep_be.service.ChiTietSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final ChiTietSanPhamService chiTietSanPhamService;


    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody CartItemDTO item,
                                          @RequestParam(required = false) Cart cart) {
        if (cart == null) cart = new Cart();

        ChiTietSanPham chiTiet = chiTietSanPhamService.getChiTietSanPhamById(item.getMaChiTiet())
                .orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại"));

        item.setGiaBan(chiTiet.getSanPham().getGiaBan());
        item.setTenSanPham(chiTiet.getSanPham().getTenSanPham());

        cart.addItem(item, chiTiet.getSoLuongTonKho());
        cart.setTotalPrice(cart.getTotalPrice()); // cập nhật tổng tiền
        return ResponseEntity.ok(cart);
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @PostMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(@RequestBody CartItemDTO item,
                                               @RequestParam Cart cart) {
        cart.removeItem(item.getMaChiTiet());
        cart.setTotalPrice(cart.getTotalPrice());
        return ResponseEntity.ok(cart);
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    @PostMapping("/update-quantity")
    public ResponseEntity<Cart> updateQuantity(@RequestBody CartItemDTO item,
                                               @RequestParam Cart cart) {
        ChiTietSanPham chiTiet = chiTietSanPhamService.getChiTietSanPhamById(item.getMaChiTiet())
                .orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại"));

        cart.updateQuantity(item.getMaChiTiet(), item.getSoLuong(), chiTiet.getSoLuongTonKho());
        cart.setTotalPrice(cart.getTotalPrice());
        return ResponseEntity.ok(cart);
    }

    // Áp dụng mã khuyến mãi
    @PostMapping("/apply-promo")
    public ResponseEntity<Cart> applyPromo(@RequestParam String maKhuyenMai,
                                           @RequestParam Cart cart) {
        cart.setMaKhuyenMai(maKhuyenMai);
        cart.setTotalPrice(cart.getTotalPrice());
        return ResponseEntity.ok(cart);
    }

    // Sử dụng điểm tích lũy
    @PostMapping("/use-points")
    public ResponseEntity<Cart> usePoints(@RequestParam int diem,
                                          @RequestParam Cart cart) {
        cart.setDiemSuDung(diem);
        cart.setTotalPrice(cart.getTotalPrice());
        return ResponseEntity.ok(cart);
    }

    // Xóa toàn bộ giỏ hàng
    @PostMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestParam Cart cart) {
        cart.clear();
        return ResponseEntity.ok(cart);
    }

    // Lấy tổng tiền giỏ hàng
    @PostMapping("/total")
    public ResponseEntity<Double> getTotalPrice(@RequestParam Cart cart) {
        return ResponseEntity.ok(cart.getTotalPrice());
    }
}
