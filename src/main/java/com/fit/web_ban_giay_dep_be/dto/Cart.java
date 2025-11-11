package com.fit.web_ban_giay_dep_be.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItemDTO> items = new ArrayList<>();

    public Cart() {}

    public Cart(List<CartItemDTO> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getGiaBan() * item.getSoLuong())
                .sum();
    }

    public void addItem(CartItemDTO newItem) {
        boolean found = false;
        for (CartItemDTO item : items) {
            if (item.getMaChiTiet().equals(newItem.getMaChiTiet())) {
                item.setSoLuong(item.getSoLuong() + newItem.getSoLuong());
                found = true;
                break;
            }
        }
        if (!found) {
            items.add(newItem);
        }
    }

    public void updateQuantity(String maChiTiet, int soLuong) {
        for (CartItemDTO item : items) {
            if (item.getMaChiTiet().equals(maChiTiet)) {
                item.setSoLuong(soLuong);
                break;
            }
        }
    }

    public void removeItem(String maChiTiet) {
        items.removeIf(item -> item.getMaChiTiet().equals(maChiTiet));
    }

    public void clear() {
        items.clear();
    }
}
