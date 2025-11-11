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

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody CartItemDTO item,
                                          @RequestBody(required = false) Cart cart) {
        if (cart == null) cart = new Cart();

        // Lấy chi tiết sản phẩm từ DB để lấy tên, giá
        ChiTietSanPham chiTiet = chiTietSanPhamService.getChiTietSanPhamById(item.getMaChiTiet())
                .orElseThrow(() -> new RuntimeException("Chi tiết sản phẩm không tồn tại"));

        item.setGiaBan(chiTiet.getSanPham().getGiaBan());
        item.setTenSanPham(chiTiet.getSanPham().getTenSanPham());

        cart.addItem(item);
        return ResponseEntity.ok(cart);
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    @PostMapping("/remove")
    public ResponseEntity<Cart> removeFromCart(@RequestBody CartItemDTO item,
                                               @RequestBody Cart cart) {
        cart.removeItem(item.getMaChiTiet());
        return ResponseEntity.ok(cart);
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng
     */
    @PostMapping("/update-quantity")
    public ResponseEntity<Cart> updateQuantity(@RequestBody CartItemDTO item,
                                               @RequestBody Cart cart) {
        cart.updateQuantity(item.getMaChiTiet(), item.getSoLuong());
        return ResponseEntity.ok(cart);
    }

    /**
     * Xóa toàn bộ giỏ hàng
     */
    @PostMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestBody Cart cart) {
        cart.clear();
        return ResponseEntity.ok(cart);
    }

    /**
     * Lấy tổng tiền giỏ hàng
     */
    @PostMapping("/total")
    public ResponseEntity<Double> getTotalPrice(@RequestBody Cart cart) {
        return ResponseEntity.ok(cart.getTotalPrice());
    }
}
