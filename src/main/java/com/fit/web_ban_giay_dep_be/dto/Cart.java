package com.fit.web_ban_giay_dep_be.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItemDTO> items = new ArrayList<>();
    private String maKhuyenMai;
    private int diemSuDung;
    private double totalPrice;

    public Cart() {}

    public Cart(List<CartItemDTO> items) {
        this.items = items;
    }

    // --- Tính tổng tiền sau khi trừ khuyến mãi/điểm ---
    public double getTotalPrice() {
        double sum = items.stream()
                .mapToDouble(item -> item.getGiaBan() * item.getSoLuong())
                .sum();
        // trừ điểm sử dụng (1 điểm = 1đ) giả sử
        sum -= diemSuDung;
        if (sum < 0) sum = 0;
        return sum;
    }

    // --- Thêm sản phẩm vào giỏ hàng ---
    public void addItem(CartItemDTO newItem, int soLuongTonKho) {
        boolean found = false;
        for (CartItemDTO item : items) {
            if (item.getMaChiTiet().equals(newItem.getMaChiTiet())) {
                // không vượt quá tồn kho
                int updatedQty = item.getSoLuong() + newItem.getSoLuong();
                item.setSoLuong(Math.min(updatedQty, soLuongTonKho));
                found = true;
                break;
            }
        }
        if (!found) {
            // nếu thêm mới, set số lượng không vượt tồn kho
            newItem.setSoLuong(Math.min(newItem.getSoLuong(), soLuongTonKho));
            items.add(newItem);
        }
    }

    public void updateQuantity(String maChiTiet, int soLuong, int soLuongTonKho) {
        for (CartItemDTO item : items) {
            if (item.getMaChiTiet().equals(maChiTiet)) {
                item.setSoLuong(Math.min(soLuong, soLuongTonKho));
                break;
            }
        }
    }

    public void removeItem(String maChiTiet) {
        items.removeIf(item -> item.getMaChiTiet().equals(maChiTiet));
    }

    public void clear() {
        items.clear();
        maKhuyenMai = null;
        diemSuDung = 0;
    }
}
